package model;

public class SearchBook {
    private String bookId;
    private String title;
    private String category;
    private int qty;
    private double price;

    public SearchBook() {
    }

    public SearchBook(String bookId, String title, String category, int qty, double price) {
        this.bookId = bookId;
        this.title = title;
        this.category = category;
        this.qty = qty;
        this.price = price;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "SearchBook{" +
                "bookId='" + bookId + '\'' +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }
}
