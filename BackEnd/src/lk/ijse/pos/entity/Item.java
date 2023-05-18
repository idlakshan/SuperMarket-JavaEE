package lk.ijse.pos.entity;

public class Item {
    private String ItemCode;
    private String ItemName;
    private double Price;
    private String Qty;

    public Item() {
    }

    public Item(String itemCode, String itemName, double price, String qty) {
        ItemCode = itemCode;
        ItemName = itemName;
        Price = price;
        Qty = qty;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }
}
