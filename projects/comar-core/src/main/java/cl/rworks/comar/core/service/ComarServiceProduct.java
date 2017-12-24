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
public interface ComarServiceProduct {

    ComarProduct create() throws ComarServiceException;

    void insert(ComarProduct product) throws ComarServiceException;

    void update(ComarProduct product) throws ComarServiceException;

    ComarProduct get(Long id) throws ComarServiceException;

    ComarProduct getByCode(String code) throws ComarServiceException;

    List<ComarProduct> getAll() throws ComarServiceException;

    List<ComarProduct> search(String text) throws ComarServiceException;

    void delete(ComarProduct product) throws ComarServiceException;

    boolean existsCode(String code) throws ComarServiceException;
}
