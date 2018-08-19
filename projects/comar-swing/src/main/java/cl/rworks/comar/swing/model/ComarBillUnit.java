/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.model;

import cl.rworks.comar.core.model.FacturaUnidadEntity;
import cl.rworks.comar.core.model.impl.FacturaUnidadEntityImpl;
import cl.rworks.comar.core.util.UUIDUtils;
import java.math.BigDecimal;

/**
 *
 * @author aplik
 */
public class ComarBillUnit {

    private FacturaUnidadEntity entity;
    //
    private ComarBill bill;
    private ComarProduct product;

    public ComarBillUnit(FacturaUnidadEntity entity, ComarBill bill, ComarProduct product) {
        this.entity = entity;
        this.bill = bill;
        this.product = product;
    }

    public ComarBillUnit(ComarBill bill, ComarProduct product) {
        this.entity = new FacturaUnidadEntityImpl();
        this.entity.setIdProducto(product.getEntity().getId());
        this.entity.setPrecioNetoCompra(BigDecimal.ZERO);
        this.entity.setCantidad(BigDecimal.ZERO);

        this.bill = bill;
        this.product = product;
    }

    public FacturaUnidadEntity getEntity() {
        return entity;
    }

    public ComarBill getBill() {
        return bill;
    }

    public ComarProduct getProduct() {
        return product;
    }

    public void setProduct(ComarProduct product) {
        this.product = product;
    }

    public String getId() {
        return UUIDUtils.toString(entity.getId());
    }

}
