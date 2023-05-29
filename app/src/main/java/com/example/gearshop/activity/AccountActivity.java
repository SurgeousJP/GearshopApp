package com.example.gearshop.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gearshop.R;
import com.example.gearshop.controller.CustomerRepository;
import com.example.gearshop.model.Customer;

public class AccountActivity extends AppCompatActivity {
    CustomerRepository customerRepository;


    private RelativeLayout CartIconLayout;
    private RelativeLayout MoreInformationLayout;
    private RelativeLayout EscapeLayout;
    private RelativeLayout HomeItem;
    private RelativeLayout CategoryItem;
    private RelativeLayout SearchItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_main);

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userId = prefs.getString("customerId", null);
        customerRepository = new CustomerRepository();
        Customer userInfo = customerRepository.getCustomerById(userId);


        TextView tvUserName = (TextView) findViewById(R.id.title_username);
        String userName = userInfo.getFirstName() + userInfo.getLastName();
        tvUserName.setText(userName);

        ConstraintLayout btnEditUserInfo = (ConstraintLayout) findViewById(R.id.user_info_box);
        btnEditUserInfo.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), EditUserInfoActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getBaseContext().startActivity(intent);
        });


        CartIconLayout = findViewById(R.id.cart_layout);
        CartIconLayout.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), CartActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getBaseContext().startActivity(intent);
        });

        MoreInformationLayout = findViewById(R.id.more_info_order_detail);
        MoreInformationLayout.setOnClickListener(view -> {

        });

        EscapeLayout = findViewById(R.id.escape);
        EscapeLayout.setOnClickListener(view -> {

        });

        HomeItem = findViewById(R.id.home_item_category_detail);
        HomeItem.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), ProductsInCategoryActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getBaseContext().startActivity(intent);
            finish();
        });

        CategoryItem = findViewById(R.id.category_item_category_detail);
        CategoryItem.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), CategoryActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getBaseContext().startActivity(intent);
            finish();
        });

        SearchItem = findViewById(R.id.search_item_category_detail);
        SearchItem.setOnClickListener(view -> {

        });
    }
}
