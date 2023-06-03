package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.gearshop.R;
import com.example.gearshop.adapter.ProvinceListAdapter;
import com.example.gearshop.database.GetProductDataFromAzure;
import com.example.gearshop.database.GetProvinceDataFromAzure;
import com.example.gearshop.model.Province;
import com.example.gearshop.utility.DatabaseHelper;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProvincePickActivity extends AppCompatActivity {
    private ListView ProvinceListView;
    private ProvinceListAdapter ProvinceAdapter;
    private List<Province> provinceList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.province_picker);
        provinceList = DatabaseHelper.getProvinceList();
        ProvinceListView = findViewById(R.id.listview_choose_province);
        ProvinceAdapter = new ProvinceListAdapter(getApplicationContext(), R.layout.list_item_province, provinceList);
        ProvinceListView.setAdapter(ProvinceAdapter);
        ProvinceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Province pickedProvince = (Province) adapterView.getItemAtPosition(i);
                Intent pickProvinceIntent = new Intent();
                pickProvinceIntent.putExtra("pickedProvince", pickedProvince);
                setResult(RESULT_OK, pickProvinceIntent);
                finish();
            }
        });
    }

}