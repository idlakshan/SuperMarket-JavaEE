package lk.ijse.pos.dto;

import lk.ijse.pos.entity.OrderDetails;

import java.util.ArrayList;

public class OrderDTO {
    private String orderID;
    private String orderDate;
    private String cusID;

    private ArrayList<OrderDetails> OrderDetails;

    public OrderDTO() {
    }

    public OrderDTO(String orderID, String orderDate, String cusID, ArrayList<lk.ijse.pos.entity.OrderDetails> orderDetails) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.cusID = cusID;
        OrderDetails = orderDetails;
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

    public ArrayList<lk.ijse.pos.entity.OrderDetails> getOrderDetails() {
        return OrderDetails;
    }

    public void setOrderDetails(ArrayList<lk.ijse.pos.entity.OrderDetails> orderDetails) {
        OrderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderID='" + orderID + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", cusID='" + cusID + '\'' +
                ", OrderDetails=" + OrderDetails +
                '}';
    }
}
