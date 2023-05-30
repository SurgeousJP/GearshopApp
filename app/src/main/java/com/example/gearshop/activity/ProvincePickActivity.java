package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.gearshop.R;
import com.example.gearshop.adapter.ProvinceListAdapter;
import com.example.gearshop.database.GetProductDataFromAzure;
import com.example.gearshop.database.GetProvinceDataFromAzure;
import com.example.gearshop.model.Province;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProvincePickActivity extends AppCompatActivity {
    private ListView ProvinceListView;
    private ProvinceListAdapter ProvinceAdapter;
    private List<Province> provinceList;
    private final String GET_ALL_PROVINCES_SQL_STRING = "" +
            "SELECT * FROM province";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.province_picker);
        initializeProvinceList();
        ProvinceListView = findViewById(R.id.listview_choose_province);
        ProvinceAdapter = new ProvinceListAdapter(getApplicationContext(), R.layout.list_item_province, provinceList);
        ProvinceListView.setAdapter(ProvinceAdapter);
    }
    private void initializeProvinceList(){
        final GetProvinceDataFromAzure[] getProvinceDataFromAzure = new GetProvinceDataFromAzure[1];
        getProvinceDataFromAzure[0] = new GetProvinceDataFromAzure();
        getProvinceDataFromAzure[0].execute(GET_ALL_PROVINCES_SQL_STRING);

        System.out.println("Async Task running");
        try {
            getProvinceDataFromAzure[0].get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Async Task ended");

        if (getProvinceDataFromAzure[0].getProvinceList() != null)
            provinceList = getProvinceDataFromAzure[0].getProvinceList();
    }
}