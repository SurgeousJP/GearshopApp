package com.example.gearshop.fragment;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gearshop.R;
import com.example.gearshop.activity.ProvincePickActivity;
import com.example.gearshop.database.GetProvinceDataFromAzure;
import com.example.gearshop.model.Address;
import com.example.gearshop.model.Province;
import com.example.gearshop.repository.GlobalRepository;
import com.example.gearshop.utility.DatabaseHelper;
import com.example.gearshop.utility.MoneyHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShippingInfoBottomSheetDialogFragment extends BottomSheetDialogFragment
        implements ConfirmChangeShippingInfoDialogFragment.DialogListener {
    private int ProvinceID;
    private View CancelEditShipping;
    private ConstraintLayout ShippingInfoLayout;
    private EditText ProvinceEditText;
    private EditText HouseNumberAddressEditText;
    private EditText PhoneNumberEditText;
    private TextView ApplyChangeShippingInfo;
    private TextView ProvincePickerApply;
    private TextView TransportFeeTextView;
    private ActivityResultLauncher<Intent> ProvincePickerLauncher;
    public ShippingInfoBottomSheetDialogFragment(){}
    public ShippingInfoBottomSheetDialogFragment(ConstraintLayout shippingInfoLayout){
        this.ShippingInfoLayout = shippingInfoLayout;
    }

    public ShippingInfoBottomSheetDialogFragment(ConstraintLayout shippingInfoLayout, TextView transportFeeTextView){
        this.ShippingInfoLayout = shippingInfoLayout;
        this.TransportFeeTextView = transportFeeTextView;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_shipping_info, container, false);

        CancelEditShipping = view.findViewById(R.id.cancel_edit_shipping);
        CancelEditShipping.setOnClickListener(view1 -> {
            dismiss();
        });
        ProvinceEditText = view.findViewById(R.id.province_edittext);
        ProvinceEditText.setEnabled(false);

        ProvincePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK){
                        Intent data = result.getData();
                        if (data != null) {
                            Province pickedProvince = (Province) data.getSerializableExtra("pickedProvince");
                            String pickedProvinceName = pickedProvince.getName();
                            ProvinceEditText.setText(pickedProvinceName);
                            ProvinceID = pickedProvince.getID();
                        }
                    }
                });
        ProvincePickerApply = view.findViewById(R.id.province_apply);
        ProvincePickerApply.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ProvincePickActivity.class);
            ProvincePickerLauncher.launch(intent);
        });
        HouseNumberAddressEditText = view.findViewById(R.id.homeadress_edittext);
        PhoneNumberEditText = view.findViewById(R.id.phonemuber_edittext);
        PhoneNumberEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        ApplyChangeShippingInfo = view.findViewById(R.id.cta_apply);
        ApplyChangeShippingInfo.setOnClickListener(view1 -> {
            if (ProvinceEditText.getText().toString().equals("")){
                Toast.makeText(getActivity(), "Chưa chọn tỉnh, vui lòng chọn tỉnh", Toast.LENGTH_SHORT).show();
                return;
            }

            String houseNumberChecking = HouseNumberAddressEditText.getText().toString();
            String pattern = "^[A-Z\\d/]+$";

            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(houseNumberChecking);
            if (!matcher.matches()){
                Toast.makeText(getActivity(), "Số nhà không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            String phoneNumberChecking = PhoneNumberEditText.getText().toString();
            if (phoneNumberChecking.charAt(0) != '0' || phoneNumberChecking.length() != 10){
                Toast.makeText(getActivity(), "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            showConfirmChangeDialog();
        });
        return view;
    }
    @SuppressLint("SetTextI18n")
    private void updateShippingInfo(String houseNumber, String province, String phoneNumber){
        TextView AddressTextView = ShippingInfoLayout.findViewById(R.id.label_ship_order_detail);
        TextView PhoneNumberTextView = ShippingInfoLayout.findViewById(R.id.description);

        if (AddressTextView == null){
            AddressTextView = ShippingInfoLayout.findViewById(R.id.label_address);
        }
        AddressTextView.setText(houseNumber + "\n" + province);

        if (PhoneNumberTextView == null){
            PhoneNumberTextView = ShippingInfoLayout.findViewById(R.id.description_shipping_product);
        }
        PhoneNumberTextView.setText(phoneNumber);

        Address globalAddress = GlobalRepository.getCustomerAddress();
        Address newAddress = new Address(globalAddress.getID(), houseNumber,
                "", ProvinceID);
        if (globalAddress.getID() == -1){
            newAddress.setID(DatabaseHelper.getAddressList().size() + 1);
            DatabaseHelper.insertAddressToAzure(newAddress);
        }
        else{
            DatabaseHelper.updateAddressToAzure(globalAddress, newAddress);
        }
        GlobalRepository.setCustomerAddress(newAddress);

        GlobalRepository.getCurrentCustomer().setPhoneNumber(phoneNumber);
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
                    HouseNumberAddressEditText.getText().toString(),
                    ProvinceEditText.getText().toString(),
                    PhoneNumberEditText.getText().toString()
            );
            if (TransportFeeTextView != null){
                double newShippingPrice = GetProvinceDataFromAzure.getShippingCharge(ProvinceID);
                TransportFeeTextView.setText(MoneyHelper.getVietnameseMoneyStringFormatted(newShippingPrice));
            }
            dismiss();
        }
    }
}
