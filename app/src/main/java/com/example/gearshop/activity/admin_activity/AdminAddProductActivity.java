package com.example.gearshop.activity.admin_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gearshop.R;
import com.example.gearshop.adapter.ProductSpecEditableAdapter;
import com.example.gearshop.model.Category;
import com.example.gearshop.utility.DatabaseHelper;
import com.example.gearshop.utility.GoogleDriveService;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class AdminAddProductActivity extends AppCompatActivity implements GoogleDriveService.OnFileSelectedListener{

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

    private GoogleDriveService googleDriveService;
    private String productImageURL;
    private Map<String, String> specMap;
    private ProductSpecEditableAdapter productSpecEditableAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_product_add_update);

        Intent getCategoryData = getIntent();
        Category productCategory = (Category) getCategoryData.getSerializableExtra("productCategory");

        ReturnView = findViewById(R.id.add_product_screen_return_view);
        ReturnView.setOnClickListener(view -> {
            finish();
        });

        ImageView = findViewById(R.id.add_product_image);

        ProductImageSelectedPathTextView = findViewById(R.id.add_product_image_selected_path);

        ProductImagePickTextView = findViewById(R.id.add_product_image_path_picker);
        ProductImagePickTextView.setOnClickListener(view -> {
            googleDriveService = new GoogleDriveService(this);
            googleDriveService.openFileSelection(this);
        });

        View.OnClickListener uploadImageListener = view -> {
            String imageURL = ProductImageSelectedPathTextView.getText().toString();
            System.out.println(imageURL);
            try{
                Picasso.get()
                        .load(imageURL)
                        .into(ImageView);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        };

        UploadProductImageTextView = findViewById(R.id.upload_add_product_text);
        UploadProductImageTextView.setOnClickListener(uploadImageListener);

        UploadProductImageIconView = findViewById(R.id.upload_product_image_icon);
        UploadProductImageIconView.setOnClickListener(uploadImageListener);

        ProductIdText = findViewById(R.id.product_id_edit_text);
        try{
            ProductIdText.setText(String.valueOf(generateNewProductId()));
        }
        catch(NullPointerException | IndexOutOfBoundsException e){
            Toast.makeText(getBaseContext(), "Lỗi generate id sản phẩm", Toast.LENGTH_SHORT).show();
        }

        ProductNameText = findViewById(R.id.product_name_edit_text);

        ProductPriceText = findViewById(R.id.product_price_edit_text);
        ProductPriceText.setInputType(InputType.TYPE_CLASS_NUMBER);

        ProductCategoryText = findViewById(R.id.product_category_edit_text);
        ProductCategoryText.setText(productCategory.getName());

        ProductSpecRecyclerView = findViewById(R.id.list_product_specs);
        specMap = new LinkedHashMap<>();
        productSpecEditableAdapter = new ProductSpecEditableAdapter(this, specMap);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1,RecyclerView.VERTICAL, false);
        ProductSpecRecyclerView.setLayoutManager(layoutManager);
        ProductSpecRecyclerView.setAdapter(productSpecEditableAdapter);

        ProductSpecTextView = findViewById(R.id.add_product_specs_text);
        ProductSpecTextView.setOnClickListener(view -> {
            specMap = updateSpecMap();
            int inputCount = specMap.size() + 1;
            specMap.put(String.valueOf(inputCount),String.valueOf(inputCount));
            productSpecEditableAdapter = new ProductSpecEditableAdapter(this, specMap);
            ProductSpecRecyclerView.setAdapter(productSpecEditableAdapter);
        });

        ProductDescriptionTextView = findViewById(R.id.add_product_description);

        ProductStatusSwitch = findViewById(R.id.switch_product_status);

        ConfirmChangeTextView = findViewById(R.id.confirm_product_text);
    }

    private Map<String, String> updateSpecMap(){
        Map<String, String> newSpecMap = new LinkedHashMap<>();
        for (int i = 0; i < specMap.size(); i++){
            View itemView = ProductSpecRecyclerView.getChildAt(i);
            EditText keyEditText = itemView.findViewById(R.id.product_detail_header_text);
            EditText valueEditText = itemView.findViewById(R.id.product_detail_description_text);
            newSpecMap.put(keyEditText.getText().toString(), valueEditText.getText().toString());
        }
        return newSpecMap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googleDriveService.handleActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onFileSelected(String fileUrl) {
        // Handle the selected file URL here
        if (fileUrl != null) {
            Log.d("AddProductActivity", "Selected file URL: " + fileUrl);
            productImageURL = fileUrl;
            ProductImageSelectedPathTextView.setText(fileUrl);
        }
        else{
            productImageURL = null;
        }
    }
    public int generateNewProductId(){
        return DatabaseHelper.getAdminProductListGivenID("").size();
    }
}