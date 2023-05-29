package com.example.gearshop.model;

public class Category {
    private int ID;
    private String Name, Description;

    public Category(int ID, String Name, String Description) {
        this.ID = ID;
        this.Name = Name;
        this.Description = Description;
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

    public void setName(String newName) {
        Name = newName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String newDescription) {
        Description = newDescription;
    }
}
