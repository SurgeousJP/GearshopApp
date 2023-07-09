package com.example.gearshop.activity.admin_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.gearshop.R;

public class AdminOrderStatusPickActivity extends AppCompatActivity {

    private ConstraintLayout OrderProcessingLayout;
    private TextView OrderProcessingTextView;
    private ConstraintLayout TickedOrderProcessingLayout;

    private ConstraintLayout OrderDeliveryLayout;
    private TextView OrderDeliveryTextView;
    private ConstraintLayout TickedOrderDeliveryLayout;

    private ConstraintLayout OrderDeliveredLayout;
    private TextView OrderDeliveredTextView;
    private ConstraintLayout TickedOrderDeliveredLayout;

    private ConstraintLayout OrderCancelledLayout;
    private TextView OrderCancelledTextView;
    private ConstraintLayout TickedOrderCancelledLayout;

    private TextView ApplyChangeOrderStatusTextView;
    private View CancelChangeOrderStatusView;
    private String CurrentOrderStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_order_list_item_status);

        OrderProcessingLayout = findViewById(R.id.list_item_1_status);
        OrderProcessingTextView = findViewById(R.id.label_option1_status);
        TickedOrderProcessingLayout = findViewById(R.id.components_1_status);
        View.OnClickListener setOrderProgressingStatus = view -> {
            setOrderStatusOptionUI(
                    OrderProcessingLayout, OrderProcessingTextView, TickedOrderProcessingLayout);
            CurrentOrderStatus = "PROCESSING";
        };
        OrderProcessingLayout.setOnClickListener(setOrderProgressingStatus);
        OrderProcessingTextView.setOnClickListener(setOrderProgressingStatus);
        TickedOrderProcessingLayout.setOnClickListener(setOrderProgressingStatus);

        OrderDeliveryLayout = findViewById(R.id.list_item_2_status);
        OrderDeliveryTextView = findViewById(R.id.label_option2_status);
        TickedOrderDeliveryLayout = findViewById(R.id.components_2_status);
        View.OnClickListener setOrderDeliveryStatus = view -> {
            setOrderStatusOptionUI(
                    OrderDeliveryLayout, OrderDeliveryTextView, TickedOrderDeliveryLayout);
            CurrentOrderStatus = "DELIVERY";
        };
        OrderDeliveryLayout.setOnClickListener(setOrderDeliveryStatus);
        OrderDeliveryTextView.setOnClickListener(setOrderDeliveryStatus);
        TickedOrderDeliveryLayout.setOnClickListener(setOrderDeliveryStatus);

        OrderDeliveredLayout = findViewById(R.id.list_item_3_status);
        OrderDeliveredTextView = findViewById(R.id.label_option3_status);
        TickedOrderDeliveredLayout = findViewById(R.id.components_3__status);
        View.OnClickListener setOrderDeliveredStatus = view -> {
            setOrderStatusOptionUI(OrderDeliveredLayout, OrderDeliveredTextView, TickedOrderDeliveredLayout);
            CurrentOrderStatus = "DELIVERED";
        };
        OrderDeliveredLayout.setOnClickListener(setOrderDeliveredStatus);
        OrderDeliveredTextView.setOnClickListener(setOrderDeliveredStatus);
        TickedOrderDeliveredLayout.setOnClickListener(setOrderDeliveredStatus);

        OrderCancelledLayout = findViewById(R.id.list_item_4_status);
        OrderCancelledTextView = findViewById(R.id.label_option4_status);
        TickedOrderCancelledLayout = findViewById(R.id.components_4_status);
        View.OnClickListener setOrderCancelledStatus = view -> {
            setOrderStatusOptionUI(OrderCancelledLayout, OrderCancelledTextView, TickedOrderCancelledLayout);
            CurrentOrderStatus = "CANCELLED";
        };
        OrderCancelledLayout.setOnClickListener(setOrderCancelledStatus);
        OrderCancelledTextView.setOnClickListener(setOrderCancelledStatus);
        TickedOrderCancelledLayout.setOnClickListener(setOrderCancelledStatus);

        Intent getCurrentOrderStatusIntent = getIntent();
        CurrentOrderStatus = getCurrentOrderStatusIntent.getStringExtra("currentOrderStatus");
        switch (CurrentOrderStatus){
            case "PROCESSING":
                setOrderStatusOptionUI(
                        OrderProcessingLayout, OrderProcessingTextView, TickedOrderProcessingLayout);
                break;
            case "DELIVERY":
                setOrderStatusOptionUI(
                        OrderDeliveryLayout, OrderDeliveryTextView, TickedOrderDeliveryLayout);
                break;
            case "DELIVERED":
                setOrderStatusOptionUI(
                        OrderDeliveredLayout, OrderDeliveredTextView, TickedOrderDeliveredLayout);
                break;
            case "CANCELLED":
                setOrderStatusOptionUI(
                        OrderCancelledLayout, OrderCancelledTextView, TickedOrderCancelledLayout);
                break;
            default:
                ResetAllOrderStatusOption();
        }

        ApplyChangeOrderStatusTextView = findViewById(R.id.cta_apply_new_order_status);
        ApplyChangeOrderStatusTextView.setOnClickListener(view -> {
            Intent pickOrderStatusIntent = new Intent();
            pickOrderStatusIntent.putExtra("pickedOrderStatus", CurrentOrderStatus);
            setResult(RESULT_OK, pickOrderStatusIntent);
            finish();
        });

        CancelChangeOrderStatusView = findViewById(R.id.cancel_list_status);
        CancelChangeOrderStatusView.setOnClickListener(view -> {
            finish();
        });
    }
    private void setOrderStatusOptionUI(
            ConstraintLayout outerLayout, TextView orderStatusTextView, ConstraintLayout tickedLayout){
        ResetAllOrderStatusOption();
        outerLayout.setBackgroundResource(R.drawable.option_item_color);
        orderStatusTextView.setTypeface(Typeface.DEFAULT_BOLD);
        tickedLayout.setBackgroundResource(R.drawable.check_icon);
    }

    private void ResetAllOrderStatusOption(){
        ResetOrderStatusOption(OrderProcessingLayout, OrderProcessingTextView, TickedOrderProcessingLayout);
        ResetOrderStatusOption(OrderDeliveryLayout, OrderDeliveryTextView, TickedOrderDeliveryLayout);
        ResetOrderStatusOption(OrderDeliveredLayout, OrderDeliveredTextView, TickedOrderDeliveredLayout);
        ResetOrderStatusOption(OrderCancelledLayout, OrderCancelledTextView, TickedOrderCancelledLayout);
    }

    private void ResetOrderStatusOption(
            ConstraintLayout outerLayout, TextView orderStatusTextView, ConstraintLayout tickedLayout){
        if (outerLayout != null){
            outerLayout.setBackground(null);
        }
        if (orderStatusTextView != null){
            orderStatusTextView.setTypeface(Typeface.DEFAULT);
        }
        if (tickedLayout != null){
            tickedLayout.setBackground(null);
        }
    }
}