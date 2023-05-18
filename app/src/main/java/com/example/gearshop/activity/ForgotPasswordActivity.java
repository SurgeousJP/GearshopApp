//package com.example.gearshop.activity;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.view.View;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.AppCompatButton;
//
//import com.example.gearshop.R;
//import com.example.gearshop.controller.CustomerRepository;
//
//import java.util.Random;
//
//public class ForgotPasswordActivity extends AppCompatActivity {
//
//    CustomerRepository customerRepository;
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.forgot_password);
//
//        customerRepository = new CustomerRepository();
//
//        AppCompatButton btnSendCode = (AppCompatButton) findViewById(R.id.forgot_send_code_button);
//        EditText edtEmail = (EditText) findViewById(R.id.forgot_email_edit);
//        EditText edtUsername = (EditText) findViewById(R.id.forgot_username_edit);
//
//        AppCompatButton btnConfirmChangePassword = (AppCompatButton) findViewById(R.id.confirm_change_password_button);
//        EditText edtNewPassword = (EditText) findViewById(R.id.new_password_edit);
//        EditText edtConfirmNewPassword = (EditText) findViewById(R.id.confirm_new_passwword_edit);
//
//        AppCompatButton btnBack = (AppCompatButton) findViewById(R.id.backward_button);
//
//        btnSendCode.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//
//                String email = edtEmail.getText().toString();
//                String username = edtUsername.getText().toString();
//
//                if (hasValue(email, username)) {
//                    if (customerRepository.isExists(email, username)){
//                        // send code to email
//                        Random random = new Random();
//                        int code = random.nextInt(5000) + 1000;
//
//                        Toast.makeText(getApplicationContext(), "Đã gửi mã xác nhận", Toast.LENGTH_SHORT).show();
//                        EditText edtConfirmCode = (EditText) findViewById(R.id.confirm_code_edit);
//                        edtConfirmCode.setText(Integer.toString(code));
//                    }
//                    else {
//                        Toast.makeText(getApplicationContext(), "Email hoặc tên đăng nhập không hợp lệ", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                }
//                else {
//                    Toast.makeText(getApplicationContext(), "Vui lòng điền đủ tất cả thông tin",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        // Button Xac nhan
//        btnConfirmChangePassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String newPassword = edtNewPassword.getText().toString();
//                String confirmNewPassword = edtConfirmNewPassword.getText().toString();
//
//                if (newPassword.equals(confirmNewPassword)){
//                    customerRepository.changePassword(edtEmail.getText().toString(), newPassword);
//
//                    Toast.makeText(getApplicationContext(), "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//                else {
//                    Toast.makeText(getApplicationContext(), "Mật khẩu xác nhận không chính xác", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }
//
//
//
//    private boolean hasValue(String email, String username) {
//        return !(email.equals("") || username.equals(""));
//    }
//
//    private boolean isValidUserInfo(String email, String username) {
//        return true;
//    }
//}
