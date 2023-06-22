package com.example.gearshop.activity.admin_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gearshop.R;
import com.example.gearshop.adapter.ProductGridAdapter;
import com.example.gearshop.dialog.ProductFilterBottomSheetDialog;
import com.example.gearshop.dialog.ProductSortBottomSheetDialog;
import com.example.gearshop.dialog.SearchNotFoundDialog;
import com.example.gearshop.model.Category;
import com.example.gearshop.model.Product;
import com.example.gearshop.utility.ActivityStartManager;
import com.example.gearshop.utility.DatabaseHelper;
import com.example.gearshop.utility.VietnameseStringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdminProductsInCategoryManagement extends AppCompatActivity implements SearchNotFoundDialog.DialogListener{

    private View ReturnView;
    private TextView CategoryNameTextView;
    private View AddProductView;
    private EditText SearchEditText;
    private View SearchIconView;
    private View FilterIconView;
    private TextView FilterTextView;
    private View SortIconView;
    private TextView SortTextView;
    private GridView ProductGridView;
    private View CustomerManagementView;
    private View OrderManagementView;
    private View AccountManagementView;
    private ProductGridAdapter ProductAdapter;
    private List<Product> ProductList;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_product_category_management);

        Intent getCategoryDataIntent = getIntent();
        Category clickedCategory = (Category) getCategoryDataIntent.getSerializableExtra("clickedCategory");
        if (clickedCategory == null){
            Toast.makeText(getBaseContext(), "Lỗi danh mục", Toast.LENGTH_SHORT).show();
            return;
        }

        ReturnView = findViewById(R.id.return_admin_product_management);
        ReturnView.setOnClickListener(view -> {
            finish();
        });

        CategoryNameTextView = findViewById(R.id.admin_product_in_category_management);
        CategoryNameTextView.setText("Quản lý sản phẩm - " + clickedCategory.getName());

        AddProductView = findViewById(R.id.add_new_product_to_database);
        AddProductView.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), AdminAddNewProductActivity.class);
        });

        ProductList = DatabaseHelper.getProductListFromCategory(clickedCategory.getID(), "ALL");
        ProductAdapter = new ProductGridAdapter(getBaseContext(), ProductList);
        ProductGridView = findViewById(R.id.gridview_product_management);
        ProductGridView.setAdapter(ProductAdapter);

        SearchEditText = findViewById(R.id.search_text_admin_product);
        SearchIconView = findViewById(R.id.search_icon_admin_product);
        SearchIconView.setOnClickListener(view -> {
            String searchText = SearchEditText.getText().toString();
            List<Product> searchResults = searchForProducts(searchText);
            if (searchText.isEmpty() || searchResults.size() == 0){
                ProductAdapter.setData(new ArrayList<>());
                ProductAdapter.notifyDataSetChanged();
                showSearchNotFoundDialog();
            }
            else{
                ProductAdapter.setData(searchResults);
                ProductAdapter.notifyDataSetChanged();
            }
        });

        final ProductFilterBottomSheetDialog[] productFilterBottomSheetDialog =
                new ProductFilterBottomSheetDialog[1];

        View.OnClickListener filterOnClickListener = view -> {
            productFilterBottomSheetDialog[0] =
                    new ProductFilterBottomSheetDialog(ProductAdapter, ProductList);
            productFilterBottomSheetDialog[0].show(getSupportFragmentManager(),
                    productFilterBottomSheetDialog[0].getTag());
        };

        final ProductSortBottomSheetDialog[] productSortBottomSheetDialog =
                new ProductSortBottomSheetDialog[1];
        View.OnClickListener sortOnClickListener = view -> {
            productSortBottomSheetDialog[0] =
                    new ProductSortBottomSheetDialog(ProductAdapter, ProductList);
            productSortBottomSheetDialog[0].show(getSupportFragmentManager(),
                    productSortBottomSheetDialog[0].getTag());
        };

        FilterIconView = findViewById(R.id.filter_icon_admin_product);
        FilterIconView.setOnClickListener(filterOnClickListener);
        FilterTextView = findViewById(R.id.label_filter_admin_product);
        FilterTextView.setOnClickListener(filterOnClickListener);

        SortIconView = findViewById(R.id.sort_icon_admin_product);
        SortIconView.setOnClickListener(sortOnClickListener);
        SortTextView = findViewById(R.id.label_sort_admin_product);
        SortTextView.setOnClickListener(sortOnClickListener);

        CustomerManagementView = findViewById(R.id.customer_manage_category);
        CustomerManagementView.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), AdminCustomerManagementActivity.class);
        });

        OrderManagementView = findViewById(R.id.order_manage_category);
        OrderManagementView.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), AdminOrderManagementActivity.class);
        });

        AccountManagementView = findViewById(R.id.account_management_category);
        AccountManagementView.setOnClickListener(view -> {
        });
    }
    @Override
    public void onDialogResult(boolean result) {

    }

    private void showSearchNotFoundDialog() {
        SearchNotFoundDialog dialogFragment = new SearchNotFoundDialog();
        dialogFragment.setDialogListener(this::onDialogResult);
        dialogFragment.show(getSupportFragmentManager(), "");
    }

    protected List<Product> searchForProducts(String searchText){
        List<Product> result = new ArrayList<>();
        for (int i = 0; i < ProductList.size(); i++){
            if (checkProductContainsInformation(ProductList.get(i), searchText)){
                result.add(ProductList.get(i));
            }
        }
        return result;
    }

    protected boolean checkProductContainsInformation(Product product, String info){
        if (info.isEmpty())
            return false;
        String productPlainString = VietnameseStringConverter.convertToPlainString(
                product.getName().toLowerCase(Locale.ROOT));
        if (VietnameseStringConverter.convertToPlainString(info).equals(info.toLowerCase(Locale.ROOT))){
            return productPlainString.contains(info.toLowerCase(Locale.ROOT));
        }
        return product.getName().toLowerCase(Locale.ROOT).contains(info.toLowerCase(Locale.ROOT));
    }
}