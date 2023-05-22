package com.example.gearshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gearshop.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductSpecAdapter extends RecyclerView.Adapter<ProductSpecAdapter.ViewHolder> {
    private Context context;
    private List<String> keys;
    private List<String> values;

    public ProductSpecAdapter(Context context, Map<String, String> dataMap) {
        this.context = context;
        this.keys = new ArrayList<>(dataMap.keySet());
        this.values = new ArrayList<>(dataMap.values());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_spec_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String key = keys.get(position);
        String value = values.get(position);

        holder.keyTextView.setText(key);
        holder.valueTextView.setText(value);
    }

    @Override
    public int getItemCount() {
        return keys.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView keyTextView;
        TextView valueTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            keyTextView = itemView.findViewById(R.id.product_detail_header_text);
            valueTextView = itemView.findViewById(R.id.product_detail_description_text);
        }
    }
}
