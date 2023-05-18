package com.example.gearshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gearshop.R;
import com.example.gearshop.activity.ProductDetailActivity;
import com.example.gearshop.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ProductSpecAdapter extends BaseAdapter {
    private Context context;
    private List<String> keys;
    private List<String> values;

    public ProductSpecAdapter(Context context, Map<String, String> dataMap) {
        this.context = context;
        this.keys = new ArrayList<>(dataMap.keySet());
        this.values = new ArrayList<>(dataMap.values());
    }

    @Override
    public int getCount() {
        return keys.size();
    }

    @Override
    public Object getItem(int position) {
        return keys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.product_spec_item, parent, false);
        }

        String key = keys.get(position);
        String value = values.get(position);

        TextView keyTextView = convertView.findViewById(R.id.product_detail_header_text);
        TextView valueTextView = convertView.findViewById(R.id.product_detail_description_text);

        keyTextView.setText(key);
        valueTextView.setText(value);

        return convertView;
    }
}
