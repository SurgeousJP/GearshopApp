package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.gearshop.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class TestMPAndroidChart extends AppCompatActivity {
    private LineChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mpandroid_chart);

        lineChart = findViewById(R.id.lineChart);

        // Create a list of entries (x,y coordinates)
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 4));
        entries.add(new Entry(1, 8));
        entries.add(new Entry(2, 6));
        entries.add(new Entry(3, 2));
        entries.add(new Entry(4, 7));

        // Create a dataset from the entries
        LineDataSet dataSet = new LineDataSet(entries, "Sample Data");
        dataSet.setColor(Color.RED);
        dataSet.setValueTextColor(Color.BLACK);

        // Create a LineData object from the dataset
        LineData lineData = new LineData(dataSet);

        // Set the LineData object to the chart
        lineChart.setData(lineData);

        // Set a description for the chart
        Description description = new Description();
        description.setText("Sample Line Chart");
        lineChart.setDescription(description);

        // Refresh the chart
        lineChart.invalidate();
    }
}