/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing;

import cl.rworks.comar.core.impl.ComarDatabaseServiceImpl;
import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.service.ComarDatabaseService;
import cl.rworks.comar.core.service.ComarDatabaseServiceException;
import java.util.List;

/**
 *
 * @author rgonzalez
 */
public class ComarSystemService {

    public ComarDatabaseService databaseService;

    public ComarSystemService() {
        this.databaseService = new ComarDatabaseServiceImpl("storage");
    }

    public ComarDatabaseService getDatabaseService() {
        return databaseService;
    }

    public List<ComarCategory> getAllCategories() throws ComarDatabaseServiceException {
        return databaseService.getServiceCategory().getAll();
    }

    public ComarProduct createProduct() throws ComarDatabaseServiceException {
        return databaseService.getServiceProduct().create();
    }

    public void insertProduct(ComarProduct product) throws ComarDatabaseServiceException {
        databaseService.getServiceProduct().insert(product);
    }

    public List<ComarProduct> searchProduct(String strText) throws ComarDatabaseServiceException {
        if (strText.isEmpty()) {
            return databaseService.getServiceProduct().getAll();
        } else {
            return databaseService.getServiceProduct().search(strText);
        }

    }

}
