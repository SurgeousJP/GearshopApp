package com.example.gearshop.activity.customer_activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gearshop.R;
import com.example.gearshop.repository.CustomerRepository;
import com.example.gearshop.model.Customer;
import com.example.gearshop.repository.GlobalRepository;
import com.example.gearshop.utility.ActivityStartManager;

public class AccountActivity extends AppCompatActivity {
    CustomerRepository customerRepository;

    private TextView ViewAllOrderTextView;
    private View TrailingAllOrderView;
    private TextView ViewAllProcessingOrderTextView;
    private View IconViewAllProcessingOrder;
    private TextView ViewAllDeliveryOrderTextView;
    private View IconViewAllDeliveryOrder;
    private TextView ViewAllDeliveredOrderTextView;
    private View IconViewAllDeliveredOrder;

    private RelativeLayout CartIconLayout;
    private RelativeLayout OptionsLayout;
    private RelativeLayout ReturnHomeLayout;
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
        String userName = userInfo.getLastName() + " " + userInfo.getFirstName();
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

        OptionsLayout = findViewById(R.id.more_info_order_detail);
        OptionsLayout.setOnClickListener(this::showPopupMenu);

        ReturnHomeLayout = findViewById(R.id.escape);
        ReturnHomeLayout.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), HomeActivity.class);
        });

        HomeItem = findViewById(R.id.home_item_category_detail);
        HomeItem.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), HomeActivity.class);
        });

        CategoryItem = findViewById(R.id.category_item_category_detail);
        CategoryItem.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), CategoryActivity.class);
        });

        SearchItem = findViewById(R.id.search_item_category_detail);
        SearchItem.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), SearchActivity.class);
        });

        int customerID = GlobalRepository.getCurrentCustomer().getID();

        View.OnClickListener viewAllOrderListener = view -> {
            Intent intent = new Intent(getBaseContext(), CustomerOrderActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("ORDER_TYPE", "ALL_ORDER");
            intent.putExtra("customerID", customerID);
            getBaseContext().startActivity(intent);
        };

        View.OnClickListener viewAllProcessingOrderListener = view -> {
            Intent intent = new Intent(getBaseContext(), CustomerOrderActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("ORDER_TYPE", "PROCESSING");
            intent.putExtra("customerID", customerID);
            getBaseContext().startActivity(intent);
        };

        View.OnClickListener viewAllDeliveryOrderListener = view -> {
            Intent intent = new Intent(getBaseContext(), CustomerOrderActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("ORDER_TYPE", "DELIVERY");
            intent.putExtra("customerID", customerID);
            getBaseContext().startActivity(intent);
        };

        View.OnClickListener viewAllDeliveredOrderListener = view -> {
            Intent intent = new Intent(getBaseContext(), CustomerOrderActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("ORDER_TYPE", "DELIVERED");
            intent.putExtra("customerID", customerID);
            getBaseContext().startActivity(intent);
        };

        ViewAllOrderTextView = findViewById(R.id.cta_viewmor);
        ViewAllOrderTextView.setOnClickListener(viewAllOrderListener);
        TrailingAllOrderView = findViewById(R.id.trailing_ic);
        TrailingAllOrderView.setOnClickListener(viewAllOrderListener);

        ViewAllProcessingOrderTextView = findViewById(R.id.label_processing);
        ViewAllProcessingOrderTextView.setOnClickListener(viewAllProcessingOrderListener);
        IconViewAllProcessingOrder = findViewById(R.id.view1);
        IconViewAllProcessingOrder.setOnClickListener(viewAllProcessingOrderListener);

        ViewAllDeliveryOrderTextView = findViewById(R.id.label_delivery);
        ViewAllDeliveryOrderTextView.setOnClickListener(viewAllDeliveryOrderListener);
        IconViewAllDeliveryOrder = findViewById(R.id.view2);
        IconViewAllDeliveryOrder.setOnClickListener(viewAllDeliveryOrderListener);

        ViewAllDeliveredOrderTextView = findViewById(R.id.label_delivered);
        ViewAllDeliveredOrderTextView.setOnClickListener(viewAllDeliveredOrderListener);
        IconViewAllDeliveredOrder = findViewById(R.id.view3);
        IconViewAllDeliveredOrder.setOnClickListener(viewAllDeliveredOrderListener);
    }
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.dots_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.logout_item) {
                    Intent intent = new Intent(AccountActivity.this, SignInActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.faq_item) {
                    Intent intent = new Intent(AccountActivity.this, FAQActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }
}
