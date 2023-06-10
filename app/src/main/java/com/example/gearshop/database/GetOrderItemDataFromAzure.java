package com.example.gearshop.database;

import com.example.gearshop.model.Order;
import com.example.gearshop.model.OrderItem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GetOrderItemDataFromAzure extends AzureSQLDatabase{
    private List<OrderItem> OrderItemList;
    public GetOrderItemDataFromAzure() {
        this.OrderItemList = new ArrayList<>();
    }
    public List<OrderItem> getOrderItemList(){
        return OrderItemList;
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
                    OrderItem newOrderItem = new OrderItem(
                            resultSet.getInt("id"),
                            resultSet.getInt("order_id"),
                            resultSet.getInt("product_id"),
                            resultSet.getInt("quantity"),
                            resultSet.getInt("rate"),
                            resultSet.getString("comment")
                    );
                    OrderItemList.add(newOrderItem);
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
