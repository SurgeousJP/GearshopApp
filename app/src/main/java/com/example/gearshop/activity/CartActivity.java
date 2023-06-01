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
import com.example.gearshop.database.GetProductDataFromAzure;
import com.example.gearshop.database.GetProvinceDataFromAzure;
import com.example.gearshop.database.InsertDataToAzure;
import com.example.gearshop.fragment.ConfirmDeleteCartItemDialogFragment;
import com.example.gearshop.fragment.ShippingInfoBottomSheetDialogFragment;
import com.example.gearshop.model.Address;
import com.example.gearshop.model.Order;
import com.example.gearshop.model.OrderItem;
import com.example.gearshop.model.Province;
import com.example.gearshop.repository.GlobalRepository;
import com.example.gearshop.model.Product;
import com.example.gearshop.model.ShoppingCartItem;
import com.example.gearshop.utility.MoneyHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
                initializeOrderList();
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

            insertOrderToAzure(newOrder);

            if (getOrderItemDataFromAzure == null){
                initializeOrderItemList();
            }
            for (int i = 0; i < CartItemList.size(); i++){
                OrderItem newOrderItem =
                        new OrderItem(
                                getOrderItemDataFromAzure[0].getOrderItemList().size() + i + 1,
                                newOrder.getID(),
                                CartItemList.get(i).getProductID(),
                                CartItemList.get(i).getQuantity(), 5, "");
                insertOrderItemToAzure(newOrderItem);
            }
        };
        CheckoutTextView.setOnClickListener(checkoutListener);
        CheckoutTextView.setOnClickListener(checkoutListener);
    }
    private void insertOrderToAzure(Order newOrder){
        String inputPattern = "yyyy-MM-dd"; // Input date format
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.getDefault());
        InsertDataToAzure insertOrderDataToAzure = new InsertDataToAzure();

        System.out.println("Async Task insert Orders is running");
        insertOrderDataToAzure.execute(
                "INSERT INTO orders (id, note, created_on_utc, " +
                        "customer_id, shipping_address_id, shipping_method_id," +
                        " payment_method_id, total_price, status, is_paid)\n" +
                        "VALUES(" + newOrder.getID()  + ", " +
                        "'" +                                     "', " +
                        "'" + inputFormat.format(newOrder.getCreatedOnUtc())        + "', " +
                        "'" + newOrder.getCustomerID()          + "', " +
                        "'" + newOrder.getShippingAddressID()   + "', " +
                        "'" + newOrder.getShipmentMethodID()    + "', " +
                        "'" + newOrder.getPaymentMethodID()     + "', " +
                        "'" + newOrder.getTotalPrice()          + "', " +
                        "'" + newOrder.getStatus()              + "', " +
                        "'" + newOrder.isPaid()                 + "')\n");
        try {
            insertOrderDataToAzure.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Async Task insert Orders ended");
    }

    private void insertOrderItemToAzure(OrderItem orderItem){
        InsertDataToAzure insertOrderItemDataToAzure = new InsertDataToAzure();

        System.out.println("Async Task insert OrderItem is running");
        insertOrderItemDataToAzure.execute(
                "INSERT INTO order_item (id, order_id, product_id, " +
                        "quantity, rate," +
                        "comment)\n" +
                        "VALUES(" +
                        "'" + orderItem.getID()           + "', " +
                        "'" + orderItem.getOrderID()      + "', " +
                        "'" + orderItem.getProductID()    + "', " +
                        "'" + orderItem.getQuantity()     + "', " +
                        "'" + 5                           + "', " +
                        "'"                               + "')\n");
        try {
            insertOrderItemDataToAzure.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Async Task insert OrderItem ended");
    }

    private void initializeOrderList() {
        getOrderDataFromAzure = new GetOrderDataFromAzure[1];
        getOrderDataFromAzure[0] = new GetOrderDataFromAzure();
        getOrderDataFromAzure[0].execute(
                "SELECT * FROM orders"
        );

        System.out.println("Async Task Order running");
        try {
            getOrderDataFromAzure[0].get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Async Task Order ended");
    }

    private void initializeOrderItemList(){
        getOrderItemDataFromAzure = new GetOrderItemDataFromAzure[1];
        getOrderItemDataFromAzure[0] = new GetOrderItemDataFromAzure();
        getOrderItemDataFromAzure[0].execute(
                "SELECT * FROM order_item"
        );
        System.out.println("Async Task OrderItem running");
        try {
            getOrderItemDataFromAzure[0].get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Async Task OrderItem ended");
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