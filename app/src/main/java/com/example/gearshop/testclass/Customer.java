package com.example.gearshop.testclass;

public class Customer {
    public int id;
    public String username;
    public String password;
    public String email;

    public Customer(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
}
