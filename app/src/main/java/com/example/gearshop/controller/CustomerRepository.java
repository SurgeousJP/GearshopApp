package com.example.gearshop.controller;

import android.annotation.SuppressLint;

import com.example.gearshop.model.Customer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerRepository {
    private final List<Customer> CustomerList = new ArrayList<>();

    @SuppressLint("SimpleDateFormat")
    public CustomerRepository() {
        try {
            CustomerList.add(new Customer(1, "test_username", "test_password", "test_email",
                    "test_first_name", "test_last_name", "test_gender", "phone_number",
                    new SimpleDateFormat("dd/MM/yyyy").parse("11/03/2003")));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Customer> getCustomers() {
        return CustomerList;
    }

    public Customer signIn(String username, String password) {
        for (Customer customer : CustomerList) {
            if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                return customer;
            }
        }

        return null;
    }

    public void signUp(Customer customer) {
        CustomerList.add(customer);
    }

    public void changePassword(String email, String newPassword) {
        for (Customer customer : CustomerList) {
            if (customer.getEmail().equals((email))) {
                customer.setPassword(newPassword);
                break;
            }
        }
    }

    public boolean isExists(String email, String username) {
        for (Customer customer : CustomerList) {
            if (customer.getUsername().equals(username) && customer.getEmail().equals(email)) {
                return true;
            }
        }

        return false;
    }

    public int generateNewCustomerId() {
        return CustomerList.size() + 1;
    }
}

