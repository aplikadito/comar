/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.model;

import cl.rworks.comar.core.model.VentaUnidadEntity;
import cl.rworks.comar.core.util.UUIDUtils;

/**
 *
 * @author aplik
 */
public class ComarSellUnit {

    private VentaUnidadEntity entity;
    private ComarSell sell;
    private ComarProduct product;

    public ComarSellUnit(VentaUnidadEntity e, ComarSell sell, ComarProduct product) {
        this.entity = e;
        this.sell = sell;
        this.product = product;
    }

    public VentaUnidadEntity getEntity() {
        return entity;
    }

    public ComarSell getSell() {
        return sell;
    }

    public ComarProduct getProduct() {
        return product;
    }
    
    public void setEntity(VentaUnidadEntity entity) {
        this.entity = entity;
    }

    public String getId() {
        return UUIDUtils.toString(entity.getId());
    }

}
