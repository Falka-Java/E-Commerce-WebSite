package models;

import java.util.Date;

public class Order {
    //region Private Fields

    private long id;
    private long userId;
    private Date creationDate;
    private String userFirstName;
    private String userLastName;
    private String address1;
    private String address2;
    private String country;
    private String state;
    private int zip;
    private double totalSum;
    private String status;

    //endregion

    //region Constructors
    public Order(){

    }
    public Order(long id, int userId, Date creationDate, String userFirstName, String userLastName,
                 String address1, String address2, String country,
                 String state, int zip, double totalSum, String status) {
        this.id = id;
        this.userId = userId;
        this.creationDate = creationDate;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.address1 = address1;
        this.address2 = address2;
        this.country = country;
        this.state = state;
        this.zip = zip;
        this.totalSum = totalSum;
        this.status = status;
    }

    public Order(long userId, Date creationDate, String userFirstName, String userLastName,
                 String address1, String address2, String country,
                 String state, int zip, double totalSum, String status) {
        this.userId = userId;
        this.creationDate = creationDate;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.address1 = address1;
        this.address2 = address2;
        this.country = country;
        this.state = state;
        this.zip = zip;
        this.totalSum = totalSum;
        this.status = status;
    }

    //endregion

    //region Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(double totalSum) {
        this.totalSum = totalSum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //endregion

    //region Overrides

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", creationDate=" + creationDate +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", zip=" + zip +
                ", totalSum=" + totalSum +
                ", status='" + status + '\'' +
                '}';
    }

    //endregion
}
