package com.example.gearshop.activity.customer_activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gearshop.R;
import com.example.gearshop.repository.CustomerRepository;
import com.example.gearshop.model.Customer;
import com.example.gearshop.repository.GlobalRepository;
import com.example.gearshop.utility.DatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                    AddCustomerToDatabase(password);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Mật khẩu xác nhận không chính xác", Toast.LENGTH_SHORT).show();
                }
            }

            private void AddCustomerToDatabase(String password) {
                int newCustomerId = customerRepository.generateNewCustomerId();
                String newCustomerUsername = edtUsername.getText().toString();
                String newCustomerPassword = edtPassword.getText().toString();
                String newCustomerFirstName = edtFirstName.getText().toString();
                String newCustomerLastName = edtLastName.getText().toString();
                String newCustomerEmail = edtEmail.getText().toString();
                String newCustomerGender = edtGender.getText().toString();
                String newCustomerPhoneNumber = edtPhoneNumber.getText().toString();

                String dayString = edtDay.getText().toString();
                int dayInt = Integer.parseInt(dayString);
                String monthString = edtMonth.getText().toString();
                int monthInt = Integer.parseInt(monthString);
                String yearString = edtYear.getText().toString();
                int yearInt = Integer.parseInt(monthString);

                String dateString = yearString + "-" + monthString + "-" + dayString;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedDate;

                if (!validatePhoneNumber(newCustomerPhoneNumber)) {
                    // Handle phone number validation error
                    return;
                }

                if (!validateDate(dayInt, monthInt, yearInt)) {
                    // Handle date validation error
                    return;
                }

                if (!validateName(newCustomerFirstName) || !validateName(newCustomerLastName)) {
                    // Handle name validation error
                    return;
                }

                if (!validateEmail(newCustomerEmail)) {
                    // Handle email validation error
                    return;
                }

                if (!validatePassword(password)) {
                    // Handle password validation error
                    return;
                }

                try {
                    parsedDate = dateFormat.parse(dateString);
                } catch (ParseException e) {
                    // Handle the parsing exception
                    e.printStackTrace();
                    return;
                }

                java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

                Customer newCustomer = new Customer(newCustomerId, newCustomerUsername, newCustomerPassword, newCustomerEmail,
                        newCustomerFirstName, newCustomerLastName, newCustomerGender, newCustomerPhoneNumber, sqlDate, -1);

                customerRepository.signUp(newCustomer);
                Toast.makeText(getApplicationContext(), "Đăng ký tài khoản thành công!", Toast.LENGTH_SHORT).show();

                finish();
            }

            private boolean validatePhoneNumber(String phoneNumber) {
                return phoneNumber.length() <= 10;
            }

            private boolean validateDate(int day, int month, int year) {
                if (day < 1 || day > 31 || month < 1 || month > 12 || year < 1900 || year > 2100) {
                    return false;
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setLenient(false);
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month - 1);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                try {
                    calendar.getTime();
                    return true;
                } catch (IllegalArgumentException e) {
                    return false;
                }
            }

            private boolean validateName(String name) {
                return !name.matches(".*\\d.*");
            }

            private boolean validateEmail(String email) {
                Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
                Matcher matcher = emailPattern.matcher(email);
                return matcher.matches();
            }

            private boolean validatePassword(String password) {
                int minPasswordLength = 8;
                boolean hasUpperCase = !password.equals(password.toLowerCase());
                boolean hasLowerCase = !password.equals(password.toUpperCase());
                boolean hasNumber = password.matches(".*\\d.*");
                boolean hasSpecialChar = !password.matches("[a-zA-Z0-9 ]*");

                return password.length() >= minPasswordLength && hasUpperCase && hasLowerCase && hasNumber && hasSpecialChar;
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
