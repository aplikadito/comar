/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service;

import cl.rworks.comar.core.data.ComarCategoryKite;
import cl.rworks.comar.core.data.ComarProductKite;
import cl.rworks.comar.core.data.ComarSellKite;
import cl.rworks.comar.core.data.ComarStockKite;
import cl.rworks.kite.KiteDb;
import cl.rworks.kite.KiteDbDisk;
import cl.rworks.kite.KiteDbMemory;
import cl.rworks.kite.KiteDbMysql;

/**
 *
 * @author rgonzalez
 */
public class ComarServiceImpl implements ComarService {

    private KiteDb kitedb;
    private Class[] modelClasses = new Class[]{
        ComarProductKite.class,
        ComarCategoryKite.class,
        ComarSellKite.class,
        ComarStockKite.class,};

    public ComarServiceImpl(int option) {
        switch (option) {
            case 0:
                kitedb = new KiteDbMemory(modelClasses);
                break;
            case 1:
                kitedb = new KiteDbDisk("storage", modelClasses);
                break;
            case 2:
                kitedb = new KiteDbMysql(null, modelClasses);
                break;
            default:
                throw new RuntimeException("opcion no soportada: " + option);
        }
    }

    @Override
    public KiteDb getKitedb() {
        return kitedb;
    }

}
