package com.example.gearshop.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.gearshop.R;
import com.example.gearshop.adapter.ProductGridAdapter;
import com.example.gearshop.database.GetProductDataFromAzure;
import com.example.gearshop.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class ListProduct extends Fragment {
    private Context context;
    private GridView ProductGridView;
    private List<Product> ProductList = new ArrayList<Product>();
    private ProductGridAdapter ProductAdapter;
    public GridView getProductGridView(){
        return ProductGridView;
    }
    public ProductGridAdapter getProductAdapter(){
        return ProductAdapter;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public ListProduct() {
        // Required empty public constructor
    }

    public static ListProduct newInstance(String param1, String param2) {
        ListProduct fragment = new ListProduct();
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

        final GetProductDataFromAzure[] getProductDataFromAzure = new GetProductDataFromAzure[1];
        getProductDataFromAzure[0] = new GetProductDataFromAzure();
        getProductDataFromAzure[0].execute("SELECT * FROM product WHERE category_id = 1");
        System.out.println("Async Task running");

        try {
            getProductDataFromAzure[0].get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Async Task ended");
        if (getProductDataFromAzure[0].getProductList() != null) this.GetProductDataFromAzure(getProductDataFromAzure[0].getProductList());

        return view;
    }
    public void GetProductDataFromAzure(List<Product> products){
        synchronized (ProductAdapter) {
            ProductAdapter.setData(products);
            ProductAdapter.notify();
        }
    }
}
