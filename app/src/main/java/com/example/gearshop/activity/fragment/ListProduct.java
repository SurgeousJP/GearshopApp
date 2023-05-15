package com.example.gearshop.activity.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.gearshop.R;
import com.example.gearshop.model.Product;

import java.util.ArrayList;
import java.util.List;


public class ListProduct extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GridView ProductGridView;
    private List<Product> DemoProductList;
    private void InitDemoProductList(){
        DemoProductList = new ArrayList<>();
        DemoProductList.add(new Product(1, "demo_product1", "", "", "", 2000000, "Available", 1));
        DemoProductList.add(new Product(2, "demo_product1", "", "", "", 30000000, "Available", 2));
        DemoProductList.add(new Product(3, "demo_product1", "", "", "", 400000, "Available", 3));
        DemoProductList.add(new Product(4, "demo_product1", "", "", "", 20000000, "Available", 4));
    }
    public ListProduct() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListProduct.
     */
    // TODO: Rename and change types and number of parameters
    public static ListProduct newInstance(String param1, String param2) {
        ListProduct fragment = new ListProduct();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.framelayout_list_product, container, false);
        InitDemoProductList();
        ProductGridView = (GridView) view.findViewById(R.id.grid_view_list_product);
        ProductGridAdapter productGridAdapter = new ProductGridAdapter(getContext(), DemoProductList);
        ProductGridView.setAdapter(productGridAdapter);
        return view;
    }

}
