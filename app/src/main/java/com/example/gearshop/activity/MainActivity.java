package com.example.gearshop.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gearshop.R;
import com.example.gearshop.activity.fragment.FilterSortBar;
import com.example.gearshop.activity.fragment.ListProduct;
import com.example.gearshop.database.SelectSQL;
import com.example.gearshop.model.Product;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userId = prefs.getString("customerId", null);
        FilterSortBar filterSortBar = new FilterSortBar();
        ListProduct listProduct = new ListProduct();

        CompletableFuture<Void> futureFragment = CompletableFuture.runAsync(() -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.main_screen, filterSortBar);
            fragmentTransaction.add(R.id.main_screen, listProduct);
            fragmentTransaction.commit();
        });

        // Chờ cho CompletableFuture hoàn thành
        try {
            futureFragment.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        final SelectSQL[] selectSQL = new SelectSQL[1];
        CompletableFuture<Void> futureData = CompletableFuture.runAsync(()->{
            try{
                selectSQL[0] = new SelectSQL();
                selectSQL[0].execute("SELECT * FROM product WHERE category_id = 1");
                System.out.println("Async Task running");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }).thenAccept(result -> {
                System.out.println("Async Task ended");
                listProduct.GetProductDataFromAzure(selectSQL[0].getProductList());
        });
        TextView titleScreen = (TextView) findViewById(R.id.title_screen);
//        titleScreen.setText(userId);
        titleScreen.setText("001");
        Toast.makeText(this, "ENDED ACTIVITY", Toast.LENGTH_SHORT).show();
    }
}