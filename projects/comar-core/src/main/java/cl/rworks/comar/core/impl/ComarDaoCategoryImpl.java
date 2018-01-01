/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.impl;

import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.service.ComarDaoCategory;
import cl.rworks.comar.core.service.ComarDaoException;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;

/**
 *
 * @author rgonzalez
 */
public class ComarDaoCategoryImpl implements ComarDaoCategory {

    @Override
    public ComarCategory create() throws ComarDaoException {
        return ComarCategoryKite.create();
    }

    @Override
    public void update(ComarCategory entity) throws ComarDaoException {
        ComarCategoryKite.update(entity);
    }

    @Override
    public void delete(ComarCategory entity) throws ComarDaoException {
        ComarCategoryKite.delete(entity);
    }

    @Override
    public ComarCategory get(Object id) throws ComarDaoException {
        return ComarCategoryKite.get((Long)id);
    }

    @Override
    public List<ComarCategory> getAll() throws ComarDaoException {
        NavigableSet<ComarCategoryKite> odbs = ComarCategoryKite.getAll();
        List<ComarCategory> list = new ArrayList<>();
        for (ComarCategoryKite odb : odbs) {
            ComarCategory o = (ComarCategory) odb.copyOut();
            list.add(o);
        }
        return list;
    }

    @Override
    public Object instance(ComarCategory entity, String reference) throws ComarDaoException {
        throw new ComarDaoException("Esta entidad no posee referencias");
    }

    @Override
    public ComarCategory getByName(String name) throws ComarDaoException {
        return ComarCategoryKite.getByName(name);
    }

}
