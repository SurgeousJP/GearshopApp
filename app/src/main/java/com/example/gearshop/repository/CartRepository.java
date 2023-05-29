package com.example.gearshop.repository;

import android.app.Application;

import com.example.gearshop.model.Product;
import com.example.gearshop.model.ShoppingCartItem;

import java.util.ArrayList;
import java.util.List;

public class CartRepository extends Application {
    private List<Product> ProductList;
    private List<ShoppingCartItem> CartItemList;
    public CartRepository(){
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
