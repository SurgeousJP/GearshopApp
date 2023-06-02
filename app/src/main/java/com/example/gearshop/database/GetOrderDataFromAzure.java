package com.example.gearshop.database;

import com.example.gearshop.model.Customer;
import com.example.gearshop.model.Order;
import com.example.gearshop.repository.GlobalRepository;
import com.example.gearshop.utility.MoneyHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetOrderDataFromAzure extends AzureSQLDatabase{
    private List<Order> OrderList;
    public GetOrderDataFromAzure() {
        this.OrderList = new ArrayList<>();
    }
    public List<Order> getOrderList(){
        return OrderList;
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
                    Order newOrder = new Order();
                    newOrder.setID(resultSet.getInt("id"));
                    newOrder.setShipmentMethodID(resultSet.getInt("shipping_method_id"));
                    newOrder.setPaymentMethodID(resultSet.getInt("payment_method_id"));
                    newOrder.setShippingAddressID(resultSet.getInt("shipping_address_id"));
                    newOrder.setCustomerID(resultSet.getInt("customer_id"));
                    newOrder.setTotalPrice(resultSet.getDouble("total_price"));
                    newOrder.setCreatedOnUtc(resultSet.getDate("created_on_utc"));
                    newOrder.setPaid(resultSet.getBoolean("is_paid"));
                    newOrder.setStatus(resultSet.getString("status"));
                    OrderList.add(newOrder);
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
