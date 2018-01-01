/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service;

import cl.rworks.comar.core.model.ComarCategory;
import java.util.List;

/**
 *
 * @author rgonzalez
 */
public interface ComarDatabaseServiceCategory {

    ComarCategory create(String name) throws ComarDaoException;

    void insert(ComarCategory product) throws ComarDaoException;

    void update(ComarCategory product) throws ComarDaoException;

    ComarCategory get(Long id) throws ComarDaoException;

    ComarCategory getByName(String name) throws ComarDaoException;

    List<ComarCategory> getAll() throws ComarDaoException;

    List<ComarCategory> search(String text) throws ComarDaoException;

    void delete(ComarCategory product) throws ComarDaoException;

    boolean existsName(String name) throws ComarDaoException;
}
