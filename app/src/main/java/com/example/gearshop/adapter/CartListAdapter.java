package com.example.gearshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gearshop.R;
import com.example.gearshop.model.Product;
import com.example.gearshop.model.ShoppingCartItem;
import com.example.gearshop.utility.StringFormat;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class CartListAdapter extends ArrayAdapter<ShoppingCartItem> {
    int resource;
    private List<ShoppingCartItem> ListCartItems;
    private List<Product> ProductList;
    public CartListAdapter(Context context, int resource, List<ShoppingCartItem> shoppingCartItems,
                           List<Product> products){
        super(context, resource, shoppingCartItems);
        this.ListCartItems = shoppingCartItems;
        this.ProductList = products;
        this.resource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View v = convertView;
        if (v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(this.getContext());
            v = vi.inflate(R.layout.list_item_cart, parent, false);
        }
        ShoppingCartItem s = getItem(position);
        Product p = ProductList.get(position);
        if (s != null){
            ImageView cartItemImage = v.findViewById(R.id.item_image_list_cart);
            TextView productNameText = v.findViewById(R.id.label_product_cart);
            TextView priceText = v.findViewById(R.id.label_price_cart);
            TextView numberOfCartItemText = v.findViewById(R.id.input_value);
            View decreaseCartItemView = v.findViewById(R.id.decrease_box);
            View increaseCartItemView = v.findViewById(R.id.increase_box);
            if (cartItemImage != null){
                String imageURL = p.getImageURL();
                Picasso.get()
                        .load(imageURL)
                        .into(cartItemImage);
            }
            if (productNameText != null){
                productNameText.setText(p.getName());
            }
            if (priceText != null){
                double price = p.getPrice();
                if (p.getDiscountInformation().isActive()){
                    price = price * (100 - p.getDiscountInformation().getDiscountPercentage());
                }
                priceText.setText(StringFormat.getVietnameseMoneyStringFormatted(price));
            }
            if (numberOfCartItemText != null){
                numberOfCartItemText.setText(String.valueOf(s.getQuantity()));
            }
            if (decreaseCartItemView != null){
                decreaseCartItemView.setOnClickListener(view -> {
                    if (numberOfCartItemText != null && s.getQuantity() > 1){
                        s.setQuantity(s.getQuantity() - 1);
                        numberOfCartItemText.setText(String.valueOf(s.getQuantity()));
                    }
                });
            }
            if (increaseCartItemView != null){
                increaseCartItemView.setOnClickListener(view -> {
                    if (numberOfCartItemText != null ){
                        s.setQuantity(s.getQuantity() + 1);
                        numberOfCartItemText.setText(String.valueOf(s.getQuantity()));
                    }
                });
            }
        }
        return v;
    }
}
