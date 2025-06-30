package otee.dev.swipe.service;

import org.springframework.stereotype.Service;
import otee.dev.swipe.model.*;
import otee.dev.swipe.util.ServiceResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public Map<String, String> addExpense(Long groupId, String username, Double amount, String description){
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Group> group = groupRepository.findById(groupId);
        if(user.isEmpty()){
            ServiceResponse.defaultResponse(true, "User does not exist");
        }
        if(group.isEmpty()){
            ServiceResponse.defaultResponse(true, "Group does not exist");
        }
        Optional<GroupMember> groupMember = groupMemberRepository.findByUserIdAndGroupId(user.get().getId(), group.get().getId());
        if(groupMember.isEmpty()){
            return ServiceResponse.defaultResponse(true, "User is not part of the group");
        }
        Expense expense = new Expense(groupId, user.get().getId(), amount, description);
        Expense expenseRes = expenseRepository.save(expense);
        addExpenseSplitsForAll(expenseRes.getId(), expenseRes.getGroupId(), expenseRes.getAmount());
        return ServiceResponse.defaultResponse(false, "Expense saved successfully");
    }
}
