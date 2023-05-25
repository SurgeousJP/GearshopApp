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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userId = prefs.getString("customerId", null);

        FragmentManager fragmentManager = getSupportFragmentManager();
        categoryListProductFragment =
                (ListProductFragment) fragmentManager.findFragmentById(R.id.fragment_grid_view);
        categoryProductFilterSortBarFragment =
                (FilterSortBarFragment)fragmentManager.findFragmentById(R.id.fragment_filter_box);
        categoryListProductFragment.setProductCategoryID(6);

        ProductList = categoryListProductFragment.getProductList();

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

        });
        CategoryItem = findViewById(R.id.category_item_category_detail);
        CategoryItem.setOnClickListener(view -> {

        });
        SearchItem = findViewById(R.id.search_item_category_detail);
        SearchItem.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), SearchActivity.class);
        });

        AccountItem = findViewById(R.id.account_item_category_detail);
        AccountItem.setOnClickListener(view -> {
        });

        TextView titleScreen = (TextView) findViewById(R.id.title_screen);
        titleScreen.setText(userId);

        View.OnClickListener filterOnClickListener = view -> {
            FilterBottomSheetDialogFragment bottomSheetDialogFragment =
                    new FilterBottomSheetDialogFragment(categoryListProductFragment, ProductList);
            bottomSheetDialogFragment.show(getSupportFragmentManager(),
                    bottomSheetDialogFragment.getTag());

        };

        View.OnClickListener sortOnClickListener = view -> {
            SortBottomSheetDialogFragment bottomSheetDialogFragment =
                    new SortBottomSheetDialogFragment();
            bottomSheetDialogFragment.show(getSupportFragmentManager(),
                    bottomSheetDialogFragment.getTag());
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
}