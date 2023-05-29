package com.example.gearshop.utility;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class VietnameseStringConverter {
    public static String convertToPlainString(String vietnameseString) {
        String normalizedString = Normalizer.normalize(vietnameseString, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizedString).replaceAll("").toLowerCase();
    }
}
