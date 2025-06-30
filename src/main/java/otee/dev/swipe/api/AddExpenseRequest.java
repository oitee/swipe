package otee.dev.swipe.api;

public class AddExpenseRequest {
    private Long groupId;
    private String username;
    private String description;
    private Double amount;

    public AddExpenseRequest(){}

    public Long getGroupId() {
        return groupId;
    }

    public String getUsername() {
        return username;
    }

    public String getDescription() {
        return description;
    }

    public Double getAmount() {
        return amount;
    }
}
