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

    ComarProductDao getProductDao();

    ComarCategoryDao getCategoryDao();

    public ComarProduct createProduct() throws ComarServiceException;

    public void insertProduct(ComarProduct product) throws ComarServiceException;

    public ComarProduct getProduct(Long id) throws ComarServiceException;

    public ComarProduct getByCodeProduct(String code) throws ComarServiceException;

    public List<ComarProduct> getAllProducts() throws ComarServiceException;

}
