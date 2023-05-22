package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gearshop.R;
import com.example.gearshop.adapter.ProductSpecAdapter;
import com.example.gearshop.model.Cart;
import com.example.gearshop.model.Discount;
import com.example.gearshop.model.Product;
import com.example.gearshop.model.ShoppingCartItem;
import com.example.gearshop.utility.MoneyFormat;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    private RelativeLayout CartIconLayout;
    private RelativeLayout MoreInformationLayout;
    private RelativeLayout EscapeLayout;

    private TextView AddToCart;
    private TextView BuyProductNow;
    protected Map<String, String> ConvertProductSpecsToMap(String productSpec){
        String[] parts = productSpec.split("\\|\\n");
        Map<String, String> result = new HashMap<>();
        for (String part : parts) {
            System.out.println(part);
            String[] headerAndDetail = part.split(" {4}");
            try {
                result.put(headerAndDetail[0], headerAndDetail[1]);
                System.out.println(headerAndDetail[0] + "\t" + headerAndDetail[1]);
            } catch (NullPointerException | IndexOutOfBoundsException e) {
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

        double originalPrice = inputtedProduct.getPrice();
        ProductOriginalPriceTextView.setText(MoneyFormat.getVietnameseMoneyStringFormatted(originalPrice));
        ProductOriginalPriceTextView.setPaintFlags(
                ProductOriginalPriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        Discount productDiscount = inputtedProduct.getDiscountInformation();
        double sellingPrice = inputtedProduct.getPrice();
        if (productDiscount.isActive()){
            double discountPercentage = productDiscount.getDiscountPercentage();
            sellingPrice = originalPrice * (100 - discountPercentage) / 100;
            ProductDiscountTextView.setText(String.format("-%s%%", discountPercentage));
        }
        else{
            ProductDiscountTextView.setText("Không giảm giá");
        }
        ProductSellingPriceTextView.setText(MoneyFormat.getVietnameseMoneyStringFormatted(sellingPrice));
        Map<String, String> specMap = ConvertProductSpecsToMap(inputtedProduct.getSpecs());
        ProductSpecAdapter productSpecAdapter = new ProductSpecAdapter(this, specMap);
        ProductSpecsGridView.setAdapter(productSpecAdapter);

        ProductDetailTextView.setText(
                Html.fromHtml(this.ConvertToHTMLBulletText(inputtedProduct.getDescription()),
                        Html.FROM_HTML_MODE_COMPACT));
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

        CartIconLayout = findViewById(R.id.cart_icon_product);
        CartIconLayout.setOnClickListener(view -> {
            startTargetActivity(getBaseContext(), CartActivity.class);
        });

        MoreInformationLayout = findViewById(R.id.dots_icon_product);
        MoreInformationLayout.setOnClickListener(view -> {

        });

        EscapeLayout = findViewById(R.id.escape);
        EscapeLayout.setOnClickListener(view -> {

        });

        AddToCart = findViewById(R.id.add_to_cart_text);
        AddToCart.setOnClickListener(view -> {
            addNewProductToCart(clickedProduct);
        });

        BuyProductNow = findViewById(R.id.buy_now_text);
        BuyProductNow.setOnClickListener(view -> {
            addNewProductToCart(clickedProduct);
            startTargetActivity(getBaseContext(), CartActivity.class);
        });


        returnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    public void startTargetActivity(Context context, Class<?> targetActivityClass) {
        Intent intent = new Intent(context, targetActivityClass)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    private void addNewProductToCart(Product product) {
        List<ShoppingCartItem> currentShoppingCartList = ((Cart) getApplication()).getCartItemList();
        List<Product> currentProductList = ((Cart)getApplication()).getProductList();
        ShoppingCartItem newItem = new ShoppingCartItem(currentShoppingCartList.size() + 1,
                1, product.getID(), 1, new Date());

        if (!currentShoppingCartList.contains(newItem))
            currentShoppingCartList.add(newItem);
        if (!currentProductList.contains(product))
            currentProductList.add(product);
    }
}