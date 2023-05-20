package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.gearshop.R;

public class CartActivity extends AppCompatActivity {

    private View ReturnView;
    private RelativeLayout MoreInformationLayout;
    private RelativeLayout EscapeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
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