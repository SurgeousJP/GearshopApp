package com.example.gearshop.activity.customer_activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gearshop.R;
import com.example.gearshop.model.Customer;
import com.example.gearshop.repository.CustomerRepository;
import com.example.gearshop.utility.ActivityStartManager;
import com.example.gearshop.utility.EmailUtil;
import com.example.gearshop.utility.ViewPasswordInputHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class ForgotPasswordActivity extends AppCompatActivity {

    CustomerRepository customerRepository;
    int ChangePasswordCode;
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
        edtConfirmCode.setInputType(InputType.TYPE_CLASS_NUMBER);
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
                    if (customerRepository.isExistUserWithEmail(email, username)){
                        // send code to email
                        Customer currentCustomer = customerRepository.getCustomerThroughEmail(email);
                        SendCodeToEmail(currentCustomer);
                        Toast.makeText(getBaseContext(), "Đã gửi email có mã xác nhận đổi mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Username không tồn tại / không có email như trên", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Vui lòng điền đủ tất cả thông tin",Toast.LENGTH_SHORT).show();
                }
            }

            @SuppressLint({"SetTextI18n", "StaticFieldLeak"})
            private void SendCodeToEmail(Customer customer) {
                // Generate a random number to represent the "send and input code" action
                Random random = new Random();
                ChangePasswordCode = random.nextInt(5000) + 1000;

                String emailReceiver = customer.getEmail();
                String emailSubject = "Password Reset (From GearShop)";
                String emailBody =
                        "Hi " + customer.getLastName() + " " + customer.getFirstName() + ",\n" +
                        "There was a request to change your password!\n" +
                        "\n" +
                        "If you did not make this request then please ignore this email.\n" +
                        "\n" +
                        "Otherwise, here is the code to change your password: " + ChangePasswordCode;

                // Perform email existence check
                boolean emailExists = checkEmailExists(emailReceiver);

                if (emailExists) {
                    String[] mailSenderContent = readTextFromAssets(getBaseContext(), "sender.txt").split("\n");
                    String username = mailSenderContent[0];
                    String password = mailSenderContent[1];

                    Properties properties = System.getProperties();
                    properties.put("mail.smtp.connectiontimeout", 1000);
                    properties.put("mail.smtp.port", "587");
                    properties.put("mail.smtp.auth", "true");
                    properties.put("mail.smtp.starttls.enable", "true");
                    properties.setProperty("mail.smtp.host", "smtp.gmail.com");

                    Session session = Session.getInstance(properties, new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
                    new AsyncTask<Void, Void, Boolean>() {
                        @SuppressLint("StaticFieldLeak")
                        @Override
                        protected Boolean doInBackground(Void... voids) {
                            EmailUtil.sendEmail(session, emailReceiver, emailSubject, emailBody);
                            return true;
                        }
                    }.execute();
                } else {
                    // Email does not exist, show an error message or perform appropriate action
                    // For example, display a toast message
                    Toast.makeText(getBaseContext(), "Email không hợp lệ !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Button Xac nhan
        btnConfirmChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(edtConfirmCode.getText().toString()) != ChangePasswordCode){
                    Toast.makeText(getBaseContext(), "Mã xác nhận đổi mật khẩu không đúng !", Toast.LENGTH_SHORT).show();
                    return;
                }
                String newPassword = edtNewPassword.getText().toString();
                String confirmNewPassword = edtConfirmNewPassword.getText().toString();

                if (newPassword.equals(confirmNewPassword)){
                    if (!validPassword(newPassword)){
                        Toast.makeText(ForgotPasswordActivity.this,
                                "Mật khẩu không đủ 8 ký tự hoặc không hợp lệ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    
                    customerRepository.changePassword(edtEmail.getText().toString(), newPassword);

                    Toast.makeText(v.getContext(), "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                    ActivityStartManager.startTargetActivity(getBaseContext(), SignInActivity.class);
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
                ActivityStartManager.startTargetActivity(getBaseContext(), SignInActivity.class);
                finish();
            }
        });
    }

    private boolean validPassword(String password) {
        int minPasswordLength = 8;
        boolean hasUpperCase = !password.equals(password.toLowerCase());
        boolean hasLowerCase = !password.equals(password.toUpperCase());
        boolean hasNumber = password.matches(".*\\d.*");
        boolean hasSpecialChar = !password.matches("[a-zA-Z0-9 ]*");

        return password.length() >= minPasswordLength;
    }

    private boolean checkEmailExists(String email) {
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            return true;
        } catch (AddressException e) {
            return false;
        }
    }

    private boolean hasValue(String email, String username) {
        return !(email.equals("") || username.equals(""));
    }

    private boolean isValidUserInfo(String email, String username) {
        return true;
    }

    public String readTextFromAssets(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
