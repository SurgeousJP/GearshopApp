package com.example.gearshop.utility;

import android.provider.Telephony;

import com.example.gearshop.database.GetAddressDataFromAzure;
import com.example.gearshop.database.GetCustomerDataFromAzure;
import com.example.gearshop.database.GetOrderDataFromAzure;
import com.example.gearshop.database.GetOrderItemDataFromAzure;
import com.example.gearshop.database.GetPaymentMethodDataFromAzure;
import com.example.gearshop.database.GetProductDataFromAzure;
import com.example.gearshop.database.GetProvinceDataFromAzure;
import com.example.gearshop.database.InsertUpdateDataToAzure;
import com.example.gearshop.model.Address;
import com.example.gearshop.model.Customer;
import com.example.gearshop.model.Order;
import com.example.gearshop.model.OrderItem;
import com.example.gearshop.model.PaymentMethod;
import com.example.gearshop.model.Product;
import com.example.gearshop.model.Province;

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
    public static List<PaymentMethod> getPaymentMethod(String particularPaymentMethod){
        String sqlCommand = "SELECT * FROM payment_method";
        if (!particularPaymentMethod.equals("ALL")){
            sqlCommand = sqlCommand + particularPaymentMethod;
        }
        List<PaymentMethod> paymentMethodList = new ArrayList<>();
        GetPaymentMethodDataFromAzure getPaymentMethodDataFromAzure = new GetPaymentMethodDataFromAzure();
        getPaymentMethodDataFromAzure.execute(sqlCommand);
        System.out.println("Async Task get PaymentMethod running");
        try{
            getPaymentMethodDataFromAzure.get();
        }
        catch (ExecutionException | InterruptedException e){
            throw new RuntimeException(e);
        }
        System.out.println("Async Task get PaymentMethod ended");
        if (getPaymentMethodDataFromAzure.getPaymentMethodList() != null)
            return getPaymentMethodDataFromAzure.getPaymentMethodList();
        return new ArrayList<>();

    }
    public static List<Address> getAddressList(String particularAddress) {
        String sqlCommand = "SELECT * FROM address";
        if (!particularAddress.equals("ALL")){
            sqlCommand = sqlCommand + particularAddress;
        }
        GetAddressDataFromAzure[] getAddressDataFromAzure = new GetAddressDataFromAzure[1];
        getAddressDataFromAzure[0] = new GetAddressDataFromAzure();
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
        return new ArrayList<>();
    }
    public static List<Order> getOrderList(String particularOrder) {
        String sqlCommand = "SELECT * FROM orders";
        if (!particularOrder.equals("ALL")){
            sqlCommand = sqlCommand + particularOrder;
        }
        GetOrderDataFromAzure[] getOrderDataFromAzure = new GetOrderDataFromAzure[1];
        getOrderDataFromAzure[0] = new GetOrderDataFromAzure();
        getOrderDataFromAzure[0].execute(sqlCommand);

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
        return new ArrayList<>();
    }

    public static List<OrderItem> getOrderItemList(String particularOrder){
        String sqlCommand = "SELECT * FROM order_item";
        if (!particularOrder.equals("ALL")){
            sqlCommand = sqlCommand + particularOrder;
        }
        GetOrderItemDataFromAzure[] getOrderItemDataFromAzure = new GetOrderItemDataFromAzure[1];
        getOrderItemDataFromAzure[0] = new GetOrderItemDataFromAzure();
        getOrderItemDataFromAzure[0].execute(sqlCommand);
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
        return new ArrayList<>();
    }

    public static List<Province> getProvinceList(String particularProvince){
        String sqlCommand = "SELECT * FROM province";
        if (!particularProvince.equals("ALL")){
            sqlCommand = sqlCommand + particularProvince;
        }
        final GetProvinceDataFromAzure[] getProvinceDataFromAzure = new GetProvinceDataFromAzure[1];
        getProvinceDataFromAzure[0] = new GetProvinceDataFromAzure();
        getProvinceDataFromAzure[0].execute(sqlCommand);

        System.out.println("Async Task running");
        try {
            getProvinceDataFromAzure[0].get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Async Task ended");

        if (getProvinceDataFromAzure[0].getProvinceList() != null)
            return getProvinceDataFromAzure[0].getProvinceList();
        return new ArrayList<>();
    }

    public static List<Product> getProductListFromCategory(int categoryID, String particularProduct) {
        System.out.println("getProductList running");
        String sqlCommand = "SELECT product.*,\n" +
                "\t   discount.id AS discount_id, discount.name AS discount_name, \n" +
                "\t   discount_percentage, start_date_utc, end_date_utc\n" +
                "FROM product\n" +
                "JOIN product_category ON product.category_id = product_category.id\n" +
                "JOIN discount_applied_category ON product_category.id = discount_applied_category.category_id\n" +
                "JOIN discount ON discount.id = discount_applied_category.discount_id\n" +
                "WHERE product.category_id = ?";
        if (!particularProduct.equals("ALL")){
            sqlCommand = sqlCommand + particularProduct;
        }
        final GetProductDataFromAzure[] getProductDataFromAzure = new GetProductDataFromAzure[1];
        getProductDataFromAzure[0] = new GetProductDataFromAzure();
        getProductDataFromAzure[0].setCategoryID(categoryID);
        getProductDataFromAzure[0].execute(sqlCommand);

        System.out.println("Async Task running");
        try {
            getProductDataFromAzure[0].get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Async Task ended");

        if (getProductDataFromAzure[0].getProductList() != null)
            return getProductDataFromAzure[0].getProductList();
        return new ArrayList<>();
    }

    public static List<Product> getProductListGivenID(String particularProduct) {
        String sqlCommand = "SELECT product.*,\n" +
                "\t   discount.id AS discount_id, discount.name AS discount_name, \n" +
                "\t   discount_percentage, start_date_utc, end_date_utc\n" +
                "FROM product\n" +
                "JOIN product_category ON product.category_id = product_category.id\n" +
                "JOIN discount_applied_category ON product_category.id = discount_applied_category.category_id\n" +
                "JOIN discount ON discount.id = discount_applied_category.discount_id\n";
        if (!particularProduct.equals("ALL")){
            sqlCommand = sqlCommand + particularProduct;
        }
        final GetProductDataFromAzure[] getProductDataFromAzure = new GetProductDataFromAzure[1];
        getProductDataFromAzure[0] = new GetProductDataFromAzure();
        getProductDataFromAzure[0].execute(sqlCommand);

        System.out.println("Async Task running");
        try {
            getProductDataFromAzure[0].get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Async Task ended");

        if (getProductDataFromAzure[0].getProductList() != null)
            return getProductDataFromAzure[0].getProductList();
        return new ArrayList<>();
    }

    public static List<Customer> getCustomerList(String particularCustomer){
        String sqlCommand = "SELECT * FROM customer";
        if (!particularCustomer.equals("ALL")){
            sqlCommand = sqlCommand + particularCustomer;
        }
        GetCustomerDataFromAzure getCustomerDataFromAzure = new GetCustomerDataFromAzure();
        getCustomerDataFromAzure.execute(sqlCommand);

        System.out.println("Async Task get Customer running");
        try {
            getCustomerDataFromAzure.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Async Task get Customer ended");
        if (getCustomerDataFromAzure.getCustomerList() != null){
            return getCustomerDataFromAzure.getCustomerList();
        }
        return new ArrayList<>();
    }

}
