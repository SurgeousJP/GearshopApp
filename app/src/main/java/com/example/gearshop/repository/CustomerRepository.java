package com.example.gearshop.repository;

import android.annotation.SuppressLint;

import com.example.gearshop.database.GetCustomerDataFromAzure;
import com.example.gearshop.database.InsertUpdateDataToAzure;
import com.example.gearshop.model.Address;
import com.example.gearshop.model.Customer;
import com.example.gearshop.utility.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CustomerRepository {
    private final String SQL_GET_CUSTOMER_BASE = "SELECT *\n" +
            "FROM customer\n";
    private final List<Customer> CustomerList = new ArrayList<>();
    private final GetCustomerDataFromAzure[] getCustomerDataFromAzure;

    @SuppressLint("SimpleDateFormat")
    public CustomerRepository() {
        // Get Customer List in Database
        getCustomerDataFromAzure = new GetCustomerDataFromAzure[2];
        CustomerList.addAll(getCustomers());
    }

    public Customer getCustomerById(String customerId) {
        getCustomerDataFromAzure[1] = new GetCustomerDataFromAzure();
        getCustomerDataFromAzure[1].execute(
                SQL_GET_CUSTOMER_BASE +
                "WHERE id = " + customerId
        );

        try {
            getCustomerDataFromAzure[1].get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (getCustomerDataFromAzure[1].getCustomerList().size() > 0){
            return getCustomerDataFromAzure[1].getCustomerList().get(0);
        }

        return null;
    }

    public List<Customer> getCustomers() {
        getCustomerDataFromAzure[0] = new GetCustomerDataFromAzure();
        getCustomerDataFromAzure[0].execute(SQL_GET_CUSTOMER_BASE);

        try {
            getCustomerDataFromAzure[0].get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (getCustomerDataFromAzure[0].getCustomerList() != null){
            return getCustomerDataFromAzure[0].getCustomerList();
        }

        return null;
    }

    public Customer signIn(String username, String password) {
        for (Customer customer : CustomerList) {
            if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                GlobalRepository.setCurrentCustomer(customer);
                List<Address> currentAddressList = DatabaseHelper.getAddressList(" WHERE id ='"+ customer.getAddressID() + "'");
                if (currentAddressList.size() > 0)
                    GlobalRepository.setCustomerAddress(currentAddressList.get(0));
                return customer;
            }
        }

        return null;
    }

    public void signUp(Customer customer) {

        CustomerList.add(customer);
        String dob = customer.getDateOfBirth().toString();

        InsertUpdateDataToAzure[] insertCustomerDataToAzure = new InsertUpdateDataToAzure[1];
        insertCustomerDataToAzure[0] = new InsertUpdateDataToAzure();
        System.out.println("Async Task insert Customers is running");

        insertCustomerDataToAzure[0].execute(
                        "INSERT INTO customer (id, username, password, email, first_name, last_name, gender, phone_number, date_of_birth)\n" +
                        "VALUES(" + customer.getID()   + ", " +
                        "'" + customer.getUsername()    + "', " +
                        "'" + customer.getPassword()    + "', " +
                        "'" + customer.getEmail()       + "', " +
                        "N'" + customer.getFirstName()   + "', " +
                        "N'" + customer.getLastName()    + "', " +
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

                String sql =
                        "UPDATE customer \n" +
                        "SET password = '" + newPassword + "' \n" +
                        "WHERE email = '" + email + "'";

                InsertUpdateDataToAzure updateDataToAzure = new InsertUpdateDataToAzure();

                updateDataToAzure.execute(sql);


                try {
                    updateDataToAzure.get();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }

                break;
            }
        }
    }

    public boolean isExistUserWithEmail(String email, String username) {
        for (Customer customer: CustomerList){
            if (customer.getUsername().equals(username) && customer.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    public boolean isExistsUsername(String username) {
        for (Customer customer : CustomerList) {
            if (customer.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }

    public boolean isExistsEmail(String email) {
        for (Customer customer : CustomerList) {
            if (customer.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public Customer getCustomerThroughEmail(String email){
        for (Customer customer : CustomerList) {
            if (customer.getEmail().equals(email)) {
                return customer;
            }
        }
        return null;
    }

    public boolean isExistsPhoneNumber(String phoneNumber) {
        for (Customer customer : CustomerList) {
            if (customer.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }

        return false;
    }

    public int generateNewCustomerId() {
        return CustomerList.size() + 1;
    }
}

