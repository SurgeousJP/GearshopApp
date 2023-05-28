package com.example.gearshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gearshop.R;
import com.example.gearshop.model.Category;

public class CategoryListViewAdapter extends ArrayAdapter<Category> {
    int resource;

    public CategoryListViewAdapter(@NonNull Context context, int resource, @NonNull Category[] categories) {
        super(context, resource, categories);

        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(this.getContext());
            v = inflater.inflate(this.resource, parent, false);
        }

        Category category = getItem(position);

        if (category != null){
            TextView tvCategoryLabel  = (TextView) v.findViewById(R.id.label_category);

            if (tvCategoryLabel != null)
            {
                tvCategoryLabel.setText(category.getName());
            }
        }

        return v;
    }
}
