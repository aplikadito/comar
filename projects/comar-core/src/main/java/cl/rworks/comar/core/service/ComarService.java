/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service;

import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.model.ComarMetric;
import cl.rworks.comar.core.model.ComarMetricObject;
import cl.rworks.comar.core.model.ComarProduct;
import java.util.List;

/**
 *
 * @author aplik
 */
public interface ComarService {

    int DERBY = 0;
    int PERMAZEN = 1;
    //
    int MEMORY = 0;
    int DISK = 1;
    int SERVER = 2;

    public void startup(int dbOption);

    public ComarTransaction createTransaction() throws ComarServiceException;

    public List<ComarProduct> getAllProducts() throws ComarServiceException;

    public List<ComarProduct> searchProductByCodeOrDescription(String str) throws ComarServiceException;

    public ComarProduct getProductByCode(String code) throws ComarServiceException;

    public void insertProduct(ComarProduct product, ComarCategory category) throws ComarServiceException;

    public void updateProduct(ComarProduct product, ComarCategory category) throws ComarServiceException;

    public List<ComarCategory> getAllCategories() throws ComarServiceException;

    public void insertCategory(ComarCategory category) throws ComarServiceException;

    public List<ComarProduct> getProductForCategory(ComarCategory category) throws ComarServiceException;

    public void deleteCategory(ComarCategory category) throws ComarServiceException;

    public void deleteProducts(List<ComarProduct> products) throws ComarServiceException;

    public void editProductCode(ComarProduct model, String code) throws ComarServiceException;

    public void editProductDescription(ComarProduct model, String description) throws ComarServiceException;

    public void editProductMetric(ComarProduct product, ComarMetric metric) throws ComarServiceException;

    public void updateCategoryOfProducts(List<ComarProduct> products, ComarCategory category) throws ComarServiceException;

    public List<ComarMetricObject> getAllMetrics() throws ComarServiceException;
}
