package model;

public class ReturnBook {
    private String issueId;
    private String bookId;
    private String title;
    private String memberId;
    private String barrowedDate;
    private String returnDate;
    private Double fine;
    private Double charge;

    public ReturnBook() {
    }

    public ReturnBook(String issueId, String bookId, String title, String memberId, String barrowedDate, String returnDate, Double fine, Double charge) {
        this.issueId = issueId;
        this.bookId = bookId;
        this.title = title;
        this.memberId = memberId;
        this.barrowedDate = barrowedDate;
        this.returnDate = returnDate;
        this.fine = fine;
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

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Double getFine() {
        return fine;
    }

    public void setFine(Double fine) {
        this.fine = fine;
    }

    public Double getCharge() {
        return charge;
    }

    public void setCharge(Double charge) {
        this.charge = charge;
    }

    @Override
    public String toString() {
        return "ReturnBook{" +
                "issueId='" + issueId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", title='" + title + '\'' +
                ", memberId='" + memberId + '\'' +
                ", barrowedDate='" + barrowedDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", fine=" + fine +
                ", charge=" + charge +
                '}';
    }
}
