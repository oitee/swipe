package otee.dev.swipe.service;

import java.util.Objects;
import java.util.PriorityQueue;

public class GroupMemberPriority {
    private Boolean isAscending;
    private PriorityQueue<GroupMemberBalance> memberPriorities;

    public GroupMemberPriority(Boolean isAscending) {
        this.isAscending = isAscending;
        if (isAscending) {
            this.memberPriorities = new PriorityQueue<GroupMemberBalance>((a, b) -> Double.compare(a.getBalance(), b.getBalance()));
        }
        else{
            this.memberPriorities = new PriorityQueue<GroupMemberBalance>((a, b) -> Double.compare(b.getBalance(), a.getBalance()));
        }
    }
    public void addMember(Long userId, Double balance){
        GroupMemberBalance member = new GroupMemberBalance(userId, balance);
        memberPriorities.offer(member);
        System.out.println("Inserted: " + member.getUserId());
    }

    public GroupMemberBalance getMember(){
        return memberPriorities.peek();
    }

    public Boolean removeMember(Long userId){
        GroupMemberBalance toRemove = null;
        for(GroupMemberBalance member : memberPriorities){
            if(member.getUserId() == userId){
                toRemove = member;
                break;
            }
        }
        if(toRemove != null){
            memberPriorities.remove(toRemove);
            System.out.println("Removed: " + toRemove.getUserId());
            return true;
        }
        System.out.println("[Deletion Failed] Could not find the Member in PQ");
        return false;
    }

    public Boolean updateMemberBalance(Long userId, Double revisedAmount){
        GroupMemberBalance toUpdate = null;
        for(GroupMemberBalance member : memberPriorities){
            if(Objects.equals(member.getUserId(), userId)){
                toUpdate = member;
                break;
            }
        }
        if(toUpdate != null){
            memberPriorities.remove(toUpdate);
            toUpdate.setBalance(revisedAmount);
            memberPriorities.offer(toUpdate);
            System.out.println("Updated: " + toUpdate.getUserId());
            return true;
        }
        System.out.println("[Updation Failed] Could not find the Member in PQ");
        return false;
    }

    public GroupMemberBalance removePeekMember(){
        GroupMemberBalance toRemove = memberPriorities.poll();
        System.out.println("Removed Peak member: " + toRemove.getUserId());
        return memberPriorities.peek();
    }
}
