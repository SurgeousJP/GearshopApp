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
    private int ProvinceID;

    public GetProvinceDataFromAzure() {
        this.ProvinceList = new ArrayList<>();
    }
    public int getProvinceID() {
        return ProvinceID;
    }

    public void setProvinceID(int provinceID) {
        ProvinceID = provinceID;
    }
    public List<Province> getProvinceList(){
        return ProvinceList;
    }
    @Override
    protected ResultSet doInBackground(String... sqlCommand) {
        ResultSet resultSet = null;
        try {
            Connection connection = DriverManager.getConnection(AzureConnectionString);
            PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sqlCommand[0]);
            if (sqlCommand[0].contains("SELECT")){
                if (sqlCommand[0].contains("?") && getProvinceID()!= 0){
                    statement.setInt(1, getProvinceID());
                }
                resultSet = statement.executeQuery();
            }
            else{
                statement.execute(sqlCommand[0]);
            }
            while (true) {
                try {
                    assert resultSet != null;
                    if (!resultSet.next()) break;

                    Province newProvince = new Province(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getDouble("shipping_charge"));
                    ProvinceList.add(newProvince);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            statement.close();
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static double getShippingCharge(int provinceID){
        final GetProvinceDataFromAzure[] getProvinceDataFromAzure = new GetProvinceDataFromAzure[1];
        getProvinceDataFromAzure[0] = new GetProvinceDataFromAzure();
        getProvinceDataFromAzure[0].setProvinceID(provinceID);
        getProvinceDataFromAzure[0].execute(GET_PROVINCE_INFORMATION_SQL_STRING);

        System.out.println("Async Task running");
        try {
            getProvinceDataFromAzure[0].get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Async Task ended");

        if (getProvinceDataFromAzure[0].getProvinceList() != null &&
                getProvinceDataFromAzure[0].getProvinceList().size() > 0){
            Province province = getProvinceDataFromAzure[0].getProvinceList().get(0);
            return province.getShippingCharge();
        }
        return 0;
    }
}