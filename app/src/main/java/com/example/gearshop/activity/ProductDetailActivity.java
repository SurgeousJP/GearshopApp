package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gearshop.R;
import com.example.gearshop.adapter.ProductSpecAdapter;
import com.example.gearshop.fragment.ShippingInfoBottomSheetDialogFragment;
import com.example.gearshop.model.Address;
import com.example.gearshop.repository.GlobalRepository;
import com.example.gearshop.model.Discount;
import com.example.gearshop.model.Product;
import com.example.gearshop.model.ShoppingCartItem;
import com.example.gearshop.utility.ActivityStartManager;
import com.example.gearshop.utility.MoneyHelper;
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
    private RecyclerView ProductSpecsGridView;
    private TextView ProductDetailTextView;
    private RelativeLayout CartIconLayout;
    private RelativeLayout OptionsLayout;
    private RelativeLayout ReturnHomeLayout;

    private ConstraintLayout ShippingInfoLayout;
    private View EditShippingInfoView;

    private TextView AddToCart;
    private TextView BuyProductNow;
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
    protected String ConvertToHTMLBulletText(String s){
        if (s == null) return "";
        String[] bulletParts = s.split("[|\\r\\n]");
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
        ProductOriginalPriceTextView.setText(MoneyHelper.getVietnameseMoneyStringFormatted(originalPrice));
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
        ProductSellingPriceTextView.setText(MoneyHelper.getVietnameseMoneyStringFormatted(sellingPrice));
        Map<String, String> specMap = ConvertProductSpecsToMap(inputtedProduct.getSpecs());
        ProductSpecAdapter productSpecAdapter = new ProductSpecAdapter(this, specMap);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1,RecyclerView.VERTICAL, false);
        ProductSpecsGridView.setLayoutManager(layoutManager);
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
        ShippingInfoLayout = findViewById(R.id.title_1);
        EditShippingInfoView = findViewById(R.id.next_shipping_info);

        Intent getProductIntent = getIntent();
        Product clickedProduct = (Product) getProductIntent.getSerializableExtra("clickedProduct");
        this.setProductInformationOnView(clickedProduct);

        CartIconLayout = findViewById(R.id.cart_icon_product);
        CartIconLayout.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), CartActivity.class);
        });

        OptionsLayout = findViewById(R.id.dots_icon_product);
        OptionsLayout.setOnClickListener(this::showPopupMenu);

        ReturnHomeLayout = findViewById(R.id.escape);
        ReturnHomeLayout.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), HomeActivity.class);
        });

        AddToCart = findViewById(R.id.add_to_cart_text);
        AddToCart.setOnClickListener(view -> {
            addNewProductToCart(clickedProduct);
        });

        BuyProductNow = findViewById(R.id.buy_now_text);
        BuyProductNow.setOnClickListener(view -> {
            addNewProductToCart(clickedProduct);
            ActivityStartManager.startTargetActivity(getBaseContext(), CartActivity.class);
        });


        returnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

        Address globalAddress = GlobalRepository.getCustomerAddress();
        updateShippingInfo(globalAddress.getHouseNumber(), globalAddress.getStreet(),
                GlobalRepository.getCurrentCustomer().getPhoneNumber());

        EditShippingInfoView.setOnClickListener(view -> {
            ShippingInfoBottomSheetDialogFragment dialogFragment = new ShippingInfoBottomSheetDialogFragment(ShippingInfoLayout);
            dialogFragment.show(getSupportFragmentManager(), dialogFragment.getTag());
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateShippingInfo(String houseNumber, String street, String phoneNumber){
        TextView AddressTextView = ShippingInfoLayout.findViewById(R.id.label_address);
        TextView PhoneNumberTextView = ShippingInfoLayout.findViewById(R.id.description_shipping_product);
        AddressTextView.setText(houseNumber + "\n" + street);
        PhoneNumberTextView.setText(phoneNumber);
    }

    private void addNewProductToCart(Product product) {
        List<ShoppingCartItem> currentShoppingCartList = ((GlobalRepository) getApplication()).getCartItemList();
        List<Product> currentProductList = ((GlobalRepository)getApplication()).getProductList();
        ShoppingCartItem newItem = new ShoppingCartItem(currentShoppingCartList.size() + 1,
                1, product.getID(), 1, new Date());

        if (!currentShoppingCartList.contains(newItem)){
            Toast.makeText(this, "Thêm sản phẩm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
            currentShoppingCartList.add(newItem);
        }
        else{
            Toast.makeText(this, "Đã tồn tại sản phẩm trong giỏ!", Toast.LENGTH_SHORT).show();
        }

        if (!currentProductList.contains(product))
            currentProductList.add(product);
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.dots_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.logout_item) {
                    Intent intent = new Intent(ProductDetailActivity.this, SignInActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.faq_item) {
                    Intent intent = new Intent(ProductDetailActivity.this, FAQActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }
}