package com.example.gearshop.dialog;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gearshop.R;
import com.example.gearshop.adapter.OrderListAdapter;
import com.example.gearshop.model.Order;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Comparator;
import java.util.List;

public class OrderSortBottomSheetDialog extends BottomSheetDialogFragment
        implements ConfirmSortDialog.DialogListener {
    private List<Order> OrderList;
    private List<Order> SortOrderList;
    private OrderListAdapter OrderAdapter;
    private View DismissView;
    private ConstraintLayout LayoutDefault;
    private ConstraintLayout LayoutOrderCreatedDateAscending;
    private ConstraintLayout LayoutOrderCreatedDateDescending;
    private ConstraintLayout LayoutTotalPriceAscending;
    private ConstraintLayout LayoutTotalPriceDescending;
    private TextView DefaultTextView;
    private TextView OrderCreatedDateAscendingTextView;
    private TextView OrderCreatedDateDescendingTextView;
    private TextView OrderTotalPriceAscendingTextView;
    private TextView OrderTotalPriceDescendingTextView;
    private ConstraintLayout TickedDefault;
    private ConstraintLayout TickedCreatedDateAscending;
    private ConstraintLayout TickedCreatedDateDescending;
    private ConstraintLayout TickedOrderTotalPriceAscending;
    private ConstraintLayout TickedCustomerNameDescending;
    private boolean checkConfirmUpdateUI;
    private ConstraintLayout tempLayout;
    private TextView tempTextView;
    private ConstraintLayout tempTickedLayout;
    public OrderSortBottomSheetDialog(){}
    public OrderSortBottomSheetDialog(OrderListAdapter orderAdapter,
                                      List<Order> orders){
        this.OrderAdapter = orderAdapter;
        this.OrderList = orders;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_order_sort_setting, container, false);

        DismissView = view.findViewById(R.id.sort_vector_order);
        DismissView.setOnClickListener(view12 -> {
            dismiss();
        });

        View.OnClickListener DefaultListener = view13 -> {
            OrderList.sort(Comparator.comparingInt(Order::getID));
            SortOrderList = OrderList;
            setUIComponentTempsForUpdate(
                    LayoutDefault, DefaultTextView, TickedDefault);
            showConfirmSortDialog();
        };

        // By default, the customers are displayed by ascending id
        LayoutDefault = view.findViewById(R.id.list_item_1);
        LayoutDefault.setOnClickListener(DefaultListener);
        DefaultTextView = view.findViewById(R.id.label_option1_order);
        DefaultTextView.setOnClickListener(DefaultListener);
        TickedDefault = view.findViewById(R.id.components_1_order);
        TickedDefault.setOnClickListener(DefaultListener);

        View.OnClickListener OrderCreatedDateAscendingListener = view13 -> {
            OrderList.sort(Comparator.comparing(Order::getCreatedOnUtc));
            SortOrderList = OrderList;
            setUIComponentTempsForUpdate(
                    LayoutOrderCreatedDateAscending, OrderCreatedDateAscendingTextView, TickedCreatedDateAscending);
            showConfirmSortDialog();
        };

        LayoutOrderCreatedDateAscending = view.findViewById(R.id.list_item_2);
        LayoutOrderCreatedDateAscending.setOnClickListener(OrderCreatedDateAscendingListener);
        OrderCreatedDateAscendingTextView = view.findViewById(R.id.label_option2_order);
        OrderCreatedDateAscendingTextView.setOnClickListener(OrderCreatedDateAscendingListener);
        TickedCreatedDateAscending = view.findViewById(R.id.components_2_order);
        TickedCreatedDateAscending.setOnClickListener(OrderCreatedDateAscendingListener);

        View.OnClickListener OrderCreatedDateDescendingListener = view13 -> {
            OrderList.sort((a, b) -> b.getCreatedOnUtc().compareTo(a.getCreatedOnUtc()));
            SortOrderList = OrderList;
            setUIComponentTempsForUpdate(
                    LayoutOrderCreatedDateDescending, OrderCreatedDateDescendingTextView, TickedCreatedDateDescending);
            showConfirmSortDialog();
        };
        LayoutOrderCreatedDateDescending = view.findViewById(R.id.list_item_3);
        LayoutOrderCreatedDateDescending.setOnClickListener(OrderCreatedDateDescendingListener);
        OrderCreatedDateDescendingTextView = view.findViewById(R.id.label_option3_order);
        OrderCreatedDateDescendingTextView.setOnClickListener(OrderCreatedDateDescendingListener);
        TickedCreatedDateDescending = view.findViewById(R.id.components_3_order);
        TickedCreatedDateDescending.setOnClickListener(OrderCreatedDateDescendingListener);

        View.OnClickListener CustomerTotalPriceListener = view13 -> {
            OrderList.sort((a, b) -> Double.compare(a.getTotalPrice(), b.getTotalPrice()));
            SortOrderList = OrderList;
            setUIComponentTempsForUpdate(
                    LayoutTotalPriceAscending,
                    OrderTotalPriceAscendingTextView,
                    TickedOrderTotalPriceAscending);
            showConfirmSortDialog();
        };

        LayoutTotalPriceAscending = view.findViewById(R.id.list_item_4);
        LayoutTotalPriceAscending.setOnClickListener(CustomerTotalPriceListener);
        OrderTotalPriceAscendingTextView = view.findViewById(R.id.label_option4_order);
        OrderTotalPriceAscendingTextView.setOnClickListener(CustomerTotalPriceListener);
        TickedOrderTotalPriceAscending = view.findViewById(R.id.components_4_order);
        TickedOrderTotalPriceAscending.setOnClickListener(CustomerTotalPriceListener);

        View.OnClickListener CustomerNameDescendingListener = view13 -> {
            OrderList.sort((a, b) -> Double.compare(b.getTotalPrice(), a.getTotalPrice()));
            SortOrderList = OrderList;
            setUIComponentTempsForUpdate(
                    LayoutTotalPriceDescending,
                    OrderTotalPriceDescendingTextView,
                    TickedCustomerNameDescending);
            showConfirmSortDialog();
        };

        LayoutTotalPriceDescending = view.findViewById(R.id.list_item_5);
        LayoutTotalPriceDescending.setOnClickListener(CustomerNameDescendingListener);
        OrderTotalPriceDescendingTextView = view.findViewById(R.id.label_option5_order);
        OrderTotalPriceDescendingTextView.setOnClickListener(CustomerNameDescendingListener);
        TickedCustomerNameDescending = view.findViewById(R.id.components_5_order);
        TickedCustomerNameDescending.setOnClickListener(CustomerNameDescendingListener);

        return view;
    }
    private void setUIComponentTempsForUpdate(ConstraintLayout layout, TextView textView,
                                              ConstraintLayout tickedLayout){
        tempLayout = layout;
        tempTextView = textView;
        tempTickedLayout = tickedLayout;
    }
    private void setUpdateSortOptionUI(ConstraintLayout layout, TextView textView, ConstraintLayout tickedLayout){
        ResetSortOption();
        layout.setBackgroundResource(R.drawable.option_item_color);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        tickedLayout.setBackgroundResource(R.drawable.check_icon);
    }
    private void ResetSortOption(){
        LayoutDefault.setBackground(null);
        DefaultTextView.setTypeface(Typeface.DEFAULT);
        TickedDefault.setBackground(null);

        LayoutOrderCreatedDateAscending.setBackground(null);
        OrderCreatedDateAscendingTextView.setTypeface(Typeface.DEFAULT);
        TickedCreatedDateAscending.setBackground(null);

        LayoutOrderCreatedDateDescending.setBackground(null);
        OrderCreatedDateDescendingTextView.setTypeface(Typeface.DEFAULT);
        TickedCreatedDateDescending.setBackground(null);

        LayoutTotalPriceAscending.setBackground(null);
        OrderTotalPriceAscendingTextView.setTypeface(Typeface.DEFAULT);
        TickedOrderTotalPriceAscending.setBackground(null);

        LayoutOrderCreatedDateDescending.setBackground(null);
        OrderCreatedDateDescendingTextView.setTypeface(Typeface.DEFAULT);
        TickedCustomerNameDescending.setBackground(null);
    }
    private void showConfirmSortDialog() {
        ConfirmSortDialog dialogFragment = new ConfirmSortDialog();
        dialogFragment.setDialogListener(this);
        dialogFragment.show(getParentFragmentManager(), "");
    }

    @Override
    public void onDialogResult(boolean result) {
        if (result){
            OrderAdapter.UpdateDataToAdapter(SortOrderList);
            setUpdateSortOptionUI(tempLayout, tempTextView, tempTickedLayout);
        }
    }
}
