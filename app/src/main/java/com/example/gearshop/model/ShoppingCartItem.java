package com.example.gearshop.model;

import java.util.Date;

public class ShoppingCartItem {
    private int ID, CustomerID, ProductID, Quantity;
    private Date CreatedOnUtc;

    public ShoppingCartItem(int ID, int customerID, int productID, int quantity, Date createdOnUtc) {
        this.ID = ID;
        CustomerID = customerID;
        ProductID = productID;
        Quantity = quantity;
        CreatedOnUtc = createdOnUtc;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
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

    public Date getCreatedOnUtc() {
        return CreatedOnUtc;
    }

    public void setCreatedOnUtc(Date createdOnUtc) {
        CreatedOnUtc = createdOnUtc;
    }
}
