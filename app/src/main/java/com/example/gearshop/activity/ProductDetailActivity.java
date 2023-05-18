package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gearshop.R;
import com.example.gearshop.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {

    private View returnView;
    private ImageView ProductImageView;
    private TextView ProductNameTextView;
    private TextView ProductSellingPriceTextView;
    private TextView ProductOriginalPriceTextView;
    private TextView ProductDiscountTextView;
    private TextView ProductSpecsTextView;
    private TextView ProductDetailTextView;
    protected void setProductInformationOnView(Product inputtedProduct){
        String imageURL = inputtedProduct.getImageURL();
        Picasso.get()
                .load(imageURL)
                .into(ProductImageView);
        ProductNameTextView.setText(inputtedProduct.getName());

        Locale locale = new Locale("vi", "VN");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
        symbols.setCurrencySymbol("đ");
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormatter = new DecimalFormat("#,##0.00 ¤", symbols);
        String formattedPrice  = decimalFormatter.format(inputtedProduct.getPrice());
        ProductSellingPriceTextView.setText(formattedPrice);
        ProductOriginalPriceTextView.setText(formattedPrice);
        ProductSpecsTextView.setText(inputtedProduct.getSpecs());
        ProductDetailTextView.setText(inputtedProduct.getDescription());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        returnView = findViewById(R.id.wayback_icon_prod);
        ProductImageView = findViewById(R.id.prod_image);
        ProductNameTextView = findViewById(R.id.label_produ);
        ProductSellingPriceTextView = findViewById(R.id.selling_pri);
        ProductOriginalPriceTextView = findViewById(R.id.original_pr);
        ProductDiscountTextView = findViewById(R.id.discount_text);
        ProductSpecsTextView = findViewById(R.id.prod_config_info);
        ProductDetailTextView = findViewById(R.id.prod_detail_info);
        Intent getProductIntent = getIntent();
        Product clickedProduct = (Product) getProductIntent.getSerializableExtra("clickedProduct");
        this.setProductInformationOnView(clickedProduct);
        returnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }
}