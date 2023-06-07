package com.example.gearshop.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gearshop.R;
import com.example.gearshop.fragment.FilterBottomSheetDialogFragment;
import com.example.gearshop.fragment.FilterSortBarFragment;
import com.example.gearshop.fragment.ListProductFragment;
import com.example.gearshop.fragment.SortBottomSheetDialogFragment;
import com.example.gearshop.model.Product;
import com.example.gearshop.utility.ActivityStartManager;
import com.example.gearshop.utility.DatabaseHelper;

import java.util.List;

public class ProductsInCategoryActivity extends AppCompatActivity {
    private List<Product> ProductList;
    private int ProductCategoryID;
    private FilterSortBarFragment categoryProductFilterSortBarFragment;
    private ListProductFragment categoryListProductFragment;

    private RelativeLayout CartIconLayout;
    private RelativeLayout OptionsLayout;
    private RelativeLayout ReturnHomeLayout;
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
        ProductList = DatabaseHelper.getProductListFromCategory(ProductCategoryID, "ALL");

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

        OptionsLayout = findViewById(R.id.more_info_order_detail);
        OptionsLayout.setOnClickListener(this::showPopupMenu);

        ReturnHomeLayout = findViewById(R.id.escape);
        ReturnHomeLayout.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), HomeActivity.class);
        });

        HomeItem = findViewById(R.id.home_item_category_detail);
        HomeItem.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), HomeActivity.class);
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
            ActivityStartManager.startTargetActivity(getBaseContext(), AccountActivity.class);
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

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.dots_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.logout_item) {
                    Intent intent = new Intent(ProductsInCategoryActivity.this, SignInActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.faq_item) {
                    Intent intent = new Intent(ProductsInCategoryActivity.this, FAQActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

}