/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import cl.rworks.kite.KiteDb;
import java.util.List;
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

    private ComarProductDao daoProduct;
    private ComarCategoryDao daoCategory;

    public ComarServiceImpl() {
        this("storage");
    }

    public ComarServiceImpl(String name) {
        this.database = name == null || name.isEmpty() ? new KiteDb(classes) : new KiteDb(name, classes);
        this.daoProduct = new ComarProductDao(database);
        this.daoCategory = new ComarCategoryDao(database);
    }

    public KiteDb getDatabase() {
        return database;
    }

    @Override
    public ComarProductDao getProductDao() {
        return daoProduct;
    }

    @Override
    public ComarCategoryDao getCategoryDao() {
        return daoCategory;
    }

    @Override
    public ComarProduct createProduct() throws ComarServiceException {
        return daoProduct.create();
    }

    @Override
    public void insertProduct(ComarProduct product) throws ComarServiceException {
        if (product == null) {
            throw new ComarServiceException("Producto nulo");
        }

        String code = product.getCode();
        if (code == null || code.isEmpty()) {
            throw new ComarServiceException("Codigo nulo o vacio");
        }

        if (daoProduct.getByCode(code) != null) {
            throw new ComarServiceException("El codigo ya existe: " + product.getCode());
        }

        daoProduct.insert(product);
    }

    @Override
    public ComarProduct getProduct(Long id) throws ComarServiceException {
        return daoProduct.get(id);
    }

    @Override
    public ComarProduct getByCodeProduct(String code) throws ComarServiceException {
        return daoProduct.getByCode(code);
    }

    @Override
    public List<ComarProduct> getAllProducts() throws ComarServiceException {
        return daoProduct.getAll();
    }

}
