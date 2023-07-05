package com.example.gearshop.activity.admin_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.gearshop.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;

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
        MonthlySaleBarChart = findViewById(R.id.bar_chart_monthly_sale);
        TopProductHorizontalBarChart = findViewById(R.id.horizon_bar_chart_top_product);

        ReturnIconView.setOnClickListener(view -> {
            finish();
        });

    }
}