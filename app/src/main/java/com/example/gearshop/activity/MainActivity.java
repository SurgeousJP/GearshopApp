package com.example.gearshop.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.gearshop.R;
import com.example.gearshop.activity.fragment.FilterSortBar;
import com.example.gearshop.activity.fragment.ListProduct;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userId = prefs.getString("customerId", null);
        FilterSortBar filterSortBar = new FilterSortBar();
        ListProduct listProduct = new ListProduct();

        // Filter Sort Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_screen, filterSortBar);
        fragmentTransaction.add(R.id.main_screen, listProduct);

        // List Item Fragment
        fragmentTransaction.commit();

        TextView titleScreen = (TextView) findViewById(R.id.title_screen);
        titleScreen.setText(userId);
    }
}