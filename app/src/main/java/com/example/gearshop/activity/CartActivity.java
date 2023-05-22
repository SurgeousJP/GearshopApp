package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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
    private RecyclerView CartRecyclerView;
    private RelativeLayout MoreInformationLayout;
    private RelativeLayout EscapeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        CartItemList = ((Cart) getApplication()).getCartItemList();
        ProductList = ((Cart) getApplication()).getProductList();
        CartRecyclerView = findViewById(R.id.list_product);
        CartListAdapter cartListAdapter = new CartListAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1,RecyclerView.VERTICAL,false);
        CartRecyclerView.setLayoutManager(gridLayoutManager);
        CartRecyclerView.setAdapter(cartListAdapter);
//        CartRecyclerView.setHasFixedSize(true);
//        CartRecyclerView.setItemViewCacheSize(5);
//        CartRecyclerView.setDrawingCacheEnabled(true);
//        CartRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        cartListAdapter.setData(ProductList);
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