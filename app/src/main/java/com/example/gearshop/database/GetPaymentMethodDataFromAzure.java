package com.example.gearshop.database;

import com.example.gearshop.model.OrderItem;
import com.example.gearshop.model.PaymentMethod;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GetPaymentMethodDataFromAzure extends AzureSQLDatabase{
    private List<PaymentMethod> PaymentMethodList;
    public GetPaymentMethodDataFromAzure() {
        this.PaymentMethodList = new ArrayList<>();
    }
    public List<PaymentMethod> getPaymentMethodList(){
        return PaymentMethodList;
    }
    @Override
    protected void handleResultSetItem(ResultSet resultSet){
        try{
            PaymentMethod newPaymentMethod = new PaymentMethod(
                    resultSet.getInt("id"),
                    resultSet.getString("name")
            );
            PaymentMethodList.add(newPaymentMethod);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
