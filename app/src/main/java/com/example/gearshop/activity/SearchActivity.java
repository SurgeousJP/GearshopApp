package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.gearshop.R;
import com.example.gearshop.database.GetProductDataFromAzure;
import com.example.gearshop.fragment.SearchNotFoundFragment;
import com.example.gearshop.fragment.SearchResultFragment;
import com.example.gearshop.interfaces.OnFragmentViewCreatedListener;
import com.example.gearshop.model.Product;
import com.example.gearshop.utility.ActivityStartManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchActivity extends AppCompatActivity implements OnFragmentViewCreatedListener {
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
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SearchResultFragment searchResultFragment = new SearchResultFragment();
        searchResultFragment.setOnFragmentViewCreatedListener(this);

        SearchNotFoundFragment searchNotFoundFragment = new SearchNotFoundFragment();
        searchNotFoundFragment.setOnFragmentViewCreatedListener(this);

        fragmentTransaction.commit();

        SearchIconView.setOnClickListener(view -> {
            String searchText = SearchEditText.getText().toString();
            if (searchText.isEmpty()){
                replaceSearchFragmentResult(searchNotFoundFragment, R.id.search_constraint_layout);
            }
            else{
                searchResultFragment.UpdateDataOntoAdapter(searchForProducts(searchText));
                replaceSearchFragmentResult(searchResultFragment, R.id.search_constraint_layout);
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
        String productName = product.getName();
        String productDescription = product.getDescription();
        String productSpec = product.getSpecs();
        return (productName.contains(info) || productDescription.contains(info) || productSpec.contains(info));
    }

    private <T extends Fragment> void replaceSearchFragmentResult(T fragment, int layoutID) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layoutID, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentViewCreated(View fragmentView) {
        // Apply constraints to the fragmentView (root view)
        ConstraintLayout searchConstraintLayout = findViewById(R.id.search_constraint_layout);
        ConstraintLayout searchInputLayout = findViewById(R.id.search_input);

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) fragmentView.getLayoutParams();
        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.topToBottom = searchInputLayout.getId();
        fragmentView.setLayoutParams(layoutParams);
    }
}