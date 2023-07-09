package com.example.gearshop.utility;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.widget.EditText;

import com.example.gearshop.R;

public class ViewPasswordInputHelper {
    private static boolean isPasswordVisible = false;

    public static void togglePasswordVisibility(Resources res, EditText edtPassword) {
        isPasswordVisible = !isPasswordVisible;
        updatePasswordVisibility(res, edtPassword);
    }

    private static void updatePasswordVisibility(Resources res, EditText edtPassword) {
        // Get the default theme
        Resources.Theme theme = res.newTheme();

        // Set the theme to null (default)
        theme.applyStyle(0, true);

        try{
            Drawable iconDrawable = isPasswordVisible
                    ? res.getDrawable(R.drawable.eye_open_icon, theme)
                    : res.getDrawable(R.drawable.eye_close_icon, theme);
        }
        catch (Resources.NotFoundException ex){
            ex.printStackTrace();
        }

        int inputType = isPasswordVisible ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
        edtPassword.setInputType(inputType);
    }
}
