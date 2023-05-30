package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gearshop.R;
import com.example.gearshop.adapter.CartListAdapter;
import com.example.gearshop.fragment.ConfirmDeleteCartItemDialogFragment;
import com.example.gearshop.fragment.ShippingInfoBottomSheetDialogFragment;
import com.example.gearshop.model.Address;
import com.example.gearshop.repository.CartRepository;
import com.example.gearshop.model.Product;
import com.example.gearshop.model.ShoppingCartItem;
import com.example.gearshop.repository.CustomerRepository;
import com.example.gearshop.utility.MoneyHelper;

import java.util.List;
import java.util.Objects;

public class CartActivity extends AppCompatActivity implements ConfirmDeleteCartItemDialogFragment.DialogListener{
    private List<ShoppingCartItem> CartItemList;
    private List<Product> ProductList;
    private ConstraintLayout ReturnView;
    private RecyclerView CartRecyclerView;
    private RelativeLayout MoreInformationLayout;
    private RelativeLayout EscapeLayout;
    private TextView TotalProductPrice;
    private TextView FinalPrice;
    private CartListAdapter CartAdapter;
    private int CartItemPosition;

    private View ChangeShippingInfoView;
    private ConstraintLayout ShippingInfoLayout;
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
        TotalProductPrice.setText(MoneyHelper.getVietnameseMoneyStringFormatted(getTotalProductPrice(ProductList)));
        FinalPrice = findViewById(R.id.final_price_order_detail);
        FinalPrice.setText(MoneyHelper.getVietnameseMoneyStringFormatted(getTotalProductPrice(ProductList)));

        CartAdapter.setTotalProductPrice(TotalProductPrice);
        CartAdapter.setFinalPrice(FinalPrice);

        ReturnView = findViewById(R.id.wayback_icon_order_detail);
        ReturnView.setOnClickListener(view -> {
            setResult(Activity.RESULT_OK);
            finish();
        });

        MoreInformationLayout = findViewById(R.id.dots_cart);
        MoreInformationLayout.setOnClickListener(view -> {

        });
        EscapeLayout = findViewById(R.id.escape_cart);
        EscapeLayout.setOnClickListener(view -> {

        });

        ShippingInfoLayout = findViewById(R.id.transport_info_box);

        Address globalAddress = CartRepository.getCustomerAddress();
        updateShippingInfo(globalAddress.getHouseNumber(), globalAddress.getStreet(),
                CartRepository.getCurrentCustomer().getPhoneNumber());

        ChangeShippingInfoView = findViewById(R.id.change_shipping_info);
        ChangeShippingInfoView.setOnClickListener(view -> {
            ShippingInfoBottomSheetDialogFragment dialogFragment = new ShippingInfoBottomSheetDialogFragment(ShippingInfoLayout);
            dialogFragment.show(getSupportFragmentManager(), dialogFragment.getTag());
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateShippingInfo(String houseNumber, String street, String phoneNumber){
        TextView AddressTextView = ShippingInfoLayout.findViewById(R.id.label_ship_order_detail);
        TextView PhoneNumberTextView = ShippingInfoLayout.findViewById(R.id.description);
        AddressTextView.setText(houseNumber + "\n" + street);
        PhoneNumberTextView.setText(phoneNumber);
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