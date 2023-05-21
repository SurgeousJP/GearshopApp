package com.example.gearshop.model;

import java.io.Serializable;
import java.util.Date;

public class Discount implements Serializable {
    private int ID;
    private String Name;
    private int DiscountPercentage;
    private Date StartDateUtc, EndDateUtc;

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

    public void setDiscountPercentage(int discountPercentage){DiscountPercentage = discountPercentage;}

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
        Date currentDate = new Date();
        return currentDate.after(getStartDateUtc()) && currentDate.before(getEndDateUtc());
    }
    public Discount(int ID, String name, int discountPercentage, Date startDateUtc, Date endDateUtc) {
        this.ID = ID;
        Name = name;
        DiscountPercentage = discountPercentage;
        StartDateUtc = startDateUtc;
        EndDateUtc = endDateUtc;
    }


}
