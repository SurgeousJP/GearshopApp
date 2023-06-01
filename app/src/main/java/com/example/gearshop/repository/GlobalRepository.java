package com.example.gearshop.repository;

import android.app.Application;

import com.example.gearshop.model.Address;
import com.example.gearshop.model.Customer;
import com.example.gearshop.model.Product;
import com.example.gearshop.model.ShoppingCartItem;

import java.util.ArrayList;
import java.util.List;

public class GlobalRepository extends Application {
    private List<Product> ProductList;
    private List<ShoppingCartItem> CartItemList;
    private static Address CustomerAddress;
    private static Customer CurrentCustomer;
    public Address demoAddress(){
        return new Address(-1, "", "", 1);
    }
    public GlobalRepository(){
        CartItemList = new ArrayList<>();
        ProductList = new ArrayList<>();

        setCustomerAddress(demoAddress());
    }
    public static Customer getCurrentCustomer() {
        return CurrentCustomer;
    }

    public static void setCurrentCustomer(Customer currentCustomer) {
        CurrentCustomer = currentCustomer;
    }

    public List<ShoppingCartItem> getCartItemList(){
        return CartItemList;
    }
    public void setCartItemList(List<ShoppingCartItem> shoppingCartItems){
        this.CartItemList = shoppingCartItems;
    }
    public List<Product> getProductList() {
        return ProductList;
    }

    public void setProductList(List<Product> productList) {
        ProductList = productList;
    }

    public static Address getCustomerAddress() {
        return CustomerAddress;
    }

    public static void setCustomerAddress(Address customerAddress) {
        CustomerAddress = customerAddress;
    }

}
