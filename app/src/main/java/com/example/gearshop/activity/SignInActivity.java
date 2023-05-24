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
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gearshop.R;
import com.example.gearshop.controller.CustomerRepository;
import com.example.gearshop.model.Customer;

public class SignInActivity extends AppCompatActivity {

    CustomerRepository customerRepository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        customerRepository = new CustomerRepository();

        ConstraintLayout btnLogin = (ConstraintLayout) findViewById(R.id.login_button);
        TextView tvForgotPassword = (TextView) findViewById(R.id.forgot_password_nav_from_sign_in);
        EditText edtUsername = (EditText) findViewById(R.id.username_text);
        EditText edtPassword = (EditText) findViewById(R.id.et_sign_up_password);

        TextView tvSignUpNav = (TextView) findViewById(R.id.sign_up_nav_from_sign_in);

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
                    Intent intent = new Intent(v.getContext(), ProductsInCategoryActivity.class);
                    v.getContext().startActivity(intent);

                    // end this activity after logged in so customer can't back to this activity
                    finish();
                }
                else{
                    if (username.equals("") || password.equals(""))
                        Toast.makeText(SignInActivity.this, "Vui lòng điền đẩy đủ thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(SignInActivity.this, "Thông tin đăng nhập không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

//        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v){
//                Intent intent = new Intent(v.getContext(), ForgotPasswordActivity.class);
//                v.getContext().startActivity(intent);
//            }
//        });
//
        tvSignUpNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SignUpActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

}