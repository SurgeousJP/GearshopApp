package com.example.gearshop.activity.admin_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
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
import com.example.gearshop.model.OrderItem;
import com.example.gearshop.model.Product;
import com.example.gearshop.utility.CustomMarkerView;
import com.example.gearshop.utility.DatabaseHelper;
import com.example.gearshop.utility.MoneyHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class AdminStatisticActivity extends AppCompatActivity {

    private View ReturnIconView;
    private View PrintStatisticIconView;
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
        PrintStatisticIconView = findViewById(R.id.print_statistic_view);
        SaleTextView = findViewById(R.id.sales);
        TotalOrdersTextView = findViewById(R.id.total_orders);
        NumberOfProductsTextView = findViewById(R.id.number_of_products);
        NumberOfCustomerTextView = findViewById(R.id.number_of_customers);
        CategoryPieChart = findViewById(R.id.pie_chart_category);
        MonthlySaleBarChart = findViewById(R.id.bar_chart_monthly_sale);
        TopProductHorizontalBarChart = findViewById(R.id.horizon_bar_chart_top_product);

        ReturnIconView.setOnClickListener(view -> {
            finish();
        });

        PrintStatisticIconView.setOnClickListener(view -> {
            // DOING IT LATER
        });

        List<Customer> customerList = DatabaseHelper.getCustomerList("ALL");
        List<Product> productList = DatabaseHelper.getCustomerProductListGivenID("ALL");
        List<Order> orderList = DatabaseHelper.getOrderList("ALL");
        List<OrderItem> orderItemList = DatabaseHelper.getOrderItemList("ALL");

        loadSaleAndTotalOrders(orderList);
        loadNumberOfProducts(productList);
        loadNumberOfCustomers(customerList);
        loadNumberOfProductsInEachCategory();
        loadSaleInMonths(orderList);
        loadTopFivePopularProducts(orderItemList);
    }

    private void loadTopFivePopularProducts(List<OrderItem> orderItemList){
        // Create a list of BarEntry objects for the data
        List<BarEntry> entries = new ArrayList<>();

        List<Map.Entry<Integer, Integer>> topFiveProducts = getTopFivePopularProducts(orderItemList);

        int iterator = 0;
        for (Map.Entry<Integer, Integer> entry : topFiveProducts) {
            entries.add(new BarEntry(iterator++, entry.getValue()));
        }

        // Create a BarDataSet with the entries
        BarDataSet dataSet = new BarDataSet(entries, "Quantity");
        // Enable value labels for the bars
        dataSet.setValueTextColor(Color.BLACK); // Set the color of the value labels
        // Set colors for the bars
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        // Create a BarData object and set the dataset
        BarData barData = new BarData(dataSet);

        // Set the value text size
        barData.setValueTextSize(12f);
        barData.setBarWidth(0.25f);

        // Get the Legend object from the chart
        Legend legend = TopProductHorizontalBarChart.getLegend();

        // Set the legend position to center
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        // Set data to the chart
        TopProductHorizontalBarChart.setData(barData);

        // Turn off Description Label
        TopProductHorizontalBarChart.getDescription().setEnabled(false);

        TopProductHorizontalBarChart.setFitBars(true); // Enable bar width calculation to fill the available space
        TopProductHorizontalBarChart.setDrawValueAboveBar(true); // Draw value labels above the bars

        // Set up X-axis
        XAxis xAxis = TopProductHorizontalBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);

        // Set a custom IndexAxisValueFormatter to truncate the labels
        String[] labels = getTopFivePopularProductsName(topFiveProducts);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels) {
            private final int MAX_LABEL_LENGTH = 15; // Adjust the maximum length of the label as desired

            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (!(index >= 0 && index < labels.length)) return "";
                String original = labels[index];
                String truncatedLabel = original; // Use the original label by default

                // Truncate the label if it exceeds the maximum length
                if (original.length() > MAX_LABEL_LENGTH) {
                    truncatedLabel = original.substring(0, MAX_LABEL_LENGTH) + "...";
                }

                return truncatedLabel;
            }
        });

        // Set up tooltip to display labels (if not fully displayed)
        CustomMarkerView markerView = new CustomMarkerView(this, R.layout.custom_marker_view);
        markerView.setLabels(labels);
        TopProductHorizontalBarChart.setMarker(markerView);

        // Set bottom offset to reduce empty space
        TopProductHorizontalBarChart.setExtraBottomOffset(-10);

        // Handle the Y axis
        YAxis leftYAxis = TopProductHorizontalBarChart.getAxisLeft();
        leftYAxis.setAxisMinimum(0);
        leftYAxis.setDrawGridLines(false);
        leftYAxis.setGranularity(1f);
        TopProductHorizontalBarChart.getAxisRight().setEnabled(false);

        // Refresh the chart
        TopProductHorizontalBarChart.invalidate();
    }

    private String[] getTopFivePopularProductsName(List<Map.Entry<Integer, Integer>> topFiveProducts){
        String[] result = new String[5];
        int iterator = 0;
        for (Map.Entry<Integer, Integer> entry : topFiveProducts) {
            Product product =
                    DatabaseHelper.getCustomerProductListGivenID
                            ("WHERE product.id = '" + entry.getKey() + "'").get(0);
            result[iterator++] = product.getName();
        }
        return result;
    }

    private List<Map.Entry<Integer, Integer>> getTopFivePopularProducts(List<OrderItem> orderItemList) {
        Map<Integer, Integer> listNumberOfProductsBought = getListNumberOfProductsBought(orderItemList);

        // Convert the map entries to a list
        List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(listNumberOfProductsBought.entrySet());

        // Sort the list in descending order based on values
        entries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Extract the top 5 values
        List<Map.Entry<Integer, Integer>> top5Entries = entries.subList(0, Math.min(entries.size(), 5));

        return top5Entries;
    }

    private Map<Integer, Integer> getListNumberOfProductsBought(List<OrderItem> orderItemList){
        Map<Integer, Integer> result = new HashMap<>();
        for (OrderItem orderItem : orderItemList){
            int key = orderItem.getProductID();
            if (!result.containsKey(key)){
                result.put(key, orderItem.getQuantity());
            }
            else{
                int oldValue = result.get(key);
                result.put(key, oldValue + orderItem.getQuantity());
            }
        }
        return result;
    }

    private void loadSaleInMonths(List<Order> orderList){
        double[] saleInMonths = new double[12];
        Arrays.fill(saleInMonths, 0);
        for (Order order : orderList){
            saleInMonths[order.getCreatedOnUtc().getMonth() - 1] += order.getTotalPrice();
        }

        // Create a list of BarEntry objects for the data
        List<BarEntry> entries = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                double value = saleInMonths[i];
                entries.add(new BarEntry(i, (float) value));
            }

        // Create a BarDataSet with the entries
        BarDataSet dataSet = new BarDataSet(entries, "Revenue in VND");

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
        MonthlySaleBarChart.getDescription().setEnabled(false);

        // Get the Legend object from the chart
        Legend legend = MonthlySaleBarChart.getLegend();

        // Set the legend position to center
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        // Customize the x-axis
        XAxis xAxis = MonthlySaleBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getMonths()));
        xAxis.setLabelCount(saleInMonths.length);

        // Customize the y-axis (optional)
        YAxis yAxis = MonthlySaleBarChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);

        // Hide the right y-axis (optional)
        MonthlySaleBarChart.getAxisRight().setEnabled(false);

        // Set padding for the chart
        int paddingLeft = 10; // Adjust the left padding as desired
        int paddingTop = 10; // Adjust the top padding as desired
        int paddingRight = 10; // Adjust the right padding as desired
        int paddingBottom = 10; // Adjust the bottom padding as desired

        MonthlySaleBarChart.setExtraOffsets(paddingLeft, paddingTop, paddingRight, paddingBottom);

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


        // Get the Legend object from the chart
        Legend legend = CategoryPieChart.getLegend();

        // Set the legend position to center
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

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