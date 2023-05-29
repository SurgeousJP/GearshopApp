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
import com.example.gearshop.activity.ProductsInCategoryActivity;
import com.example.gearshop.interfaces.ItemClickListener;
import com.example.gearshop.model.Category;
import com.example.gearshop.model.Product;
import com.example.gearshop.utility.MoneyHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryListViewHolder> {

    private List<Category> listCategoryItems;
    private Context mContext;

    private TextView TotalProductPrice;
    private TextView FinalPrice;


    public CategoryRecyclerAdapter(List<Category> listCategoryItems, Context context){
        this.listCategoryItems = listCategoryItems;
        this.mContext = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List <Category> listCategoryItems){
        this.listCategoryItems = listCategoryItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category,parent,false);
        return new CategoryListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListViewHolder holder, int position) {
        Category category = listCategoryItems.get(position);

        if (category == null) return;

        if (holder.tvCategoryLabel != null){
            holder.tvCategoryLabel.setText(category.getName());
        }

        if (holder.ivCategoryThumbnail != null){
            switch(category.getName()) {
                case "Laptop":
                    holder.ivCategoryThumbnail.setImageResource(R.mipmap.laptop_icon);
                    break;
                case "PC":
                    holder.ivCategoryThumbnail.setImageResource(R.mipmap.pc_icon);
                    break;
                case "Screen":
                    holder.ivCategoryThumbnail.setImageResource(R.mipmap.screen_icon);
                    break;
                case "Keyboard":
                    holder.ivCategoryThumbnail.setImageResource(R.mipmap.keyboard_icon);
                    break;
                case "Mouse":
                    holder.ivCategoryThumbnail.setImageResource(R.mipmap.mouse_icon);
                    break;
                case "Earphone & Speaker":
                    holder.ivCategoryThumbnail.setImageResource(R.mipmap.headphone_icon);
                    break;
                case "PC components":
                    holder.ivCategoryThumbnail.setImageResource(R.mipmap.pc_component_icon);
                    break;
                case "Apple":
                    holder.ivCategoryThumbnail.setImageResource(R.mipmap.apple_icon);
                    break;
                default:
                    holder.ivCategoryThumbnail.setImageResource(R.mipmap.ic_launcher);
                    break;
            }


            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    if (!isLongClick) {
                        Intent intent = new Intent(mContext, ProductsInCategoryActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("categoryId", category.getID());
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (listCategoryItems != null)
            return listCategoryItems.size();
        return 0;
    }

    public class CategoryListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView ivCategoryThumbnail;
        TextView tvCategoryLabel;

        private ItemClickListener itemClickListener;

        public CategoryListViewHolder(@NonNull View itemView){
            super(itemView);

            ivCategoryThumbnail = (ImageView) itemView.findViewById(R.id.item_image_listview_categoty);
            tvCategoryLabel = (TextView) itemView.findViewById(R.id.label_category);

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
