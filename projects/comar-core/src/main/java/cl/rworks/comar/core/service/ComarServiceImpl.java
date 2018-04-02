/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service;

import cl.rworks.comar.core.data.ComarProductDb;
import cl.rworks.comar.core.data.ComarProductHistorialDb;
import io.permazen.Permazen;
import cl.rworks.comar.core.data.ComarSellDb;

/**
 *
 * @author rgonzalez
 */
public class ComarServiceImpl implements ComarService {

    private Permazen db;
    private Class[] modelClasses = new Class[]{
        ComarProductDb.class,
        ComarProductHistorialDb.class,
        ComarSellDb.class,
        };

    public ComarServiceImpl(int option) {
        switch (option) {
            case 0:
                db = KiteUtils.createPermazen(KiteUtils.createOnMemoryDatabase(), modelClasses);
                break;
            case 1:
                db = KiteUtils.createPermazen(KiteUtils.createDerbyDatabase("storage"), modelClasses);
                break;
            case 2:
                db = KiteUtils.createPermazen(KiteUtils.createMysqlDatabase(null), modelClasses);
                break;
            default:
                throw new RuntimeException("opcion no soportada: " + option);
        }
    }

    @Override
    public Permazen getDb() {
        return db;
    }

}
