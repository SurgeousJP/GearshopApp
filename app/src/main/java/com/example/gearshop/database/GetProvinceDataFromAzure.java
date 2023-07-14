package com.example.gearshop.database;

import com.example.gearshop.model.Province;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GetProvinceDataFromAzure extends AzureSQLDatabase{
    private static final String GET_PROVINCE_INFORMATION_SQL_STRING = "" +
            "SELECT * FROM province WHERE id = ?";
    private List<Province> ProvinceList;
    public List<Province> getProvinceList(){
        return ProvinceList;
    }

    public GetProvinceDataFromAzure() {
        this.ProvinceList = new ArrayList<>();
    }
    @Override
    protected void handleResultSetItem(ResultSet resultSet){
        try{
            Province newProvince = new Province(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getDouble("shipping_charge"));
            ProvinceList.add(newProvince);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static double getShippingCharge(int provinceID){
        final GetProvinceDataFromAzure[] getProvinceDataFromAzure = new GetProvinceDataFromAzure[1];
        getProvinceDataFromAzure[0] = new GetProvinceDataFromAzure();
        String newSQLCommand =
                GET_PROVINCE_INFORMATION_SQL_STRING.substring(0, GET_PROVINCE_INFORMATION_SQL_STRING.length() - 1);
        newSQLCommand = newSQLCommand + "'" + provinceID + "'";
        getProvinceDataFromAzure[0].execute(newSQLCommand);

        System.out.println("Async Task get ShippingCharge running");
        try {
            getProvinceDataFromAzure[0].get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Async Task getShippingCharge ended");

        if (getProvinceDataFromAzure[0].getProvinceList() != null &&
                getProvinceDataFromAzure[0].getProvinceList().size() > 0){
            Province province = getProvinceDataFromAzure[0].getProvinceList().get(0);
            return province.getShippingCharge();
        }
        return 0;
    }
}