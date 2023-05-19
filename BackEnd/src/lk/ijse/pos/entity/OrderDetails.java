package lk.ijse.pos.entity;

public class OrderDetails {
    private String orderID;
    private String itemCode;
    private String orderqty;
    private double discount;
    private double balance;

    public OrderDetails() {
    }

    public OrderDetails(String orderID, String itemCode, String orderqty, double discount, double balance) {
        this.orderID = orderID;
        this.itemCode = itemCode;
        this.orderqty = orderqty;
        this.discount = discount;
        this.balance = balance;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getOrderqty() {
        return orderqty;
    }

    public void setOrderqty(String orderqty) {
        this.orderqty = orderqty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
