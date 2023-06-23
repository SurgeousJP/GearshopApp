package com.example.gearshop.database;

import com.example.gearshop.model.Discount;
import com.example.gearshop.model.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetCustomerProductDataFromAzure extends GetProductDataFromAzure{
    @Override
    public boolean isIgnoreProduct(int status) {
        // DELETED / NOT AVAILABLE
        return status == 0 || status == 2;
    }
}
