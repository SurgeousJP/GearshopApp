package com.example.gearshop.activity.admin_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gearshop.R;
import com.example.gearshop.model.Category;
import com.example.gearshop.utility.DatabaseHelper;

public class AdminAddProductActivity extends AppCompatActivity {

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

    private String chosenImagePath;
    private static final String FILE_CHOOSER_URL = "https://www.google.com.vn/";
    private static final int PICK_IMAGE_FROM_WEBSITE = 2;

    private WebView webView;

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
            selectPictureFromWebsite();
        });

        UploadProductImageTextView = findViewById(R.id.upload_add_product_text);

        UploadProductImageIconView = findViewById(R.id.upload_product_image_icon);

        ProductIdText = findViewById(R.id.product_id_edit_text);
        try{
            ProductIdText.setText(String.valueOf(generateNewProductId()));
        }
        catch(NullPointerException | IndexOutOfBoundsException e){
            Toast.makeText(getBaseContext(), "Lỗi generate id sản phẩm", Toast.LENGTH_SHORT).show();
        }

        ProductNameText = findViewById(R.id.product_name_edit_text);

        ProductPriceText = findViewById(R.id.product_price_edit_text);

        ProductCategoryText = findViewById(R.id.product_category_edit_text);
        ProductCategoryText.setText(productCategory.getName());

        ProductSpecRecyclerView = findViewById(R.id.list_product_specs);

        ProductSpecTextView = findViewById(R.id.add_product_specs_text);

        ProductDescriptionTextView = findViewById(R.id.add_product_description);

        ProductStatusSwitch = findViewById(R.id.switch_product_status);

        ConfirmChangeTextView = findViewById(R.id.confirm_product_text);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void selectPictureFromWebsite() {
        webView = new WebView(this);
        setContentView(webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView webView, android.webkit.ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                Intent intent = fileChooserParams.createIntent();
                startActivityForResult(intent, PICK_IMAGE_FROM_WEBSITE);
                return true;
            }
        });

        webView.loadUrl(FILE_CHOOSER_URL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_FROM_WEBSITE && resultCode == RESULT_OK) {
            Uri[] result = null;
            if (data != null) {
                result = new Uri[]{data.getData()};
            }
            // Process the selected image URI from the website
            handleWebsiteImageSelection(result);
        }
        finish();
    }

    private void handleWebsiteImageSelection(Uri[] imageUris) {
        // Process the selected image URI from the website
        chosenImagePath = String.valueOf(imageUris[0]);
        Toast.makeText(getBaseContext(), chosenImagePath, Toast.LENGTH_SHORT).show();
    }


    public int generateNewProductId(){
        return DatabaseHelper.getAdminProductListGivenID("").size();
    }
}