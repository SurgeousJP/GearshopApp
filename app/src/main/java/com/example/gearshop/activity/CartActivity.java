package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gearshop.R;
import com.example.gearshop.adapter.CartListAdapter;
import com.example.gearshop.fragment.ConfirmDeleteCartItemDialogFragment;
import com.example.gearshop.controller.CartRepository;
import com.example.gearshop.model.Product;
import com.example.gearshop.model.ShoppingCartItem;
import com.example.gearshop.utility.MoneyFormat;

import java.util.List;

public class CartActivity extends AppCompatActivity implements ConfirmDeleteCartItemDialogFragment.DialogListener{
    private List<ShoppingCartItem> CartItemList;
    private List<Product> ProductList;
    private View ReturnView;
    private RecyclerView CartRecyclerView;
    private RelativeLayout MoreInformationLayout;
    private RelativeLayout EscapeLayout;
    private TextView TotalProductPrice;
    private TextView FinalPrice;
    private CartListAdapter CartAdapter;
    private int CartItemPosition;

    public int getCartItemPosition() {
        return CartItemPosition;
    }

    public void setCartItemPosition(int cartItemPosition) {
        CartItemPosition = cartItemPosition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        CartItemList = ((CartRepository) getApplication()).getCartItemList();
        ProductList = ((CartRepository) getApplication()).getProductList();
        CartRecyclerView = findViewById(R.id.list_product);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false);
        CartAdapter = new CartListAdapter(CartItemList, ProductList);
        CartRecyclerView.setLayoutManager(gridLayoutManager);
        CartRecyclerView.setAdapter(CartAdapter);
        CartAdapter.setData(ProductList);
        CartAdapter.setOnDeleteItemClickListener(new CartListAdapter.OnDeleteItemClickListener() {
            @Override
            public void onDeleteItemClick(int position) {
                setCartItemPosition(position);
                showConfirmDeleteDialog();
            }
        });
        CartRecyclerView.setAdapter(CartAdapter);


        TotalProductPrice = findViewById(R.id.total_price_order_detail);
        TotalProductPrice.setText(MoneyFormat.getVietnameseMoneyStringFormatted(getTotalProductPrice(ProductList)));
        FinalPrice = findViewById(R.id.final_price_order_detail);
        FinalPrice.setText(MoneyFormat.getVietnameseMoneyStringFormatted(getTotalProductPrice(ProductList)));

        CartAdapter.setTotalProductPrice(TotalProductPrice);
        CartAdapter.setFinalPrice(FinalPrice);

        ReturnView = findViewById(R.id.return_cart_icon);
        ReturnView.setOnClickListener(view -> {
            setResult(Activity.RESULT_OK);
            finish();
        });
        MoreInformationLayout = findViewById(R.id.dots_user_info);
        MoreInformationLayout.setOnClickListener(view -> {

        });
        EscapeLayout = findViewById(R.id.escape_user_info);
        EscapeLayout.setOnClickListener(view -> {

        });
    }

    private void deleteCartItem(int position, CartListAdapter cartListAdapter) {
        if (position >= 0 && position < CartItemList.size()) {
            Product product = ProductList.get(position);
            ShoppingCartItem item = CartItemList.get(position);
            CartItemList.remove(position);
            ProductList.remove(position);
            ((CartRepository) getApplication()).setCartItemList(CartItemList);
            ((CartRepository) getApplication()).setProductList(ProductList);
            cartListAdapter.notifyItemRemoved(position);
            cartListAdapter.updateTotalPriceAfterDelete(product, item);
        }
    }

    protected double getTotalProductPrice(List<Product> products){
        double resultPrice = 0;
        for (int i = 0; i < products.size(); i++){
            Product product = products.get(i);
            double price = product.getPrice();
            if (product.getDiscountInformation().isActive()){
                price = price * (100 - product.getDiscountInformation().getDiscountPercentage()) / 100;
            }
            resultPrice += price;
        }
        return resultPrice;
    }
    private void showConfirmDeleteDialog() {
        ConfirmDeleteCartItemDialogFragment dialogFragment = new ConfirmDeleteCartItemDialogFragment();
        dialogFragment.setDialogListener(this);
        dialogFragment.show(getSupportFragmentManager(), "");
    }

    @Override
    public void onDialogResult(boolean result) {
        int itemPosition = getCartItemPosition();
        if (result){
            deleteCartItem(itemPosition, CartAdapter);
        }
    }
}