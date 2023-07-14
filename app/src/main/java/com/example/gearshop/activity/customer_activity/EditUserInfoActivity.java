package com.example.gearshop.activity.customer_activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gearshop.R;
import com.example.gearshop.model.Address;
import com.example.gearshop.model.Province;
import com.example.gearshop.repository.CustomerRepository;
import com.example.gearshop.model.Customer;
import com.example.gearshop.repository.GlobalRepository;
import com.example.gearshop.utility.ActivityStartManager;
import com.example.gearshop.utility.DatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditUserInfoActivity extends AppCompatActivity {
    private CustomerRepository customerRepository;

    private RelativeLayout OptionsLayout;
    private RelativeLayout ReturnHomeLayout;
    private TextView SaveUserInfoTextView;
    ConstraintLayout backwardButton;

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userId = prefs.getString("customerId", null);

        customerRepository = new CustomerRepository();
        Customer userInfo = customerRepository.getCustomerById(userId);

        String userName = userInfo.getLastName() + " " + userInfo.getFirstName();
        TextView tvUserName = findViewById(R.id.title_username_user_info);
        tvUserName.setText(userName);

        TextView tvUsername = findViewById(R.id.username_user_info);
        tvUsername.setText(userInfo.getUsername());

        EditText PasswordTextView = findViewById(R.id.password_user_info);
        String hiddenPassword = new String(new char[userInfo.getPassword().length()]).replace('\0', '*');
        PasswordTextView.setText(hiddenPassword);

        EditText edtLastName = findViewById(R.id.lastname_user_info);
        edtLastName.setText(userInfo.getLastName());

        EditText edtFirstName = findViewById(R.id.firstname_user_info);
        edtFirstName.setText(userInfo.getFirstName());

        EditText edtGender = findViewById(R.id.sex_user_info);
        if (userInfo.getGender().equals("male")){
            edtGender.setText("Nam");
        }
        else if (userInfo.getGender().equals("female")){
            edtGender.setText("Nữ");
        }
        else edtGender.setText("N/A");

        EditText edtDob = findViewById(R.id.dob_user_info);
        edtDob.setText(userInfo.getDateOfBirth().toString());

        EditText edtEmail = findViewById(R.id.email_user_info);
        edtEmail.setText(userInfo.getEmail());

        EditText tvAddress = findViewById(R.id.address_user_info);
        Address userAddress = GlobalRepository.getCustomerAddress();
        tvAddress.setText(userAddress.getHouseNumber() + "\n" + userAddress.getStreet());

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

        SaveUserInfoTextView = findViewById(R.id.save_user_info_tv);
        SaveUserInfoTextView.setOnClickListener(view -> {
            if (edtLastName.getText().toString().isEmpty()){
                makeToast("Chưa điền Họ !");
                return;
            }

            if (edtFirstName.getText().toString().isEmpty()){
                makeToast("Chưa điền Tên !");
                return;
            }
            if (!(edtGender.getText().toString().equals("Nam") ||
                     edtGender.getText().toString().equals("Nữ"))){
                makeToast("Chưa điền đúng thông tin giới tính !");
                return;
            }

            String dateString = edtDob.getText().toString();
            try {
                new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            }
            catch (ParseException e) {
                makeToast("Ngày tháng không đúng format yyyy-MM-dd");
                return;
            }

            if (!edtEmail.getText().toString().contains("@") || !edtEmail.getText().toString().contains(".")){
                makeToast("Email không hợp lệ");
                return;
            }

            if (!edtPhoneNumber.getText().toString().matches("0\\d{9}")){
                makeToast("Số điện thoại không hợp lệ");
                return;
            }

            userInfo.setLastName(edtLastName.getText().toString());
            userInfo.setFirstName(edtFirstName.getText().toString());
            if (edtGender.getText().toString().equals("Nam")){
                userInfo.setGender("male");
            }
            else userInfo.setGender("female");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate;
            try {
                parsedDate = dateFormat.parse(dateString);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            userInfo.setDateOfBirth(new java.sql.Date(parsedDate.getTime()));

            userInfo.setPhoneNumber(edtPhoneNumber.getText().toString());

            DatabaseHelper.updateCustomerDataToAzure(userInfo);

            makeToast("Cập nhật thông tin thành công");
        });

        backwardButton = findViewById(R.id.wayback_icon_user_info);
        backwardButton.setOnClickListener(view -> {
            finish();
        });

        OptionsLayout = findViewById(R.id.more_info_order_detail);
        OptionsLayout.setOnClickListener(this::showPopupMenu);

        ReturnHomeLayout = findViewById(R.id.escape);
        ReturnHomeLayout.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), HomeActivity.class);
        });
    }
    private void makeToast(String message){
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
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

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.dots_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.logout_item) {
                    Intent intent = new Intent(getBaseContext(), SignInActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.faq_item) {
                    Intent intent = new Intent(getBaseContext(), FAQActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

}
