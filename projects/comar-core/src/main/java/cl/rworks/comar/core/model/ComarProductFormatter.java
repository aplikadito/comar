/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.model;

import cl.rworks.rservices.UUIDUtils;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *
 * @author aplik
 */
public class ComarProductFormatter {

    private DecimalFormat dfInt;
    private DecimalFormat dfDec;

    public ComarProductFormatter() {
        dfInt = new DecimalFormat("#");
        dfDec = new DecimalFormat("000");
    }

    public String format(ComarProduct product) {
        StringBuilder sb = new StringBuilder();
        sb.append(UUIDUtils.toString(product.getId())).append(";");
        sb.append(product.getCode()).append(";");
        sb.append(product.getDescription()).append(";");
        sb.append(product.getBuyPrice()).append(";");
        sb.append(product.getTax()).append(";");
        sb.append(product.getSellPrice()).append(";");
        sb.append(product.getStock()).append(";");
        sb.append(product.getMetric().getId());

        return sb.toString();
    }
}
