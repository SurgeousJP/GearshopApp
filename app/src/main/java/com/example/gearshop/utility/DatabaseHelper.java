package com.example.gearshop.utility;

import com.example.gearshop.database.GetOrderDataFromAzure;
import com.example.gearshop.database.GetOrderItemDataFromAzure;
import com.example.gearshop.database.InsertDataToAzure;
import com.example.gearshop.model.Order;
import com.example.gearshop.model.OrderItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class DatabaseHelper {
    public static void insertOrderToAzure(Order newOrder){
        String inputPattern = "yyyy-MM-dd"; // Input date format
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.getDefault());
        InsertDataToAzure insertOrderDataToAzure = new InsertDataToAzure();

        System.out.println("Async Task insert Orders is running");
        insertOrderDataToAzure.execute(
                "INSERT INTO orders (id, note, created_on_utc, " +
                        "customer_id, shipping_address_id, shipping_method_id," +
                        " payment_method_id, total_price, status, is_paid)\n" +
                        "VALUES(" + newOrder.getID()  + ", " +
                        "'" +                                     "', " +
                        "'" + inputFormat.format(newOrder.getCreatedOnUtc())        + "', " +
                        "'" + newOrder.getCustomerID()          + "', " +
                        "'" + newOrder.getShippingAddressID()   + "', " +
                        "'" + newOrder.getShipmentMethodID()    + "', " +
                        "'" + newOrder.getPaymentMethodID()     + "', " +
                        "'" + newOrder.getTotalPrice()          + "', " +
                        "'" + newOrder.getStatus()              + "', " +
                        "'" + newOrder.isPaid()                 + "')\n");
        try {
            insertOrderDataToAzure.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Async Task insert Orders ended");
    }

    public static void insertOrderItemToAzure(OrderItem orderItem){
        InsertDataToAzure insertOrderItemDataToAzure = new InsertDataToAzure();

        System.out.println("Async Task insert OrderItem is running");
        insertOrderItemDataToAzure.execute(
                "INSERT INTO order_item (id, order_id, product_id, " +
                        "quantity, rate," +
                        "comment)\n" +
                        "VALUES(" +
                        "'" + orderItem.getID()           + "', " +
                        "'" + orderItem.getOrderID()      + "', " +
                        "'" + orderItem.getProductID()    + "', " +
                        "'" + orderItem.getQuantity()     + "', " +
                        "'" + 5                           + "', " +
                        "'"                               + "')\n");
        try {
            insertOrderItemDataToAzure.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Async Task insert OrderItem ended");
    }

    public static List<Order> getOrderList() {
        List<Order> resultOrderList = new ArrayList<>();
        GetOrderDataFromAzure[] getOrderDataFromAzure = new GetOrderDataFromAzure[1];
        getOrderDataFromAzure[0] = new GetOrderDataFromAzure();
        getOrderDataFromAzure[0].execute(
                "SELECT * FROM orders"
        );

        System.out.println("Async Task Order running");
        try {
            getOrderDataFromAzure[0].get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Async Task Order ended");
        if (getOrderDataFromAzure[0].getOrderList() != null){
            return getOrderDataFromAzure[0].getOrderList();
        }
        else return resultOrderList;
    }

    public static List<OrderItem> getOrderItemList(){
        List<OrderItem> orderItems = new ArrayList<>();
        GetOrderItemDataFromAzure[] getOrderItemDataFromAzure = new GetOrderItemDataFromAzure[1];
        getOrderItemDataFromAzure[0] = new GetOrderItemDataFromAzure();
        getOrderItemDataFromAzure[0].execute(
                "SELECT * FROM order_item"
        );
        System.out.println("Async Task OrderItem running");
        try {
            getOrderItemDataFromAzure[0].get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Async Task OrderItem ended");
        if (getOrderItemDataFromAzure[0].getOrderItemList() != null){
            return getOrderItemDataFromAzure[0].getOrderItemList();
        }
        else return orderItems;
    }
}
