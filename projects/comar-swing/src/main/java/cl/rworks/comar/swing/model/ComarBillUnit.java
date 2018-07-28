/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.model;

import cl.rworks.comar.core.model.FacturaUnidadEntity;
import cl.rworks.comar.core.model.impl.FacturaUnidadEntityImpl;
import java.math.BigDecimal;

/**
 *
 * @author aplik
 */
public class ComarBillUnit {

    private FacturaUnidadEntity entity;

    public ComarBillUnit() {
        this.entity = new FacturaUnidadEntityImpl();
    }

    public ComarBillUnit(FacturaUnidadEntity entity) {
        this.entity = entity;
    }

    public ComarBillUnit(ComarProduct product) {
        this.entity = new FacturaUnidadEntityImpl();
        this.entity.setIdProducto(product.getEntity().getId());
        this.entity.setCodigoProducto(product.getEntity().getCodigo());
        this.entity.setDescripcionProducto(product.getEntity().getDescripcion());
        this.entity.setPrecioCompra(BigDecimal.ZERO);
        this.entity.setCantidad(BigDecimal.ZERO);
    }

    public FacturaUnidadEntity getEntity() {
        return entity;
    }

    public void updateBuyPrice(BigDecimal buyPrice) {
        this.entity.setPrecioCompra(buyPrice);
    }

    public void updateQuantity(BigDecimal quantity) {
        this.entity.setCantidad(quantity);
    }

}
