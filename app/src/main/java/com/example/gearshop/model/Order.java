package com.example.gearshop.model;

import java.util.Date;

public class Order {
    private int ID;
    private Date CreatedOnUtc;
    private int CustomerID, ShippingAddressID, ShipmentMethodID, PaymentMethodID;
    private double TotalPrice;
    private String Status;
    private boolean IsPaid;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getCreatedOnUtc() {
        return CreatedOnUtc;
    }

    public void setCreatedOnUtc(Date createdOnUtc) {
        CreatedOnUtc = createdOnUtc;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public int getShippingAddressID() {
        return ShippingAddressID;
    }

    public void setShippingAddressID(int shippingAddressID) {
        ShippingAddressID = shippingAddressID;
    }

    public int getShipmentMethodID() {
        return ShipmentMethodID;
    }

    public void setShipmentMethodID(int shipmentMethodID) {
        ShipmentMethodID = shipmentMethodID;
    }

    public int getPaymentMethodID() {
        return PaymentMethodID;
    }

    public void setPaymentMethodID(int paymentMethodID) {
        PaymentMethodID = paymentMethodID;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public boolean isPaid() {
        return IsPaid;
    }

    public void setPaid(boolean paid) {
        IsPaid = paid;
    }
    public Order(){};

    public Order(int ID, Date createdOnUtc, int customerID, int shippingAddressID,
                 int shipmentMethodID, int paymentMethodID, double totalPrice,
                 String status, boolean isPaid) {
        this.ID = ID;
        CreatedOnUtc = createdOnUtc;
        CustomerID = customerID;
        ShippingAddressID = shippingAddressID;
        ShipmentMethodID = shipmentMethodID;
        PaymentMethodID = paymentMethodID;
        TotalPrice = totalPrice;
        Status = status;
        IsPaid = isPaid;
    }
}
