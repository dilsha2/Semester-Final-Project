package model;

public class Donation {
    private String donation_id;
    private String name;
    private String title;
    private String category;
    private int qty;

    public Donation() {
    }

    public Donation(String donation_id, String name, String title, String category, int qty) {
        this.donation_id = donation_id;
        this.name = name;
        this.title = title;
        this.category = category;
        this.qty = qty;
    }

    public String getDonation_id() {
        return donation_id;
    }

    public void setDonation_id(String donation_id) {
        this.donation_id = donation_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Donation{" +
                "donation_id='" + donation_id + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", qty=" + qty +
                '}';
    }
}
