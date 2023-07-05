package com.example.gearshop.activity.admin_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.gearshop.R;
import com.example.gearshop.model.Admin;
import com.example.gearshop.utility.DatabaseHelper;

public class AdminInfoActivity extends AppCompatActivity {

    private TextView AdminUsernameTextView;
    private TextView AdminPasswordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_info);

        AdminUsernameTextView = findViewById(R.id.admin_username_info);
        AdminPasswordTextView = findViewById(R.id.admin_password_info);

        Admin adminInfo = DatabaseHelper.getAdmin();

        if (adminInfo != null){
            AdminUsernameTextView.setText(adminInfo.getUsername());
            AdminPasswordTextView.setText(adminInfo.getPassword());
        }
    }
}