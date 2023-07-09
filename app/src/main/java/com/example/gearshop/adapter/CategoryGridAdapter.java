package com.example.gearshop.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gearshop.R;
import com.example.gearshop.model.Category;
import com.example.gearshop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CategoryGridAdapter extends BaseAdapter {
    Context mContext;
    List<Category> mCategories;

    public CategoryGridAdapter(Context context, List<Category> categories) {
        this.mContext = context;
        this.mCategories = categories;
    }

    @Override
    public int getCount() {
        return mCategories.size();
    }

    @Override
    public Category getItem(int position) {
        return mCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mCategories.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            v = inflater.inflate(R.layout.gridview_category, parent, false);
        }

        Category category = getItem(position);

        TextView tvCategoryLabel = v.findViewById(R.id.label_category);
        ImageView ivCategoryThumbnail = v.findViewById(R.id.item_image_list_category);

        if (tvCategoryLabel != null) {
            tvCategoryLabel.setText(category.getName());
        }




        switch(category.getName()) {
            case "Laptop":
                ivCategoryThumbnail.setImageResource(R.mipmap.laptop_icon);
                break;
            case "PC":
                ivCategoryThumbnail.setImageResource(R.mipmap.pc_icon);
                break;
            case "Screen":
                ivCategoryThumbnail.setImageResource(R.mipmap.screen_icon);
                break;
            case "Keyboard":
                ivCategoryThumbnail.setImageResource(R.mipmap.keyboard_icon);
                break;
            case "Mouse":
                ivCategoryThumbnail.setImageResource(R.mipmap.mouse_icon);
                break;
            case "Earphone & Speaker":
                ivCategoryThumbnail.setImageResource(R.mipmap.headphone_icon);
                break;
            case "PC components":
                ivCategoryThumbnail.setImageResource(R.mipmap.pc_component_icon);
                break;
            case "Apple":
                ivCategoryThumbnail.setImageResource(R.mipmap.apple_icon);
                break;
            default:
                ivCategoryThumbnail.setImageResource(R.mipmap.ic_launcher);
                break;
        }

        return v;
    }
}
