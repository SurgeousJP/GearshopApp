package com.example.gearshop.testclass;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    private final List<Customer> customerList = new ArrayList<>();

    public CustomerRepository() {
        customerList.add(new Customer(1,"username1", "password1", "john.doe@gmail.com"));
        customerList.add(new Customer(2, "username2", "password2", "jane.smith@gmail.com"));
        customerList.add(new Customer(3, "Dan", "password3", "bob.johnson@gmail.com"));
    }

    public List<Customer> getCustomers() {
        return customerList;
    }

    public Customer signIn(String username, String password) {
        for (Customer customer : customerList) {
            if (customer.username.equals(username) && customer.password.equals(password)) {
                return customer;
            }
        }

        return null;
    }

    public void signUp(Customer customer) {
        customerList.add(customer);
    }

    public void changePassword(String email, String newPassword) {
        for (Customer customer : customerList) {
            if (customer.email.equals((email))) {
                customer.setPassword(newPassword);
                break;
            }
        }
    }

    public boolean isExists(String email, String username) {
        for (Customer customer : customerList) {
            if (customer.username.equals(username) && customer.email.equals(email)) {
                return true;
            }
        }

        return false;
    }

    public int generateNewCustomerId() {
        return customerList.size() + 1;
    }
}

