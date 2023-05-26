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
        return view;
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
