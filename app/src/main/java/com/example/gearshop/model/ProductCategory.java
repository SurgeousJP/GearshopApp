package com.example.gearshop.model;

public class ProductCategory {
    private int ID;
    private String Name;
    private String Description;

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

    public ProductCategory(int ID, String name, String description) {
        this.ID = ID;
        Name = name;
        Description = description;
    }
}
