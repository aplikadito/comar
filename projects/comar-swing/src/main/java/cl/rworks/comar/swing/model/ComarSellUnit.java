/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.model;

import cl.rworks.comar.core.model.VentaUnidadEntity;

/**
 *
 * @author aplik
 */
public class ComarSellUnit {

    private VentaUnidadEntity entity;
    
    public ComarSellUnit(VentaUnidadEntity e) {
        this.entity = e;
    }

    public VentaUnidadEntity getEntity() {
        return entity;
    }

    public void setEntity(VentaUnidadEntity entity) {
        this.entity = entity;
    }
    
}
