package com.example.gearshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gearshop.R;
import com.example.gearshop.model.Category;
import com.example.gearshop.model.Product;
import com.example.gearshop.model.Province;
import com.example.gearshop.repository.ProductRepository;
import com.example.gearshop.utility.MoneyHelper;

import java.util.List;

public class ProvinceListAdapter extends ArrayAdapter<Province> {
    private int resource;
    private List<Province> provinces;
    public ProvinceListAdapter(@NonNull Context context, int resource, @NonNull List<Province> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.provinces = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(this.getContext());
            v = inflater.inflate(this.resource, parent, false);
        }

        Province province = getItem(position);
        if (province != null){
            TextView provinceName = v.findViewById(R.id.province_name);
            TextView provinceShippingPrice = v.findViewById(R.id.province_shipping_price);
            if (provinceName != null)
                provinceName.setText(province.getName());
            if (provinceShippingPrice != null){
                provinceShippingPrice.setText(
                        MoneyHelper.getVietnameseMoneyStringFormatted(province.getShippingCharge()));
            }

        }
        return v;
    }
}
