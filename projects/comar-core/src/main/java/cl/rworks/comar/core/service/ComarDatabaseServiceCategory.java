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

    ComarCategory create(String name) throws ComarDatabaseServiceException;

    void insert(ComarCategory product) throws ComarDatabaseServiceException;

    void update(ComarCategory product) throws ComarDatabaseServiceException;

    ComarCategory get(Long id) throws ComarDatabaseServiceException;

    ComarCategory getByName(String name) throws ComarDatabaseServiceException;

    List<ComarCategory> getAll() throws ComarDatabaseServiceException;

    List<ComarCategory> search(String text) throws ComarDatabaseServiceException;

    void delete(ComarCategory product) throws ComarDatabaseServiceException;

    boolean existsName(String name) throws ComarDatabaseServiceException;
}
