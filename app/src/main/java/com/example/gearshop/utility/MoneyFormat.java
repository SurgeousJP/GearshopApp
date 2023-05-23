package com.example.gearshop.utility;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MoneyFormat {
    public static String getVietnameseMoneyStringFormatted(double money){
        Locale locale = new Locale("vi", "VN");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);

        symbols.setCurrencySymbol("đ");
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');

        DecimalFormat decimalFormatter = new DecimalFormat("#,##0.00 ¤", symbols);
        return decimalFormatter.format(money);
    }
    public static double getVietnameseMoneyDouble(String formattedMoney){
        String numericMoney = formattedMoney.replace("đ", "").replace(",", "");
        numericMoney = numericMoney.replace(".", "");
        return Double.parseDouble(numericMoney);
    }
}