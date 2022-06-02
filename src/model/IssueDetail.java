package model;

public class IssueDetail {
    private String issueId;
    private String bookId;
    private String title;
    private String memberId;
    private String barrowedDate;

    public IssueDetail() {
    }

    public IssueDetail(String issueId, String bookId, String title, String memberId, String barrowedDate) {
        this.issueId = issueId;
        this.bookId = bookId;
        this.title = title;
        this.memberId = memberId;
        this.barrowedDate = barrowedDate;
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

    @Override
    public String toString() {
        return "IssueDetail{" +
                "issueId='" + issueId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", title='" + title + '\'' +
                ", memberId='" + memberId + '\'' +
                ", barrowedDate='" + barrowedDate + '\'' +
                '}';
    }
}
