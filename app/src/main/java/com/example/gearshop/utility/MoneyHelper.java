package com.example.gearshop.utility;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoneyHelper {
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

    public static double extractVietnameseMoneyFromString(String input){
        // Define the regular expression pattern to match Vietnamese money format
        Pattern pattern = Pattern.compile("(\\d+[,.]?)+");

        // Find the matching pattern in the input string
        Matcher matcher = pattern.matcher(input);

        // Extract the numerical value from the matched pattern
        if (matcher.find()) {
            String moneyString = matcher.group();

            // Remove the comma separators in the Vietnamese money format
            moneyString = moneyString.replaceAll("[,.]", "");

            // Convert the extracted string to a double
            double money = Double.parseDouble(moneyString);

            System.out.println("Extracted money: " + money);

            return money;
        }
        // Conversion failed return value
        return -1;
    }

}
