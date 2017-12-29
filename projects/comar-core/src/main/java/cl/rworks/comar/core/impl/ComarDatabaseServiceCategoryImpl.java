/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.impl;

import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.service.ComarDatabaseServiceException;
import cl.rworks.kite.KiteDb;
import cl.rworks.kite.KiteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NavigableSet;
import cl.rworks.comar.core.service.ComarDatabaseServiceCategory;

/**
 *
 * @author aplik
 */
public class ComarDatabaseServiceCategoryImpl implements ComarDatabaseServiceCategory {

    private KiteDb database;

    public ComarDatabaseServiceCategoryImpl(KiteDb database) {
        this.database = database;
    }

    @Override
    public ComarCategory create(String name) throws ComarDatabaseServiceException {
        try {
            return (ComarCategory) database.execute(jtx -> {
                ComarCategoryKite c = (ComarCategoryKite) ComarCategoryKite.create(name).copyOut();
                jtx.rollback();
                return c;
            });
        } catch (KiteException ex) {
            throw new ComarDatabaseServiceException("Error", ex);
        }
    }

    @Override
    public void insert(ComarCategory c) throws ComarDatabaseServiceException {
        try {
            if (c == null) {
                throw new ComarDatabaseServiceException("Categoria nula");
            }

            String name = c.getName();
            if (name == null || name.isEmpty()) {
                throw new ComarDatabaseServiceException("Nombre nulo o vacio");
            }

            database.execute(jtx -> {
                if (ComarCategoryKite.getByName(name) != null) {
                    throw new KiteException("El nombre ya existe: " + c.getName());
                }

                ComarCategoryKite.insert(c);
                jtx.commit();
                return null;
            });
        } catch (KiteException ex) {
            throw new ComarDatabaseServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public void update(ComarCategory c) throws ComarDatabaseServiceException {
        try {
            if (c == null) {
                throw new ComarDatabaseServiceException("Producto nulo");
            }

            database.execute(jtx -> {
                ComarCategoryKite cc = ComarCategoryKite.get(c.getId());
                if (cc != null) {
                    ComarCategoryKite.update(cc, c);
                    jtx.commit();
                } else {
                    jtx.rollback();
                    throw new KiteException("Producto no encontrado: " + c.getName());
                }

                return null;
            });
        } catch (KiteException ex) {
            throw new ComarDatabaseServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public ComarCategory get(Long id) throws ComarDatabaseServiceException {
        try {
            if (id == null) {
                return null;
            }

            return (ComarCategory) database.execute(jtx -> {
                ComarCategoryKite pp = ComarCategoryKite.get(id);
                ComarCategoryKite p = pp != null ? (ComarCategoryKite) pp.copyOut() : null;
                return p;
            });
        } catch (KiteException ex) {
            throw new ComarDatabaseServiceException("Error", ex);
        }
    }

    @Override
    public ComarCategory getByName(String name) throws ComarDatabaseServiceException {
        try {
            return (ComarCategory) database.execute(jtx -> {
                ComarCategoryKite pp = ComarCategoryKite.getByName(name);
                ComarCategoryKite p = pp != null ? (ComarCategoryKite) pp.copyOut() : null;
                jtx.rollback();
                return pp;
            });
        } catch (KiteException ex) {
            throw new ComarDatabaseServiceException("Error", ex);
        }
    }

    @Override
    public List<ComarCategory> getAll() throws ComarDatabaseServiceException {
        try {
            return (List<ComarCategory>) database.execute(jtx -> {
                NavigableSet<ComarCategoryKite> categories = ComarCategoryKite.getAll();
                List<ComarCategory> list = new ArrayList<>();
                for (ComarCategoryKite cc : categories) {
                    ComarCategoryKite c = (ComarCategoryKite) cc.copyOut();
                    list.add(c);
                }

                jtx.rollback();
                return list;
            });
        } catch (KiteException ex) {
            throw new ComarDatabaseServiceException("Error", ex);
        }
    }

    @Override
    public void delete(final ComarCategory c) throws ComarDatabaseServiceException {
        try {
            database.execute(jtx -> {
                ComarCategoryKite.delete(c);
                c.setId(null);
                jtx.commit();
                return null;
            });
        } catch (KiteException ex) {
            throw new ComarDatabaseServiceException("Error", ex);
        }
    }

    @Override
    public List<ComarCategory> search(final String text) throws ComarDatabaseServiceException {
        if (text == null || text.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        try {
            return (List<ComarCategory>) database.execute(jtx -> {
                NavigableSet<ComarCategoryKite> cats = ComarCategoryKite.search(text);
                List<ComarCategory> list = new ArrayList<>();
                for (ComarCategoryKite cc : cats) {
                    ComarCategoryKite c = (ComarCategoryKite) cc.copyOut();
                    list.add(c);
                }

                jtx.rollback();
                return list;
            });
        } catch (KiteException ex) {
            throw new ComarDatabaseServiceException("Error", ex);
        }
    }

    @Override
    public boolean existsName(final String name) throws ComarDatabaseServiceException {
        try {
            return (Boolean) database.execute(jtx -> {
                boolean response = ComarCategoryKite.existsName(name);
                jtx.rollback();
                return response;
            });
        } catch (KiteException ex) {
            throw new ComarDatabaseServiceException("Error", ex);
        }
    }
}
