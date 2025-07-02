package otee.dev.swipe.service;

import org.springframework.stereotype.Service;
import otee.dev.swipe.dto.TransactionDtos;
import otee.dev.swipe.model.*;
import otee.dev.swipe.util.ServiceResponse;

import java.util.*;

@Service
public class TransactionService {
    private ExpenseRepository expenseRepository;
    private ExpenseSplitRepository expenseSplitRepository;
    private UserRepository userRepository;
    private GroupRepository groupRepository;
    private GroupMemberRepository groupMemberRepository;

    public TransactionService(ExpenseRepository expenseRepository, UserRepository userRepository, GroupRepository groupRepository, GroupMemberRepository groupMemberRepository, ExpenseSplitRepository expenseSplitRepository){
        this.expenseRepository = expenseRepository;
        this.expenseSplitRepository = expenseSplitRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
    }
    private void addExpenseSplitForOne(Long expenseId, Long userId, Double amount){
        ExpenseSplit expenseSplit = new ExpenseSplit(expenseId, userId, amount);
        expenseSplitRepository.save(expenseSplit);
    }
    private void addExpenseSplitsForAll(Long expenseId, Long groupId, Double amount){
        List<GroupMember> members = groupMemberRepository.findByGroupId(groupId);
        List<Long> userIds = members.stream().map(member -> member.getUserId()).toList();
        Double perUserShare = amount / userIds.size();
        userIds.forEach(userId -> addExpenseSplitForOne(expenseId, userId, perUserShare));
    }
    public TransactionDtos.DefaultDto addExpense(Long groupId, String username, Double amount, String description){
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Group> group = groupRepository.findById(groupId);
        if(user.isEmpty()){
            return new TransactionDtos.DefaultDto(false, "User does not exist");
        }
        if(group.isEmpty()){
            return new TransactionDtos.DefaultDto(false, "Group does not exist");
        }
        Optional<GroupMember> groupMember = groupMemberRepository.findByUserIdAndGroupId(user.get().getId(), group.get().getId());
        if(groupMember.isEmpty()){
            return new TransactionDtos.DefaultDto(false, "User is not part of the group");
        }
        Expense expense = new Expense(groupId, user.get().getId(), amount, description);
        Expense expenseRes = expenseRepository.save(expense);
        addExpenseSplitsForAll(expenseRes.getId(), expenseRes.getGroupId(), expenseRes.getAmount());
        return new TransactionDtos.DefaultDto(true, "Expense saved successfully", group.get().getId());
    }
    private Double getTotalExpensesByAUser(Long userId, List<Expense> groupExpenses){
        if(groupExpenses.isEmpty()){
            return 0D;
        }
        List<Expense> expenses = groupExpenses
                .stream()
                .filter(exp -> Objects.equals(exp.getPaidBy(), userId))
                .toList();
        Double total = 0D;
        for (Expense exp : expenses) total = total + exp.getAmount();
        return total;
    }
    private Double getTotalExpensesOwedByAUser(Long userId, List<Expense> groupExpenses){
        if(groupExpenses.isEmpty()){
            return 0D;
        }
        List<Long> expenseIds = groupExpenses
                .stream()
                .map(exp -> exp.getId())
                .toList();
        if(expenseIds.isEmpty()){
            return 0D;
        }
        List<ExpenseSplit> expenseSplits = expenseSplitRepository.findAllByUserIdAndExpenseIds(userId, expenseIds);
        Double total = 0D;
        for(ExpenseSplit expSplit : expenseSplits) total = total + expSplit.getAmount();
        return total;
    }
    public TransactionDtos.UserDuesDto getTransactionStatusForAUser(Long groupId, String username){
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Group> group = groupRepository.findById(groupId);
        if(user.isEmpty()){
            return new TransactionDtos.UserDuesDto(false, "User does not exist");
        }
        if(group.isEmpty()){
            return new TransactionDtos.UserDuesDto(false, "Group does not exist");
        }
        List<Expense> groupExpenses = expenseRepository.findAllByGroupId(group.get().getId());
        Double totalExpensesIncurred = getTotalExpensesByAUser(user.get().getId(), groupExpenses);
        Double totalExpensesOwed = getTotalExpensesOwedByAUser(user.get().getId(), groupExpenses);
        Double dues = totalExpensesIncurred - totalExpensesOwed;
        return new TransactionDtos.UserDuesDto(user.get().getUsername(), totalExpensesIncurred, dues);
    }
    private List<GroupMemberBalance> getGroupMembersBalances(Group group, Boolean addUsername){
        List<Expense> groupExpenses = expenseRepository.findAllByGroupId(group.getId());
        List<Long> groupUserIds = groupMemberRepository
                .findByGroupId(group.getId())
                .stream()
                .map(grpMember -> grpMember.getUserId())
                .toList();
        List<GroupMemberBalance> membersBalances = new ArrayList<>();
        GroupMemberBalance member;
        for(Long groupUserId : groupUserIds){
            Double totalExpensesIncurred = getTotalExpensesByAUser(groupUserId, groupExpenses);
            Double totalExpensesOwed = getTotalExpensesOwedByAUser(groupUserId, groupExpenses);
            Double dues = totalExpensesIncurred - totalExpensesOwed;
            if(addUsername){
                member = new GroupMemberBalance(groupUserId, dues, userRepository.findById(groupUserId).get().getUsername());
            } else{
                member = new GroupMemberBalance(groupUserId, dues);
            }
            membersBalances.add(member);
        }
        return membersBalances;
    }
    public TransactionDtos.GroupDuesDto getTransactionStatusForGroup(Long groupId){
        Optional<Group> group = groupRepository.findById(groupId);
        if(group.isEmpty()){
            return new TransactionDtos.GroupDuesDto(false, "Group does not exist");
        }
        List<GroupMemberBalance> memberBalances = getGroupMembersBalances(group.get(), true);

        TransactionDtos.GroupDuesDto res = new TransactionDtos.GroupDuesDto();
        for(GroupMemberBalance member : memberBalances){
            res.addDue(member.getUsername(), member.getBalance());
        }
        return res;
    }

    public TransactionDtos.SettlementDto getSettlementsForGroup(Long groupId){
        Optional<Group> group = groupRepository.findById(groupId);
        if(group.isEmpty()){
            return new TransactionDtos.SettlementDto(false, "Group does not exist");
        }
        List<GroupMemberBalance> memberBalances = getGroupMembersBalances(group.get(), true);
        GroupMemberPriority creditors = new GroupMemberPriority(false);
        GroupMemberPriority debtors = new GroupMemberPriority(true);
        for(GroupMemberBalance member : memberBalances){
            if(member.getBalance() > 0) creditors.addMember(member.getUserId(), member.getBalance(), member.getUsername());
            else if(member.getBalance() < 0) debtors.addMember(member.getUserId(), member.getBalance(), member.getUsername());
        }
        List<SettlementTransaction> settlements = new ArrayList<>();
        while(!(creditors.isEmpty() || debtors.isEmpty())){
            GroupMemberBalance creditor = creditors.getMember();
            GroupMemberBalance debtor = debtors.getMember();
            Double debtAmount = Math.min(Math.abs(creditor.getBalance()), Math.abs(debtor.getBalance()));
            Double updatedDebtorBalance = debtor.getBalance() + debtAmount;
            Double updatedCreditorBalance = creditor.getBalance() - debtAmount;
            SettlementTransaction settlement = new SettlementTransaction(debtor.getUsername(), creditor.getUsername(), debtAmount);
            settlements.add(settlement);
            creditors.updateMemberBalance(creditor.getUserId(), updatedCreditorBalance);
            debtors.updateMemberBalance(debtor.getUserId(), updatedDebtorBalance);
        }
        return new TransactionDtos.SettlementDto(settlements, group.get().getId());
    }
}
