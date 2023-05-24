package com.example.gearshop.controller;

import android.annotation.SuppressLint;

import com.example.gearshop.database.GetCustomerDataFromAzure;
import com.example.gearshop.database.InsertCustomerDataToAzure;
import com.example.gearshop.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CustomerRepository {
    private final List<Customer> CustomerList = new ArrayList<>();

    @SuppressLint("SimpleDateFormat")
    public CustomerRepository() {
        // Get Customer List in Database
        GetCustomerDataFromAzure[] getCustomerDataInAzure = new GetCustomerDataFromAzure[1];
        getCustomerDataInAzure[0] = new GetCustomerDataFromAzure();
        getCustomerDataInAzure[0].execute(
                "SELECT customer.*\n" +
                        "FROM customer\n"
        );
        System.out.println("Async Task get Customers is running");

        try {
            getCustomerDataInAzure[0].get();
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }

        System.out.println("Async Task get Customers ended");



        if (getCustomerDataInAzure[0].getCustomerList() != null){
            CustomerList.addAll(getCustomerDataInAzure[0].getCustomerList());
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
        String dob = customer.getDateOfBirth().toString();

        InsertCustomerDataToAzure[] insertCustomerDataToAzure = new InsertCustomerDataToAzure[1];
        insertCustomerDataToAzure[0] = new InsertCustomerDataToAzure();
        System.out.println("Async Task insert Customers is running");

        insertCustomerDataToAzure[0].execute(
                        "INSERT INTO customer (id, username, password, email, first_name, last_name, gender, phone_number, date_of_birth)\n" +
                        "VALUES(" + customer.getID()   + ", " +
                        "'" + customer.getUsername()    + "', " +
                        "'" + customer.getPassword()    + "', " +
                        "'" + customer.getEmail()       + "', " +
                        "'" + customer.getFirstName()   + "', " +
                        "'" + customer.getLastName()    + "', " +
                        "'" + customer.getGender()      + "', " +
                        "'" + customer.getPhoneNumber() + "', " +
                        "'" + customer.getDateOfBirth() + "')\n");



        try {
            insertCustomerDataToAzure[0].get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Async Task insert Customers ended");
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

