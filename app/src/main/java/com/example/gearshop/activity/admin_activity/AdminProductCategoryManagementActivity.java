package com.example.gearshop.activity.admin_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gearshop.R;
import com.example.gearshop.adapter.CategoryListViewAdapter;
import com.example.gearshop.model.Category;
import com.example.gearshop.repository.CategoryRepository;
import com.example.gearshop.utility.ActivityStartManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class AdminProductCategoryManagementActivity extends AppCompatActivity {
    private ListView CategoryListView;
    private View CustomerManagementView;
    private View OrderManagementView;
    private View AccountManagementView;
    private TextView LoadProductListTextView;
    private static final int REQUEST_CODE_FILE_PICKER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_product_mainscreen_management);
        CategoryListView = findViewById(R.id.listview_category_product_management);

        //Get Categories
        CategoryRepository categoryRepository = new CategoryRepository();
        List<Category> categoriesList = categoryRepository.getCategories();
        Category[] categories = categoriesList.toArray(new Category[0]);

        // Set up Category ListView
        CategoryListView.setAdapter(new
                CategoryListViewAdapter(this, R.layout.list_item_category, categories));

        CategoryListView.setOnItemClickListener((parent, view, position, id) -> {
            // Intent to start products by category page
            Intent intent = new Intent(getBaseContext(), AdminProductsInCategoryManagement.class);
            intent.putExtra("clickedCategory", categories[position]);
            startActivity(intent);
        });

        LoadProductListTextView = findViewById(R.id.load_multiple_products_text);
        LoadProductListTextView.setOnClickListener(view -> {
            openCSVFilePicker();
        });

        CustomerManagementView = findViewById(R.id.product_customer_management);
        CustomerManagementView.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), AdminCustomerManagementActivity.class);
        });
        OrderManagementView = findViewById(R.id.product_order_management);
        OrderManagementView.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), AdminOrderManagementActivity.class);
        });
        AccountManagementView = findViewById(R.id.product_other_management);
        AccountManagementView.setOnClickListener(view -> {

        });
    }
    private void openCSVFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("csv"); // Specify the MIME type to restrict file selection to CSV files

        startActivityForResult(intent, REQUEST_CODE_FILE_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_FILE_PICKER && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    // Process the selected CSV file
                    readCsvFile(uri);
                }
            }
        }
    }

    private void readCsvFile(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Process each line of the CSV file
                String[] values = line.split(",");
                // Access the values array to get individual column values
                // ...
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}