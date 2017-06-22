/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsimpledb.JTransaction;

/**
 *
 * @author rgonzalez
 */
public class ComarContext {

    private static final Logger LOG = LogManager.getLogger(ComarContext.class);
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
        database.execute(new ComarDatabaseTask() {
            @Override
            public Object execute(JTransaction jtx) {
                ComarCategory cat = ComarCategory.getByName("Varios");
                if (cat == null) {
                    LOG.info("Creando categoria 'Varios' ...");

                    ComarCategory category = ComarCategory.create();
                    category.setName("Varios");
                    jtx.commit();
                } else {
                    jtx.rollback();
                }
                return null;
            }
        });
    }

    public ComarProperties getProperties() {
        return properties;
    }

    public ComarDatabase getDatabase() {
        return database;
    }

}
