package model;

public class ReturnBookList {
    private String memberID;
    private String bookID;
    private String bookName;
    private String barrowedDate;
    private String returnedDate;

    public ReturnBookList() {
    }

    public ReturnBookList(String memberID, String bookID, String bookName, String barrowedDate, String returnedDate) {
        this.memberID = memberID;
        this.bookID = bookID;
        this.bookName = bookName;
        this.barrowedDate = barrowedDate;
        this.returnedDate = returnedDate;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBarrowedDate() {
        return barrowedDate;
    }

    public void setBarrowedDate(String barrowedDate) {
        this.barrowedDate = barrowedDate;
    }

    public String getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(String returnedDate) {
        this.returnedDate = returnedDate;
    }

    @Override
    public String toString() {
        return "ReturnBookList{" +
                "memberID='" + memberID + '\'' +
                ", bookID='" + bookID + '\'' +
                ", bookName='" + bookName + '\'' +
                ", barrowedDate='" + barrowedDate + '\'' +
                ", returnedDate='" + returnedDate + '\'' +
                '}';
    }
}
