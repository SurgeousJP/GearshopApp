package com.example.gearshop.activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gearshop.R;
import com.example.gearshop.database.GetProductDataFromAzure;
import com.example.gearshop.fragment.FilterBottomSheetDialogFragment;
import com.example.gearshop.fragment.FilterSortBarFragment;
import com.example.gearshop.fragment.ListProductFragment;
import com.example.gearshop.fragment.SortBottomSheetDialogFragment;
import com.example.gearshop.model.Product;
import com.example.gearshop.utility.ActivityStartManager;
import com.example.gearshop.utility.MoneyHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ProductsInCategoryActivity extends AppCompatActivity {
    private List<Product> ProductList;
    private int ProductCategoryID;
    private FilterSortBarFragment categoryProductFilterSortBarFragment;
    private ListProductFragment categoryListProductFragment;

    private RelativeLayout CartIconLayout;
    private RelativeLayout MoreInformationLayout;
    private RelativeLayout EscapeLayout;
    private RelativeLayout HomeItem;
    private RelativeLayout CategoryItem;
    private RelativeLayout SearchItem;
    private RelativeLayout AccountItem;

    private View FilterIconView;
    private TextView FilterTextView;
    private View SortIconView;
    private TextView SortTextView;
    private View ReturnView;
    private TextView CategoryLabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_layout_detail);
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userId = prefs.getString("customerId", null);

        Intent comingIntent = getIntent();
        ProductCategoryID = comingIntent.getIntExtra("categoryId", 0);
        String categoryName = comingIntent.getStringExtra("categoryName");
        CategoryLabel = findViewById(R.id.category_detail_label);
        CategoryLabel.setText(categoryName);
        initializeProductsInCategory();

        FragmentManager fragmentManager = getSupportFragmentManager();
        categoryListProductFragment =
                (ListProductFragment) fragmentManager.findFragmentById(R.id.fragment_grid_view);
        if (categoryListProductFragment != null)
            categoryListProductFragment.UpdateDataOntoAdapter(ProductList);
        categoryProductFilterSortBarFragment =
                (FilterSortBarFragment)fragmentManager.findFragmentById(R.id.fragment_filter_box);

        ReturnView = findViewById(R.id.return_to_category);
        ReturnView.setOnClickListener(view -> {
            finish();
        });

        CartIconLayout = findViewById(R.id.cart_layout);
        CartIconLayout.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), CartActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getBaseContext().startActivity(intent);
        });

        MoreInformationLayout = findViewById(R.id.more_info_order_detail);
        MoreInformationLayout.setOnClickListener(view -> {

        });

        EscapeLayout = findViewById(R.id.escape);
        EscapeLayout.setOnClickListener(view -> {

        });

        HomeItem = findViewById(R.id.home_item_category_detail);
        HomeItem.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), ProductsInCategoryActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getBaseContext().startActivity(intent);
            finish();
        });
        CategoryItem = findViewById(R.id.category_item_category_detail);
        CategoryItem.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), CategoryActivity.class);
        });
        SearchItem = findViewById(R.id.search_item_category_detail);
        SearchItem.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), SearchActivity.class);
        });

        AccountItem = findViewById(R.id.account_item_category_detail);
        AccountItem.setOnClickListener(view -> {
        });

        final FilterBottomSheetDialogFragment[] filterBottomSheetDialogFragment =
                new FilterBottomSheetDialogFragment[1];
        final SortBottomSheetDialogFragment[] sortBottomSheetDialogFragment =
                new SortBottomSheetDialogFragment[1];
        View.OnClickListener filterOnClickListener = view -> {
            filterBottomSheetDialogFragment[0] =
                    new FilterBottomSheetDialogFragment(categoryListProductFragment, ProductList);
            filterBottomSheetDialogFragment[0].show(getSupportFragmentManager(),
                    filterBottomSheetDialogFragment[0].getTag());

        };

        View.OnClickListener sortOnClickListener = view -> {
            sortBottomSheetDialogFragment[0] =
                    new SortBottomSheetDialogFragment(categoryListProductFragment, ProductList);
            sortBottomSheetDialogFragment[0].show(getSupportFragmentManager(),
                    sortBottomSheetDialogFragment[0].getTag());
        };

        FilterIconView = categoryProductFilterSortBarFragment.getFilterIconView();
        FilterIconView.setOnClickListener(filterOnClickListener);
        FilterTextView = categoryProductFilterSortBarFragment.getFilterTextView();
        FilterTextView.setOnClickListener(filterOnClickListener);
        SortIconView = categoryProductFilterSortBarFragment.getSortIconView();
        SortIconView.setOnClickListener(sortOnClickListener);
        SortTextView = categoryProductFilterSortBarFragment.getSortTextView();
        SortTextView.setOnClickListener(sortOnClickListener);
    }
    private void initializeProductsInCategory() {
        final GetProductDataFromAzure[] getProductDataFromAzure = new GetProductDataFromAzure[1];
        getProductDataFromAzure[0] = new GetProductDataFromAzure();
        getProductDataFromAzure[0].setCategoryID(ProductCategoryID);
        getProductDataFromAzure[0].execute(
                "SELECT product.*,\n" +
                        "\t   discount.id AS discount_id, discount.name AS discount_name, \n" +
                        "\t   discount_percentage, start_date_utc, end_date_utc\n" +
                        "FROM product\n" +
                        "JOIN product_category ON product.category_id = product_category.id\n" +
                        "JOIN discount_applied_category ON product_category.id = discount_applied_category.category_id\n" +
                        "JOIN discount ON discount.id = discount_applied_category.discount_id\n" +
                        "WHERE product.category_id = ?"
        );

        System.out.println("Async Task running");
        try {
            getProductDataFromAzure[0].get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Async Task ended");

        if (getProductDataFromAzure[0].getProductList() != null)
            ProductList = getProductDataFromAzure[0].getProductList();
    }
}