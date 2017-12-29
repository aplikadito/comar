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

    ComarProduct create() throws ComarDatabaseServiceException;

    void insert(ComarProduct product) throws ComarDatabaseServiceException;

    void update(ComarProduct product) throws ComarDatabaseServiceException;

    ComarProduct get(Long id) throws ComarDatabaseServiceException;

    ComarProduct getByCode(String code) throws ComarDatabaseServiceException;

    List<ComarProduct> getAll() throws ComarDatabaseServiceException;

    List<ComarProduct> search(String text) throws ComarDatabaseServiceException;

    void delete(ComarProduct product) throws ComarDatabaseServiceException;

    boolean existsCode(String code) throws ComarDatabaseServiceException;
}
