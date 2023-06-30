package com.example.gearshop.activity.customer_activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gearshop.R;
import com.example.gearshop.activity.admin_activity.AdminProductCategoryManagementActivity;
import com.example.gearshop.model.Admin;
import com.example.gearshop.repository.AdminRepository;
import com.example.gearshop.repository.CustomerRepository;
import com.example.gearshop.model.Customer;
import com.example.gearshop.utility.ActivityStartManager;

public class SignInActivity extends AppCompatActivity {

    CustomerRepository customerRepository;
    AdminRepository adminRepository;
    private View passwordIconView;
    EditText edtPassword;

    private boolean isPasswordVisible = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        customerRepository = new CustomerRepository();
        adminRepository = new AdminRepository();

        ConstraintLayout btnLogin = findViewById(R.id.login_button);
        TextView tvForgotPassword = findViewById(R.id.forgot_password_nav_from_sign_in);
        EditText edtUsername = findViewById(R.id.username_text);
        edtPassword = findViewById(R.id.et_sign_up_password);

        TextView tvSignUpNav = findViewById(R.id.sign_up_nav_from_sign_in);

        btnLogin.setOnClickListener (new View.OnClickListener() {
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                // try login as admin
                Admin admin  = adminRepository.signIn(username, password);
                if (admin != null)
                {
                    ActivityStartManager.startTargetActivity(getApplicationContext(), AdminProductCategoryManagementActivity.class);
                    finish();

                    return;
                }

                Customer customer = customerRepository.signIn(username, password);
                if (customer != null) {

                    // save the logged in customer id
                    SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("customerId", Integer.toString(customer.getID()));
                    editor.apply();

                    // go to main activity (home page)
                    Intent intent = new Intent(v.getContext(), HomeActivity.class);
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

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                ActivityStartManager.startTargetActivity(getBaseContext(), ForgotPasswordActivity.class);
            }
        });

        tvSignUpNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityStartManager.startTargetActivity(v.getContext(), SignUpActivity.class);
            }
        });

        passwordIconView = findViewById(R.id.view_password_icon);
        passwordIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });
    }

    private void togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible;
        updatePasswordVisibility();
    }

    private void updatePasswordVisibility() {
        // Get the default theme
        Resources.Theme theme = getResources().newTheme();

        // Set the theme to null (default)
        theme.applyStyle(0, true);

        try{
            Drawable iconDrawable = isPasswordVisible
                    ? getResources().getDrawable(R.drawable.eye_open_icon, theme)
                    : getResources().getDrawable(R.drawable.eye_close_icon, theme);
        }
        catch (Resources.NotFoundException ex){
            ex.printStackTrace();
        }

        int inputType = isPasswordVisible ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
        edtPassword.setInputType(inputType);
    }

}