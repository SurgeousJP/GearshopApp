package com.example.gearshop.model;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class Cart extends Application {
    private List<Product> ProductList;
    private List<ShoppingCartItem> CartItemList;
    public Cart(){
        CartItemList = new ArrayList<>();
        ProductList = new ArrayList<>();
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

}
