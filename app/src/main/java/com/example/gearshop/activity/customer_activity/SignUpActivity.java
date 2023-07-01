package com.example.gearshop.activity.customer_activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gearshop.R;
import com.example.gearshop.repository.CustomerRepository;
import com.example.gearshop.model.Customer;
import com.example.gearshop.utility.ViewPasswordInputHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    CustomerRepository customerRepository;
    private boolean isPasswordVisible = false;

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
        TextView edtGender = (TextView) findViewById(R.id.et_sign_up_gender);
        EditText edtPhoneNumber = (EditText) findViewById(R.id.et_sign_up_phone_number);
        EditText edtDay = (EditText) findViewById(R.id.et_sign_up_born_day);
        EditText edtMonth = (EditText) findViewById(R.id.et_sign_up_born_month);
        EditText edtYear = (EditText) findViewById(R.id.et_sign_up_born_year);
        TextView btnSignInNavigate = (TextView) findViewById(R.id.sign_in_nav_from_sign_up);
        View btnBack = (View) findViewById(R.id.wayback_icon);
        View btnViewPasswordNew = (View) findViewById(R.id.view_password_icon_password);
        View btnViewPasswordConfirm = (View) findViewById(R.id.view_password_icon_confirm_password);

        edtDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 2) {
                    edtMonth.requestFocus();
                }
            }
        });

        edtMonth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 2) {
                    edtYear.requestFocus();
                }
            }
        });

        edtGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] genderOptions = {"Nam", "Nữ"};

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Select Gender")
                        .setItems(genderOptions, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String selectedGender = genderOptions[which];
                                edtGender.setText(selectedGender);
                            }
                        })
                        .show();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate data
                // Code for validate data

                // Match password and confirm password
                String password = edtPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();
                if (password.equals(confirmPassword)){
                    if (!AddedCustomerToDatabase(password)){
                        // not successful
                    };
                }
                else {
                    Toast.makeText(getApplicationContext(), "Mật khẩu xác nhận không chính xác", Toast.LENGTH_SHORT).show();
                }
            }

            private boolean AddedCustomerToDatabase(String password) {
                int newCustomerId = customerRepository.generateNewCustomerId();
                String newCustomerUsername = edtUsername.getText().toString();
                String newCustomerPassword = edtPassword.getText().toString();
                String newCustomerFirstName = edtFirstName.getText().toString();
                String newCustomerLastName = edtLastName.getText().toString();
                String newCustomerEmail = edtEmail.getText().toString();
                String newCustomerPhoneNumber = edtPhoneNumber.getText().toString();
                String newCustomerGender = edtGender.getText().toString();

                String dayString = edtDay.getText().toString();
                int dayInt = Integer.parseInt(dayString);
                String monthString = edtMonth.getText().toString();
                int monthInt = Integer.parseInt(monthString);
                String yearString = edtYear.getText().toString();
                int yearInt = Integer.parseInt(yearString);

                if (newCustomerGender.equals("Nam")) {
                    newCustomerGender = "male";
                }
                else {
                    newCustomerGender = "female";
                }

                String dateString = yearString + "-" + monthString + "-" + dayString;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedDate;

                if (!validName(newCustomerFirstName) || !validName(newCustomerLastName)) {
                    // Handle name validation error
                    Toast.makeText(getApplicationContext(), "Tên không hợp lệ", Toast.LENGTH_SHORT).show();

                    return false;
                }

                if (!validUsername(newCustomerUsername)){
                    Toast.makeText(getApplicationContext(), "Tên đăng nhập không hợp lệ", Toast.LENGTH_SHORT).show();
                }

                if (!validPhoneNumber(newCustomerPhoneNumber)) {
                    Toast.makeText(getApplicationContext(), "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                    return false;
                }

                if (!validDate(dayInt, monthInt, yearInt)) {
                    // Handle date validation error
                    Toast.makeText(getApplicationContext(), "Ngày sinh không hợp lệ", Toast.LENGTH_SHORT).show();

                    return false;
                }



                if (!validEmail(newCustomerEmail)) {
                    // Handle email validation error
                    Toast.makeText(getApplicationContext(), "Email không hợp lệ", Toast.LENGTH_SHORT).show();

                    return false;
                }

                if (!validPassword(password)) {
                    // Handle password validation error
                    Toast.makeText(getApplicationContext(), "Mật khẩu quá ngắn", Toast.LENGTH_SHORT).show();

                    return false;
                }

                try {
                    parsedDate = dateFormat.parse(dateString);
                } catch (ParseException e) {
                    // Handle the parsing exception
                    e.printStackTrace();
                    return false;
                }

                java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

                // check if username, email and phone number is unique

                if (customerRepository.isExistsUsername(newCustomerUsername)){
                    Toast.makeText(getApplicationContext(), "Tên đăng nhập đã được đăng ký", Toast.LENGTH_SHORT).show();

                    return false;
                }

                if (customerRepository.isExistsEmail(newCustomerEmail)){
                    Toast.makeText(getApplicationContext(), "Email đã được đăng ký", Toast.LENGTH_SHORT).show();

                    return false;
                }

                if (customerRepository.isExistsPhoneNumber(newCustomerPhoneNumber)){
                    Toast.makeText(getApplicationContext(), "Số điện thoại đã được đăng ký", Toast.LENGTH_SHORT).show();

                    return false;
                }


                Customer newCustomer = new Customer(newCustomerId, newCustomerUsername, newCustomerPassword, newCustomerEmail,
                        newCustomerFirstName, newCustomerLastName, newCustomerGender, newCustomerPhoneNumber, sqlDate, -1);

                customerRepository.signUp(newCustomer);
                Toast.makeText(getApplicationContext(), "Đăng ký tài khoản thành công!", Toast.LENGTH_SHORT).show();

                finish();
                return true;
            }

            private boolean validUsername(String username) {
                return username.length() > 0;
            }

            private boolean validPhoneNumber(String phoneNumber) {
                return phoneNumber.length() == 10;
            }

            private boolean validDate(int day, int month, int year) {
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

            private boolean validName(String name) {
                return name.matches("[\\p{L}\\s]+") && name.length() > 0;
            }

            private boolean validEmail(String email) {
                Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
                Matcher matcher = emailPattern.matcher(email);
                return matcher.matches() && email.contains("@");
            }

            private boolean validPassword(String password) {
                int minPasswordLength = 8;
                boolean hasUpperCase = !password.equals(password.toLowerCase());
                boolean hasLowerCase = !password.equals(password.toUpperCase());
                boolean hasNumber = password.matches(".*\\d.*");
                boolean hasSpecialChar = !password.matches("[a-zA-Z0-9 ]*");

                return password.length() >= minPasswordLength;
            }
        });

        btnViewPasswordNew.setOnClickListener(view -> {
            ViewPasswordInputHelper.togglePasswordVisibility(getResources(), edtPassword);
        });

        btnViewPasswordConfirm.setOnClickListener(view -> {
            ViewPasswordInputHelper.togglePasswordVisibility(getResources(), edtConfirmPassword);
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
