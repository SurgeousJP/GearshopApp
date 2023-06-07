package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.gearshop.R;
import com.example.gearshop.model.Address;
import com.example.gearshop.model.Customer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomerDetailActivity extends AppCompatActivity {
    private View ReturnView;
    private TextView CustomerUsernameInfoTextView;
    private TextView CustomerPasswordInfoTextView;
    private TextView CustomerLastNameInfoTextView;
    private TextView CustomerFirstNameInfoTextView;
    private TextView CustomerGenderInfoTextView;
    private TextView CustomerDOBInfoTextView;
    private TextView CustomerEmailInfoTextView;
    private TextView CustomerAddressInfoTextView;
    private TextView CustomerPhoneNumberInfoTextView;
    private TextView CustomerOrderManagement;
    private Customer CurrentCustomer;

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_customer_detail);

        Intent getCustomerData = getIntent();
        CurrentCustomer = (Customer) getCustomerData.getSerializableExtra("clickedCustomer");
        ReturnView = findViewById(R.id.customer_detail_return_view);
        CustomerUsernameInfoTextView = findViewById(R.id.admin_username_user_info);
        CustomerPasswordInfoTextView = findViewById(R.id.admin_password_user_info);
        CustomerLastNameInfoTextView = findViewById(R.id.admin_lastname_user_info);
        CustomerFirstNameInfoTextView = findViewById(R.id.admin_firstname_user_info);
        CustomerGenderInfoTextView = findViewById(R.id.admin_sex_user_info);
        CustomerDOBInfoTextView = findViewById(R.id.admin_dob_user_info);
        CustomerEmailInfoTextView = findViewById(R.id.admin_email_user_info);
        CustomerAddressInfoTextView = findViewById(R.id.admin_address_user_info);
        CustomerPhoneNumberInfoTextView = findViewById(R.id.admin_phonenumber_user_info);
        CustomerOrderManagement = findViewById(R.id.order_management_text);
        ReturnView.setOnClickListener(view -> {
            finish();
        });

        if (CurrentCustomer != null){
            CustomerUsernameInfoTextView.setText(CurrentCustomer.getUsername());

            CustomerPasswordInfoTextView.setText(CurrentCustomer.getPassword());

            CustomerLastNameInfoTextView.setText(CurrentCustomer.getLastName());

            CustomerFirstNameInfoTextView.setText(CurrentCustomer.getFirstName());

            String customerGender = CurrentCustomer.getGender();
            if (customerGender.equals("male")) {
                CustomerGenderInfoTextView.setText("Nam");
            }
            else if (customerGender.equals("female")){
                CustomerGenderInfoTextView.setText("Nữ");
            }
            else CustomerGenderInfoTextView.setText("N/A");

            Date customerDOB = CurrentCustomer.getDateOfBirth();
            CustomerDOBInfoTextView.setText(new SimpleDateFormat("dd/MM/yyyy").format(customerDOB));

            CustomerEmailInfoTextView.setText(CurrentCustomer.getEmail());

            CustomerPhoneNumberInfoTextView.setText(CurrentCustomer.getPhoneNumber());

            CustomerOrderManagement.setOnClickListener(view -> {
                Intent intent = new Intent(getBaseContext(), OrderActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("ORDER_TYPE", "ALL_ORDER");
                intent.putExtra("customerID", CurrentCustomer.getID());
                getBaseContext().startActivity(intent);
            });
            // CustomerAddress ?
        }
    }
}