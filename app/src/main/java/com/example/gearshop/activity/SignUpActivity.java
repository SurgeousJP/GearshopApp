package com.example.gearshop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gearshop.R;
import com.example.gearshop.controller.CustomerRepository;
import com.example.gearshop.model.Customer;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SignUpActivity extends AppCompatActivity {
    CustomerRepository customerRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        customerRepository = new CustomerRepository();

        ConstraintLayout btnRegister = (ConstraintLayout) findViewById(R.id.btn_confirm_sign_up);
        EditText edtPassword = (EditText) findViewById(R.id.et_sign_up_password);
        EditText edtConfirmPassword = (EditText) findViewById(R.id.et_sign_up_confirm_password);
        EditText edtUsername = (EditText) findViewById(R.id.et_sign_up_username);
        EditText edtEmail = (EditText) findViewById(R.id.et_sign_up_email);
        EditText edtFirstName = (EditText) findViewById(R.id.et_sign_up_first_name);
        EditText edtLastName = (EditText) findViewById(R.id.et_sign_up_last_name);
        EditText edtGender = (EditText) findViewById(R.id.et_sign_up_gender);
        EditText edtPhoneNumber = (EditText) findViewById(R.id.et_sign_up_phone_number);
        EditText edtDay = (EditText) findViewById(R.id.et_sign_up_born_day);
        EditText edtMonth = (EditText) findViewById(R.id.et_sign_up_born_month);
        EditText edtYear = (EditText) findViewById(R.id.et_sign_up_born_year);
        TextView btnSignInNavigate = (TextView) findViewById(R.id.sign_in_nav_from_sign_up);
        View btnBack = (View) findViewById(R.id.wayback_icon);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate data
                // Code for validate data

                // Match password and confirm password
                String password = edtPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();
                if (password.equals(confirmPassword)){
                    int newCustomerId = customerRepository.generateNewCustomerId();
                    String newCustomerUsername = edtUsername.getText().toString();
                    String newCustomerPassword = edtPassword.getText().toString();
                    String newCustomerFirstName = edtFirstName.getText().toString();
                    String newCustomerLastName = edtLastName.getText().toString();
                    String newCustomerEmail = edtEmail.getText().toString();
                    String newCustomerGender = edtGender.getText().toString();
                    String newCustomerPhoneNumber = edtPhoneNumber.getText().toString();
                    Customer newCustomer;


                    String dayString = edtDay.getText().toString();
                    String monthString = edtMonth.getText().toString();
                    String yearString = edtYear.getText().toString();

                    String dateString = yearString + "-" + monthString + "-" + dayString;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date parsedDate;

                    try {
                        parsedDate = dateFormat.parse(dateString);
                    } catch (ParseException e) {
                        // Handle the parsing exception
                        e.printStackTrace();
                        return;
                    }

                    java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

                    newCustomer = new Customer(newCustomerId, newCustomerUsername, newCustomerPassword, newCustomerEmail,
                            newCustomerFirstName, newCustomerLastName, newCustomerGender, newCustomerPhoneNumber, sqlDate);

                    customerRepository.signUp(newCustomer);
                    Toast.makeText(getApplicationContext(), "Đăng ký tài khoản thành công!", Toast.LENGTH_SHORT).show();

                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Mật khẩu xác nhận không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSignInNavigate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
