package com.example.gearshop.model;

public class CreditCardType {
    private int ID;
    private String Name;
    private double Charge;

    public CreditCardType(int ID, String name, double charge) {
        this.ID = ID;
        Name = name;
        Charge = charge;
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

    public double getCharge() {
        return Charge;
    }

    public void setCharge(double charge) {
        Charge = charge;
    }
}
