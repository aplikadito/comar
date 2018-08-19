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
    private static final DecimalFormat DEFAULT_DECIMAL_FORMAT_PERCENTUAL;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        String pattern = "#,##0.###";

        DEFAULT_DECIMAL_FORMAT = new DecimalFormat(pattern, symbols);
        DEFAULT_DECIMAL_FORMAT.setParseBigDecimal(true);

        DecimalFormatSymbols symbolsPercentual = new DecimalFormatSymbols();
        String patternPercentual = "#,##0.###%";

        DEFAULT_DECIMAL_FORMAT_PERCENTUAL = new DecimalFormat(patternPercentual, symbolsPercentual);
        DEFAULT_DECIMAL_FORMAT_PERCENTUAL.setParseBigDecimal(true);
    }

    private ComarNumberFormat() {
    }

    public static String format(BigDecimal bd) {
        return bd != null ? DEFAULT_DECIMAL_FORMAT.format(bd) : "";
    }

    public static String formatPercentual(BigDecimal bd) {
        return bd != null ? DEFAULT_DECIMAL_FORMAT_PERCENTUAL.format(bd) : "";
    }

    public static BigDecimal parse(String strValue) throws ParseException {
        return (BigDecimal) DEFAULT_DECIMAL_FORMAT.parse(strValue);
    }

    public static BigDecimal parsePercentual(String strValue) throws ParseException {
        return (BigDecimal) DEFAULT_DECIMAL_FORMAT_PERCENTUAL.parse(strValue);
    }

    public static String formatDbl(double dblValue) {
        return DEFAULT_DECIMAL_FORMAT.format(dblValue);
    }

    public static double parseDbl(String strValue) throws ParseException {
        return DEFAULT_DECIMAL_FORMAT.parse(strValue).doubleValue();
    }
}
