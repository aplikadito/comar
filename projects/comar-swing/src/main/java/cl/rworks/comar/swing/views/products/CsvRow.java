/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.products;

import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.swing.model.ComarCategory;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author aplik
 */
public class CsvRow {

    private String code;
    private String description;
    private BigDecimal precio;
    private String categoria;

    public CsvRow(String code, String description) {
        this.code = code;
        this.description = description;
        this.precio = new BigDecimal(BigInteger.ZERO);
        this.categoria = "Varios";
    }

    public CsvRow(String code, String description, BigDecimal precio, String categoria) {
        this.code = code;
        this.description = description;
        this.precio = precio;
        this.categoria = categoria;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }

}
