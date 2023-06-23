package com.example.gearshop.database;

public class GetAdminProductDataFromAzure extends GetProductDataFromAzure{

    @Override
    public boolean isIgnoreProduct(int status) {
        return status == 0;
    }
}
