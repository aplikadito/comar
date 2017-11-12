/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import java.util.List;

/**
 *
 * @author rgonzalez
 */
public interface ComarService {

    ComarProduct createProduct() throws ComarServiceException;

    void insertProduct(final ComarProduct p) throws ComarServiceException;

    public List<ComarProduct> getAllProducts() throws ComarServiceException;

    public ComarProduct getByIdProduct(Long id) throws ComarServiceException;

    public ComarProduct getByIdCode(String code) throws ComarServiceException;

}
