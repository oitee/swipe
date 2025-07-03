package otee.dev.swipe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import otee.dev.swipe.service.SettlementTransaction;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
public class TransactionDtos {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SettlementDto{
        private Long groupId;
        private String message;
        private Boolean success;
        private List<SettlementTransaction> settlements;

        public SettlementDto(List<SettlementTransaction> settlements, Long groupId){
            this.groupId = groupId;
            this.success = true;
            this.settlements = settlements;
        }
        public SettlementDto(Boolean success, String message){
            this.success = success;
            this.message = message;
        }

        public Boolean getSuccess() {
            return this.success;
        }

        public List<SettlementTransaction> getSettlements() {
            return this.settlements;
        }

        public Long getGroupId() {
            return groupId;
        }

        public String getMessage() {
            return message;
        }
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GroupDuesDto{
        private String message;
        private Boolean success;
        private HashMap<String, Double> dues;

        public GroupDuesDto(){
            this.success = true;
            this.dues = new HashMap<String, Double>();
        }
        public GroupDuesDto(Boolean success, String message){
            this.success = success;
            this.message = message;
        }

        public void addDue(String username, Double amount){
            if(this.dues == null){
                this.dues = new HashMap<String, Double>();
            }
            this.dues.put(username, amount);
        }

        public Boolean getSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public HashMap<String, Double> getDues() {
            return dues;
        }
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UserDuesDto {
        private String username;
        private Double totalExpenses;
        private Double totalDues;
        private Boolean success;
        private String message;

        public UserDuesDto(String username, Double totalExpenses, Double totalDues){
            this.success = true;
            this.totalExpenses = totalExpenses;
            this.totalDues = totalDues;
        }
        public UserDuesDto(Boolean success, String message){
            this.success = success;
            this.message = message;
        }

        public String getUsername() {
            return username;
        }

        public Double getTotalExpenses() {
            return totalExpenses;
        }

        public Double getTotalDues() {
            return totalDues;
        }

        public Boolean getSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DefaultDto {
        private String message;
        private Boolean success;
        private Long groupId;

        public DefaultDto(Boolean success, String message){
            this.success = success;
            this.message = message;
        }
        public DefaultDto(Boolean success, String message, Long groupId){
            this.success = success;
            this.message = message;
            this.groupId = groupId;
        }

        public String getMessage() {
            return message;
        }

        public Boolean getSuccess() {
            return success;
        }

        public Long getGroupId() {
            return groupId;
        }
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ExpenseWithUsernameDto {
        private String description;
        private Double amount;
        private String paidBy;
        OffsetDateTime ts;
        public ExpenseWithUsernameDto(String description, Double amount, String paidBy, OffsetDateTime ts){
            this.description = description;
            this.amount = amount;
            this.paidBy = paidBy;
            this.ts = ts;
        }

        public String getDescription() {
            return description;
        }

        public Double getAmount() {
            return amount;
        }

        public String getPaidBy() {
            return paidBy;
        }

        public OffsetDateTime getTs() {
            return ts;
        }
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GroupExpensesWithUsernameDto {
        private List<ExpenseWithUsernameDto> expenses;
        private Boolean success;
        private String message;
        public GroupExpensesWithUsernameDto(Boolean success, String message){
            this.success = success;
            this.message = message;
        }
        public GroupExpensesWithUsernameDto(List<ExpenseWithUsernameDto> expenses){
            this.success = true;
            this.expenses = expenses;
        }

        public List<ExpenseWithUsernameDto> getExpenses() {
            return expenses;
        }

        public Boolean getSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }

}
