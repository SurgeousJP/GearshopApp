package com.example.gearshop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gearshop.R;
import com.example.gearshop.activity.customer_activity.ProductDetailActivity;
import com.example.gearshop.interfaces.ItemClickListener;
import com.example.gearshop.model.Discount;
import com.example.gearshop.model.Product;
import com.example.gearshop.utility.MoneyHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ProductListViewHolder> {

    private List<Product> listProductItems;
    private Context mContext;

    public ProductRecyclerAdapter(List<Product> listProductItems, Context context){
        this.listProductItems = listProductItems;
        this.mContext = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Product> listProductItems){
        this.listProductItems = listProductItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_product,parent,false);
        return new ProductListViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductListViewHolder holder, int position) {
        Product product = listProductItems.get(position);

        if (product == null) return;

        if (holder.tvProductLabel != null) {
            holder.tvProductLabel.setText(product.getName());
        }

        if (holder.tvSellingPrice != null) {
            holder.tvSellingPrice.setText(MoneyHelper.getVietnameseMoneyStringFormatted(product.getPrice()));
        }

        if (holder.tvDiscount != null) {
            Discount productDiscount = product.getDiscountInformation();
            if (productDiscount.isActive()){
                holder.tvDiscount.setText(String.valueOf(productDiscount.getDiscountPercentage()) + "%");
            }
            else{
                holder.tvDiscount.setText("Không giảm giá");
            }
        }

        if (holder.ivProductThumbnail != null) {
            String imageURL = product.getImageURL();
            Picasso.get().load(imageURL).into(holder.ivProductThumbnail);
        }

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (!isLongClick) {
                    Intent intent = new Intent(mContext, ProductDetailActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("clickedProduct", product);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listProductItems != null)
            return listProductItems.size();
        return 0;
    }

    public class ProductListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView ivProductThumbnail;
        TextView tvProductLabel;
        TextView tvSellingPrice;
        TextView tvDiscount;
        private ItemClickListener itemClickListener;

        public ProductListViewHolder(@NonNull View itemView){
            super(itemView);

            ivProductThumbnail = (ImageView) itemView.findViewById(R.id.item_image_list_item);
            tvProductLabel = (TextView) itemView.findViewById(R.id.label_product);
            tvSellingPrice = (TextView) itemView.findViewById(R.id.selling_price);
            tvDiscount = (TextView) itemView.findViewById(R.id.discount_text);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }
    }
}
