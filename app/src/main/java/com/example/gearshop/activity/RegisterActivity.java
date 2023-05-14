package com.example.gearshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.gearshop.R;
import com.example.gearshop.testclass.Customer;
import com.example.gearshop.testclass.CustomerRepository;

public class RegisterActivity extends AppCompatActivity {
    CustomerRepository customerRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        customerRepository = new CustomerRepository();

        AppCompatButton btnRegister = (AppCompatButton) findViewById(R.id.register_button);
        EditText edtPassword = (EditText) findViewById(R.id.register_password_edit);
        EditText edtConfirmPassword = (EditText) findViewById(R.id.confirm_register_password_edit);
        EditText edtUsername = (EditText) findViewById(R.id.register_username_edit);
        EditText edtEmail = (EditText) findViewById(R.id.register_email_edit);
        AppCompatButton btnSignInNavigate = (AppCompatButton) findViewById(R.id.sign_in_navigate_button);
        AppCompatButton btnBack = (AppCompatButton) findViewById(R.id.backward_button);

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
                    String newCustomerEmail = edtEmail.getText().toString();

                    Customer newCustomer = new Customer(newCustomerId, newCustomerUsername, newCustomerPassword, newCustomerEmail);

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
