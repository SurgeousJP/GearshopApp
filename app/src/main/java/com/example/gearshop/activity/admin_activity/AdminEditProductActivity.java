package com.example.gearshop.activity.admin_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gearshop.R;
import com.example.gearshop.adapter.ProductSpecEditableAdapter;
import com.example.gearshop.dialog.ConfirmDeleteSpecRowDialog;
import com.example.gearshop.model.Category;
import com.example.gearshop.model.Product;
import com.example.gearshop.utility.DatabaseHelper;
import com.example.gearshop.utility.GoogleDriveService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AdminEditProductActivity extends AppCompatActivity
        implements GoogleDriveService.OnFileSelectedListener, ConfirmDeleteSpecRowDialog.DialogListener{
    private View ReturnView;
    private android.widget.ImageView ImageView;
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
    private TextView TitleTextView;
    private GoogleDriveService googleDriveService;
    private String productImageURL;
    private Map<String, String> specMap;
    private ProductSpecEditableAdapter productSpecEditableAdapter;
    private int SpecRowItemPosition;
    private final int PRODUCT_AVAILABLE_STATUS = 1;
    private final int PRODUCT_NOT_AVAILABLE_STATUS = 2;

    private Category currentCategory;
    private Product currentProduct;
    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_product_add_update);

        Intent getDataIntent = getIntent();
        currentProduct = (Product) getDataIntent.getSerializableExtra("productItem");
        currentCategory = (Category) getDataIntent.getSerializableExtra("productCategory");
        if (currentProduct == null || currentCategory == null) return;

        TitleTextView = findViewById(R.id.add_edit_title);
        ReturnView = findViewById(R.id.add_product_screen_return_view);
        ImageView = findViewById(R.id.add_product_image);
        ProductImageSelectedPathTextView = findViewById(R.id.add_product_image_selected_path);
        ProductImagePickTextView = findViewById(R.id.add_product_image_path_picker);
        UploadProductImageTextView = findViewById(R.id.upload_add_product_text);
        UploadProductImageIconView = findViewById(R.id.upload_product_image_icon);

        ProductIdText = findViewById(R.id.product_id_edit_text);
        ProductNameText = findViewById(R.id.product_name_edit_text);
        ProductPriceText = findViewById(R.id.product_price_edit_text);
        ProductCategoryText = findViewById(R.id.product_category_edit_text);
        ProductSpecRecyclerView = findViewById(R.id.list_product_specs);
        ProductSpecTextView = findViewById(R.id.add_product_specs_text);
        ProductDescriptionTextView = findViewById(R.id.add_product_description);
        ProductStatusSwitch = findViewById(R.id.switch_product_status);
        ConfirmChangeTextView = findViewById(R.id.confirm_product_text);

        TitleTextView.setText("Sửa sản phẩm");

        ReturnView.setOnClickListener(view -> {
            finish();
        });

        View.OnClickListener uploadImageListener = view -> {
            String currentImageURL = ProductImageSelectedPathTextView.getText().toString();
            System.out.println(currentImageURL);
            try{
                Picasso.get()
                        .load(currentImageURL)
                        .into(ImageView);
            }
            catch (NullPointerException e){
                createToast("Chưa có ảnh để load");
            }
        };

        UploadProductImageTextView.setOnClickListener(uploadImageListener);
        UploadProductImageIconView.setOnClickListener(uploadImageListener);

        ProductImagePickTextView.setOnClickListener(view -> {
            googleDriveService = new GoogleDriveService(this);
            googleDriveService.openFileSelection(this);
        });

        ProductSpecTextView = findViewById(R.id.add_product_specs_text);
        ProductSpecTextView.setOnClickListener(view -> {
            specMap = updateSpecMap();
            int inputCount = specMap.size();
            do {
                inputCount++;
            } while (specMap.containsKey(String.valueOf(inputCount)));
            specMap.put(String.valueOf(inputCount),String.valueOf(inputCount));
            productSpecEditableAdapter = new ProductSpecEditableAdapter(this, specMap);
            productSpecEditableAdapter.setOnDeleteItemClickListener(position -> {
                setProductSpecRowItemPosition(position);
                showConfirmDeleteDialog();
            });
            ProductSpecRecyclerView.setAdapter(productSpecEditableAdapter);
        });

        ConfirmChangeTextView = findViewById(R.id.confirm_product_text);
        ConfirmChangeTextView.setOnClickListener(view -> {
            if (ImageView.getDrawable() == null){
                createToast("Chưa chọn ảnh cho sản phẩm !");
                return;
            }
            else currentProduct.setImageURL(ProductImageSelectedPathTextView.getText().toString());
            if (ProductNameText.getText().toString().isEmpty()){
                createToast("Chưa điền tên sản phẩm !");
                return;
            }
            else currentProduct.setName(ProductNameText.getText().toString());

            if (specMap == null || specMap.size() == 0){
                createToast("Chưa điền cấu hình sản phẩm");
                return;
            }
            else{
                String specString = generateSpecString(specMap);
                currentProduct.setSpecs(specString);
            }

            double productPrice = tryParseDouble(ProductPriceText.getText().toString());
            if (productPrice == 0){
                return;
            }
            else currentProduct.setPrice(productPrice);

            if (ProductDescriptionTextView.getText().toString().isEmpty()){
                createToast("Chưa điền chi tiết sản phẩm");
                return;
            }
            else currentProduct.setDescription(ProductDescriptionTextView.getText().toString());

            int PRODUCT_AVAILABLE_STATUS = 1;
            int PRODUCT_NOT_AVAILABLE_STATUS = 2;
            if (ProductStatusSwitch.isChecked()){
                currentProduct.setStatus(PRODUCT_AVAILABLE_STATUS);
            }
            else currentProduct.setStatus(PRODUCT_NOT_AVAILABLE_STATUS);

            currentProduct.setCategoryID(currentCategory.getID());

            try{
                // UPDATE THE PRODUCT AND RETURN RESULT
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
        loadProductData(currentProduct);
    }

    @SuppressLint("DefaultLocale")
    private void loadProductData(Product product){
        String imageURL = product.getImageURL();
        Picasso.get()
                .load(imageURL)
                .into(ImageView);

        ProductImageSelectedPathTextView.setText(product.getImageURL());
        ProductIdText.setText(String.valueOf(product.getID()));
        ProductNameText.setText(product.getName());
        ProductPriceText.setText(String.format("%.0f", product.getPrice()));
        ProductCategoryText.setText(currentCategory.getName());
        ProductDescriptionTextView.setText(product.getDescription());

        specMap = ConvertProductSpecsToMap(product.getSpecs());
        productSpecEditableAdapter = new ProductSpecEditableAdapter(this, specMap);
        productSpecEditableAdapter.setOnDeleteItemClickListener(position -> {
            setProductSpecRowItemPosition(position);
            showConfirmDeleteDialog();
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1,RecyclerView.VERTICAL, false);
        ProductSpecRecyclerView.setLayoutManager(layoutManager);
        ProductSpecRecyclerView.setAdapter(productSpecEditableAdapter);
        if (product.getStatus() == PRODUCT_AVAILABLE_STATUS){
            ProductStatusSwitch.setChecked(true);
        }
        else if (product.getStatus() == PRODUCT_NOT_AVAILABLE_STATUS){
            ProductStatusSwitch.setChecked(false);
        }
    }

    protected String generateSpecString(Map<String, String> inputSpecMap){
        String result = "";
        List<String> keySet = new ArrayList<>(inputSpecMap.keySet());
        for (int i = 0; i < inputSpecMap.size(); i++){
            result = result + keySet.get(i) + "    " + inputSpecMap.get(keySet.get(i)) + "|" + "\r" + "\n";
        }
        return result;
    }
    public Double tryParseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException | NullPointerException e) {
            createToast("Chưa điền giá / Giá không hợp lệ !" + "(" + value + ")");
            return Double.parseDouble("0");
        }
    }
    private void createToast(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
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
        return DatabaseHelper.getAdminProductListGivenID("").size() + 1;
    }
    private void showConfirmDeleteDialog() {
        ConfirmDeleteSpecRowDialog dialogFragment = new ConfirmDeleteSpecRowDialog();
        dialogFragment.setDialogListener(this);
        dialogFragment.show(getSupportFragmentManager(), "");
    }
    @Override
    public void onDialogResult(boolean result) {
        int itemPosition = getProductSpecRowItemPosition();
        if (result){
            deleteRowItem(itemPosition);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void deleteRowItem(int itemPosition){
        List<String> keys = productSpecEditableAdapter.getKeys();
        List<String> values = productSpecEditableAdapter.getValues();
        Map<String, String> specMap = productSpecEditableAdapter.getDataMap();
        String key = productSpecEditableAdapter.getKeys().get(itemPosition);
        String value = productSpecEditableAdapter.getValues().get(itemPosition);
        keys.remove(key);
        values.remove(value);
        specMap.remove(key, value);
        productSpecEditableAdapter.setKeys(keys);
        productSpecEditableAdapter.setValues(values);
        productSpecEditableAdapter.setDataMap(specMap);
        productSpecEditableAdapter.notifyDataSetChanged();
    }

    public void setProductSpecRowItemPosition(int cartItemPosition) {
        SpecRowItemPosition = cartItemPosition;
    }
    public int getProductSpecRowItemPosition() {
        return SpecRowItemPosition;
    }
    protected Map<String, String> ConvertProductSpecsToMap(String productSpec){
        String[] parts = productSpec.split("[|\\r\\n]");
        Map<String, String> result = new HashMap<>();
        for (String part : parts) {
            if (part.equals("")) continue;
            String[] headerAndDetail = part.split(" {4}");
            try {
                result.put(headerAndDetail[0], headerAndDetail[1]);
            } catch (NullPointerException | IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}