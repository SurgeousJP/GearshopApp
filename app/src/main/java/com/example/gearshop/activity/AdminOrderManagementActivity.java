package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gearshop.R;

public class AdminOrderManagementActivity extends AppCompatActivity {
    private EditText SearchTextView;
    private View SearchIcon;
    private View FilterIcon;
    private TextView FilterTextView;
    private View SortIcon;
    private TextView SortTextView;
    private TextView AllOrderTextView;
    private View AllOrderView;
    private TextView AllDeliveredOrderTextView;
    private View AllDeliveredOrderView;
    private TextView AllProcessingOrderTextView;
    private View AllProcessingOrderView;
    private TextView AllDeliveryOrderTextView;
    private View AllDeliveryOrderView;
    private TextView AllCancelledOrderTextView;
    private View AllCancelledOrderView;
    private View ProductManagementView;
    private View CustomerManagementView;
    private View AccountManagementView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_order_mainscreen_management);

        SearchTextView = findViewById(R.id.admin_order_management_search_text);
        SearchIcon = findViewById(R.id.admin_order_management_search_icon);
        SearchIcon.setOnClickListener(view -> {

        });

        FilterTextView = findViewById(R.id.admin_order_label_filter);
        FilterIcon = findViewById(R.id.admin_order_filter_icon);
        FilterIcon.setOnClickListener(view -> {

        });

        SortTextView = findViewById(R.id.admin_order_label_sort);
        SortIcon = findViewById(R.id.admin_order_sort_icon);
        SortIcon.setOnClickListener(view -> {

        });

        AllOrderTextView = findViewById(R.id.admin_order_content_all);
        AllOrderView = findViewById(R.id.admin_order_view_all);

        AllProcessingOrderTextView = findViewById(R.id.order_admin_processing);
        AllProcessingOrderView = findViewById(R.id.order_admin_processing_view);

        AllDeliveredOrderTextView = findViewById(R.id.order_admin_delivered);
        AllDeliveredOrderView = findViewById(R.id.order_admin_delivered_view);

        AllDeliveryOrderTextView = findViewById(R.id.order_admin_delivery);
        AllDeliveryOrderView = findViewById(R.id.order_admin_delivery_view);

        AllCancelledOrderTextView = findViewById(R.id.order_admin_cancel);
        AllCancelledOrderView = findViewById(R.id.order_admin_cancel_view);

        ProductManagementView = findViewById(R.id.product_manage_order);
        ProductManagementView.setOnClickListener(view -> {

        });

        CustomerManagementView = findViewById(R.id.customer_manage_order);
        CustomerManagementView.setOnClickListener(view -> {

        });

        AccountManagementView = findViewById(R.id.account_manage_order);
        AccountManagementView.setOnClickListener(view -> {

        });
    }
}