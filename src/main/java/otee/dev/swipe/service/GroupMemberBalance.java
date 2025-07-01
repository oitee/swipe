package otee.dev.swipe.service;

public class GroupMemberBalance {
    private Long userId;
    private Double balance;
    private String username;
    public GroupMemberBalance(Long userId, Double balance){
        this.userId = userId;
        this.balance = balance;
    }
    public GroupMemberBalance(Long userId, Double balance, String username){
        this.userId = userId;
        this.balance = balance;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
