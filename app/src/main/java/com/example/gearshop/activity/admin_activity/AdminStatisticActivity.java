package com.example.gearshop.activity.admin_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.gearshop.R;
import com.example.gearshop.model.Customer;
import com.example.gearshop.model.Order;
import com.example.gearshop.model.Product;
import com.example.gearshop.utility.DatabaseHelper;
import com.example.gearshop.utility.MoneyHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;

import java.util.List;

public class AdminStatisticActivity extends AppCompatActivity {

    private View ReturnIconView;
    private TextView SaleTextView;
    private TextView TotalOrdersTextView;
    private TextView NumberOfProductsTextView;
    private TextView NumberOfCustomerTextView;
    private PieChart CategoryPieChart;
    private BarChart MonthlySaleBarChart;
    private HorizontalBarChart TopProductHorizontalBarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_statistic);

        ReturnIconView = findViewById(R.id.statistics_return_icon_view);
        SaleTextView = findViewById(R.id.sales);
        TotalOrdersTextView = findViewById(R.id.total_orders);
        NumberOfProductsTextView = findViewById(R.id.number_of_products);
        NumberOfCustomerTextView = findViewById(R.id.number_of_customers);

        // GET CATEGORY JOIN PRODUCT DATA
        CategoryPieChart = findViewById(R.id.pie_chart_category);

        // GROUP BY MONTH
        MonthlySaleBarChart = findViewById(R.id.bar_chart_monthly_sale);
        // GROUP BY PRODUCT_ID IN ORDER_ITEM
        TopProductHorizontalBarChart = findViewById(R.id.horizon_bar_chart_top_product);

        ReturnIconView.setOnClickListener(view -> {
            finish();
        });

        loadSaleAndTotalOrders();
        loadNumberOfProducts();
        loadNumberOfCustomers();
    }


    private void loadNumberOfCustomers(){
        List<Customer> customerList = DatabaseHelper.getCustomerList("ALL");
        NumberOfCustomerTextView.setText(String.valueOf(customerList.size()));
    }


    private void loadNumberOfProducts(){
        List<Product> productList = DatabaseHelper.getCustomerProductListGivenID("ALL");
        NumberOfProductsTextView.setText(String.valueOf(productList.size()));
    }

    private void loadSaleAndTotalOrders(){
        List<Order> orderList = DatabaseHelper.getOrderList("ALL");
        SaleTextView.setText(MoneyHelper.getVietnameseMoneyStringFormatted(getTotalSale(orderList)));
        TotalOrdersTextView.setText(String.valueOf(orderList.size()));
    }

    private double getTotalSale(List<Order> orderList) {
        double totalSale = 0;
        for (Order order : orderList){
            totalSale += order.getTotalPrice();
        }
        return totalSale;
    }
}