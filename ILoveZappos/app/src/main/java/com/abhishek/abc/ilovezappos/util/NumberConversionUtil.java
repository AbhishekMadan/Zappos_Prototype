package com.abhishek.abc.ilovezappos.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Created by abc on 06-Feb-17.
 */

public class NumberConversionUtil {

    public static double getCurrencyFromString(String s) throws ParseException {
        return Double.parseDouble(s.substring(1));
    }

    public static double getPercentFractionFromString(String s) {
        return Double.parseDouble(s.substring(0,s.length()-1));
    }

    public static String setPrecision(double d) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(d);
    }
}
