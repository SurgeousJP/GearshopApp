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
import com.example.gearshop.model.Discount;
import com.example.gearshop.model.Product;

import java.util.List;

import com.example.gearshop.utility.MoneyFormat;
import com.squareup.picasso.Picasso;

public class ProductGridAdapter extends BaseAdapter {
    private Context context;
    private List<Product> products;

    public ProductGridAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }
    public void setData(List<Product> products){
        this.products = products;
    }
    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        // Get UI components in xml file
        ImageView productImageView = convertView.findViewById(R.id.item_image_list_item);
        TextView productSellingPriceTextView = convertView.findViewById(R.id.selling_price);
        TextView productNameTextView = convertView.findViewById(R.id.label_product);
        TextView productDiscountTextView = convertView.findViewById(R.id.text);
        // Set data to UI components
        Product product = products.get(position);

        productNameTextView.setText(product.getName());

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
        productSellingPriceTextView.setText(MoneyFormat.getVietnameseMoneyStringFormatted(sellingPrice));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("clickedProduct", product);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}

