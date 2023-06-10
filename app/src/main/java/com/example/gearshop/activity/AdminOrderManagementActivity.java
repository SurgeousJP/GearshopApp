package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gearshop.R;
import com.example.gearshop.adapter.OrderListAdapter;
import com.example.gearshop.fragment.OrderSortBottomSheetDialogFragment;
import com.example.gearshop.model.Customer;
import com.example.gearshop.model.Order;
import com.example.gearshop.utility.ActivityStartManager;
import com.example.gearshop.utility.DatabaseHelper;
import com.example.gearshop.utility.VietnameseStringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    private List<Order> OrderList;
    private OrderListAdapter OrderAdapter;
    private ListView OrderListView;
    private List<Order> CustomerParticularTypeOrderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_order_mainscreen_management);

        OrderList = DatabaseHelper.getOrderList("ALL");
        OrderAdapter = new OrderListAdapter(getBaseContext(), R.layout.list_order_status, OrderList);
        OrderListView = findViewById(R.id.listview_admin_order_management);
        OrderListView.setAdapter(OrderAdapter);

        SearchTextView = findViewById(R.id.admin_order_management_search_text);
        SearchIcon = findViewById(R.id.admin_order_management_search_icon);
        SearchIcon.setOnClickListener(view -> {
            String searchText = SearchTextView.getText().toString();
            int customerID = tryParseInt(searchText, -1);
            if (customerID == -1) return;
            List<Order> searchResults = searchForOrders(customerID);
            OrderAdapter.UpdateDataToAdapter(searchResults);
        });

        FilterTextView = findViewById(R.id.admin_order_label_filter);
        FilterIcon = findViewById(R.id.admin_order_filter_icon);

        View.OnClickListener sortOrderListener = view -> {
            OrderSortBottomSheetDialogFragment dialogFragment = new OrderSortBottomSheetDialogFragment(
                    OrderAdapter, OrderList);
            dialogFragment.show(getSupportFragmentManager(), dialogFragment.getTag());
        };
        SortTextView = findViewById(R.id.admin_order_label_sort);
        SortTextView.setOnClickListener(sortOrderListener);
        SortIcon = findViewById(R.id.admin_order_sort_icon);
        SortIcon.setOnClickListener(sortOrderListener);

        AllOrderTextView = findViewById(R.id.admin_order_content_all);
        AllOrderTextView.setOnClickListener(view -> {
            ResetOrderType();
            loadAllOrderOntoScreen();
        });
        AllOrderView = findViewById(R.id.admin_order_view_all);

        AllProcessingOrderTextView = findViewById(R.id.order_admin_processing);
        AllProcessingOrderTextView.setOnClickListener(view -> {
            ResetOrderType();
            loadProcessingOrderOntoScreen();
        });
        AllProcessingOrderView = findViewById(R.id.order_admin_processing_view);

        AllDeliveredOrderTextView = findViewById(R.id.order_admin_delivered);
        AllDeliveredOrderTextView.setOnClickListener(view -> {
            ResetOrderType();
            loadDeliveredOrderOntoScreen();
        });
        AllDeliveredOrderView = findViewById(R.id.order_admin_delivered_view);

        AllDeliveryOrderTextView = findViewById(R.id.order_admin_delivery);
        AllDeliveryOrderTextView.setOnClickListener(view -> {
            ResetOrderType();
            loadDeliveryOrderOntoScreen();
        });
        AllDeliveryOrderView = findViewById(R.id.order_admin_delivery_view);

        AllCancelledOrderTextView = findViewById(R.id.order_admin_cancel);
        AllCancelledOrderTextView.setOnClickListener(view -> {
            ResetOrderType();
            loadCancelledOrderOntoScreen();
        });
        AllCancelledOrderView = findViewById(R.id.order_admin_cancel_view);

        ProductManagementView = findViewById(R.id.product_manage_order);
        ProductManagementView.setOnClickListener(view -> {

        });

        CustomerManagementView = findViewById(R.id.customer_manage_order);
        CustomerManagementView.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), AdminCustomerManagementActivity.class);
        });

        AccountManagementView = findViewById(R.id.account_manage_order);
        AccountManagementView.setOnClickListener(view -> {

        });

        OrderListView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getBaseContext(), OrderDetailActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("clickedOrder",(Order) adapterView.getItemAtPosition(i));
            intent.putExtra("ADMIN_MODE", true);
            startActivity(intent);
            finish();
            return true;
        });
    }

    public int tryParseInt(String value, int defaultVal) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            Toast.makeText(getBaseContext(), "Mã khách hàng không hợp lệ !", Toast.LENGTH_SHORT).show();
            return defaultVal;
        }
    }

    protected List<Order> searchForOrders(int customerID){
        List<Order> result = new ArrayList<>();
        for (int i = 0; i < CustomerParticularTypeOrderList.size(); i++){
            if (checkOrderContainsInformation(CustomerParticularTypeOrderList.get(i), customerID)){
                result.add(CustomerParticularTypeOrderList.get(i));
            }
        }
        return result;
    }
    protected boolean checkOrderContainsInformation(Order order, int customerID){
        return order.getCustomerID() == customerID;
    }

    private void loadAllOrderOntoScreen() {
        AllOrderTextView.setTextColor(getResources().getColor(R.color.blue));
        AllOrderView.setBackgroundColor(getResources().getColor(R.color.blue));
        CustomerParticularTypeOrderList = OrderList;
        OrderAdapter.UpdateDataToAdapter(CustomerParticularTypeOrderList);
    }

    private void loadDeliveredOrderOntoScreen() {
        AllDeliveredOrderTextView.setTextColor(getResources().getColor(R.color.blue));
        AllDeliveredOrderView.setBackgroundColor(getResources().getColor(R.color.blue));
        CustomerParticularTypeOrderList = getAllOrderOfSameType("DELIVERED");
        OrderAdapter.UpdateDataToAdapter(CustomerParticularTypeOrderList);
    }

    private void loadDeliveryOrderOntoScreen() {
        AllDeliveryOrderTextView.setTextColor(getResources().getColor(R.color.blue));
        AllDeliveryOrderView.setBackgroundColor(getResources().getColor(R.color.blue));
        CustomerParticularTypeOrderList = getAllOrderOfSameType("DELIVERY");
        OrderAdapter.UpdateDataToAdapter(CustomerParticularTypeOrderList);
    }

    private void loadProcessingOrderOntoScreen() {
        AllProcessingOrderTextView.setTextColor(getResources().getColor(R.color.blue));
        AllProcessingOrderView.setBackgroundColor(getResources().getColor(R.color.blue));
        CustomerParticularTypeOrderList = getAllOrderOfSameType("PROCESSING");
        OrderAdapter.UpdateDataToAdapter(CustomerParticularTypeOrderList);
    }

    private void loadCancelledOrderOntoScreen(){
        AllCancelledOrderTextView.setTextColor(getResources().getColor(R.color.blue));
        AllCancelledOrderView.setBackgroundColor(getResources().getColor(R.color.blue));
        CustomerParticularTypeOrderList = getAllOrderOfSameType("CANCELLED");
        OrderAdapter.UpdateDataToAdapter(CustomerParticularTypeOrderList);
    }

    private List<Order> getAllOrderOfSameType(String orderType){
        List<Order> result = new ArrayList<>();
        for (int i = 0; i < OrderList.size(); i++){
            if (OrderList.get(i).getStatus().equals(orderType)){
                result.add(OrderList.get(i));
            }
        }
        return result;
    }
    private void ResetOrderType(){
        AllOrderTextView.setTextColor(Color.GRAY);
        AllOrderView.setBackgroundColor(Color.GRAY);
        AllProcessingOrderTextView.setTextColor(Color.GRAY);
        AllProcessingOrderView.setBackgroundColor(Color.GRAY);
        AllDeliveryOrderTextView.setTextColor(Color.GRAY);
        AllDeliveryOrderView.setBackgroundColor(Color.GRAY);
        AllDeliveredOrderTextView.setTextColor(Color.GRAY);
        AllDeliveredOrderView.setBackgroundColor(Color.GRAY);
        AllCancelledOrderTextView.setTextColor(Color.GRAY);
        AllCancelledOrderView.setBackgroundColor(Color.GRAY);
    }
}