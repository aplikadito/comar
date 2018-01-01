/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.impl;

import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.service.ComarDaoException;
import cl.rworks.comar.core.service.ComarDaoProduct;
import io.permazen.JTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.stream.Collectors;

/**
 *
 * @author rgonzalez
 */
public class ComarDaoProductImpl implements ComarDaoProduct {

    @Override
    public ComarProduct create() {
        return ComarProductKite.create();
    }

    @Override
    public void update(ComarProduct product) throws ComarDaoException {
        ComarProductKite.update(product);
    }

    @Override
    public void delete(ComarProduct product) {
        ComarProductKite.delete(product);
    }

    @Override
    public ComarProduct get(Object id) throws ComarDaoException {
        return ComarProductKite.get(id);
    }

    @Override
    public List<ComarProduct> getAll() {
        return ComarProductKite.getAll().stream().collect(Collectors.toList());
    }

    @Override
    public Object instance(ComarProduct product, String reference) throws ComarDaoException {
        if ("category".equals(reference)) {
            ComarCategory category = product.getCategory();
            return category;
        } else {
            throw new ComarDaoException("Referencia no encontradao: " + reference);
        }
    }

    @Override
    public ComarProduct getByCode(String code) throws ComarDaoException {
        return ComarProductKite.getByCode(code);
    }

    @Override
    public List<ComarProduct> search(String str) throws ComarDaoException {
        return ComarProductKite.search(str).stream().collect(Collectors.toList());
    }

}
