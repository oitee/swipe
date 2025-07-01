package otee.dev.swipe.service;

public class SettlementTransaction {
    private String from;
    private String to;
    Double amount;
    public SettlementTransaction(String from, String to, Double amount){
        this.from = from;
        this.to = to;
        this.amount =amount;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
