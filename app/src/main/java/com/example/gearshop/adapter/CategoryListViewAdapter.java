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
import com.example.gearshop.repository.ProductRepository;
import com.example.gearshop.model.Category;
import com.example.gearshop.model.Product;

public class CategoryListViewAdapter extends ArrayAdapter<Category> {
    int resource;
    private ProductRepository productRepository;

    public CategoryListViewAdapter(@NonNull Context context, int resource, @NonNull Category[] categories) {
        super(context, resource, categories);

        this.resource = resource;

        productRepository = new ProductRepository();
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
            TextView tvCategoryLabel  = v.findViewById(R.id.label_category);
            ImageView ivCategoryThumbnail = v.findViewById(R.id.item_image_listview_categoty);

            if (tvCategoryLabel != null)
            {
                tvCategoryLabel.setText(category.getName());
            }

            if (ivCategoryThumbnail != null){
                // set image
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

            }
        }

        return v;
    }
}
