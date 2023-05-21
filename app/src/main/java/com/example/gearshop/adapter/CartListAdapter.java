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
import androidx.recyclerview.widget.RecyclerView;

import com.example.gearshop.R;
import com.example.gearshop.model.Product;
import com.example.gearshop.model.ShoppingCartItem;
import com.example.gearshop.utility.StringFormat;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartListViewHolder> {
    private Context context;
    private List <Product> ProductList;

    public CartListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List <Product> productlist){
        this.ProductList=productlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cart,parent,false);
        return new CartListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListViewHolder holder, int position) {
        Product product= ProductList.get(position);
        if (product == null) return;
        holder.productNameText.setText(product.getName());
        holder.priceText.setText(String.valueOf(product.getPrice()));
        if (holder.cartItemImage != null){
                String imageURL = product.getImageURL();
                Picasso.get().load(imageURL).into(holder.cartItemImage);
        }
        if (holder.productNameText != null){
            holder.productNameText.setText(product.getName());
        }
        if (holder.priceText != null){
            holder.priceText.setText(StringFormat.getVietnameseMoneyStringFormatted(product.getPrice()));
        }
//        if (holder.numberOfCartItemText != null){
//                holder.numberOfCartItemText.setText(String.valueOf(s.getQuantity()));
//        }
//        if (holder.decreaseCartItemView != null){
//            holder.decreaseCartItemView.setOnClickListener(view -> {
//                if (holder.numberOfCartItemText != null && s.getQuantity() > 1){
//                    s.setQuantity(s.getQuantity() - 1);
//                    holder.numberOfCartItemText.setText(String.valueOf(s.getQuantity()));
//                }
//            });
//        }
//        if (holder.increaseCartItemView != null){
//            holder.increaseCartItemView.setOnClickListener(view -> {
//                if (holder.numberOfCartItemText != null ){
//                    s.setQuantity(s.getQuantity() + 1);
//                    holder.numberOfCartItemText.setText(String.valueOf(s.getQuantity()));
//                }
//            });
//        }

    }

    @Override
    public int getItemCount() {
        if (ProductList!=null)
            return ProductList.size();
        return 0;
    }

    public class CartListViewHolder extends RecyclerView.ViewHolder{
        private ImageView cartItemImage;
        private TextView productNameText;
        private TextView priceText;
        private TextView numberOfCartItemText;
        private  View decreaseCartItemView;
        private  View increaseCartItemView;
        public CartListViewHolder(@NonNull View itemView){
            super(itemView);
            cartItemImage = itemView.findViewById(R.id.item_image_list_cart);
            productNameText = itemView.findViewById(R.id.label_product_cart);
            priceText = itemView.findViewById(R.id.label_price_cart);
            numberOfCartItemText = itemView.findViewById(R.id.input_value);
            decreaseCartItemView = itemView.findViewById(R.id.decrease_box);
           increaseCartItemView = itemView.findViewById(R.id.increase_box);

        }
    }
}

//    public CartListAdapter(Context context, int resource, List<ShoppingCartItem> shoppingCartItems,
//                           List<Product> products){
//        super(context, resource, shoppingCartItems);
//        this.ListCartItems = shoppingCartItems;
//        this.ProductList = products;
//        this.resource = resource;
//    }
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
//        View v = convertView;
//        if (v == null){
//            LayoutInflater vi;
//            vi = LayoutInflater.from(this.getContext());
//            v = vi.inflate(R.layout.list_item_cart, parent, false);
//        }
//        ShoppingCartItem s = getItem(position);
//        Product p = ProductList.get(position);
//        if (s != null){
//            ImageView cartItemImage = v.findViewById(R.id.item_image_list_cart);
//            TextView productNameText = v.findViewById(R.id.label_product_cart);
//            TextView priceText = v.findViewById(R.id.label_price_cart);
//            TextView numberOfCartItemText = v.findViewById(R.id.input_value);
//            View decreaseCartItemView = v.findViewById(R.id.decrease_box);
//            View increaseCartItemView = v.findViewById(R.id.increase_box);
//            if (cartItemImage != null){
//                String imageURL = p.getImageURL();
//                Picasso.get()
//                        .load(imageURL)
//                        .into(cartItemImage);
//            }
//            if (productNameText != null){
//                productNameText.setText(p.getName());
//            }
//            if (priceText != null){
//                priceText.setText(StringFormat.getVietnameseMoneyStringFormatted(p.getPrice()));
//            }
//            if (numberOfCartItemText != null){
//                numberOfCartItemText.setText(String.valueOf(s.getQuantity()));
//            }
//            if (decreaseCartItemView != null){
//                decreaseCartItemView.setOnClickListener(view -> {
//                    if (numberOfCartItemText != null && s.getQuantity() > 1){
//                        s.setQuantity(s.getQuantity() - 1);
//                        numberOfCartItemText.setText(String.valueOf(s.getQuantity()));
//                    }
//                });
//            }
//            if (increaseCartItemView != null){
//                increaseCartItemView.setOnClickListener(view -> {
//                    if (numberOfCartItemText != null ){
//                        s.setQuantity(s.getQuantity() + 1);
//                        numberOfCartItemText.setText(String.valueOf(s.getQuantity()));
//                    }
//                });
//            }
//        }
//        return v;
