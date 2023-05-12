package com.example.gearshop.model;

public class OrderItem {
    private int ID, OrderID, ProductID, Quantity;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public OrderItem(int ID, int orderID, int productID, int quantity) {
        this.ID = ID;
        OrderID = orderID;
        ProductID = productID;
        Quantity = quantity;
    }
}
