package otee.dev.swipe.service;

public class GroupMemberBalance {
    private Long userId;
    private Double balance;
    public GroupMemberBalance(Long userId, Double balance){
        this.userId = userId;
        this.balance = balance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
