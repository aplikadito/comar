/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 *
 * @author aplik
 */
public class TestFast {

    public static void main(String[] args) {
        MathContext MATH_CTX = new MathContext(3, RoundingMode.HALF_UP);
        double tercio = 1.0 / 3.0;
        System.out.println("tercio: " + tercio);

        String str = Double.toString(tercio);
        System.out.println("str: " + str);
        BigDecimal bd = new BigDecimal(str, MATH_CTX);
        System.out.println(bd.toString());

    }
}
