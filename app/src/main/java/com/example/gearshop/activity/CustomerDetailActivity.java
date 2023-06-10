package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gearshop.R;
import com.example.gearshop.model.Address;
import com.example.gearshop.model.Customer;
import com.example.gearshop.model.Province;
import com.example.gearshop.utility.DatabaseHelper;

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
    private TextView CustomerTitle;
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
        CustomerTitle = findViewById(R.id.admin_title_username_user_info);
        ReturnView.setOnClickListener(view -> {
            finish();
        });

        if (CurrentCustomer != null){
            CustomerUsernameInfoTextView.setText(CurrentCustomer.getUsername());

            CustomerPasswordInfoTextView.setText(CurrentCustomer.getPassword());

            CustomerLastNameInfoTextView.setText(CurrentCustomer.getLastName());

            CustomerFirstNameInfoTextView.setText(CurrentCustomer.getFirstName());

            CustomerTitle.setText(CurrentCustomer.getLastName() + " " + CurrentCustomer.getFirstName());

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

            Address customerAddress;
            Province customerProvince;
            try {
                customerAddress = DatabaseHelper.getAddressList(" WHERE id ='"
                        + CurrentCustomer.getAddressID() + "'").get(0);
                String customerHouseNumber = customerAddress.getHouseNumber();
                String customerStreet = customerAddress.getStreet();
                customerProvince = DatabaseHelper.getProvinceList(
                        " WHERE id = '" + customerAddress.getProvinceID() + "'").get(0);
                String address;
                if (customerStreet.equals("")) {
                    address = customerHouseNumber + "\n" + customerProvince.getName();
                } else
                    address = customerHouseNumber + "\n" + customerStreet + "\n" + customerProvince.getName();
                CustomerAddressInfoTextView.setText(address);
            }
            catch (NullPointerException | IndexOutOfBoundsException e){
                Toast.makeText(
                        getBaseContext(),
                        "Lỗi không lấy được thông tin địa chỉ và số điện thoại",
                        Toast.LENGTH_SHORT).show();
            }

            CustomerEmailInfoTextView.setText(CurrentCustomer.getEmail());

            CustomerPhoneNumberInfoTextView.setText(CurrentCustomer.getPhoneNumber());

            CustomerOrderManagement.setOnClickListener(view -> {
                Intent intent = new Intent(getBaseContext(), CustomerOrderActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("ORDER_TYPE", "ALL_ORDER");
                intent.putExtra("customerID", CurrentCustomer.getID());
                getBaseContext().startActivity(intent);
            });
            // CustomerAddress ?
        }
    }
}