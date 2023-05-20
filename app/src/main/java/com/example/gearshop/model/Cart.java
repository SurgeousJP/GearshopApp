package com.example.gearshop.model;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class Cart extends Application {
    private List<ShoppingCartItem> CartItemList = new ArrayList<>();

    public List<ShoppingCartItem> getCartItemList(){
        return CartItemList;
    }
    public void setCartItemList(List<ShoppingCartItem> shoppingCartItems){
        this.CartItemList = shoppingCartItems;
    }
}
