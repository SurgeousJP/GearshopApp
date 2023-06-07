package com.example.gearshop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gearshop.R;
import com.example.gearshop.model.Customer;


import java.util.List;

public class CustomerListAdapter extends BaseAdapter {
    private Context context;
    private int resource;
    private List<Customer> CustomerList;

    public void UpdateDataToAdapter(List<Customer> customers){
        CustomerList = customers;
        notifyDataSetChanged();
    }
    public CustomerListAdapter(@NonNull Context context, int resource, @NonNull List<Customer> objects) {
        this.context = context;
        this.resource = resource;
        this.CustomerList = objects;
    }

    @Override
    public int getCount() {
        return CustomerList.size();
    }

    @Override
    public Customer getItem(int i) {
        return CustomerList.get(i);
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
        Customer customer = CustomerList.get(position);
        if (customer != null){
            TextView CustomerIDTextView = v.findViewById(R.id.customer_id);
            if (CustomerIDTextView != null){
                CustomerIDTextView.setText(String.valueOf(customer.getID()));
            }
            TextView CustomerUsernameTextView = v.findViewById(R.id.customer_username);
            if (CustomerUsernameTextView != null){
                CustomerUsernameTextView.setText(customer.getUsername());
            }
            TextView CustomerNameTextView = v.findViewById(R.id.customer_name);
            if (CustomerNameTextView != null){
                CustomerNameTextView.setText(customer.getLastName() + " " + customer.getFirstName());
            }
        }
        return v;
    }
}
