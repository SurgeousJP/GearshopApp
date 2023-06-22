package com.example.gearshop.activity.admin_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.gearshop.R;

public class AdminAddEditProductActivity extends AppCompatActivity {

    private View ReturnView;
    private ImageView ImageView;
    private TextView ProductImagePickTextView;
    private TextView ProductImageSelectedPathTextView;
    private TextView UploadProductImageTextView;
    private View UploadProductImageIconView;
    private EditText ProductIdText;
    private EditText ProductNameText;
    private EditText ProductPriceText;
    private TextView ProductCategoryText;
    private RecyclerView ProductSpecRecyclerView;
    private TextView ProductSpecTextView;
    private TextView ProductDescriptionTextView;
    private Switch ProductStatusSwitch;
    private TextView ConfirmChangeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_product_add_update);

        ReturnView = findViewById(R.id.add_product_screen_return_view);
        ImageView = findViewById(R.id.add_product_image);
        ProductImagePickTextView = findViewById(R.id.add_product_image_path_picker);
        UploadProductImageTextView = findViewById(R.id.upload_add_product_text);
        UploadProductImageIconView = findViewById(R.id.upload_product_image_icon);
        ProductImageSelectedPathTextView = findViewById(R.id.add_product_image_selected_path);
        ProductIdText = findViewById(R.id.product_id_edit_text);
        ProductNameText = findViewById(R.id.product_name_edit_text);
        ProductPriceText = findViewById(R.id.product_price_edit_text);
        ProductCategoryText = findViewById(R.id.product_category_edit_text);
        ProductSpecRecyclerView = findViewById(R.id.list_product_specs);
        ProductSpecTextView = findViewById(R.id.add_product_specs_text);
        ProductDescriptionTextView = findViewById(R.id.add_product_description);
        ProductStatusSwitch = findViewById(R.id.switch_product_status);
        ConfirmChangeTextView = findViewById(R.id.confirm_product_text);
    }
}