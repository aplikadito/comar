/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.controller;

import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.model.ComarProductHistorial;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author aplik
 */
public interface ComarController {

    int MEMORY = 0;
    int DISK = 1;
    int SERVER = 2;

    public void startup(int dbOption);

    public void createTransaction() throws ComarControllerException;

    public void commit() throws ComarControllerException;

    public void rollback();

    public void endTransaction();

    public List<ComarProduct> loadProducts(String str) throws ComarControllerException;

    public ComarProduct getProductByCode(String code) throws ComarControllerException;

    public void addProduct(ComarProduct product) throws ComarControllerException;

    public void updateProduct(String code, LocalDateTime now, String action, ComarProduct changes) throws ComarControllerException;

    public List<ComarProductHistorial> searchProductHistorial(String text);

}
