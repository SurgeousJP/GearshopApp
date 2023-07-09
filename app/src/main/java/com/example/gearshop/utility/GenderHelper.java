package com.example.gearshop.utility;

public class GenderHelper {
    public static String getDatabaseGenderValueFromUI(String UIGender){
        if (UIGender.contains("am")){
            return "male";
        }

        return "female";
    }

    public static String getUIGenderValueFromDatabase(String DatabaseGender){
        if (DatabaseGender.equals("male"))
            return "Nam";

        return "Nữ";
    }
}
