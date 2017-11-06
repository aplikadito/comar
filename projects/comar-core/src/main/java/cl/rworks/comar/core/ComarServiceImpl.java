/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import cl.rworks.kite.KiteDb;

/**
 *
 * @author rgonzalez
 */
public class ComarServiceImpl implements ComarService {

    private KiteDb database;

    public ComarServiceImpl(KiteDb database) {
        this.database = database;
    }

    public ComarProduct createProduct() {
        Object response = database.executeSnap(jtx -> {
            return ComarProductKite.create();
        });
        return (ComarProduct) response;
    }

    public void insertProduct(final ComarProduct p) {
        database.execute(jtx -> {
            ComarProduct pp = ComarProductKite.create();
            pp.setCode(p.getCode());
            pp.setName(p.getName());
            pp.setUnit(p.getUnit());
            pp.setDecimalFormat(p.getDecimalFormat());

            jtx.commit();
            return null;
        });
    }

}
