/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service;

import cl.rworks.comar.core.model.ComarProduct;
import java.util.List;

/**
 *
 * @author aplik
 */
public interface ComarDatabaseServiceProduct {

    ComarProduct create() throws ComarDaoException;

    void insert(ComarProduct product) throws ComarDaoException;

    void update(ComarProduct product) throws ComarDaoException;

    ComarProduct get(Long id) throws ComarDaoException;

    ComarProduct getByCode(String code) throws ComarDaoException;

    List<ComarProduct> getAll() throws ComarDaoException;

    List<ComarProduct> search(String text) throws ComarDaoException;

    void delete(ComarProduct product) throws ComarDaoException;

    boolean existsCode(String code) throws ComarDaoException;
}
