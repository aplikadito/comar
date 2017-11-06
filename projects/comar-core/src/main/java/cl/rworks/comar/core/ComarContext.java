/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import cl.rworks.kite.KiteDb;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rgonzalez
 */
public class ComarContext {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ComarContext.class);
    private static ComarContext instance;
    //
    private ComarProperties properties;
    private KiteDb database;
    private ComarService service;

    public static ComarContext getInstance() {
        instance = instance == null ? new ComarContext() : instance;
        return instance;
    }

    private ComarContext() {
        properties = new ComarProperties();
        database = new KiteDb("storage", ComarProductKite.class, ComarStockKite.class, ComarSellKite.class);
        service = new ComarServiceImpl(database);
        init();
    }

    private void init() {
    }

    public ComarProperties getProperties() {
        return properties;
    }

    public KiteDb getDatabase() {
        return database;
    }

}
