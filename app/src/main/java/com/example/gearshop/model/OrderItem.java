package com.example.gearshop.model;

public class OrderItem {
    private int ID, OrderID, ProductID, Quantity;
    private int Rate;
    private String Comment;

    public int getRate() {
        return Rate;
    }

    public void setRate(int rate) {
        Rate = rate;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

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

    public OrderItem(int ID, int orderID, int productID, int quantity, int rate, String comment) {
        this.ID = ID;
        this.OrderID = orderID;
        this.ProductID = productID;
        this.Quantity = quantity;
        this.Rate = rate;
        this.Comment = comment;
    }
}
