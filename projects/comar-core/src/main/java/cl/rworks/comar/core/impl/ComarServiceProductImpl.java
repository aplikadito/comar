/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.impl;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.service.ComarServiceProduct;
import cl.rworks.comar.core.model.ComarDecimalFormat;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.model.ComarUnit;
import cl.rworks.kite.KiteDb;
import cl.rworks.kite.KiteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NavigableSet;

/**
 *
 * @author aplik
 */
public class ComarServiceProductImpl implements ComarServiceProduct {

    private KiteDb database;

    public ComarServiceProductImpl(KiteDb database) {
        this.database = database;
    }

    @Override
    public ComarProduct create() throws ComarServiceException {
        try {
            return (ComarProduct) database.execute(jtx -> {
                ComarProductKite pp = (ComarProductKite) ComarProductKite.create().copyOut();
                pp.setUnit(ComarUnit.UNIDAD);
                pp.setDecimalFormat(ComarDecimalFormat.ZERO);
                jtx.rollback();
                return pp;
            });
        } catch (KiteException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    @Override
    public void insert(ComarProduct product) throws ComarServiceException {
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

                ComarProductKite pp = ComarProductKite.insert(product);
                jtx.commit();
                return null;
            });
        } catch (KiteException ex) {
            throw new ComarServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public void update(ComarProduct product) throws ComarServiceException {
        try {
            if (product == null) {
                throw new ComarServiceException("Producto nulo");
            }

            database.execute(jtx -> {
                ComarProductKite pp = ComarProductKite.get(product.getId());
                if (pp != null) {
                    ComarProductKite.update(pp, product);
                    jtx.commit();
                } else {
                    jtx.rollback();
                    throw new KiteException("Producto no encontrado: " + product.getCode());
                }

                return null;
            });
        } catch (KiteException ex) {
            throw new ComarServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public ComarProduct get(Long id) throws ComarServiceException {
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
    public ComarProduct getByCode(String code) throws ComarServiceException {
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
    public List<ComarProduct> getAll() throws ComarServiceException {
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
    public void delete(final ComarProduct p) throws ComarServiceException {
        try {
            database.execute(jtx -> {
                String code = p.getCode();
                ComarProductKite.delete(p);
                p.setId(null);
                jtx.commit();
                return null;
            });
        } catch (KiteException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    @Override
    public List<ComarProduct> search(final String text) throws ComarServiceException {
        if (text == null || text.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        try {
            return (List<ComarProduct>) database.execute(jtx -> {
                NavigableSet<ComarProductKite> products = ComarProductKite.search(text);
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
    public boolean existsCode(final String code) throws ComarServiceException {
        try {
            return (Boolean) database.execute(jtx -> {
                boolean response = ComarProductKite.existsCode(code);
                jtx.rollback();
                return response;
            });
        } catch (KiteException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }
}
