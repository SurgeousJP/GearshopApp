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
import com.example.gearshop.model.Order;
import com.example.gearshop.model.OrderItem;
import com.example.gearshop.model.Product;
import com.example.gearshop.utility.DatabaseHelper;
import com.example.gearshop.utility.MoneyHelper;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class OrderItemListAdapter extends RecyclerView.Adapter<OrderItemListAdapter.OrderItemListViewHolder>{
    private List<OrderItem> ListOrderItem;
    private Order CurrentOrder;

    public OrderItemListAdapter(List<OrderItem> listOrderItem, Order currentOrder){
        this.ListOrderItem = listOrderItem;
        this.CurrentOrder = currentOrder;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<OrderItem> orderItemList){
        this.ListOrderItem = orderItemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderItemListAdapter.OrderItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cart,parent,false);
        return new OrderItemListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemListAdapter.OrderItemListViewHolder holder, int position) {
        OrderItem orderItem = ListOrderItem.get(position);
        Product productItem = DatabaseHelper.getCustomerProductListGivenID(
                "WHERE product.id ='" + orderItem.getProductID() + "'").get(0);
        if (productItem == null) return;
        else{
            holder.ProductNameText.setText(productItem.getName());
            holder.PriceText.setText(String.valueOf(productItem.getPrice()));
        }
        if (holder.CartItemImage != null){
            String imageURL = productItem.getImageURL();
            Picasso.get().load(imageURL).into(holder.CartItemImage);
        }

        if (holder.ProductNameText != null){
            holder.ProductNameText.setText(productItem.getName());
        }

        if (holder.PriceText != null){
            double discountedPrice = getDiscountedPrice(productItem, CurrentOrder.getCreatedOnUtc());
            holder.PriceText.setText(MoneyHelper.getVietnameseMoneyStringFormatted(discountedPrice));
        }

        if (holder.NumberOfCartItemText != null){
            holder.NumberOfCartItemText.setText(String.valueOf(orderItem.getQuantity()));
        }
    }
    @Override
    public int getItemCount() {
        if (ListOrderItem != null)
            return ListOrderItem.size();
        return 0;
    }

    private double getDiscountedPrice(Product p, Date createdOrderDate){
        double price = p.getPrice();
        if (p.getDiscountInformation().isActive(createdOrderDate)){
            price = price * (100 - p.getDiscountInformation().getDiscountPercentage()) / 100;
        }
        return price;
    }

    public class OrderItemListViewHolder extends RecyclerView.ViewHolder{
        private ImageView CartItemImage;
        private TextView ProductNameText;
        private TextView PriceText;
        private TextView NumberOfCartItemText;
        private View DecreaseCartItemView;
        private View IncreaseCartItemView;
        private View DeleteProductInCartView;
        public OrderItemListViewHolder(@NonNull View itemView){
            super(itemView);
            CartItemImage = itemView.findViewById(R.id.item_image_list_cart);
            ProductNameText = itemView.findViewById(R.id.label_product_cart);
            PriceText = itemView.findViewById(R.id.label_price_cart);
            NumberOfCartItemText = itemView.findViewById(R.id.input_value);
            DecreaseCartItemView = itemView.findViewById(R.id.decrease_box);
            DecreaseCartItemView.setVisibility(View.INVISIBLE);
            IncreaseCartItemView = itemView.findViewById(R.id.increase_box);
            IncreaseCartItemView.setVisibility(View.INVISIBLE);
            DeleteProductInCartView = itemView.findViewById(R.id.delete_product_view);
            DeleteProductInCartView.setVisibility(View.INVISIBLE);
        }
    }
}
