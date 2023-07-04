package com.example.gearshop.activity.admin_activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gearshop.R;
import com.example.gearshop.adapter.CategoryListViewAdapter;
import com.example.gearshop.model.Category;
import com.example.gearshop.model.Product;
import com.example.gearshop.repository.CategoryRepository;
import com.example.gearshop.utility.ActivityStartManager;
import com.example.gearshop.utility.DatabaseHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
                    try {
                        InsertProductListOntoDatabase(readProductList(uri));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private void InsertProductListOntoDatabase(List<Product> products){
        for (Product product : products){
            DatabaseHelper.insertNewProductToAzure(product);
        }
        Toast.makeText(getBaseContext(), "Load sản phẩm mới thành công !", Toast.LENGTH_SHORT).show();
    }

    private List<Product> readProductList(Uri uri) throws IOException {
        List<Product> resultProductList = new ArrayList<>();
        int newProductID = generateNewProductId();
        InputStream inputStream = getContentResolver().openInputStream(uri);
        String[] productRows = readAllCSVLines(inputStream).split("\n;");
        for (String productRow : productRows){
            // A row contains: name, image_url, description, specs, price, status, category_id
            String[] productAttributes = productRow.split(";");

            String productName = productAttributes[0].trim();
            String imageURL = productAttributes[1].trim();
            String productDescription = productAttributes[2].replace("\"", "").trim();
            String productSpec = productAttributes[3].replace("\"", "").trim();
            double productPrice = Double.parseDouble(productAttributes[4].trim());
            int productStatus = Integer.parseInt(productAttributes[5].trim());
            int productCategoryID = Integer.parseInt(productAttributes[6].trim());

            Product product = new Product(newProductID++, productName, imageURL, productDescription, productSpec,
                    productPrice, productStatus, productCategoryID, null);
            resultProductList.add(product);
        }
        return resultProductList;
    }
    public int generateNewProductId(){
        return DatabaseHelper.getAdminProductListGivenID("").size() + 1;
    }

    public String readAllCSVLines(InputStream inputStream) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}