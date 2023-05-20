package com.example.gearshop.activity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gearshop.R;
import com.example.gearshop.fragment.FilterSortBar;
import com.example.gearshop.fragment.ListProduct;

public class ProductsInCategoryActivity extends AppCompatActivity {

    private FilterSortBar CategoryProductFilterSortBar;
    private ListProduct CategoryListProduct;

    private RelativeLayout CartIconLayout;
    private RelativeLayout MoreInformationLayout;
    private RelativeLayout EscapeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userId = prefs.getString("customerId", null);
        CategoryListProduct = new ListProduct();
        CategoryProductFilterSortBar = new FilterSortBar();

//        // Adding fragments
//        CompletableFuture<Void> futureFragment = CompletableFuture.runAsync(() -> {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.add(R.id.fragment_filter_box, CategoryProductFilterSortBar);
//            fragmentTransaction.add(R.id.fragment_grid_view, CategoryListProduct);
//            fragmentTransaction.commit();
//        });
//        try {
//            futureFragment.get();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
        CartIconLayout = findViewById(R.id.cart_layout);
        CartIconLayout.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), CartActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getBaseContext().startActivity(intent);
        });
        MoreInformationLayout = findViewById(R.id.more_info);
        MoreInformationLayout.setOnClickListener(view -> {

        });
        EscapeLayout = findViewById(R.id.escape);
        EscapeLayout.setOnClickListener(view -> {

        });
        TextView titleScreen = (TextView) findViewById(R.id.title_screen);
        titleScreen.setText(userId);
    }
}