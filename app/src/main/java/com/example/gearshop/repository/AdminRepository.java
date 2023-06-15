package com.example.gearshop.repository;

import com.example.gearshop.model.Address;
import com.example.gearshop.model.Admin;
import com.example.gearshop.model.Customer;
import com.example.gearshop.utility.DatabaseHelper;

import java.util.List;

public class AdminRepository {
    private final String SQL_GET_ADMIN_BASE = "SELECT *\n" +
            "FROM admin\n";

    public Admin signIn(String username, String password) {
        Admin adminInfo = DatabaseHelper.getAdmin();

        if (adminInfo.getUsername().equals(username) && adminInfo.getPassword().equals(password)) {
            return adminInfo;
        }

        return null;
    }
}
