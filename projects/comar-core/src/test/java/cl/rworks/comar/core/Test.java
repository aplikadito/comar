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
public class Test {

    public void test() {
        ComarProperties p = new ComarProperties();
    }

    public static void main(String[] args) {
        KiteDb db = new KiteDb(ComarProductKite.class, ComarStockKite.class, ComarSellKite.class);
        db.execute(jtx -> {
            ComarProductKite p = ComarProductKite.create();
            
            jtx.commit();
            return null;
        });
    }
}
