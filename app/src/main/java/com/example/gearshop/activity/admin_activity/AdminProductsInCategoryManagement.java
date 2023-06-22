package com.example.gearshop.activity.admin_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gearshop.R;
import com.example.gearshop.model.Category;
import com.example.gearshop.utility.ActivityStartManager;

public class AdminProductsInCategoryManagement extends AppCompatActivity {

    private View ReturnView;
    private TextView CategoryNameTextView;
    private View AddProductView;
    private EditText SearchEditText;
    private View SearchIconView;
    private View FilterIconView;
    private TextView FilterTextView;
    private View SortIconView;
    private TextView SortTextView;
    private GridView ProductGridView;
    private View CustomerManagementView;
    private View OrderManagementView;
    private View AccountManagementView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_product_category_management);

        Intent getCategoryDataIntent = getIntent();
        Category clickedCategory = (Category) getCategoryDataIntent.getSerializableExtra("clickedCategory");
        if (clickedCategory == null){
            Toast.makeText(getBaseContext(), "Lỗi danh mục", Toast.LENGTH_SHORT).show();
            return;
        }

        ReturnView = findViewById(R.id.return_admin_product_management);
        ReturnView.setOnClickListener(view -> {
            finish();
        });

        CategoryNameTextView = findViewById(R.id.admin_product_in_category_management);
        CategoryNameTextView.setText("Quản lý sản phẩm - " + clickedCategory.getName());

        AddProductView = findViewById(R.id.add_new_product_to_database);
        AddProductView.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), AdminAddNewProductActivity.class);
        });

        SearchEditText = findViewById(R.id.search_text_admin_product);
        SearchIconView = findViewById(R.id.search_icon_admin_product);
        SearchIconView.setOnClickListener(view -> {

        });

        FilterIconView = findViewById(R.id.filter_icon_admin_product);
        FilterTextView = findViewById(R.id.label_filter_admin_product);

        SortIconView = findViewById(R.id.sort_icon_admin_product);
        SortTextView = findViewById(R.id.label_sort_admin_product);

        ProductGridView = findViewById(R.id.gridview_product_management);

        CustomerManagementView = findViewById(R.id.customer_manage_category);
        CustomerManagementView.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), AdminCustomerManagementActivity.class);
        });

        OrderManagementView = findViewById(R.id.order_manage_category);
        OrderManagementView.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), AdminOrderManagementActivity.class);
        });

        AccountManagementView = findViewById(R.id.account_management_category);
        AccountManagementView.setOnClickListener(view -> {
        });
    }
}