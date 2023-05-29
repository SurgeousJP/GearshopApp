package com.example.gearshop.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gearshop.R;
import com.example.gearshop.controller.CustomerRepository;
import com.example.gearshop.model.Customer;

public class EditUserInfoActivity extends AppCompatActivity {
    private CustomerRepository customerRepository;

    private RelativeLayout MoreInformationLayout;
    private RelativeLayout EscapeLayout;
    ConstraintLayout backwardButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userId = prefs.getString("customerId", null);

        customerRepository = new CustomerRepository();
        Customer userInfo = customerRepository.getCustomerById(userId);

        String userName = userInfo.getFirstName() + userInfo.getLastName();
        TextView tvUserName = findViewById(R.id.title_username_user_info);
        tvUserName.setText(userName);

        TextView tvUsername = findViewById(R.id.username_user_info);
        tvUsername.setText(userInfo.getUsername());

        TextView tvLastName = findViewById(R.id.lastname_user_info);
        tvLastName.setText(userInfo.getLastName());

        TextView tvFirstName = findViewById(R.id.firstname_user_info);
        tvFirstName.setText(userInfo.getFirstName());

        TextView tvGender = findViewById(R.id.sex_user_info);
        tvGender.setText(userInfo.getGender());

        TextView tvDob = findViewById(R.id.dob_user_info);
        tvDob.setText(userInfo.getDateOfBirth().toString());

        TextView tvEmail = findViewById(R.id.email_user_info);
        tvEmail.setText(userInfo.getEmail());

        TextView tvAddress = findViewById(R.id.address_user_info);

        TextView tvPhoneNumber = findViewById(R.id.phonenumber_user_info);
        tvPhoneNumber.setText(userInfo.getPhoneNumber());

        View btnEditLastName = findViewById(R.id.edit_lastname_icon);
        EditText edtLastName = findViewById(R.id.lastname_user_info);
        btnEditLastName.setOnClickListener(view -> {
            if (edtLastName.isEnabled()){
                //save data


                // disable
                DisableEditText(edtLastName);
            }
            else{
                EnableEditText(edtLastName, InputType.TYPE_CLASS_TEXT);
                //then typing
            }
        });

        View btnEditFirstName = findViewById(R.id.edit_firstname_icon);



        backwardButton = findViewById(R.id.wayback_icon_user_info);
        backwardButton.setOnClickListener(view -> {
            finish();
        });

        MoreInformationLayout = findViewById(R.id.more_info_order_detail);
        MoreInformationLayout.setOnClickListener(view -> {

        });

        EscapeLayout = findViewById(R.id.escape);
        EscapeLayout.setOnClickListener(view -> {

        });
    }

    private void EnableEditText(EditText editText, int inputType) {
        // enable
        editText.setEnabled(true);
        editText.setInputType(inputType);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);

        // Show keyboard
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    private void DisableEditText(EditText editText){
        editText.setEnabled(false);
        editText.setFocusable(false);
    }
}
