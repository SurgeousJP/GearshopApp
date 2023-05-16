package com.example.gearshop.model;

public class Product {
    private int ID;
    private String Name, ImageURL, Description, Specs;
    private double Price;
    private boolean Status;
    private int CategoryID;

    public Product(){}
    public Product(int ID, String name, String imageURL, String description,
                   String specs, double price, boolean status, int categoryID) {
        ID = ID;
        Name = name;
        ImageURL = imageURL;
        Description = description;
        Specs = specs;
        Price = price;
        Status = status;
        CategoryID = categoryID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setSpecs(String specs) {
        Specs = specs;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }
    public int getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public String getDescription() {
        return Description;
    }

    public String getSpecs() {
        return Specs;
    }

    public double getPrice() {
        return Price;
    }

    public boolean getStatus() {
        return Status;
    }

    public int getCategoryID() {
        return CategoryID;
    }


}
