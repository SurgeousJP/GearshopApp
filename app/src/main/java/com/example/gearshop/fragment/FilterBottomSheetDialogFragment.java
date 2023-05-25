package com.example.gearshop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gearshop.R;
import com.example.gearshop.model.Product;
import com.example.gearshop.utility.MoneyHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FilterBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private View DismissView;
    private TextView UnderPriceTextView;
    private TextView RangePrice1TextView;
    private TextView RangePrice2TextView;
    private TextView OverPriceTextView;
    private EditText LesserThanPriceEditText;
    private EditText BiggerThanPriceEditText;
    private TextView HavingDiscountTextView;
    private TextView ResetFilterTextView;
    private TextView ApplyFilterTextView;
    private List<Product> ProductList;
    private ListProductFragment CategoryListProductFragment;

    public FilterBottomSheetDialogFragment(){}
    public FilterBottomSheetDialogFragment(ListProductFragment categoryListProductFragment, List<Product> products){
        this.ProductList = products;
        this.CategoryListProductFragment = categoryListProductFragment;
    }
    public View getDismissView() {
        return DismissView;
    }

    public TextView getUnderPriceTextView() {
        return UnderPriceTextView;
    }

    public TextView getRangePrice1TextView() {
        return RangePrice1TextView;
    }

    public TextView getRangePrice2TextView() {
        return RangePrice2TextView;
    }

    public TextView getOverPriceTextView() {
        return OverPriceTextView;
    }

    public EditText getLesserThanPriceEditText() {
        return LesserThanPriceEditText;
    }

    public EditText getBiggerThanPriceEditText() {
        return BiggerThanPriceEditText;
    }

    public TextView getHavingDiscountTextView() {
        return HavingDiscountTextView;
    }

    public TextView getResetFilterTextView() {
        return ResetFilterTextView;
    }

    public TextView getApplyFilterTextView() {
        return ApplyFilterTextView;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_setting, container, false);
        DismissView = view.findViewById(R.id.vector);
        DismissView.setOnClickListener(view1 -> {
            dismiss();
        });
        UnderPriceTextView = view.findViewById(R.id.text_price1);
        RangePrice1TextView = view.findViewById(R.id.text_price2);
        RangePrice2TextView = view.findViewById(R.id.text_price3);
        OverPriceTextView = view.findViewById(R.id.text_price4);

        BiggerThanPriceEditText = view.findViewById(R.id.low_price);
        LesserThanPriceEditText = view.findViewById(R.id.high_price);

        HavingDiscountTextView = view.findViewById(R.id.content_pro2);
        ResetFilterTextView = view.findViewById(R.id.cta_reset);
        ApplyFilterTextView = view.findViewById(R.id.cta_apply);

        UnderPriceTextView.setOnClickListener(view1 -> {
            List<Product> result = new ArrayList<>();
            for (int i = 0; i < ProductList.size(); i++){
                if (ProductList.get(i).getPrice() <
                        MoneyHelper.extractVietnameseMoneyFromString(UnderPriceTextView.getText().toString())){
                    result.add(ProductList.get(i));
                }
            }
            CategoryListProductFragment.UpdateDataOntoAdapter(result);
            System.out.println("Successfully Clicked");
        });
        RangePrice1TextView.setOnClickListener(view1 -> {

        });

        RangePrice2TextView.setOnClickListener(view1 -> {

        });

        OverPriceTextView.setOnClickListener(view1 -> {

        });

        HavingDiscountTextView.setOnClickListener(view1 -> {

        });

        ResetFilterTextView.setOnClickListener(view1 -> {

        });


        ApplyFilterTextView.setOnClickListener(view1 -> {

        });
        return view;
    }
}