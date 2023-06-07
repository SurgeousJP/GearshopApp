package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gearshop.R;

public class CustomerManagementActivity extends AppCompatActivity {
    private View CustomerSearchIcon;
    private View CustomerFilterIcon;
    private View CustomerSortIcon;
    private EditText CustomerSearchText;
    private TextView CustomerFilterText;
    private TextView CustomerSortText;
    private View TransitionToProductManagementView;
    private View TransitionToOrderManagementView;
    private View TransitionToAccountManagementView;
    private ListView CustomerManagementListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_customer_mainsceen_management);
        CustomerSearchIcon = findViewById(R.id.customer_management_search_icon);
        CustomerFilterIcon = findViewById(R.id.customer_management_filter_icon);
        CustomerSortIcon = findViewById(R.id.customer_management_sort_icon);
        CustomerSearchText = findViewById(R.id.customer_management_search_text);
        CustomerFilterText = findViewById(R.id.customer_management_label_filter);
        CustomerSortText = findViewById(R.id.customer_management_sort_text);
        TransitionToProductManagementView = findViewById(R.id.product_manage_customer);
        TransitionToAccountManagementView = findViewById(R.id.account_manage_customer);
        TransitionToOrderManagementView = findViewById(R.id.order_manage_customer);
        CustomerManagementListView = findViewById(R.id.listview_customer_management);
    }
}