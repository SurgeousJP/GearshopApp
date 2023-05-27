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

public class SortBottomSheetDialogFragment extends BottomSheetDialogFragment
        implements ConfirmSortDialogFragment.DialogListener {
    private List<Product> ProductList;
    private List<Product> SortProductResult;
    private ListProductFragment CategoryListProductFragment;
    private View DismissView;
    private ConstraintLayout LayoutPopular;
    private ConstraintLayout LayoutAtoZ;
    private ConstraintLayout LayoutZtoA;
    private ConstraintLayout LayoutLowToHighPrice;
    private ConstraintLayout LayoutHighToLowPrice;
    private TextView PopularTextView;
    private TextView AtoZTextView;
    private TextView ZtoATextView;
    private TextView LowToHighTextView;
    private TextView HighToLowTextView;
    private ConstraintLayout TickedPopular;
    private ConstraintLayout TickedAToZ;
    private ConstraintLayout TickedZtoA;
    private ConstraintLayout TickedLayoutLowToHighPrice;
    private ConstraintLayout TickedLayoutHighToLowPrice;
    private boolean checkConfirmUpdateUI;
    private ConstraintLayout tempLayout;
    private TextView tempTextView;
    private ConstraintLayout tempTickedLayout;
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
        LayoutPopular = view.findViewById(R.id.list_item_1);
        PopularTextView = view.findViewById(R.id.label_option1);
        TickedPopular = view.findViewById(R.id.components_1);

        View.OnClickListener AtoZOnClickListener = view13 -> {
            ProductList.sort((a, b) -> {
                String productNameA = a.getName();
                String productNameB = b.getName();
                int result = productNameA.compareTo(productNameB);
                return Integer.compare(0, result);
            });
            SortProductResult = ProductList;
            setUIComponentTempsForUpdate(LayoutAtoZ, AtoZTextView, TickedAToZ);
            showConfirmSortDialog();
        };

        LayoutAtoZ = view.findViewById(R.id.list_item_2);
        LayoutAtoZ.setOnClickListener(AtoZOnClickListener);
        AtoZTextView = view.findViewById(R.id.label_option2);
        AtoZTextView.setOnClickListener(AtoZOnClickListener);
        TickedAToZ = view.findViewById(R.id.components_2);
        TickedAToZ.setOnClickListener(AtoZOnClickListener);

        View.OnClickListener ZToAOnClickListener = view13 -> {
            ProductList.sort((b, a) -> {
                String productNameA = a.getName();
                String productNameB = b.getName();
                int result = productNameA.compareTo(productNameB);
                return Integer.compare(0, result);
            });
            SortProductResult = ProductList;
            setUIComponentTempsForUpdate(LayoutZtoA, ZtoATextView, TickedZtoA);
            showConfirmSortDialog();
        };
        LayoutZtoA = view.findViewById(R.id.list_item_3);
        LayoutZtoA.setOnClickListener(ZToAOnClickListener);
        ZtoATextView = view.findViewById(R.id.label_option3);
        ZtoATextView.setOnClickListener(ZToAOnClickListener);
        TickedZtoA = view.findViewById(R.id.components_3);
        TickedZtoA.setOnClickListener(ZToAOnClickListener);

        View.OnClickListener LowToHighOnClickListener = view13 -> {
            ProductList.sort((a, b) -> Double.compare(getDiscountedPrice(a), getDiscountedPrice(b)));
            SortProductResult = ProductList;
            setUIComponentTempsForUpdate(LayoutLowToHighPrice, LowToHighTextView, TickedLayoutLowToHighPrice);
            showConfirmSortDialog();
        };

        LayoutLowToHighPrice = view.findViewById(R.id.list_item_4);
        LayoutLowToHighPrice.setOnClickListener(LowToHighOnClickListener);
        LowToHighTextView = view.findViewById(R.id.label_option4);
        LowToHighTextView.setOnClickListener(LowToHighOnClickListener);
        TickedLayoutLowToHighPrice = view.findViewById(R.id.components_4);
        TickedLayoutLowToHighPrice.setOnClickListener(LowToHighOnClickListener);

        View.OnClickListener HighToLowOnClickListener = view13 -> {
            ProductList.sort((a, b) -> Double.compare(getDiscountedPrice(b), getDiscountedPrice(a)));
            SortProductResult = ProductList;
            setUIComponentTempsForUpdate(LayoutHighToLowPrice, HighToLowTextView, TickedLayoutHighToLowPrice);
            showConfirmSortDialog();
        };

        LayoutHighToLowPrice = view.findViewById(R.id.list_item_5);
        LayoutHighToLowPrice.setOnClickListener(HighToLowOnClickListener);
        HighToLowTextView = view.findViewById(R.id.label_option5);
        HighToLowTextView.setOnClickListener(HighToLowOnClickListener);
        TickedLayoutHighToLowPrice = view.findViewById(R.id.components_5);
        TickedLayoutHighToLowPrice.setOnClickListener(HighToLowOnClickListener);

        return view;
    }
    private void setUIComponentTempsForUpdate(ConstraintLayout layout, TextView textView,
                                              ConstraintLayout tickedLayout){
        tempLayout = layout;
        tempTextView = textView;
        tempTickedLayout = tickedLayout;
    }
    private void setUpdateSortOptionUI(ConstraintLayout layout, TextView textView, ConstraintLayout tickedLayout){
        ResetSortOption();
        layout.setBackgroundResource(R.drawable.option_item_color);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        tickedLayout.setBackgroundResource(R.drawable.check_icon);
    }
    private void ResetSortOption(){
        LayoutPopular.setBackground(null);
        PopularTextView.setTypeface(Typeface.DEFAULT);
        TickedPopular.setBackground(null);

        LayoutAtoZ.setBackground(null);
        AtoZTextView.setTypeface(Typeface.DEFAULT);
        TickedAToZ.setBackground(null);

        LayoutZtoA.setBackground(null);
        ZtoATextView.setTypeface(Typeface.DEFAULT);
        TickedZtoA.setBackground(null);

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
    private void showConfirmSortDialog() {
        ConfirmSortDialogFragment dialogFragment = new ConfirmSortDialogFragment();
        dialogFragment.setDialogListener(this);
        dialogFragment.show(getParentFragmentManager(), "");
    }

    @Override
    public void onDialogResult(boolean result) {
        if (result){
            CategoryListProductFragment.UpdateDataOntoAdapter(SortProductResult);
            setUpdateSortOptionUI(tempLayout, tempTextView, tempTickedLayout);
        }

    }
}
