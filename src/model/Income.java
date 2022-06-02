package model;

public class Income {
    private String isssueId;
    private String date;
    private double amount;

    public Income() {
    }

    public Income(String isssueId, String date, double amount) {
        this.isssueId = isssueId;
        this.date = date;
        this.amount = amount;
    }

    public String getIsssueId() {
        return isssueId;
    }

    public void setIsssueId(String isssueId) {
        this.isssueId = isssueId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Income{" +
                "isssueId='" + isssueId + '\'' +
                ", date='" + date + '\'' +
                ", amount=" + amount +
                '}';
    }
}
