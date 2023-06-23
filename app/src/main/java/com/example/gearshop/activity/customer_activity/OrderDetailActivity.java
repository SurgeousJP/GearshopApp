package com.example.gearshop.activity.customer_activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gearshop.R;
import com.example.gearshop.activity.admin_activity.AdminOrderManagementActivity;
import com.example.gearshop.activity.admin_activity.AdminOrderStatusPickActivity;
import com.example.gearshop.activity.customer_activity.CustomerOrderActivity;
import com.example.gearshop.activity.customer_activity.CustomerOrderStatusPickActivity;
import com.example.gearshop.adapter.OrderItemListAdapter;
import com.example.gearshop.model.Address;
import com.example.gearshop.model.Customer;
import com.example.gearshop.model.Order;
import com.example.gearshop.model.OrderItem;
import com.example.gearshop.model.Province;
import com.example.gearshop.repository.GlobalRepository;
import com.example.gearshop.utility.DatabaseHelper;
import com.example.gearshop.utility.MoneyHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {
    boolean ADMIN_MODE = false;
    private TextView OrderIDTextView;
    private TextView OrderCreatedDateTextView;
    private TextView OrderStatusTextView;
    private TextView OrderTransportMethodTextView;
    private TextView OrderStatusInDeliveryBoxTextView;
    private TextView OrderShippingDateTextView;
    private TextView OrderAddressTextView;
    private TextView CustomerPhoneNumber;
    private TextView TypeOfDeliveryTextView;
    private TextView TimeLengthOfDeliveryTypeTextView;
    private RecyclerView OrderDetailRecyclerView;
    private OrderItemListAdapter OrderDetailAdapter;
    private TextView TotalPriceTextView;
    private TextView DeliveryPriceTextView;
    private TextView FinalPriceTextView;
    private TextView ChangeOrderStatusTextView;
    private ActivityResultLauncher<Intent> PickOrderStatusLauncher;
    private List<OrderItem> orderItemList;
    private View ReturnView;
    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);

        Intent getClickedOrderIntent = getIntent();
        Order clickedOrder = (Order) getClickedOrderIntent.getSerializableExtra("clickedOrder");
        ADMIN_MODE = getClickedOrderIntent.getBooleanExtra("ADMIN_MODE", false);

        OrderIDTextView = findViewById(R.id.customer_order_id);
        OrderIDTextView.setText(String.valueOf(clickedOrder.getID()));

        OrderCreatedDateTextView = findViewById(R.id.order_day);
        OrderCreatedDateTextView.setText(
                new SimpleDateFormat("dd/MM/yyyy").format(clickedOrder.getCreatedOnUtc()));

        OrderStatusTextView = findViewById(R.id.order_status);
        OrderStatusInDeliveryBoxTextView = findViewById(R.id.tracking_status_text);

        loadOrderStatusOntoScreen(clickedOrder);
        OrderTransportMethodTextView = findViewById(R.id.order_transport_unit);
        OrderTransportMethodTextView.setText("Giao hàng thường");

        TypeOfDeliveryTextView = findViewById(R.id.type_of_transport_unit_text_order_detail);
        TypeOfDeliveryTextView.setText("Giao hàng thường");

        TimeLengthOfDeliveryTypeTextView = findViewById(R.id.time_of_transport_order_detail);
        TimeLengthOfDeliveryTypeTextView.setText("7 ngày");

        OrderAddressTextView = findViewById(R.id.label_ship_order_detail);
        Address customerAddress;
        Province customerProvince;
        try{
            customerAddress= DatabaseHelper.getAddressList(" WHERE id ='"
                    + clickedOrder.getShippingAddressID() + "'").get(0);
            String customerHouseNumber = customerAddress.getHouseNumber();
            String customerStreet = customerAddress.getStreet();
            customerProvince = DatabaseHelper.getProvinceList(
                    " WHERE id = '" + customerAddress.getProvinceID() +"'").get(0);
            String address;
            if (customerStreet.equals("")){
                address = customerHouseNumber + "\n" + customerProvince.getName();
            }
            else address = customerHouseNumber + "\n" + customerStreet + "\n" + customerProvince.getName();
            OrderAddressTextView.setText(address);

            CustomerPhoneNumber = findViewById(R.id.label_ship_description_order_detail);
            Customer currentCustomer = DatabaseHelper.getCustomerList(
                    " WHERE id ='" + clickedOrder.getCustomerID() +"'").get(0);
            CustomerPhoneNumber.setText(currentCustomer.getPhoneNumber());

            TotalPriceTextView = findViewById(R.id.total_price_order_detail);
            TotalPriceTextView.setText(
                    MoneyHelper.getVietnameseMoneyStringFormatted(clickedOrder.getTotalPrice())
            );

            DeliveryPriceTextView = findViewById(R.id.transport_fee_price_order_detail);
            DeliveryPriceTextView.setText(
                    MoneyHelper.getVietnameseMoneyStringFormatted(customerProvince.getShippingCharge()));

            FinalPriceTextView = findViewById(R.id.final_price_order_detail);
            FinalPriceTextView.setText(
                    MoneyHelper.getVietnameseMoneyStringFormatted(clickedOrder.getTotalPrice()
                            + customerProvince.getShippingCharge()));
        }
        catch(IndexOutOfBoundsException | NullPointerException e){
            Toast.makeText(
                    getBaseContext(),
                    "Lỗi không lấy được thông tin địa chỉ và số điện thoại",
                    Toast.LENGTH_SHORT).show();
        }

        OrderDetailRecyclerView = findViewById(R.id.recycler_view_order_detail);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false);
        orderItemList = DatabaseHelper.getOrderItemList(" WHERE order_id ='" + clickedOrder.getID() + "'");
        OrderDetailAdapter = new OrderItemListAdapter(orderItemList);
        OrderDetailRecyclerView.setLayoutManager(gridLayoutManager);
        OrderDetailRecyclerView.setAdapter(OrderDetailAdapter);
        OrderDetailAdapter.setData(orderItemList);

        OrderDetailRecyclerView.setAdapter(OrderDetailAdapter);

        PickOrderStatusLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK){
                        Intent data = result.getData();
                        if (data != null) {
                            String pickedOrderStatus = data.getStringExtra("pickedOrderStatus");
                            clickedOrder.setStatus(pickedOrderStatus);
                            DatabaseHelper.updateOrderStatusToAzure(clickedOrder);
                            loadOrderStatusOntoScreen(clickedOrder);
                        }
                    }
                });
        ChangeOrderStatusTextView = findViewById(R.id.change_order_status_text);
        ChangeOrderStatusTextView.setOnClickListener(view -> {
            // CONDITION
            Intent intent;
            if (ADMIN_MODE){
                intent = new Intent(getBaseContext(), AdminOrderStatusPickActivity.class);
            }
            else{
                intent = new Intent(getBaseContext(), CustomerOrderStatusPickActivity.class);
            }
            intent.putExtra("currentOrderStatus", clickedOrder.getStatus());
            PickOrderStatusLauncher.launch(intent);
        });

        ReturnView = findViewById(R.id.order_detail_return_view);
        ReturnView.setOnClickListener(view -> {
            Intent intent;
            if (ADMIN_MODE){
                intent = new Intent(getBaseContext(), AdminOrderManagementActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            }
            else{
                intent = new Intent(getBaseContext(), CustomerOrderActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Customer currentCustomer = GlobalRepository.getCurrentCustomer();
                if (currentCustomer == null){
                    intent.putExtra("customerID", 0);
                }
                else{
                    intent.putExtra("customerID", currentCustomer.getID());
                }
            }
            intent.putExtra("ORDER_TYPE", "ALL_ORDER");
            getBaseContext().startActivity(intent);
            finish();

        });
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    private void loadOrderStatusOntoScreen(Order clickedOrder) {
        String orderStatus = clickedOrder.getStatus();
        switch (orderStatus){
            case "PROCESSING":
                OrderStatusTextView.setText("Đang xử lý");
                OrderStatusInDeliveryBoxTextView.setText("Đang xử lý");
                break;
            case "DELIVERY":
                OrderStatusTextView.setText("Đang giao hàng");
                OrderStatusInDeliveryBoxTextView.setText("Đang giao hàng");
                break;
            case "DELIVERED":
                OrderStatusTextView.setText("Đã giao");
                OrderStatusInDeliveryBoxTextView.setText("Đã giao");
                break;
            case "CANCELLED":
                OrderStatusTextView.setText("Đã hủy đơn");
                OrderStatusInDeliveryBoxTextView.setText("Đã hủy đơn");
                break;
            default:
                OrderStatusTextView.setText("N/A");
                OrderStatusInDeliveryBoxTextView.setText("N/A");
        }

        OrderShippingDateTextView = findViewById(R.id.tracking_status_date);
        Date updatedDate = getIncrementedDate(clickedOrder);
        switch (clickedOrder.getStatus()) {
            case "DELIVERED":
                OrderShippingDateTextView.setText("Đã giao ngày: " +
                        new SimpleDateFormat("dd/MM/yyyy").format(updatedDate));
                break;
            case "CANCELLED":
                OrderShippingDateTextView.setText("Đã hủy đơn hàng");
                break;
            case "DELIVERY":
                OrderShippingDateTextView.setText("Sẽ giao ngày: " +
                        new SimpleDateFormat("dd/MM/yyyy").format(updatedDate));
                break;
            case "PROCESSING":
                OrderShippingDateTextView.setText("Đang xử lý đơn hàng, vui lòng chờ cập nhật");
                break;
        }
    }

    @NonNull
    private Date getIncrementedDate(Order clickedOrder) {
        Date orderCreateDate = clickedOrder.getCreatedOnUtc();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(orderCreateDate);
        int numberOfDaysToAdd = 7;
        calendar.add(Calendar.DAY_OF_MONTH, numberOfDaysToAdd);
        return calendar.getTime();
    }
}