package com.example.gearshop.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gearshop.R;
import com.example.gearshop.model.Product;
import com.example.gearshop.model.ShoppingCartItem;
import com.example.gearshop.utility.MoneyHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartListViewHolder> {

    private List<ShoppingCartItem> ListCartItems;
    private List<Product> ProductList;
    private TextView TotalProductPrice;
    private TextView FinalPrice;
    private OnDeleteItemClickListener onDeleteItemClickListener;


    public void setTotalProductPrice(TextView totalProductPrice) {
        TotalProductPrice = totalProductPrice;
    }

    public void setFinalPrice(TextView finalPrice) {
        FinalPrice = finalPrice;
    }
    public CartListAdapter(List<ShoppingCartItem> shoppingCartItems,
                           List<Product> products){
        this.ListCartItems = shoppingCartItems;
        this.ProductList = products;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List <Product> productlist){
        this.ProductList=productlist;
        notifyDataSetChanged();
    }

    public interface OnDeleteItemClickListener {
        void onDeleteItemClick(int position);
    }

    public void setOnDeleteItemClickListener(OnDeleteItemClickListener listener) {
        this.onDeleteItemClickListener = listener;
    }

    @NonNull
    @Override
    public CartListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cart,parent,false);
        return new CartListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListViewHolder holder, int position) {
        Product product = ProductList.get(position);
        ShoppingCartItem cartItem = ListCartItems.get(position);

        if (product == null) return;

        setPriceToView(holder.ProductNameText, product.getName(), holder.PriceText, String.valueOf(product.getPrice()));

        if (holder.CartItemImage != null){
                String imageURL = product.getImageURL();
                Picasso.get().load(imageURL).into(holder.CartItemImage);
        }

        if (holder.ProductNameText != null){
            holder.ProductNameText.setText(product.getName());
        }

        if (holder.PriceText != null){
            double discountedPrice = getDiscountedPrice(product);
            holder.PriceText.setText(MoneyHelper.getVietnameseMoneyStringFormatted(discountedPrice));
        }

        if (holder.NumberOfCartItemText != null){
                holder.NumberOfCartItemText.setText(String.valueOf(cartItem.getQuantity()));
        }

        if (holder.DecreaseCartItemView != null){
            holder.DecreaseCartItemView.setOnClickListener(view -> {
                if (holder.NumberOfCartItemText != null && cartItem.getQuantity() > 1){
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                    holder.NumberOfCartItemText.setText(String.valueOf(cartItem.getQuantity()));

                    double currentTotalPrice = MoneyHelper.getVietnameseMoneyDouble(
                            TotalProductPrice.getText().toString());
                    currentTotalPrice -= getDiscountedPrice(product);
                    setPriceToView(TotalProductPrice,
                            MoneyHelper.getVietnameseMoneyStringFormatted(currentTotalPrice),
                            FinalPrice,
                            MoneyHelper.getVietnameseMoneyStringFormatted(currentTotalPrice));
                }
            });
        }

        if (holder.IncreaseCartItemView != null){
            holder.IncreaseCartItemView.setOnClickListener(view -> {
                if (holder.NumberOfCartItemText != null ){
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                    holder.NumberOfCartItemText.setText(String.valueOf(cartItem.getQuantity()));

                    double currentTotalPrice = MoneyHelper.getVietnameseMoneyDouble(
                            TotalProductPrice.getText().toString());
                    currentTotalPrice += getDiscountedPrice(product);
                    setPriceToView(TotalProductPrice,
                            MoneyHelper.getVietnameseMoneyStringFormatted(currentTotalPrice),
                            FinalPrice,
                            MoneyHelper.getVietnameseMoneyStringFormatted(currentTotalPrice));
                }
            });
        }

        if (holder.DeleteProductInCartView != null){
            holder.DeleteProductInCartView.setOnClickListener(v -> {
                if (onDeleteItemClickListener != null) {
                    onDeleteItemClickListener.onDeleteItemClick(position);
                }
            });
        }

    }
    public void updateTotalPriceAfterDelete(Product product, ShoppingCartItem item) {
        double currentItemPrice = getDiscountedPrice(product) * item.getQuantity();
        double currentTotalPrice = MoneyHelper.getVietnameseMoneyDouble(
                TotalProductPrice.getText().toString());
        currentTotalPrice -= currentItemPrice;
        setPriceToView(TotalProductPrice,
                MoneyHelper.getVietnameseMoneyStringFormatted(currentTotalPrice),
                FinalPrice,
                MoneyHelper.getVietnameseMoneyStringFormatted(currentTotalPrice));
    }

    private void setPriceToView(TextView totalProductPrice, String currentTotalPrice, TextView finalPrice, String currentTotalPrice1) {
        totalProductPrice.setText(currentTotalPrice);
        finalPrice.setText(currentTotalPrice1);
    }

    @Override
    public int getItemCount() {
        if (ProductList!=null)
            return ProductList.size();
        return 0;
    }

    private double getDiscountedPrice(Product p) {
        double price = p.getPrice();
        if (p.getDiscountInformation().isActive()){
            price = price * (100 - p.getDiscountInformation().getDiscountPercentage()) / 100;
        }
        return price;
    }

    public class CartListViewHolder extends RecyclerView.ViewHolder{
        private ImageView CartItemImage;
        private TextView ProductNameText;
        private TextView PriceText;
        private TextView NumberOfCartItemText;
        private View DecreaseCartItemView;
        private View IncreaseCartItemView;
        private View DeleteProductInCartView;
        public CartListViewHolder(@NonNull View itemView){
            super(itemView);
            CartItemImage = itemView.findViewById(R.id.item_image_list_cart);
            ProductNameText = itemView.findViewById(R.id.label_product_cart);
            PriceText = itemView.findViewById(R.id.label_price_cart);
            NumberOfCartItemText = itemView.findViewById(R.id.input_value);
            DecreaseCartItemView = itemView.findViewById(R.id.decrease_box);
            IncreaseCartItemView = itemView.findViewById(R.id.increase_box);
            DeleteProductInCartView = itemView.findViewById(R.id.delete_product_view);
        }
    }
}
