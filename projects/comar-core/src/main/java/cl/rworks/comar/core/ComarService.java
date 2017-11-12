/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import java.util.List;
import java.util.NavigableSet;

/**
 *
 * @author rgonzalez
 */
public interface ComarService {

    ComarProduct createProduct() throws ComarServiceException;

    void insertProduct(ComarProduct product) throws ComarServiceException;

    ComarProduct getProduct(Long id) throws ComarServiceException;

    ComarProduct getByCodeProduct(String code) throws ComarServiceException;

    List<ComarProduct> getAllProducts() throws ComarServiceException;

    List<ComarProduct> search(String text) throws ComarServiceException;

}
