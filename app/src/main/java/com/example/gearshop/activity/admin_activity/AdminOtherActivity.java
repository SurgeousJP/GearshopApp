package com.example.gearshop.activity.admin_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.gearshop.R;
import com.example.gearshop.activity.customer_activity.SignInActivity;
import com.example.gearshop.utility.ActivityStartManager;

public class AdminOtherActivity extends AppCompatActivity {

    private View StatisticsTransitionView;
    private View AdminInfoTransitionView;
    private View LogoutView;
    private View ProductManagementView;
    private View CustomerManagementView;
    private View OrderManagementView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_other);

        StatisticsTransitionView = findViewById(R.id.transition_to_statistics);
        AdminInfoTransitionView = findViewById(R.id.transition_to_admin_info);
        LogoutView = findViewById(R.id.logout_view);
        ProductManagementView = findViewById(R.id.other_product_management_view);
        CustomerManagementView = findViewById(R.id.other_customer_management_view);
        OrderManagementView = findViewById(R.id.other_order_management_view);

        StatisticsTransitionView.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), AdminStatisticActivity.class);
        });

        AdminInfoTransitionView.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), AdminInfoActivity.class);
        });

        LogoutView.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), SignInActivity.class);
            finish();
        });

        ProductManagementView.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), AdminProductCategoryManagementActivity.class);
            finish();
        });

        CustomerManagementView.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), AdminCustomerManagementActivity.class);
            finish();
        });

        OrderManagementView.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), AdminOrderManagementActivity.class);
        });
    }
}