package me.aqua.currency.utils;

import java.text.DecimalFormat;

public class Formatter {

    private static final DecimalFormat df = new DecimalFormat("#.###");
    private static final DecimalFormat pf = new DecimalFormat("#0.0");
    private static final char[] suffix = {' ', 'k', 'm', 'b', 't', 'q', 'Q', 's', 'S', 'O', 'N', 'D', 'U'};

    public static String formatter(Number number) {
        double numValue = number.doubleValue();
        int value = (int) Math.floor(Math.log10(numValue));
        int base = value / 3;
        if (value >= 3 && base < suffix.length) {
            return pf.format(numValue / Math.pow(10, base * 3)) + suffix[base];
        } else {
            return df.format(numValue);
        }
    }

    public static String formatFully(Number number) {
        return df.format(number);
    }
}
