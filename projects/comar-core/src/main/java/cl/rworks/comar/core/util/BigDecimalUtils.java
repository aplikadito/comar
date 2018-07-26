/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 *
 * @author aplik
 */
public class BigDecimalUtils {

    public static BigDecimal IVA = new BigDecimal(0.19);
    public static BigDecimal MIL = new BigDecimal(1000);
    public static MathContext MC = new MathContext(3, RoundingMode.HALF_UP);

//    public static BigDecimal create(double value) {
//        return new BigDecimal(value, MC);
//    }

    public static long toLong(BigDecimal bd) {
        return bd.multiply(MIL).longValue();
    }

    public static BigDecimal toBigDecimal(long buyprice) {
        return new BigDecimal(buyprice).divide(MIL, MC);
    }
}
