package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gearshop.R;
import com.example.gearshop.database.GetProductDataFromAzure;
import com.example.gearshop.fragment.FilterBottomSheetDialogFragment;
import com.example.gearshop.fragment.FilterSortBarFragment;
import com.example.gearshop.fragment.ListProductFragment;
import com.example.gearshop.fragment.SearchNotFoundFragment;
import com.example.gearshop.fragment.SortBottomSheetDialogFragment;
import com.example.gearshop.model.Product;
import com.example.gearshop.utility.ActivityStartManager;
import com.example.gearshop.utility.VietnameseStringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class SearchActivity extends AppCompatActivity implements ListProductFragment.DialogListener{
    private List<Product> ProductList;
    private View SearchIconView;
    private EditText SearchEditText;
    private RelativeLayout CartIconLayout;
    private RelativeLayout MoreInformationLayout;
    private RelativeLayout EscapeLayout;
    private RelativeLayout HomeItem;
    private RelativeLayout CategoryItem;
    private RelativeLayout AccountItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        this.setProductListFromAzure();
        SearchEditText = findViewById(R.id.search_text);
        SearchIconView = findViewById(R.id.search_icon);

        FragmentManager fragmentManager = getSupportFragmentManager();
        ListProductFragment listProductFragment =
                (ListProductFragment) fragmentManager.findFragmentById(R.id.fragment_grid_view_search);
        SearchIconView.setOnClickListener(view -> {
            String searchText = SearchEditText.getText().toString();
            List<Product> searchResults = searchForProducts(searchText);
            if (searchText.isEmpty() || searchResults.size() == 0){
                if (listProductFragment != null) {
                    listProductFragment.UpdateDataOntoAdapter(new ArrayList<>());
                }
                showSearchNotFoundDialog();
            }
            else{
                if (listProductFragment != null) {
                    listProductFragment.UpdateDataOntoAdapter(searchResults);
                }
            }
        });


        CartIconLayout = findViewById(R.id.cart_order_detail);
        CartIconLayout.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), CartActivity.class);
        });

        MoreInformationLayout = findViewById(R.id.more_info_order_detail);
        MoreInformationLayout.setOnClickListener(view -> {

        });

        EscapeLayout = findViewById(R.id.escape_order_detail);
        EscapeLayout.setOnClickListener(view -> {

        });

        HomeItem = findViewById(R.id.home_item_category_detail);
        HomeItem.setOnClickListener(view -> {

        });
        CategoryItem = findViewById(R.id.category_item_category_detail);
        CategoryItem.setOnClickListener(view -> {

        });
        AccountItem = findViewById(R.id.account_item_category_detail);
        AccountItem.setOnClickListener(view -> {
        });
    }

    private void setProductListFromAzure(){
        final GetProductDataFromAzure[] getProductDataFromAzure = new GetProductDataFromAzure[1];
        getProductDataFromAzure[0] = new GetProductDataFromAzure();
        getProductDataFromAzure[0].execute(
                "SELECT product.*,\n" +
                        "\t   discount.id AS discount_id, discount.name AS discount_name, \n" +
                        "\t   discount_percentage, start_date_utc, end_date_utc\n" +
                        "FROM product\n" +
                        "JOIN product_category ON product.category_id = product_category.id\n" +
                        "JOIN discount_applied_category ON product_category.id = discount_applied_category.category_id\n" +
                        "JOIN discount ON discount.id = discount_applied_category.discount_id\n"
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
        String productPlainString = VietnameseStringConverter.convertToPlainString(product.getName().toLowerCase(Locale.ROOT));
        if (VietnameseStringConverter.convertToPlainString(info).equals(info)){
            return productPlainString.contains(info);
        }
        return product.getName().toLowerCase(Locale.ROOT).contains(info.toLowerCase(Locale.ROOT));
    }

    private void showSearchNotFoundDialog() {
        SearchNotFoundFragment dialogFragment = new SearchNotFoundFragment();
        dialogFragment.setDialogListener(this::onDialogResult);
        dialogFragment.show(getSupportFragmentManager(), "");
    }

    @Override
    public void onDialogResult(boolean result) {

    }
}