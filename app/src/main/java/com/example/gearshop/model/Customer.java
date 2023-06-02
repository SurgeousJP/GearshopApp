package com.example.gearshop.model;

import java.sql.Date;

public class Customer {
    private int ID;
    private String Username, Password, Email, FirstName, LastName;
    private String Gender;
    private String PhoneNumber;
    private Date DateOfBirth;
    private int AddressId;
    private int CardID;

    public Customer(int ID, String username, String password, String email,
                    String firstName, String lastName, String gender,
                    String phoneNumber, Date dateOfBirth, int addressID) {
        this.ID = ID;
        this.Username = username;
        this.Password = password;
        this.Email = email;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Gender = gender;
        this.PhoneNumber = phoneNumber;
        this.DateOfBirth = dateOfBirth;
        this.AddressId = addressID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
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
