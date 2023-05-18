package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
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
    protected String ConvertToHTMLText(String s){
        String[] bulletParts = s.split("\\|\\n");
        String result = "<ul>\n";
        for (int i = 0; i < bulletParts.length; i++){
            System.out.println(bulletParts[i]);
            bulletParts[i] = "<li>" + bulletParts[i] + "<\\li>\n";
            result = result + bulletParts[i];
        }
        result = result + "<\\ul>";
        return result;
    }
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
        ProductDetailTextView.setText(
                Html.fromHtml(this.ConvertToHTMLText(inputtedProduct.getDescription()), Html.FROM_HTML_MODE_COMPACT));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        returnView = findViewById(R.id.wayback_icon_product);
        ProductImageView = findViewById(R.id.product_image);
        ProductNameTextView = findViewById(R.id.label_product_text);
        ProductSellingPriceTextView = findViewById(R.id.selling_price_text);
        ProductOriginalPriceTextView = findViewById(R.id.original_price);
        ProductDiscountTextView = findViewById(R.id.discount_text);
        ProductSpecsTextView = findViewById(R.id.product_config_info);
        ProductDetailTextView = findViewById(R.id.product_detail_info);
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