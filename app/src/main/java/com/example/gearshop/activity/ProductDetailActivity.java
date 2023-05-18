package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gearshop.R;
import com.example.gearshop.adapter.ProductSpecAdapter;
import com.example.gearshop.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ProductDetailActivity extends AppCompatActivity {

    private View returnView;
    private ImageView ProductImageView;
    private TextView ProductNameTextView;
    private TextView ProductSellingPriceTextView;
    private TextView ProductOriginalPriceTextView;
    private TextView ProductDiscountTextView;
    private GridView ProductSpecsGridView;
    private TextView ProductDetailTextView;
    protected Map<String, String> ConvertProductSpecsToMap(String productSpec){
        String[] parts = productSpec.split("\\|\\n");
        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < parts.length; i++){
            System.out.println(parts[i]);
            String[] headerAndDetail = parts[i].split(" {4}");
            try{
                result.put(headerAndDetail[0], headerAndDetail[1]);
                System.out.println(headerAndDetail[0] + "\t" + headerAndDetail[1]);
            }
            catch(NullPointerException | IndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }
        return result;
    }
    protected String ConvertToHTMLBulletText(String s){
        if (s == null) return "";
        String[] bulletParts = s.split("\\|\\n");
        String result = "<ul>\n";
        for (int i = 0; i < bulletParts.length; i++){
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
        Map<String, String> specMap = ConvertProductSpecsToMap(inputtedProduct.getSpecs());
        ProductSpecAdapter productSpecAdapter = new ProductSpecAdapter(this, specMap);
        ProductSpecsGridView.setAdapter(productSpecAdapter);
        ProductDetailTextView.setText(
                Html.fromHtml(this.ConvertToHTMLBulletText(inputtedProduct.getDescription()), Html.FROM_HTML_MODE_COMPACT));
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
        ProductSpecsGridView = findViewById(R.id.product_config_info);
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