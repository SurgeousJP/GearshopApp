package com.example.gearshop.model;

public class Address {
    private int ID;
    private String HouseNumber, Street;
    private int ProvinceID;

    public Address(int ID, String houseNumber, String street, int provinceID) {
        this.ID = ID;
        HouseNumber = houseNumber;
        Street = street;
        ProvinceID = provinceID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getHouseNumber() {
        return HouseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        HouseNumber = houseNumber;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public int getProvinceID() {
        return ProvinceID;
    }

    public void setProvinceID(int provinceID) {
        ProvinceID = provinceID;
    }
}
