package com.example.gearshop.model;

import java.util.Date;

public class Discount {
    private int ID;
    private String Name;
    private int DiscountPercentage;
    private Date StartDateUtc, EndDateUtc;
    private boolean IsActive;

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

    public int getDiscountPercentage() {
        return DiscountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        DiscountPercentage = discountPercentage;
    }

    public Date getStartDateUtc() {
        return StartDateUtc;
    }

    public void setStartDateUtc(Date startDateUtc) {
        StartDateUtc = startDateUtc;
    }

    public Date getEndDateUtc() {
        return EndDateUtc;
    }

    public void setEndDateUtc(Date endDateUtc) {
        EndDateUtc = endDateUtc;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public Discount(int ID, String name, int discountPercentage, Date startDateUtc, Date endDateUtc, boolean isActive) {
        this.ID = ID;
        Name = name;
        DiscountPercentage = discountPercentage;
        StartDateUtc = startDateUtc;
        EndDateUtc = endDateUtc;
        IsActive = isActive;
    }


}
