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

        EditText edtLastName = findViewById(R.id.lastname_user_info);
        edtLastName.setText(userInfo.getLastName());

        EditText edtFirstName = findViewById(R.id.firstname_user_info);
        edtFirstName.setText(userInfo.getFirstName());

        EditText edtGender = findViewById(R.id.sex_user_info);
        edtGender.setText(userInfo.getGender());

        EditText edtDob = findViewById(R.id.dob_user_info);
        edtDob.setText(userInfo.getDateOfBirth().toString());

        EditText edtEmail = findViewById(R.id.email_user_info);
        edtEmail.setText(userInfo.getEmail());

        EditText tvAddress = findViewById(R.id.address_user_info);

        EditText edtPhoneNumber = findViewById(R.id.phonenumber_user_info);
        edtPhoneNumber.setText(userInfo.getPhoneNumber());

        View btnEditLastName = findViewById(R.id.edit_lastname_icon);
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
        btnEditFirstName.setOnClickListener(view -> {
            if (edtFirstName.isEnabled()){
                //save data


                // disable
                DisableEditText(edtFirstName);
            }
            else{
                EnableEditText(edtFirstName, InputType.TYPE_CLASS_TEXT);
                //then typing
            }
        });

        View btnEditGender = findViewById(R.id.edit_sex_icon);
        btnEditGender.setOnClickListener(view -> {
            if (edtGender.isEnabled()){
                //save data


                // disable
                DisableEditText(edtGender);
            }
            else{
                EnableEditText(edtGender, InputType.TYPE_CLASS_TEXT);
                //then typing
            }
        });

        View btnEditDob = findViewById(R.id.edit_dob_icon);
        btnEditDob.setOnClickListener(view -> {
            if (edtDob.isEnabled()){
                //save data


                // disable
                DisableEditText(edtDob);
            }
            else{
                EnableEditText(edtDob, InputType.TYPE_CLASS_TEXT);
                //then typing
            }
        });

        View btnEditEmail = findViewById(R.id.edit_email_icon);
        btnEditEmail.setOnClickListener(view -> {
            if (edtEmail.isEnabled()){
                //save data


                // disable
                DisableEditText(edtEmail);
            }
            else{
                EnableEditText(edtEmail, InputType.TYPE_CLASS_TEXT);
                //then typing
            }
        });

        View btnEditPhoneNumber = findViewById(R.id.edit_phonenumber_icon);
        btnEditPhoneNumber.setOnClickListener(view -> {
            if (edtPhoneNumber.isEnabled()){
                //save data


                // disable
                DisableEditText(edtPhoneNumber);
            }
            else{
                EnableEditText(edtPhoneNumber, InputType.TYPE_CLASS_TEXT);
                //then typing
            }
        });



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
