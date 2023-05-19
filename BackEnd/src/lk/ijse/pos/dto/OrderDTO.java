package lk.ijse.pos.dto;

import java.util.ArrayList;

public class OrderDTO {
    private String orderID;
    private String orderDate;
    private String cusID;

    private ArrayList<OrderDetailsDTO> orderDetailsDTOS;

    public OrderDTO() {
    }

    public OrderDTO(String orderID, String orderDate, String cusID, ArrayList<OrderDetailsDTO> orderDetailsDTOS) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.cusID = cusID;
        this.orderDetailsDTOS = orderDetailsDTOS;
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

    public ArrayList<OrderDetailsDTO> getOrderDetailsDTOS() {
        return orderDetailsDTOS;
    }

    public void setOrderDetailsDTOS(ArrayList<OrderDetailsDTO> orderDetailsDTOS) {
        this.orderDetailsDTOS = orderDetailsDTOS;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderID='" + orderID + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", cusID='" + cusID + '\'' +
                ", orderDetailsDTOS=" + orderDetailsDTOS +
                '}';
    }
}
