/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import cl.rworks.kite.KiteDb;
import cl.rworks.kite.KiteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NavigableSet;
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

    public ComarServiceImpl() {
        this("storage");
    }

    public ComarServiceImpl(String name) {
        this.database = name == null || name.isEmpty() ? new KiteDb(classes) : new KiteDb(name, classes);
    }

    public KiteDb getDatabase() {
        return database;
    }

    @Override
    public ComarProduct createProduct() throws ComarServiceException {
        try {
            return (ComarProduct) database.execute(jtx -> {
                ComarProductKite pp = (ComarProductKite) ComarProductKite.create().copyOut();
                jtx.rollback();
                return pp;
            });
        } catch (KiteException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    @Override
    public void insertProduct(ComarProduct product) throws ComarServiceException {
        try {
            if (product == null) {
                throw new ComarServiceException("Producto nulo");
            }

            String code = product.getCode();
            if (code == null || code.isEmpty()) {
                throw new ComarServiceException("Codigo nulo o vacio");
            }

            database.execute(jtx -> {
                if (ComarProductKite.getByCode(code) != null) {
                    throw new KiteException("El codigo ya existe: " + product.getCode());
                }
                ComarProductKite.insert(product);
                jtx.commit();
                return null;
            });
        } catch (KiteException ex) {
            throw new ComarServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public ComarProduct getProduct(Long id) throws ComarServiceException {
        try {
            if (id == null) {
                return null;
            }

            return (ComarProduct) database.execute(jtx -> {
                ComarProductKite p = ComarProductKite.get(id);
                ComarProductKite pp = p != null ? (ComarProductKite) p.copyOut() : null;
                return pp;
            });
        } catch (KiteException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    @Override
    public ComarProduct getByCodeProduct(String code) throws ComarServiceException {
        try {
            return (ComarProduct) database.execute(jtx -> {
                ComarProductKite p = ComarProductKite.getByCode(code);
                ComarProductKite pp = p != null ? (ComarProductKite) p.copyOut() : null;
                jtx.rollback();
                return pp;
            });
        } catch (KiteException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    @Override
    public List<ComarProduct> getAllProducts() throws ComarServiceException {
        try {
            return (List<ComarProduct>) database.execute(jtx -> {
                NavigableSet<ComarProductKite> products = ComarProductKite.getAll();
                List<ComarProduct> list = new ArrayList<>();
                for (ComarProductKite p : products) {
                    ComarProductKite pp = (ComarProductKite) p.copyOut();
                    list.add(pp);
                }

                jtx.rollback();
                return list;
            });
        } catch (KiteException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    @Override
    public List<ComarProduct> search(final String text) throws ComarServiceException {
        if(text == null || text.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        
        try {
            return (List<ComarProduct>) database.execute(jtx -> {
                NavigableSet<ComarProduct> products = ComarProductKite.search(text);
                List<ComarProduct> list = new ArrayList<>();
                for (ComarProduct aux : products) {
                    ComarProductKite p = (ComarProductKite) aux;
                    ComarProductKite pp = (ComarProductKite) p.copyOut();
                    list.add(pp);
                }

                jtx.rollback();
                return list;
            });
        } catch (KiteException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

}
