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
import com.example.gearshop.database.GetOrderDataFromAzure;
import com.example.gearshop.database.GetOrderItemDataFromAzure;
import com.example.gearshop.database.GetProvinceDataFromAzure;
import com.example.gearshop.database.InsertDataToAzure;
import com.example.gearshop.fragment.ConfirmDeleteCartItemDialogFragment;
import com.example.gearshop.fragment.ShippingInfoBottomSheetDialogFragment;
import com.example.gearshop.model.Address;
import com.example.gearshop.model.Order;
import com.example.gearshop.model.OrderItem;
import com.example.gearshop.repository.GlobalRepository;
import com.example.gearshop.model.Product;
import com.example.gearshop.model.ShoppingCartItem;
import com.example.gearshop.utility.DatabaseHelper;
import com.example.gearshop.utility.MoneyHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class CartActivity extends AppCompatActivity implements ConfirmDeleteCartItemDialogFragment.DialogListener{
    private List<ShoppingCartItem> CartItemList;
    private List<Product> ProductList;
    private ConstraintLayout ReturnView;
    private RecyclerView CartRecyclerView;
    private RelativeLayout MoreInformationLayout;
    private RelativeLayout EscapeLayout;
    private TextView TotalProductPrice;
    private TextView FinalPrice;
    private TextView TransportFee;
    private CartListAdapter CartAdapter;
    private TextView CheckoutTextView;
    private ConstraintLayout CheckoutLayout;
    private int CartItemPosition;

    private View ChangeShippingInfoView;
    private ConstraintLayout ShippingInfoLayout;
    private GetOrderDataFromAzure[] getOrderDataFromAzure;
    private GetOrderItemDataFromAzure[] getOrderItemDataFromAzure;
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

        CheckoutTextView = findViewById(R.id.check_out_text);
        CheckoutLayout = findViewById(R.id.check_out_button);
        ShippingInfoLayout = findViewById(R.id.transport_info_box);

        Address globalAddress = GlobalRepository.getCustomerAddress();
        updateShippingInfo(globalAddress.getHouseNumber(), globalAddress.getStreet(),
                GlobalRepository.getCurrentCustomer().getPhoneNumber());
        TransportFee = findViewById(R.id.transport_fee_price_order_detail);
        TransportFee.setText(
                MoneyHelper.getVietnameseMoneyStringFormatted(
                        GetProvinceDataFromAzure.getShippingCharge(globalAddress.getProvinceID())));

        CartItemList = ((GlobalRepository) getApplication()).getCartItemList();
        ProductList = ((GlobalRepository) getApplication()).getProductList();
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
        CheckoutTextView.setText(MoneyHelper.getVietnameseMoneyStringFormatted(getTotalProductPrice(ProductList)));

        CartAdapter.setTotalProductPrice(TotalProductPrice);
        CartAdapter.setFinalPrice(FinalPrice);
        CartAdapter.setCheckoutPrice(CheckoutTextView);

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

        ChangeShippingInfoView = findViewById(R.id.change_shipping_info);
        ChangeShippingInfoView.setOnClickListener(view -> {
            ShippingInfoBottomSheetDialogFragment dialogFragment =
                    new ShippingInfoBottomSheetDialogFragment(ShippingInfoLayout, TransportFee);
            dialogFragment.show(getSupportFragmentManager(), dialogFragment.getTag());
        });

        View.OnClickListener checkoutListener = view -> {
            Order newOrder = new Order();
            int newOrderID;
            if (getOrderDataFromAzure == null){
                DatabaseHelper.getOrderList();
            }
            newOrder.setID(getOrderDataFromAzure[0].getOrderList().size());
            newOrder.setShipmentMethodID(1);
            newOrder.setPaymentMethodID(2);
            newOrder.setShippingAddressID(GlobalRepository.getCustomerAddress().getID());
            newOrder.setCustomerID(GlobalRepository.getCurrentCustomer().getID());
            newOrder.setTotalPrice(MoneyHelper.getVietnameseMoneyDouble(TotalProductPrice.getText().toString()));
            newOrder.setCreatedOnUtc(new Date());
            newOrder.setPaid(false);
            newOrder.setStatus("Đang xử lý");

            DatabaseHelper.insertOrderToAzure(newOrder);

            if (getOrderItemDataFromAzure == null){
                DatabaseHelper.getOrderItemList();
            }
            for (int i = 0; i < CartItemList.size(); i++){
                OrderItem newOrderItem =
                        new OrderItem(
                                getOrderItemDataFromAzure[0].getOrderItemList().size() + i + 1,
                                newOrder.getID(),
                                CartItemList.get(i).getProductID(),
                                CartItemList.get(i).getQuantity(), 5, "");
                DatabaseHelper.insertOrderItemToAzure(newOrderItem);
            }
        };
        CheckoutTextView.setOnClickListener(checkoutListener);
        CheckoutTextView.setOnClickListener(checkoutListener);
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
            ((GlobalRepository) getApplication()).setCartItemList(CartItemList);
            ((GlobalRepository) getApplication()).setProductList(ProductList);
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
        return resultPrice + MoneyHelper.getVietnameseMoneyDouble(TransportFee.getText().toString());
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