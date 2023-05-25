package com.example.gearshop.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.example.gearshop.R;
import com.example.gearshop.adapter.ProductGridAdapter;
import com.example.gearshop.database.GetProductDataFromAzure;
import com.example.gearshop.interfaces.OnFragmentViewCreatedListener;
import com.example.gearshop.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class ListProductFragment extends Fragment implements OnFragmentViewCreatedListener {
    private Context context;
    private GridView ProductGridView;
    private List<Product> ProductList = new ArrayList<Product>();
    private ProductGridAdapter ProductAdapter;
    private OnFragmentViewCreatedListener callback;
    public List<Product> getProductList() {
        return ProductList;
    }

    public GridView getProductGridView(){
        return ProductGridView;
    }
    public ProductGridAdapter getProductAdapter(){
        return ProductAdapter;
    }

    private int ProductCategoryID;
    public int getProductCategoryID() {
        return ProductCategoryID;
    }

    public void setProductCategoryID(int productCategoryID) {
        ProductCategoryID = productCategoryID;
        this.onCreateView(getLayoutInflater(), (ViewGroup) requireView().getParent(), null);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public ListProductFragment() {
        // Required empty public constructor
    }

    public static ListProductFragment newInstance(String param1, String param2) {
        ListProductFragment fragment = new ListProductFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.framelayout_list_product, container, false);

        ProductGridView = (GridView) view.findViewById(R.id.grid_view_list_product);
        ProductAdapter = new ProductGridAdapter(getContext(), ProductList);
        ProductGridView.setAdapter(ProductAdapter);

        if (ProductList == null || ProductList.size() == 0){
            initializeProductsInCategory();
        }
        return view;
    }

    private void initializeProductsInCategory() {
        final GetProductDataFromAzure[] getProductDataFromAzure = new GetProductDataFromAzure[1];
        getProductDataFromAzure[0] = new GetProductDataFromAzure();
        getProductDataFromAzure[0].setCategoryID(5);
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

        if (getProductDataFromAzure[0].getProductList() != null) this.UpdateDataOntoAdapter(getProductDataFromAzure[0].getProductList());
    }

    public void UpdateDataOntoAdapter(List<Product> products){
        ProductAdapter.setData(products);
        ProductAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFragmentViewCreated(View fragmentView) {

    }
    public void setOnFragmentViewCreatedListener(OnFragmentViewCreatedListener callback) {
        this.callback = callback;
    }
}
