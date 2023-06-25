package com.example.gearshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gearshop.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductSpecEditableAdapter extends RecyclerView.Adapter<ProductSpecEditableAdapter.ViewHolder> {
    private Context context;
    private List<String> keys;
    private List<String> values;
    private Map<String, String> dataMap;

    private List<String> generateValueSet(List<String> keys){
        List<String> resultValueSet = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++){
            resultValueSet.add(dataMap.get(keys.get(i)));
        }
        return resultValueSet;
    }

    public ProductSpecEditableAdapter(Context context, Map<String, String> dataMap) {
        this.context = context;
        this.dataMap = dataMap;
        this.keys = new ArrayList<>(dataMap.keySet());
        this.values = generateValueSet(keys);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_spec_item_editable, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String key = keys.get(position);
        String value = values.get(position);
        holder.keyEditText.setText(key);
        holder.valueEditText.setText(value);
    }

    @Override
    public int getItemCount() {
        return keys.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditText keyEditText;
        EditText valueEditText;

        public ViewHolder(View itemView) {
            super(itemView);
            keyEditText = itemView.findViewById(R.id.product_detail_header_text);
            valueEditText = itemView.findViewById(R.id.product_detail_description_text);
        }
    }
}
