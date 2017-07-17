/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *
 * @author rgonzalez
 */
public enum ComarDecimalFormat {

    ZERO("Sin decimales", "#"),
    ONE("Un decimales", "#.0"),
    TWO("Dos decimales", "#.00"),
    THREE("Tres decimales", "#.000"),
    FOUR("Cuatro decimales", "#.0000"),
    FIVE("Cinco decimales", "#.00000"),
    SIX("Seis decimales", "#.000000");

    private final String name;
    private final String pattern;
    private final DecimalFormat df;

    ComarDecimalFormat(String name, String pattern) {
        this.name = name;
        this.pattern = pattern;
        this.df = new DecimalFormat(pattern);
        this.df.setRoundingMode(RoundingMode.HALF_UP);
    }

    public String getName() {
        return name;
    }

    public String getPattern() {
        return pattern;
    }

    public DecimalFormat getDf() {
        return df;
    }


}
