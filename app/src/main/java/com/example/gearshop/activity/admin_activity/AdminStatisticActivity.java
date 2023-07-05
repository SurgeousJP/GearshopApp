package com.example.gearshop.activity.admin_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.gearshop.R;
import com.example.gearshop.database.GetCategoryDataFromAzure;
import com.example.gearshop.database.GetNumberOfProductsInEachCategoryFromAzure;
import com.example.gearshop.database.GetOrderItemDataFromAzure;
import com.example.gearshop.model.Category;
import com.example.gearshop.model.Customer;
import com.example.gearshop.model.Order;
import com.example.gearshop.model.Product;
import com.example.gearshop.utility.DatabaseHelper;
import com.example.gearshop.utility.MoneyHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
        CategoryPieChart = findViewById(R.id.pie_chart_category);

        // GROUP BY MONTH
        MonthlySaleBarChart = findViewById(R.id.bar_chart_monthly_sale);
        // GROUP BY PRODUCT_ID IN ORDER_ITEM
        TopProductHorizontalBarChart = findViewById(R.id.horizon_bar_chart_top_product);

        ReturnIconView.setOnClickListener(view -> {
            finish();
        });

        List<Customer> customerList = DatabaseHelper.getCustomerList("ALL");
        List<Product> productList = DatabaseHelper.getCustomerProductListGivenID("ALL");
        List<Order> orderList = DatabaseHelper.getOrderList("ALL");


        loadSaleAndTotalOrders(orderList);
        loadNumberOfProducts(productList);
        loadNumberOfCustomers(customerList);
        loadNumberOfProductsInEachCategory();
        loadSaleInMonths(orderList);
    }

    private void loadSaleInMonths(List<Order> orderList){
        double[] saleInMonths = new double[12];
        Arrays.fill(saleInMonths, 0);
        for (Order order : orderList){
            saleInMonths[order.getCreatedOnUtc().getMonth() - 1] += order.getTotalPrice();
        }

        for (double saleInMonth : saleInMonths){
            System.out.println(saleInMonth);
        }

        // Create a list of BarEntry objects for the data
        List<BarEntry> entries = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                double value = saleInMonths[i];
                entries.add(new BarEntry(i, (float) value));
            }

        // Create a BarDataSet with the entries
        BarDataSet dataSet = new BarDataSet(entries, "Monthly Sales");

        // Set color for the bars
        dataSet.setColor(Color.BLUE);

        // Create a BarData object and set the dataset
        BarData barData = new BarData(dataSet);

        // Set the value text size
        barData.setValueTextSize(12f);
        barData.setValueTextColor(Color.WHITE);

        // Set the data to the chart
        MonthlySaleBarChart.setData(barData);

        // Set no description line
        CategoryPieChart.getDescription().setEnabled(false);

        // Customize the x-axis
        XAxis xAxis = MonthlySaleBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getMonths()));

        // Customize the y-axis (optional)
        YAxis yAxis = MonthlySaleBarChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);

        // Hide the right y-axis (optional)
        MonthlySaleBarChart.getAxisRight().setEnabled(false);

        // Set chart animation (optional)
        MonthlySaleBarChart.animateY(10000000);

        // Refresh the chart
        MonthlySaleBarChart.invalidate();
    }

    private String[] getMonths() {
        return new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    }

    private void loadNumberOfProductsInEachCategory(){
        String sqlCommand = "  SELECT product_category.id, product_category.name, product_category.description," +
                "  COUNT(*) AS number_of_products\n" +
                "  FROM product, product_category\n" +
                "  WHERE product.category_id = product_category.id\n" +
                "  GROUP BY product_category.id, product_category.name, product_category.description";

        GetNumberOfProductsInEachCategoryFromAzure getNumberOfProductsInEachCategoryFromAzure
                = new GetNumberOfProductsInEachCategoryFromAzure();
        getNumberOfProductsInEachCategoryFromAzure.execute(sqlCommand);

        System.out.println("Async Task get Category and Number Of Products running");
        try {
            getNumberOfProductsInEachCategoryFromAzure.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Async Task get Category and Number Of Products ended");

        List<Category> categoryList =
                getNumberOfProductsInEachCategoryFromAzure.getCategoryList();
        List<Integer> numberOfProducts =
                getNumberOfProductsInEachCategoryFromAzure.getNumberOfProductsInEachCategory();

        // Create entries
        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < categoryList.size(); i++) {
            entries.add(new PieEntry(numberOfProducts.get(i), categoryList.get(i).getName()));
        }

        // Create dataset
        PieDataSet dataSet = new PieDataSet(entries, "");

        // Set colors
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        
        // Create pie data object
        PieData data = new PieData(dataSet);

        // Set value color and text size
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);

        // Set label color and text size
        CategoryPieChart.setEntryLabelColor(Color.BLACK);
        CategoryPieChart.setEntryLabelTextSize(12f);

        // Set draw hole to be disabled
        CategoryPieChart.setDrawHoleEnabled(false);

        // Set TransparentCircleAlpha
        CategoryPieChart.setTransparentCircleAlpha(200);

        // Set no description line
        CategoryPieChart.getDescription().setEnabled(false);

        // Set data to pie chart
        CategoryPieChart.setData(data);

        // Refresh the chart
        CategoryPieChart.invalidate();
    }

    private void loadNumberOfCustomers(List<Customer> customerList){
        NumberOfCustomerTextView.setText(String.valueOf(customerList.size()));
    }


    private void loadNumberOfProducts(List<Product> productList){
        NumberOfProductsTextView.setText(String.valueOf(productList.size()));
    }

    private void loadSaleAndTotalOrders(List<Order> orderList){
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