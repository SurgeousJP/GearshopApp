//package com.example.gearshop.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.GridView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.constraintlayout.widget.ConstraintLayout;
//
//import com.example.gearshop.R;
//import com.example.gearshop.adapter.ProductGridAdapter;
//import com.example.gearshop.controller.CategoryRepository;
//import com.example.gearshop.controller.ProductRepository;
//import com.example.gearshop.model.Category;
//import com.example.gearshop.model.Product;
//
//import java.util.List;
//
//public class CategoryDetailActivity extends AppCompatActivity{
//    ProductRepository productRepository;
//    CategoryRepository categoryRepository;
//
//
//    ConstraintLayout backwardButton;
//    private RelativeLayout CartIconLayout;
//    private RelativeLayout MoreInformationLayout;
//    private RelativeLayout EscapeLayout;
//    private RelativeLayout HomeItem;
//    private RelativeLayout CategoryItem;
//    private RelativeLayout SearchItem;
//    private RelativeLayout AccountItem;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        this.setContentView(R.layout.category_layout_detail);
//
//        productRepository = new ProductRepository();
//        categoryRepository = new CategoryRepository();
//
//        // set title
//        Intent comingIntent = getIntent();
//        String categoryId = comingIntent.getStringExtra("categoryId");
//        Category productCategory = categoryRepository.getCategoryById(categoryId);
//        TextView categoryLabelTextView = findViewById(R.id.category_detail_label);
//        categoryLabelTextView.setText(productCategory.getName());
//
//
//        // Set up product GridView
//        List<Product> products = productRepository.getProductsByCategoryId(categoryId);
//
//        GridView productsGridView = (GridView) findViewById(R.id.category_product_gridview);
//        productsGridView.setAdapter(new ProductGridAdapter(this, products));
//
//        backwardButton = findViewById(R.id.wayback_icon_category_detail);
//        backwardButton.setOnClickListener(view -> {
//            finish();
//        });
//
//        CartIconLayout = findViewById(R.id.cart_layout);
//        CartIconLayout.setOnClickListener(view -> {
//
//        });
//
//        MoreInformationLayout = findViewById(R.id.more_info_order_detail);
//        MoreInformationLayout.setOnClickListener(view -> {
//
//        });
//
//        EscapeLayout = findViewById(R.id.escape);
//        EscapeLayout.setOnClickListener(view -> {
//
//        });
//
//        HomeItem = findViewById(R.id.home_item_category_detail);
//        HomeItem.setOnClickListener(view -> {
//
//        });
//
//        SearchItem = findViewById(R.id.search_item_category_detail);
//        SearchItem.setOnClickListener(view -> {
//
//        });
//
//        AccountItem = findViewById(R.id.account_item_category_detail);
//        AccountItem.setOnClickListener(view -> {
//        });
//    }
//}
