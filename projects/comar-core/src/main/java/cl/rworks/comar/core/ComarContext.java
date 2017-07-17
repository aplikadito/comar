/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import org.jsimpledb.JSimpleDB;
import org.jsimpledb.JTransaction;
import org.jsimpledb.ValidationMode;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rgonzalez
 */
public class ComarContext {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ComarDatabase.class);
    private static ComarContext instance;
    //
    private ComarProperties properties;
    private ComarDatabase database;

    public static ComarContext getInstance() {
        instance = instance == null ? new ComarContext() : instance;
        return instance;
    }

    private ComarContext() {
        properties = new ComarProperties();
        database = new ComarDatabase();
        init();
    }

    private void init() {
        JSimpleDB db = database.get();
        JTransaction jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
        JTransaction.setCurrent(jtx);
        try {
            ComarCategory cat = ComarCategory.getVarios();
            if (cat == null) {
                LOG.info("Creando categoria 'Varios' ...");
                ComarCategory.createVarios();
            }
            
            jtx.commit();
        } finally {
            JTransaction.setCurrent(null);
        }

    }

    public ComarProperties getProperties() {
        return properties;
    }

    public ComarDatabase getDatabase() {
        return database;
    }

}
