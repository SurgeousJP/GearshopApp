package com.example.gearshop.activity.customer_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gearshop.R;
import com.example.gearshop.adapter.OrderListAdapter;
import com.example.gearshop.model.Order;
import com.example.gearshop.utility.ActivityStartManager;
import com.example.gearshop.utility.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class CustomerOrderActivity extends AppCompatActivity {
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
    private TextView AllCancelledOrder;
    private View UnderlineAllCancelledOrder;
    private ListView OrderListView;
    private List<Order> CustomerOrderList;
    private List<Order> CustomerParticularTypeOrderList;
    private OrderListAdapter CustomerOrderAdapter;
    private int CustomerID;
    private RelativeLayout OptionsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_management);

        Intent getDataIntent = getIntent();
        OrderType = getDataIntent.getStringExtra("ORDER_TYPE");
        CustomerID = getDataIntent.getIntExtra("customerID", 0);

        AllOrder = findViewById(R.id.content_all);
        AllOrder.setOnClickListener(view -> {
            ResetOrderType();
            loadAllOrderOntoScreen();
        });
        UnderlineAllOrder = findViewById(R.id.view1);
        AllProcessingOrder = findViewById(R.id.content_processing);
        AllProcessingOrder.setOnClickListener(view -> {
            ResetOrderType();
            loadProcessingOrderOntoScreen();
        });
        UnderlineAllProcessingOrder = findViewById(R.id.view3);
        AllDeliveryOrder = findViewById(R.id.content_delivery);
        AllDeliveryOrder.setOnClickListener(view -> {
            ResetOrderType();
            loadDeliveryOrderOntoScreen();
        });
        UnderlineAllDeliveryOrder = findViewById(R.id.order_admin_delivery_view);
        AllDeliveredOrder = findViewById(R.id.content_delivered);
        AllDeliveredOrder.setOnClickListener(view -> {
            ResetOrderType();
            loadDeliveredOrderOntoScreen();
        });
        UnderlineAllDeliveredOrder = findViewById(R.id.view2);
        AllCancelledOrder = findViewById(R.id.content_cancel);
        AllCancelledOrder.setOnClickListener(view -> {
            ResetOrderType();
            loadCancelledOrderOntoScreen();
        });
        UnderlineAllCancelledOrder = findViewById(R.id.view5);

        OrderListView = findViewById(R.id.list_view_order_management);
        getNewOrderDataAndLoadOntoScreen();

        IconReturnView = findViewById(R.id.wayback_icon_order_management);
        IconReturnView.setOnClickListener(view -> {
            finish();
        });

        ReturnHomeLayout = findViewById(R.id.roundx_order_management);
        ReturnHomeLayout.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), HomeActivity.class);
        });

        OptionsLayout = findViewById(R.id.dots_order_management);
        OptionsLayout.setOnClickListener(this::showPopupMenu);

        OrderListView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getBaseContext(), OrderDetailActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("clickedOrder",(Order) adapterView.getItemAtPosition(i));
            startActivity(intent);
            finish();
            return true;
        });
    }

    private void getNewOrderDataAndLoadOntoScreen() {
        if (CustomerID == 0){
            CustomerOrderList = DatabaseHelper.getOrderList("ALL");
        }
        else CustomerOrderList = DatabaseHelper.getOrderList(
                " WHERE customer_id ='" + CustomerID +"'");
        CustomerOrderAdapter = new OrderListAdapter(getBaseContext(),
                R.layout.list_order_status, CustomerOrderList);
        OrderListView.setAdapter(CustomerOrderAdapter);

        ResetOrderType();
        switch (OrderType){
            case "PROCESSING":
                loadProcessingOrderOntoScreen();
                break;
            case "DELIVERY":
                loadDeliveryOrderOntoScreen();
                break;
            case "DELIVERED":
                loadDeliveredOrderOntoScreen();
                break;
            case "ALL_ORDER":
                loadAllOrderOntoScreen();
                break;
        }
    }

    private void loadAllOrderOntoScreen() {
        AllOrder.setTextColor(getResources().getColor(R.color.blue));
        UnderlineAllOrder.setBackgroundColor(getResources().getColor(R.color.blue));
        CustomerParticularTypeOrderList = CustomerOrderList;
        CustomerOrderAdapter.UpdateDataToAdapter(CustomerParticularTypeOrderList);
    }

    private void loadDeliveredOrderOntoScreen() {
        AllDeliveredOrder.setTextColor(getResources().getColor(R.color.blue));
        UnderlineAllDeliveredOrder.setBackgroundColor(getResources().getColor(R.color.blue));
        CustomerParticularTypeOrderList = getAllOrderOfSameType("DELIVERED");
        CustomerOrderAdapter.UpdateDataToAdapter(CustomerParticularTypeOrderList);
    }

    private void loadDeliveryOrderOntoScreen() {
        AllDeliveryOrder.setTextColor(getResources().getColor(R.color.blue));
        UnderlineAllDeliveryOrder.setBackgroundColor(getResources().getColor(R.color.blue));
        CustomerParticularTypeOrderList = getAllOrderOfSameType("DELIVERY");
        CustomerOrderAdapter.UpdateDataToAdapter(CustomerParticularTypeOrderList);
    }

    private void loadProcessingOrderOntoScreen() {
        AllProcessingOrder.setTextColor(getResources().getColor(R.color.blue));
        UnderlineAllProcessingOrder.setBackgroundColor(getResources().getColor(R.color.blue));
        CustomerParticularTypeOrderList = getAllOrderOfSameType("PROCESSING");
        CustomerOrderAdapter.UpdateDataToAdapter(CustomerParticularTypeOrderList);
    }

    private void loadCancelledOrderOntoScreen(){
        AllCancelledOrder.setTextColor(getResources().getColor(R.color.blue));
        UnderlineAllCancelledOrder.setBackgroundColor(getResources().getColor(R.color.blue));
        CustomerParticularTypeOrderList = getAllOrderOfSameType("CANCELLED");
        CustomerOrderAdapter.UpdateDataToAdapter(CustomerParticularTypeOrderList);
    }

    private List<Order> getAllOrderOfSameType(String orderType){
        List<Order> result = new ArrayList<>();
        for (int i = 0; i < CustomerOrderList.size(); i++){
            if (CustomerOrderList.get(i).getStatus().equals(orderType)){
                result.add(CustomerOrderList.get(i));
            }
        }
        return result;
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
        AllCancelledOrder.setTextColor(Color.GRAY);
        UnderlineAllCancelledOrder.setBackgroundColor(Color.GRAY);
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
                    Intent intent = new Intent(CustomerOrderActivity.this, SignInActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.faq_item) {
                    Intent intent = new Intent(CustomerOrderActivity.this, FAQActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }
}