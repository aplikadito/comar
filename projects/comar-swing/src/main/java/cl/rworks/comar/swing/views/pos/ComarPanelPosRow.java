/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.pos;

import cl.rworks.comar.core.model.Metrica;
import cl.rworks.comar.swing.model.ComarProduct;
import java.math.BigDecimal;

/**
 *
 * @author aplik
 */
public class ComarPanelPosRow {

    private ComarProduct product;
    private BigDecimal price;
    private BigDecimal count;

    public ComarPanelPosRow(ComarProduct product) {
        this.product = product;
    }

     public ComarProduct getProduct() {
        return product;
    }
    
    public String getCode() {
        return product.getEntity().getCodigo();
    }

    public String getDescription() {
        return product.getEntity().getDescripcion();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public boolean isFixedPrice() {
        return product.getEntity().isPrecioVentaFijo();
    }

    public BigDecimal getSubtotal() {
        return price.multiply(count);
    }

    public Metrica getMetric() {
        return product.getEntity().getMetrica();
    }

    public boolean isIncludeOnSell() {
        return product.getEntity().isIncluirEnBoleta();
    }

   

}
