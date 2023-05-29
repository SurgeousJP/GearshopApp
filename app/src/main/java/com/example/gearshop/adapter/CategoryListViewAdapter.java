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
        Product productToGetImage = productRepository.getProductsByCategoryId(Integer.toString(category.getID())).get(0);

        if (category != null){
            TextView tvCategoryLabel  = v.findViewById(R.id.label_category);
            ImageView tvCategoryImage = v.findViewById(R.id.item_image_listview_categoty);

            if (tvCategoryLabel != null)
            {
                tvCategoryLabel.setText(category.getName());
            }

            if (tvCategoryImage != null){
                // set image

//                String imageURL = productToGetImage.getImageURL();
//                Picasso.get()
//                        .load(imageURL)
//                        .into(tvCategoryImage);

            }
        }

        return v;
    }
}
