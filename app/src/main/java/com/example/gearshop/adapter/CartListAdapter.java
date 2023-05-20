package com.example.gearshop.adapter;

import android.content.Context;
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
import com.example.gearshop.model.ShoppingCartItem;

import org.w3c.dom.Text;

import java.util.List;

public class CartListAdapter extends ArrayAdapter<ShoppingCartItem> {
    int resource;
    private List<ShoppingCartItem> ListCartItems;
    public CartListAdapter(Context context, int resource, List<ShoppingCartItem> shoppingCartItems){
        super(context, resource, shoppingCartItems);
        this.ListCartItems = shoppingCartItems;
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
        if (s != null){
            ImageView cartItemImage = v.findViewById(R.id.item_image_list_cart);
            TextView productNameText = v.findViewById(R.id.label_product_cart);
            TextView priceText = v.findViewById(R.id.label_price_cart);
            TextView numberOfCartItemText = v.findViewById(R.id.input_value);
            View decreaseCartItemView = v.findViewById(R.id.decrease_box);
            View increaseCartItemView = v.findViewById(R.id.increase_box);
            if (cartItemImage != null){

            }
        }
        return v;
    }
}
