package com.example.gearshop.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.gearshop.R;
import com.example.gearshop.controller.CustomerRepository;
import com.example.gearshop.model.Customer;

public class LoginActivity extends AppCompatActivity {

    CustomerRepository customerRepository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        customerRepository = new CustomerRepository();

        AppCompatButton btnLogin = (AppCompatButton) findViewById(R.id.login_button);
        TextView forgotPassword = (TextView) findViewById(R.id.forgot_password_textview);
        EditText edtUsername = (EditText) findViewById(R.id.username_text);
        EditText edtPassword = (EditText) findViewById(R.id.password_text);

        AppCompatButton btnSignUp = (AppCompatButton) findViewById(R.id.register_navigate_button);

        btnLogin.setOnClickListener (new View.OnClickListener() {
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                Customer customer = customerRepository.signIn(username, password);
                if (customer != null) {

                    // save the logged in customer id
                    SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("customerId", Integer.toString(customer.getID()));
                    editor.apply();

                    // go to main activity (home page)
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    v.getContext().startActivity(intent);

                    // end this activity after logged in so customer can't back to this activity
                    finish();
                }
                else{
                    if (username.equals("") || password.equals(""))
                        Toast.makeText(LoginActivity.this, "Vui lòng điền đẩy đủ thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(LoginActivity.this, "Thông tin đăng nhập không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), ForgotPasswordActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RegisterActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

}