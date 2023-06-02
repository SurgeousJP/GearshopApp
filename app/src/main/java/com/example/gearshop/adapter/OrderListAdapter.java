package com.example.gearshop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gearshop.R;
import com.example.gearshop.activity.OrderActivity;
import com.example.gearshop.model.Order;
import com.example.gearshop.model.Province;
import com.example.gearshop.utility.DatabaseHelper;
import com.example.gearshop.utility.MoneyHelper;

import java.text.SimpleDateFormat;
import java.util.List;

public class OrderListAdapter extends BaseAdapter {
    private Context context;
    private int resource;
    private List<Order> OrderList;

    public void UpdateDataToAdapter(List<Order> orders){
        OrderList = orders;
        notifyDataSetChanged();
    }
    public OrderListAdapter(@NonNull Context context, int resource, @NonNull List<Order> objects) {
        this.context = context;
        this.resource = resource;
        this.OrderList = objects;
    }

    @Override
    public int getCount() {
        return OrderList.size();
    }

    @Override
    public Order getItem(int i) {
        return OrderList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(this.context);
            v = inflater.inflate(this.resource, parent, false);
        }

        Order order = getItem(position);
        if (order != null){
            TextView OrderStatusTextView = v.findViewById(R.id.content_sta);
            if (OrderStatusTextView != null)
                OrderStatusTextView.setText(order.getStatus());
            ConstraintLayout BackgroundOrderStatusLayout = v.findViewById(R.id.order_status_border);
            if (BackgroundOrderStatusLayout != null && OrderStatusTextView != null){
                switch (order.getStatus()){
                    case "PROCESSING":
                    case "DELIVERY":
                        OrderStatusTextView.setTextColor(Color.parseColor("#007D3A"));
                        BackgroundOrderStatusLayout.setBackgroundResource(R.drawable.delivery_box);
                        break;
                    case "DELIVERED":
                        OrderStatusTextView.setTextColor(Color.parseColor("#007D3A"));
                        BackgroundOrderStatusLayout.setBackgroundResource(R.drawable.delivered_box);
                        break;
                    case "CANCELLED":
                        OrderStatusTextView.setTextColor(Color.parseColor("#BF1D28"));
                        BackgroundOrderStatusLayout.setBackgroundResource(R.drawable.cancel_box);
                    default:
                        break;
                }
            }
            TextView OrderNameTextView = v.findViewById(R.id.order_name);
            if (OrderNameTextView != null)
                OrderNameTextView.setText("Đơn hàng ngày" +
                        new SimpleDateFormat("dd/MM/yyyy").format(order.getCreatedOnUtc()));
            TextView OrderTotalPriceTextView = v.findViewById(R.id.order_total_price);
            if (OrderTotalPriceTextView != null)
                OrderTotalPriceTextView.setText(MoneyHelper.getVietnameseMoneyStringFormatted(order.getTotalPrice()));
            TextView OrderPaymentMethodTextView = v.findViewById(R.id.label_payment);
            if (OrderPaymentMethodTextView != null){
                OrderPaymentMethodTextView.setText(
                        DatabaseHelper.getPaymentMethod(
                                " WHERE id = '" + order.getPaymentMethodID() + "'").get(0).getName());
            }
        }
        return v;
    }
}

