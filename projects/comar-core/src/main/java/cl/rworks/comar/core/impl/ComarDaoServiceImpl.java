/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.impl;

import cl.rworks.comar.core.service.ComarDaoException;
import cl.rworks.kite.KiteDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.permazen.JTransaction;
import io.permazen.ValidationMode;
import cl.rworks.comar.core.service.ComarDaoService;
import cl.rworks.comar.core.service.ComarDaoQuery;

/**
 *
 * @author rgonzalez
 */
public class ComarDaoServiceImpl implements ComarDaoService {

    private static final Logger LOG = LoggerFactory.getLogger(ComarDaoServiceImpl.class);

    private KiteDb database;
    //
    private final Class<?>[] classes = new Class<?>[]{
        ComarProductKite.class,
        ComarStockKite.class,
        ComarSellKite.class,
        ComarCategoryKite.class
    };
    //
//    private ComarDatabaseServiceProduct productService;
//    private ComarDatabaseServiceCategory categoryService;

    /**
     * Crea la base de datos en memoria
     */
    public ComarDaoServiceImpl() {
        this(null);
    }

    /**
     * Crea la base en disco dentro del directorio con el nombre 'name'
     *
     * @param name
     */
    public ComarDaoServiceImpl(String name) {
        this.database = name == null || name.isEmpty() ? new KiteDb(classes) : new KiteDb(name, classes);
//        this.productService = new ComarDatabaseServiceProductImpl(database);
//        this.categoryService = new ComarDatabaseServiceCategoryImpl(database);
    }

    public KiteDb getDatabase() {
        return database;
    }

//    @Override
//    public ComarDatabaseServiceProduct getServiceProduct() {
//        return productService;
//    }
//
//    @Override
//    public ComarDatabaseServiceCategory getServiceCategory() {
//        return categoryService;
//    }
    @Override
    public void openTransaction() {
        JTransaction jtx = database.get().createTransaction(true, ValidationMode.AUTOMATIC);
        JTransaction.setCurrent(jtx);
    }

    @Override
    public Object execute(ComarDaoQuery job) throws ComarDaoException {
        return job.execute();
    }

    @Override
    public void closeTransaction() {
        JTransaction.setCurrent(null);
    }

    @Override
    public void commit() {
        JTransaction jtx = JTransaction.getCurrent();
        jtx.commit();
    }

    @Override
    public void rollback() {
        JTransaction jtx = JTransaction.getCurrent();
        jtx.rollback();
    }

}
