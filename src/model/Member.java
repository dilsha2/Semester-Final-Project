package model;

public class Member {
    private String id;
    private String name;
    private String nic;
    private String contact;
    private double regFee;
    private String regDate;

    public Member() {
    }

    public Member(String id, String name, String nic, String contact, double regFee, String regDate) {
        this.id = id;
        this.name = name;
        this.nic = nic;
        this.contact = contact;
        this.regFee = regFee;
        this.regDate = regDate;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public double getRegFee() {
        return regFee;
    }

    public void setRegFee(double regFee) {
        this.regFee = regFee;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", nic='" + nic + '\'' +
                ", contact='" + contact + '\'' +
                ", regFee=" + regFee +
                ", regDate='" + regDate + '\'' +
                '}';
    }
}
