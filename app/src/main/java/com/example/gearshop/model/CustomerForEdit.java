package com.example.gearshop.model;

import java.sql.Date;

public class CustomerForEdit {
    private int ID;
    private String Email, FirstName, LastName;
    private String Gender;
    private String PhoneNumber;
    private Date DateOfBirth;
    private int AddressId;
    private int CardID;

    public CustomerForEdit(int ID, String email,
                    String firstName, String lastName, String gender,
                    String phoneNumber) {
        this.ID = ID;
        Email = email;
        FirstName = firstName;
        LastName = lastName;
        Gender = gender;
        PhoneNumber = phoneNumber;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public Date getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public int getAddressID() {
        return AddressId;
    }

    public void setAddressID(int addressID) {
        AddressId = addressID;
    }

    public int getCardID() {
        return CardID;
    }

    public void setCardID(int cardID) {
        CardID = cardID;
    }
}
