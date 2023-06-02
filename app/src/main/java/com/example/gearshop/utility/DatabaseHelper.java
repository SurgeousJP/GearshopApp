package com.example.gearshop.utility;

import android.provider.Telephony;

import com.example.gearshop.database.GetAddressDataFromAzure;
import com.example.gearshop.database.GetOrderDataFromAzure;
import com.example.gearshop.database.GetOrderItemDataFromAzure;
import com.example.gearshop.database.InsertUpdateDataToAzure;
import com.example.gearshop.model.Address;
import com.example.gearshop.model.Order;
import com.example.gearshop.model.OrderItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
public class DatabaseHelper {
    public static void updateAddressToAzure(Address oldAddress, Address newAddress){
        InsertUpdateDataToAzure updateAddressDataToAzure = new InsertUpdateDataToAzure();
        System.out.println("Async Task update Address is running");
        updateAddressDataToAzure.execute("UPDATE address\n" +
                "SET house_number = '" + newAddress.getHouseNumber() + "',\n" +
                "    street = N'"      + newAddress.getStreet() + "',\n" +
                "    province_id = '"  + newAddress.getProvinceID() +"'\n" +
                "WHERE id = '" + oldAddress.getID() +"';");
        try{
            updateAddressDataToAzure.get();
        }
        catch (ExecutionException | InterruptedException e){
            throw new RuntimeException(e);
        }
        System.out.println("Async Task update Address ended");
    }
    public static void insertAddressToAzure(Address newAddress){
        InsertUpdateDataToAzure insertAddressDataToAzure = new InsertUpdateDataToAzure();

        System.out.println("Async Task insert Address is running");
        insertAddressDataToAzure.execute(
                "INSERT INTO address (id, house_number, " +
                        "street," +
                        " province_id)\n" +
                        "VALUES(" + newAddress.getID()        + ", " +
                        "'" + newAddress.getHouseNumber()     + "', " +
                        "'" + newAddress.getStreet()          + "', " +
                        "'" + newAddress.getProvinceID()      + "')\n");
        try {
            insertAddressDataToAzure.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Async Task insert Address ended");
    }

    public static void insertOrderToAzure(Order newOrder){
        String inputPattern = "yyyy-MM-dd"; // Input date format
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.getDefault());
        InsertUpdateDataToAzure insertOrderDataToAzure = new InsertUpdateDataToAzure();

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
        InsertUpdateDataToAzure insertOrderItemDataToAzure = new InsertUpdateDataToAzure();

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
    public static List<Address> getAddressList(String... particularAddress) {
        String sqlCommand = "SELECT * FROM address";
        if (particularAddress != null){
            sqlCommand = sqlCommand + particularAddress[0];
        }
        List<Address> resultAddressList = new ArrayList<>();
        GetAddressDataFromAzure[] getAddressDataFromAzure = new GetAddressDataFromAzure[1];
        getAddressDataFromAzure[0] = new GetAddressDataFromAzure();
        System.out.println(sqlCommand);
        getAddressDataFromAzure[0].execute(sqlCommand);

        System.out.println("Async Task Address running");
        try {
            getAddressDataFromAzure[0].get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Async Task Address ended");
        if (getAddressDataFromAzure[0].getAddressList() != null){
            return getAddressDataFromAzure[0].getAddressList();
        }
        else return resultAddressList;
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
