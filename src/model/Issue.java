package model;

public class Issue {
    private String issueId;
    private String bookId;
    private String title;
    private String memberId;
    private String barrowedDate;
    private String DueDate;
    private Double charge;

    public Issue() {
    }

    public Issue(String issueId, String bookId, String title, String memberId, String barrowedDate, String dueDate, Double charge) {
        this.issueId = issueId;
        this.bookId = bookId;
        this.title = title;
        this.memberId = memberId;
        this.barrowedDate = barrowedDate;
        DueDate = dueDate;
        this.charge = charge;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getBarrowedDate() {
        return barrowedDate;
    }

    public void setBarrowedDate(String barrowedDate) {
        this.barrowedDate = barrowedDate;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public Double getCharge() {
        return charge;
    }

    public void setCharge(Double charge) {
        this.charge = charge;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "issueId='" + issueId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", title='" + title + '\'' +
                ", memberId='" + memberId + '\'' +
                ", barrowedDate='" + barrowedDate + '\'' +
                ", DueDate='" + DueDate + '\'' +
                ", charge=" + charge +
                '}';
    }
}
