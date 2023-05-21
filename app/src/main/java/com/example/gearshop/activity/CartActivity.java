package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.gearshop.R;
import com.example.gearshop.adapter.CartListAdapter;
import com.example.gearshop.model.Cart;
import com.example.gearshop.model.Product;
import com.example.gearshop.model.ShoppingCartItem;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    private List<ShoppingCartItem> CartItemList;
    private List<Product> ProductList;
    private View ReturnView;
    private ListView CartListView;
    private RelativeLayout MoreInformationLayout;
    private RelativeLayout EscapeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        CartItemList = ((Cart) getApplication()).getCartItemList();
        ProductList = ((Cart) getApplication()).getProductList();
        CartListView = findViewById(R.id.list_product);
        CartListAdapter cartListAdapter = new CartListAdapter(getBaseContext(), R.id.list_product,
                CartItemList, ProductList);
        CartListView.setAdapter(cartListAdapter);
        ReturnView = findViewById(R.id.wayback_icon_cart);
        ReturnView.setOnClickListener(view -> {
            setResult(Activity.RESULT_OK);
            finish();
        });
        MoreInformationLayout = findViewById(R.id.dots_cart);
        MoreInformationLayout.setOnClickListener(view -> {

        });
        EscapeLayout = findViewById(R.id.escape_cart);
        EscapeLayout.setOnClickListener(view -> {

        });

    }
}