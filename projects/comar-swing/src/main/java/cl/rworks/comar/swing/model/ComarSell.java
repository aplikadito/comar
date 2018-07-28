/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.model;

import cl.rworks.comar.core.model.VentaEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aplik
 */
public class ComarSell {

    private VentaEntity entity;
    private List<ComarSellUnit> units = new ArrayList<>();

    public ComarSell(VentaEntity e) {
        this.entity = e;
    }

    public VentaEntity getEntity() {
        return entity;
    }

    public void setEntity(VentaEntity entity) {
        this.entity = entity;
    }

    public void addUnit(ComarSellUnit unit) {
        this.units.add(unit);
    }

}
