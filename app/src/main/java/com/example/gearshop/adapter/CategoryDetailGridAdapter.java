package com.example.gearshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gearshop.R;
import com.example.gearshop.activity.ProductDetailActivity;
import com.example.gearshop.repository.CategoryRepository;
import com.example.gearshop.model.Discount;
import com.example.gearshop.model.Product;
import com.example.gearshop.utility.MoneyHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryDetailGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<Product> products;
    private CategoryRepository categoryRepository;

    public CategoryDetailGridAdapter(Context context, List<Product> products, CategoryRepository categoryRepository) {
        mContext = context;
        this.products = products;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            v = inflater.inflate(R.layout.list_item_product, parent, false);
        }

        Product product = getItem(position);


        // Get UI components in xml file
        ImageView productImageView = v.findViewById(R.id.item_image_list_item);
        TextView productSellingPriceTextView = v.findViewById(R.id.selling_price);
        TextView productNameTextView = v.findViewById(R.id.label_product);
        TextView productDiscountTextView = v.findViewById(R.id.discount_text);

        if (productNameTextView != null) {
            productNameTextView.setText(product.getName());
        }

        String imageURL = product.getImageURL();
        Picasso.get()
                .load(imageURL)
                .into(productImageView);

        Discount productDiscount = product.getDiscountInformation();
        double sellingPrice = product.getPrice();
        if (productDiscount.isActive()){
            double discountPercentage = productDiscount.getDiscountPercentage();
            sellingPrice = sellingPrice * (100 - discountPercentage) / 100;
            productDiscountTextView.setText(String.format("-%s%%", discountPercentage));
        }
        else{
            productDiscountTextView.setText("Không giảm giá");
        }
        productSellingPriceTextView.setText(MoneyHelper.getVietnameseMoneyStringFormatted(sellingPrice));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("clickedProduct", product);
                mContext.startActivity(intent);
            }
        });
        return v;
    }
}