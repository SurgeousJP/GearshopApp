package com.example.gearshop.model;

public class Province {
    private int ID;
    private String Name;
    private double ShippingCharge;

    public Province(int ID, String name, double shippingCharge) {
        this.ID = ID;
        Name = name;
        ShippingCharge = shippingCharge;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getShippingCharge() {
        return ShippingCharge;
    }

    public void setShippingCharge(double shippingCharge) {
        ShippingCharge = shippingCharge;
    }
}
