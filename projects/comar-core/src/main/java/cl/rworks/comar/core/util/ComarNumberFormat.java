/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

/**
 *
 * @author aplik
 */
public final class ComarNumberFormat {

    private static final DecimalFormat DEFAULT_DECIMAL_FORMAT;

    static {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');

        DEFAULT_DECIMAL_FORMAT = new DecimalFormat("#0.000");
        DEFAULT_DECIMAL_FORMAT.setDecimalFormatSymbols(dfs);
        DEFAULT_DECIMAL_FORMAT.setGroupingUsed(false);
    }

    private ComarNumberFormat() {
    }

    public static String format(BigDecimal bd) {
        return bd != null ? DEFAULT_DECIMAL_FORMAT.format(bd) : "";
    }

    public static BigDecimal parse(String strValue) throws ParseException {
        return new BigDecimal(DEFAULT_DECIMAL_FORMAT.parse(strValue).doubleValue(), BigDecimalUtils.MC);
    }

    public static String formatDbl(double dblValue) {
        return DEFAULT_DECIMAL_FORMAT.format(dblValue);
    }

    public static double parseDbl(String strValue) throws ParseException {
        return DEFAULT_DECIMAL_FORMAT.parse(strValue).doubleValue();
    }
}
