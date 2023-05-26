package com.example.gearshop.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gearshop.R;
import com.example.gearshop.model.Product;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private List<Product> ProductList;
    private ListProductFragment CategoryListProductFragment;
    private View DismissView;

    private ConstraintLayout LayoutPopular;
    private ConstraintLayout LayoutLowToHighPrice;
    private ConstraintLayout LayoutHighToLowPrice;
    private TextView PopularTextView;
    private TextView LowToHighTextView;
    private TextView HighToLowTextView;
    private ConstraintLayout TickedLayoutPopular;
    private ConstraintLayout TickedLayoutLowToHighPrice;
    private ConstraintLayout TickedLayoutHighToLowPrice;

    public SortBottomSheetDialogFragment(){}
    public SortBottomSheetDialogFragment(ListProductFragment categoryListProductFragment, List<Product> products){
        this.ProductList = products;
        this.CategoryListProductFragment = categoryListProductFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sort_setting, container, false);

        DismissView = view.findViewById(R.id.sort_vector);
        DismissView.setOnClickListener(view12 -> {
            dismiss();
        });
        LayoutPopular = view.findViewById(R.id.list_item_sex_1);
        PopularTextView = view.findViewById(R.id.label_option1);
        TickedLayoutPopular = view.findViewById(R.id.components_1);
        TickedLayoutPopular.setOnClickListener(view1 -> {
            ResetSortOption();
            LayoutPopular.setBackgroundResource(R.drawable.option_item_color);
            PopularTextView.setTypeface(Typeface.DEFAULT_BOLD);
            TickedLayoutPopular.setBackgroundResource(R.drawable.check_icon);
        });

        LayoutLowToHighPrice = view.findViewById(R.id.list_item3);
        LowToHighTextView = view.findViewById(R.id.label_option3);
        TickedLayoutLowToHighPrice = view.findViewById(R.id.components_3);

        TickedLayoutLowToHighPrice.setOnClickListener(view1 -> {
            ResetSortOption();
            LayoutLowToHighPrice.setBackgroundResource(R.drawable.option_item_color);
            LowToHighTextView.setTypeface(Typeface.DEFAULT_BOLD);
            TickedLayoutLowToHighPrice.setBackgroundResource(R.drawable.check_icon);
            ProductList.sort((a, b) -> Double.compare(getDiscountedPrice(a), getDiscountedPrice(b)));
            CategoryListProductFragment.UpdateDataOntoAdapter(ProductList);
        });

        LayoutHighToLowPrice = view.findViewById(R.id.list_item_sex_3);
        HighToLowTextView = view.findViewById(R.id.label_option4);
        TickedLayoutHighToLowPrice = view.findViewById(R.id.components_4);
        TickedLayoutHighToLowPrice.setOnClickListener(view1 -> {
            ResetSortOption();
            LayoutHighToLowPrice.setBackgroundResource(R.drawable.option_item_color);
            HighToLowTextView.setTypeface(Typeface.DEFAULT_BOLD);
            TickedLayoutHighToLowPrice.setBackgroundResource(R.drawable.check_icon);
            ProductList.sort((a, b) -> Double.compare(getDiscountedPrice(b), getDiscountedPrice(a)));
            CategoryListProductFragment.UpdateDataOntoAdapter(ProductList);
        });

        return view;
    }
    private void ResetSortOption(){
        LayoutPopular.setBackground(null);
        PopularTextView.setTypeface(Typeface.DEFAULT);
        TickedLayoutPopular.setBackground(null);

        LayoutLowToHighPrice.setBackground(null);
        LowToHighTextView.setTypeface(Typeface.DEFAULT);
        TickedLayoutLowToHighPrice.setBackground(null);

        LayoutHighToLowPrice.setBackground(null);
        HighToLowTextView.setTypeface(Typeface.DEFAULT);
        TickedLayoutHighToLowPrice.setBackground(null);
    }
    private double getDiscountedPrice(Product p) {
        double price = p.getPrice();
        if (p.getDiscountInformation().isActive()){
            price = price * (100 - p.getDiscountInformation().getDiscountPercentage()) / 100;
        }
        return price;
    }

}
