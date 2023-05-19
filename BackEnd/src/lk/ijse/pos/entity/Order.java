package lk.ijse.pos.entity;


import java.util.ArrayList;

public class Order {
    private String orderID;
    private String orderDate;
    private String cusID;

    private ArrayList<OrderDetails> orderDetails;

    public Order() {
    }

    public Order(String orderID, String orderDate, String cusID, ArrayList<OrderDetails> orderDetails) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.cusID = cusID;
        this.orderDetails = orderDetails;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCusID() {
        return cusID;
    }

    public void setCusID(String cusID) {
        this.cusID = cusID;
    }

    public ArrayList<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
