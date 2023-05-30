package com.example.gearshop.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gearshop.R;
import com.example.gearshop.model.Product;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class ShippingInfoBottomSheetDialogFragment extends BottomSheetDialogFragment
        implements ConfirmChangeShippingInfoDialogFragment.DialogListener {
    private ConstraintLayout ShippingInfoLayout;
    private EditText ProvinceEditText;
    private EditText HouseNumberAddressEditText;
    private EditText PhoneNumberEditText;
    private TextView ApplyChangeShippingInfo;
    public ShippingInfoBottomSheetDialogFragment(){}
    public ShippingInfoBottomSheetDialogFragment(ConstraintLayout shippingInfoLayout){
        this.ShippingInfoLayout = shippingInfoLayout;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_shipping_info, container, false);
        ProvinceEditText = view.findViewById(R.id.province_edittext);
        HouseNumberAddressEditText = view.findViewById(R.id.homeadress_edittext);
        PhoneNumberEditText = view.findViewById(R.id.phonemuber_edittext);
        PhoneNumberEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        ApplyChangeShippingInfo = view.findViewById(R.id.cta_apply);
        ApplyChangeShippingInfo.setOnClickListener(view1 -> {
            if (PhoneNumberEditText.getText().toString().length() != 10){
                Toast.makeText(getActivity(), "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            showConfirmChangeDialog();
        });
        return view;
    }
    private void updateShippingInfo(String address, String phoneNumber){
        TextView AddressTextView = ShippingInfoLayout.findViewById(R.id.label_ship_order_detail);
        TextView PhoneNumberTextView = ShippingInfoLayout.findViewById(R.id.description);
        if (AddressTextView != null){
            AddressTextView.setText(address);
        }
        else{
            AddressTextView = ShippingInfoLayout.findViewById(R.id.label_address);
            if (AddressTextView != null){
                AddressTextView.setText(address);
            }
        }
        if (PhoneNumberTextView != null){
            PhoneNumberTextView.setText(phoneNumber);
        }
    }

    private void showConfirmChangeDialog() {
        ConfirmChangeShippingInfoDialogFragment dialogFragment=
                new ConfirmChangeShippingInfoDialogFragment();
        dialogFragment.setDialogListener(this::onDialogResult);
        dialogFragment.show(getParentFragmentManager(), "");
    }
    @Override
    public void onDialogResult(boolean result) {
        if (result){
            updateShippingInfo(
                    HouseNumberAddressEditText.getText().toString() + "\n" +
                    ProvinceEditText.getText().toString(),
                    PhoneNumberEditText.getText().toString()
            );
        }
    }
}
