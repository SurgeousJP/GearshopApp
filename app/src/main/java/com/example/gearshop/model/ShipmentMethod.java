package com.example.gearshop.model;

public class ShipmentMethod {
    private int ID;
    private String Name, Description;
    private double ShipCharge;

    public ShipmentMethod(int ID, String name, String description, double shipCharge) {
        this.ID = ID;
        Name = name;
        Description = description;
        ShipCharge = shipCharge;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getShipCharge() {
        return ShipCharge;
    }

    public void setShipCharge(double shipCharge) {
        ShipCharge = shipCharge;
    }
}
