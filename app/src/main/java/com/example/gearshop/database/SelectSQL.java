package com.example.gearshop.database;

import java.sql.ResultSet;

public class SelectSQL extends AzureSQLDatabase{
    private ResultSet SQLDataResult;

    public ResultSet getSQLDataResult(){
        return SQLDataResult;
    }

    @Override
    protected void onPostExecute(ResultSet result) {
        SQLDataResult = result;
    }
}
