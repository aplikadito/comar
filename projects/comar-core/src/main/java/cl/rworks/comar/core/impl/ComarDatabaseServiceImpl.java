/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.impl;

import cl.rworks.kite.KiteDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cl.rworks.comar.core.service.ComarDatabaseService;
import cl.rworks.comar.core.service.ComarDatabaseServiceProduct;
import cl.rworks.comar.core.service.ComarDatabaseServiceCategory;

/**
 *
 * @author rgonzalez
 */
public class ComarDatabaseServiceImpl implements ComarDatabaseService {

    private static final Logger LOG = LoggerFactory.getLogger(ComarDatabaseServiceImpl.class);

    private KiteDb database;
    //
    private final Class<?>[] classes = new Class<?>[]{
        ComarProductKite.class,
        ComarStockKite.class,
        ComarSellKite.class,
        ComarCategoryKite.class
    };
    //
    private ComarDatabaseServiceProduct productService;
    private ComarDatabaseServiceCategory categoryService;

    /**
     * Crea la base de datos en memoria
     */
    public ComarDatabaseServiceImpl() {
        this(null);
    }

    /**
     * Crea la base en disco dentro del directorio con el nombre 'name'
     *
     * @param name
     */
    public ComarDatabaseServiceImpl(String name) {
        this.database = name == null || name.isEmpty() ? new KiteDb(classes) : new KiteDb(name, classes);
        this.productService = new ComarDatabaseServiceProductImpl(database);
        this.categoryService = new ComarDatabaseServiceCategoryImpl(database);
    }

    public KiteDb getDatabase() {
        return database;
    }

    @Override
    public ComarDatabaseServiceProduct getServiceProduct() {
        return productService;
    }

    @Override
    public ComarDatabaseServiceCategory getServiceCategory() {
        return categoryService;
    }

}
