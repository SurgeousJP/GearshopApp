package com.example.gearshop.model;

import java.io.Serializable;

public class Product implements Serializable {
    private int ID;
    private String Name, ImageURL, Description, Specs;
    private double Price;

    // Product Status: 0 - DELETED, 1 - AVAILABLE, 2 - NOT AVAILABLE
    private int Status;
    private int CategoryID;
    private Discount DiscountInformation;
    public Product(){}
    public Product(int Id, String name, String imageURL, String description,
                   String specs, double price, int status, int categoryID, Discount discountInformation) {
        ID = Id;
        Name = name;
        ImageURL = imageURL;
        Description = description;
        Specs = specs;
        Price = price;
        Status = status;
        CategoryID = categoryID;
        DiscountInformation = discountInformation;
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

    public void setStatus(int status) {
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

    public int getStatus() {
        return Status;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public Discount getDiscountInformation() {
        return DiscountInformation;
    }

    public void setDiscountInformation(Discount discountInformation) {
        DiscountInformation = discountInformation;
    }

}
