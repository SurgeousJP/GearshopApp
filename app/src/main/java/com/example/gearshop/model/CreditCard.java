package com.example.gearshop.model;

import java.util.Date;

public class CreditCard {
    private int ID, CardTypeID;
    private String Username;
    private int CSVNumber;
    private double Balance;
    private Date ExpireOnUtc;
    private String AccountNumber;

    public CreditCard(int ID, int cardTypeID, String username, int CSVNumber,
                      double balance, Date expireOnUtc, String accountNumber) {
        this.ID = ID;
        CardTypeID = cardTypeID;
        Username = username;
        this.CSVNumber = CSVNumber;
        Balance = balance;
        ExpireOnUtc = expireOnUtc;
        AccountNumber = accountNumber;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCardTypeID() {
        return CardTypeID;
    }

    public void setCardTypeID(int cardTypeID) {
        CardTypeID = cardTypeID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public int getCSVNumber() {
        return CSVNumber;
    }

    public void setCSVNumber(int CSVNumber) {
        this.CSVNumber = CSVNumber;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double balance) {
        Balance = balance;
    }

    public Date getExpireOnUtc() {
        return ExpireOnUtc;
    }

    public void setExpireOnUtc(Date expireOnUtc) {
        ExpireOnUtc = expireOnUtc;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }
}
