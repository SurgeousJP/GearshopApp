package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gearshop.R;
import com.example.gearshop.utility.ActivityStartManager;

public class OrderActivity extends AppCompatActivity {
    private String OrderType;
    private View IconReturnView;
    private View FAQView;
    private RelativeLayout ReturnHomeLayout;
    private TextView AllOrder;
    private View UnderlineAllOrder;
    private TextView AllDeliveryOrder;
    private View UnderlineAllDeliveryOrder;
    private TextView AllProcessingOrder;
    private View UnderlineAllProcessingOrder;
    private TextView AllDeliveredOrder;
    private View UnderlineAllDeliveredOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_management);

        AllOrder = findViewById(R.id.content_all);
        UnderlineAllOrder = findViewById(R.id.view1);
        AllProcessingOrder = findViewById(R.id.content_processing);
        UnderlineAllProcessingOrder = findViewById(R.id.view4);
        AllDeliveryOrder = findViewById(R.id.content_delivery);
        UnderlineAllDeliveryOrder = findViewById(R.id.view3);
        AllDeliveredOrder = findViewById(R.id.content_delivered);
        UnderlineAllDeliveredOrder = findViewById(R.id.view2);

        IconReturnView = findViewById(R.id.wayback_icon_order_management);
        IconReturnView.setOnClickListener(view -> {
            finish();
        });
        FAQView = findViewById(R.id.thin);
        FAQView.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), FAQActivity.class);
        });
        ReturnHomeLayout = findViewById(R.id.roundx_order_management);
        ReturnHomeLayout.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), HomeActivity.class);
        });

        Intent getOrderTypeIntent = getIntent();
        OrderType = getOrderTypeIntent.getStringExtra("ORDER_TYPE");
        ResetOrderType();
        switch (OrderType){
            case "ALL_PROCESSING_ORDER":
                AllProcessingOrder.setTextColor(getResources().getColor(R.color.blue));
                UnderlineAllProcessingOrder.setBackgroundColor(getResources().getColor(R.color.blue));
                break;
            case "ALL_DELIVERY_ORDER":
                AllDeliveryOrder.setTextColor(getResources().getColor(R.color.blue));
                UnderlineAllDeliveryOrder.setBackgroundColor(getResources().getColor(R.color.blue));
                break;
            case "ALL_DELIVERED_ORDER":
                AllDeliveredOrder.setTextColor(getResources().getColor(R.color.blue));
                UnderlineAllDeliveredOrder.setBackgroundColor(getResources().getColor(R.color.blue));
                break;
            default:
                AllOrder.setTextColor(getResources().getColor(R.color.blue));
                UnderlineAllOrder.setBackgroundColor(getResources().getColor(R.color.blue));
                break;
        }
    }
    private void ResetOrderType(){
        AllOrder.setTextColor(Color.GRAY);
        UnderlineAllOrder.setBackgroundColor(Color.GRAY);
        AllProcessingOrder.setTextColor(Color.GRAY);
        UnderlineAllProcessingOrder.setBackgroundColor(Color.GRAY);
        AllDeliveryOrder.setTextColor(Color.GRAY);
        UnderlineAllDeliveryOrder.setBackgroundColor(Color.GRAY);
        AllDeliveredOrder.setTextColor(Color.GRAY);
        UnderlineAllDeliveredOrder.setBackgroundColor(Color.GRAY);
    }
}