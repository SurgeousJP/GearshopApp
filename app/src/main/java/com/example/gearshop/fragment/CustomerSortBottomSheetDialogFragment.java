package com.example.gearshop.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gearshop.R;
import com.example.gearshop.adapter.CustomerListAdapter;
import com.example.gearshop.model.Customer;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Comparator;
import java.util.List;

public class CustomerSortBottomSheetDialogFragment extends BottomSheetDialogFragment
        implements ConfirmSortDialogFragment.DialogListener {
    private List<Customer> CustomerList;
    private List<Customer> SortCustomerResult;
    private CustomerListAdapter CustomerAdapter;
    private View DismissView;
    private ConstraintLayout LayoutDefault;
    private ConstraintLayout LayoutCustomerIDAscending;
    private ConstraintLayout LayoutCustomerIDDescending;
    private ConstraintLayout LayoutCustomerNameAscending;
    private ConstraintLayout LayoutCustomerNameDescending;
    private TextView DefaultTextView;
    private TextView CustomerIDAscendingTextView;
    private TextView CustomerIDDescendingTextView;
    private TextView CustomerNameAscendingTextView;
    private TextView CustomerNameDescendingTextView;
    private ConstraintLayout TickedDefault;
    private ConstraintLayout TickedCustomerIDAscending;
    private ConstraintLayout TickedCustomerIDDescending;
    private ConstraintLayout TickedCustomerNameAscending;
    private ConstraintLayout TickedCustomerNameDescending;
    private boolean checkConfirmUpdateUI;
    private ConstraintLayout tempLayout;
    private TextView tempTextView;
    private ConstraintLayout tempTickedLayout;
    public CustomerSortBottomSheetDialogFragment(){}
    public CustomerSortBottomSheetDialogFragment(CustomerListAdapter customerAdapter,
                                                 List<Customer> customers){
        this.CustomerAdapter = customerAdapter;
        this.CustomerList = customers;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_customer_sort_setting, container, false);

        DismissView = view.findViewById(R.id.sort_vector_customer);
        DismissView.setOnClickListener(view12 -> {
            dismiss();
        });

        View.OnClickListener DeafaultListener = view13 -> {
            CustomerList.sort(Comparator.comparingInt(Customer::getID));
            SortCustomerResult = CustomerList;
            setUIComponentTempsForUpdate(
                    LayoutDefault, DefaultTextView, TickedDefault);
            showConfirmSortDialog();
        };

        // By default, the customers are displayed by ascending id
        LayoutDefault = view.findViewById(R.id.list_item_1);
        LayoutDefault.setOnClickListener(DeafaultListener);
        DefaultTextView = view.findViewById(R.id.label_option1_customer);
        DefaultTextView.setOnClickListener(DeafaultListener);
        TickedDefault = view.findViewById(R.id.components_1_customer);
        TickedDefault.setOnClickListener(DeafaultListener);

        View.OnClickListener CustomerIDAscendingListener = view13 -> {
            CustomerList.sort(Comparator.comparingInt(Customer::getID));
            SortCustomerResult = CustomerList;
            setUIComponentTempsForUpdate(
                    LayoutCustomerIDAscending, CustomerIDAscendingTextView, TickedCustomerIDAscending);
            showConfirmSortDialog();
        };

        LayoutCustomerIDAscending = view.findViewById(R.id.list_item_2);
        LayoutCustomerIDAscending.setOnClickListener(CustomerIDAscendingListener);
        CustomerIDAscendingTextView = view.findViewById(R.id.label_option2_customer);
        CustomerIDAscendingTextView.setOnClickListener(CustomerIDAscendingListener);
        TickedCustomerIDAscending = view.findViewById(R.id.components_2_customer);
        TickedCustomerIDAscending.setOnClickListener(CustomerIDAscendingListener);

        View.OnClickListener CustomerIDDescendingListener = view13 -> {
            CustomerList.sort((a, b) -> Integer.compare(b.getID(), a.getID()));
            SortCustomerResult = CustomerList;
            setUIComponentTempsForUpdate(
                    LayoutCustomerIDDescending, CustomerIDDescendingTextView, TickedCustomerIDDescending);
            showConfirmSortDialog();
        };
        LayoutCustomerIDDescending = view.findViewById(R.id.list_item_3);
        LayoutCustomerIDDescending.setOnClickListener(CustomerIDDescendingListener);
        CustomerIDDescendingTextView = view.findViewById(R.id.label_option3_customer);
        CustomerIDDescendingTextView.setOnClickListener(CustomerIDDescendingListener);
        TickedCustomerIDDescending = view.findViewById(R.id.components_3_customer);
        TickedCustomerIDDescending.setOnClickListener(CustomerIDDescendingListener);

        View.OnClickListener CustomerNameAscendingListener = view13 -> {
            CustomerList.sort((a, b) -> (a.getLastName() + "\t" +  a.getFirstName())
                    .compareTo(b.getLastName() + "\t" + b.getFirstName()));
            SortCustomerResult = CustomerList;
            setUIComponentTempsForUpdate(
                    LayoutCustomerNameAscending,
                    CustomerNameAscendingTextView,
                    TickedCustomerNameAscending);
            showConfirmSortDialog();
        };

        LayoutCustomerNameAscending = view.findViewById(R.id.list_item_4);
        LayoutCustomerNameAscending.setOnClickListener(CustomerNameAscendingListener);
        CustomerNameAscendingTextView = view.findViewById(R.id.label_option4_customer);
        CustomerNameAscendingTextView.setOnClickListener(CustomerNameAscendingListener);
        TickedCustomerNameAscending = view.findViewById(R.id.components_4_customer);
        TickedCustomerNameAscending.setOnClickListener(CustomerNameAscendingListener);

        View.OnClickListener CustomerNameDescendingListener = view13 -> {
            CustomerList.sort((a, b) -> (b.getLastName() + "\t" +  b.getFirstName())
                    .compareTo(a.getLastName() + "\t" + a.getFirstName()));
            SortCustomerResult = CustomerList;
            setUIComponentTempsForUpdate(
                    LayoutCustomerNameDescending,
                    CustomerNameDescendingTextView,
                    TickedCustomerNameDescending);
            showConfirmSortDialog();
        };

        LayoutCustomerNameDescending = view.findViewById(R.id.list_item_5);
        LayoutCustomerNameDescending.setOnClickListener(CustomerNameDescendingListener);
        CustomerNameDescendingTextView = view.findViewById(R.id.label_option5_customer);
        CustomerNameDescendingTextView.setOnClickListener(CustomerNameDescendingListener);
        TickedCustomerNameDescending = view.findViewById(R.id.components_5_customer);
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

        LayoutCustomerIDAscending.setBackground(null);
        CustomerIDAscendingTextView.setTypeface(Typeface.DEFAULT);
        TickedCustomerIDAscending.setBackground(null);

        LayoutCustomerIDDescending.setBackground(null);
        CustomerIDDescendingTextView.setTypeface(Typeface.DEFAULT);
        TickedCustomerIDDescending.setBackground(null);

        LayoutCustomerNameAscending.setBackground(null);
        CustomerNameAscendingTextView.setTypeface(Typeface.DEFAULT);
        TickedCustomerNameAscending.setBackground(null);

        LayoutCustomerIDDescending.setBackground(null);
        CustomerIDDescendingTextView.setTypeface(Typeface.DEFAULT);
        TickedCustomerNameDescending.setBackground(null);
    }
    private void showConfirmSortDialog() {
        ConfirmSortDialogFragment dialogFragment = new ConfirmSortDialogFragment();
        dialogFragment.setDialogListener(this);
        dialogFragment.show(getParentFragmentManager(), "");
    }

    @Override
    public void onDialogResult(boolean result) {
        if (result){
            CustomerAdapter.UpdateDataToAdapter(SortCustomerResult);
            setUpdateSortOptionUI(tempLayout, tempTextView, tempTickedLayout);
        }
    }
}