/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.bills;

import cl.rworks.comar.swing.main.ComarSystem;
import cl.rworks.comar.swing.model.ComarBillUnit;
import cl.rworks.comar.swing.model.ComarControllerException;
import cl.rworks.comar.swing.model.ComarProduct;
import cl.rworks.comar.swing.model.ComarWorkspace;

/**
 *
 * @author aplik
 */
public class ComarPanelBillInsertController {

    public ComarBillUnit createBillUnit(String code) throws ComarControllerException {
        ComarWorkspace ws = ComarSystem.getInstance().getWorkspace();
        ComarProduct p = ws.getProductByCode(code);
        if (p != null) {
            ComarBillUnit unit = new ComarBillUnit();
            unit.setProduct(p);
            return unit;
        } else {
            throw new ComarControllerException("Producto no encontrado: " + p);
        }
    }

}
