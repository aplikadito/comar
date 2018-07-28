/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.model;

import cl.rworks.comar.core.model.FacturaEntity;
import cl.rworks.comar.core.model.impl.FacturaEntityImpl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aplik
 */
public class ComarBill {

    private FacturaEntity entity;
    private List<ComarBillUnit> units = new ArrayList<>();

    public ComarBill() {
        this.entity = new FacturaEntityImpl();
        this.entity.setFecha(LocalDate.now());
        this.entity.setCodigo("");
    }

    public ComarBill(FacturaEntity entity) {
        this.entity = entity;
    }

    public FacturaEntity getEntity() {
        return entity;
    }

    public void addUnit(ComarBillUnit unit) {
        units.add(unit);
    }

    public List<ComarBillUnit> getUnits() {
        return units;
    }

    public void deleteUnits(List<ComarBillUnit> dunits) {
        this.units.removeAll(dunits);
    }

}
