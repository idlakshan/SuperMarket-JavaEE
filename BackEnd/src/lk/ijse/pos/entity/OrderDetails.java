package lk.ijse.pos.entity;

public class OrderDetails {
    private String orderID;
    private String itemCode;
    private double price;
    private int orderQty;


    public OrderDetails() {
    }

    public OrderDetails(String orderID, String itemCode, double price, int orderQty) {
        this.orderID = orderID;
        this.itemCode = itemCode;
        this.price = price;
        this.orderQty = orderQty;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderID='" + orderID + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", price=" + price +
                ", orderQty=" + orderQty +
                '}';
    }
}
