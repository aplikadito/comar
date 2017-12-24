/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.impl;

import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.core.service.ComarServiceProduct;
import cl.rworks.kite.KiteDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rgonzalez
 */
public class ComarServiceImpl implements ComarService {

    private static final Logger LOG = LoggerFactory.getLogger(ComarServiceImpl.class);

    private KiteDb database;
    //
    private final Class<?>[] classes = new Class<?>[]{
        ComarProductKite.class,
        ComarStockKite.class,
        ComarSellKite.class,
        ComarCategoryKite.class
    };
    //
    private ComarServiceProduct productService;

    public ComarServiceImpl() {
        this(null);
    }

    public ComarServiceImpl(String name) {
        this.database = name == null || name.isEmpty() ? new KiteDb(classes) : new KiteDb(name, classes);
        this.productService = new ComarServiceProductImpl(database);
    }

    public KiteDb getDatabase() {
        return database;
    }

    @Override
    public ComarServiceProduct getServiceProduct() {
        return productService;
    }

}
