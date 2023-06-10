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
    protected ResultSet doInBackground(String... sqlCommand) {
        ResultSet resultSet = null;
        try {
            Connection connection = DriverManager.getConnection(AzureConnectionString);
            Statement statement = connection.createStatement();
            if (sqlCommand[0].contains("SELECT")){
                resultSet = statement.executeQuery(sqlCommand[0]);
            }
            else{
                statement.execute(sqlCommand[0]);
            }
            while (true) {
                try {
                    assert resultSet != null;
                    if (!resultSet.next()) break;
                    PaymentMethod newPaymentMethod = new PaymentMethod(
                            resultSet.getInt("id"),
                            resultSet.getString("name")
                    );
                    PaymentMethodList.add(newPaymentMethod);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            statement.close();
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
