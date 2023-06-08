package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.gearshop.R;

public class CustomerOrderStatusPickActivity extends AppCompatActivity {

    private ConstraintLayout OrderProgressingLayout;
    private TextView OrderProgressingTextView;
    private ConstraintLayout TickedOrderProgressingLayout;

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
    private boolean isCancelledStatus = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_order_list_item_status);

        OrderProgressingLayout = findViewById(R.id.list_item_1_status);
        OrderProgressingLayout.setVisibility(View.INVISIBLE);
        OrderProgressingTextView = findViewById(R.id.label_option1_status);
        OrderProgressingTextView.setVisibility(View.INVISIBLE);
        TickedOrderProgressingLayout = findViewById(R.id.components_1_status);
        TickedOrderProgressingLayout.setVisibility(View.INVISIBLE);

        OrderDeliveryLayout = findViewById(R.id.list_item_2_status);
        OrderDeliveryLayout.setVisibility(View.INVISIBLE);
        OrderDeliveryTextView = findViewById(R.id.label_option2_status);
        OrderDeliveryTextView.setVisibility(View.INVISIBLE);
        TickedOrderDeliveryLayout = findViewById(R.id.components_2_status);
        TickedOrderDeliveryLayout.setVisibility(View.INVISIBLE);

        OrderDeliveredLayout = findViewById(R.id.list_item_3_status);
        OrderDeliveredLayout.setVisibility(View.INVISIBLE);
        OrderDeliveredTextView = findViewById(R.id.label_option3_status);
        OrderDeliveredTextView.setVisibility(View.INVISIBLE);
        TickedOrderDeliveredLayout = findViewById(R.id.components_3__status);
        TickedOrderDeliveredLayout.setVisibility(View.INVISIBLE);

        OrderCancelledLayout = findViewById(R.id.list_item_4_status);
        OrderCancelledTextView = findViewById(R.id.label_option4_status);
        TickedOrderCancelledLayout = findViewById(R.id.components_4_status);

        String oldOrderStatus = CurrentOrderStatus;
        View.OnClickListener setOrderCancelledStatus = view -> {
            if (!isCancelledStatus){
                setOrderStatusOptionUI(OrderCancelledLayout, OrderCancelledTextView, TickedOrderCancelledLayout);
                CurrentOrderStatus = "CANCELLED";
                isCancelledStatus = true;
            }
            else{
                ResetAllOrderStatusOption();
                CurrentOrderStatus = oldOrderStatus;
                isCancelledStatus = false;
            }

        };
        OrderCancelledLayout.setOnClickListener(setOrderCancelledStatus);
        OrderCancelledTextView.setOnClickListener(setOrderCancelledStatus);
        TickedOrderCancelledLayout.setOnClickListener(setOrderCancelledStatus);

        Intent getCurrentOrderStatusIntent = getIntent();
        CurrentOrderStatus = getCurrentOrderStatusIntent.getStringExtra("currentOrderStatus");
        if ("CANCELLED".equals(CurrentOrderStatus)) {
            setOrderStatusOptionUI(
                    OrderCancelledLayout, OrderCancelledTextView, TickedOrderCancelledLayout);
        } else {
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
        ResetOrderStatusOption(OrderProgressingLayout, OrderProgressingTextView, TickedOrderProgressingLayout);
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