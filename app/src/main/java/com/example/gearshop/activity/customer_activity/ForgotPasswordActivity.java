package com.example.gearshop.activity.customer_activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gearshop.R;
import com.example.gearshop.repository.CustomerRepository;
import com.example.gearshop.utility.ViewPasswordInputHelper;

import java.util.Random;

public class ForgotPasswordActivity extends AppCompatActivity {

    CustomerRepository customerRepository;
    private boolean isPasswordVisible = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        customerRepository = new CustomerRepository();

        View btnBack = findViewById(R.id.wayback_icon);
        View btnViewPasswordNewPassword = findViewById(R.id.view_password_icon_new_password);
        View btnViewPasswordConfirmPassword = findViewById(R.id.view_password_icon_confirm_password);
        TextView btnSendCode = findViewById(R.id.sendcode);
        ConstraintLayout btnConfirmChangePassword = findViewById(R.id.confirm_button);
        EditText edtEmail = findViewById(R.id.et_sign_up_email);
        EditText edtUsername = findViewById(R.id.et_sign_up_username);
        EditText edtConfirmCode = (EditText) findViewById(R.id.confirm_code_edit);
        EditText edtNewPassword = findViewById(R.id.password_edit);
        EditText edtConfirmNewPassword = findViewById(R.id.confirm_password_edit_text);


        btnSendCode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                String email = edtEmail.getText().toString();
                String username = edtUsername.getText().toString();

                if (hasValue(email, username)) {
                    if (customerRepository.isExists(email, username)){
                        // send code to email
                        SendCodeToEmail();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Email hoặc tên đăng nhập không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Vui lòng điền đủ tất cả thông tin",Toast.LENGTH_SHORT).show();
                }
            }

            private void SendCodeToEmail() {
                // Generate a random number to represent the "send and input code" action
                Random random = new Random();
                int code = random.nextInt(5000) + 1000;

                Toast.makeText(getApplicationContext(), "Đã gửi mã xác nhận", Toast.LENGTH_SHORT).show();

                edtConfirmCode.setText(Integer.toString(code));
            }
        });

        // Button Xac nhan
        btnConfirmChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = edtNewPassword.getText().toString();
                String confirmNewPassword = edtConfirmNewPassword.getText().toString();

                if (newPassword.equals(confirmNewPassword)){
                    customerRepository.changePassword(edtEmail.getText().toString(), newPassword);

                    Toast.makeText(v.getContext(), "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(v.getContext(), "Mật khẩu xác nhận không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnViewPasswordNewPassword.setOnClickListener(view -> {
            ViewPasswordInputHelper.togglePasswordVisibility(getResources(), edtNewPassword);
        });
        btnViewPasswordConfirmPassword.setOnClickListener(view -> {
            ViewPasswordInputHelper.togglePasswordVisibility(getResources(), edtConfirmNewPassword);
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean hasValue(String email, String username) {
        return !(email.equals("") || username.equals(""));
    }

    private boolean isValidUserInfo(String email, String username) {
        return true;
    }
}
