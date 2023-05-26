package com.example.gearshop.fragment;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gearshop.R;
import com.example.gearshop.model.Product;
import com.example.gearshop.utility.MoneyHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FilterBottomSheetDialogFragment extends BottomSheetDialogFragment implements 
ConfirmFilterDialogFragment.DialogListener{
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
    private List<Product> FilterProductResult;
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
        BiggerThanPriceEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        LesserThanPriceEditText = view.findViewById(R.id.high_price);
        LesserThanPriceEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

        HavingDiscountTextView = view.findViewById(R.id.content_pro2);
        ResetFilterTextView = view.findViewById(R.id.cta_reset);
        ApplyFilterTextView = view.findViewById(R.id.cta_apply);

        UnderPriceTextView.setOnClickListener(view1 -> {
            List<Product> result = filter(ProductList, product -> getDiscountedPrice(product)
                    < MoneyHelper.extractVietnameseMoneyFromString(UnderPriceTextView.getText().toString()));
            FilterProductResult = result;
            showConfirmFilterDialog();
        });
        RangePrice1TextView.setOnClickListener(view1 -> {
            String[] pricePair = RangePrice1TextView.getText().toString().split("-");
            double biggerThanPrice = MoneyHelper.extractVietnameseMoneyFromString(pricePair[0]);
            double lesserThanPrice = MoneyHelper.extractVietnameseMoneyFromString(pricePair[1]);

            List<Product> result = filter(ProductList,
                    product -> biggerThanPrice <= getDiscountedPrice(product)
                            && getDiscountedPrice(product) <= lesserThanPrice);
            FilterProductResult = result;
            showConfirmFilterDialog();
        });

        RangePrice2TextView.setOnClickListener(view1 -> {
            String[] pricePair = RangePrice2TextView.getText().toString().split("-");
            double biggerThanPrice = MoneyHelper.extractVietnameseMoneyFromString(pricePair[0]);
            double lesserThanPrice = MoneyHelper.extractVietnameseMoneyFromString(pricePair[1]);

            List<Product> result = filter(ProductList,
                    product -> biggerThanPrice <= getDiscountedPrice(product)
                            && getDiscountedPrice(product) <= lesserThanPrice);
            FilterProductResult = result;
            showConfirmFilterDialog();
        });

        OverPriceTextView.setOnClickListener(view1 -> {
            List<Product> result = filter(ProductList, product -> getDiscountedPrice(product)
                    > MoneyHelper.extractVietnameseMoneyFromString(OverPriceTextView.getText().toString()));
            FilterProductResult = result;
            showConfirmFilterDialog();
        });

        HavingDiscountTextView.setOnClickListener(view1 -> {
            List<Product> result = filter(ProductList, product -> product.getDiscountInformation().isActive());
            FilterProductResult = result;
            showConfirmFilterDialog();
        });

        ResetFilterTextView.setOnClickListener(view1 -> {
            FilterProductResult = ProductList;
            showConfirmFilterDialog();
        });
        ApplyFilterTextView.setOnClickListener(view1 -> {
            String lesserThanPriceText = LesserThanPriceEditText.getText().toString();
            String biggerThanPriceText = BiggerThanPriceEditText.getText().toString();
            if (lesserThanPriceText.isEmpty() || biggerThanPriceText.isEmpty()){
                Toast.makeText(getActivity(), "Chưa điền đủ thông tin khoảng giá", Toast.LENGTH_SHORT).show();
            }
            else{
                double lesserThanPrice = Double.parseDouble(lesserThanPriceText);
                double biggerThanPrice = Double.parseDouble(biggerThanPriceText);
                if (lesserThanPrice < biggerThanPrice){
                    Toast.makeText(getActivity(), "Khoảng giá không hợp lệ", Toast.LENGTH_SHORT).show();
                }
                else{
                    List<Product> result = filter(ProductList, product ->
                            getDiscountedPrice(product) < lesserThanPrice &&
                                    getDiscountedPrice(product) > biggerThanPrice);
                    FilterProductResult = result;
                    showConfirmFilterDialog();
                }
            }
        });
        return view;
    }
    public List<Product> filter(List<Product> products, Predicate<Product> checkFunction) {
        List<Product> result = new ArrayList<>();
        for (int i = 0; i < products.size(); i++){
            if (checkFunction.test(products.get(i))){
                result.add(ProductList.get(i));
            }
        }

        return result;
    }
    private double getDiscountedPrice(Product p) {
        double price = p.getPrice();
        if (p.getDiscountInformation().isActive()){
            price = price * (100 - p.getDiscountInformation().getDiscountPercentage()) / 100;
        }
        return price;
    }

    private void showConfirmFilterDialog() {
        ConfirmFilterDialogFragment dialogFragment = new ConfirmFilterDialogFragment();
        dialogFragment.setDialogListener(this);
        dialogFragment.show(getParentFragmentManager(), "");
    }
    
    @Override
    public void onDialogResult(boolean result) {
        if (result){
            CategoryListProductFragment.UpdateDataOntoAdapter(FilterProductResult);
        }
    }
}