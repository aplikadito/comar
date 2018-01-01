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
 * @author rgonzalez
 */
public interface ComarDaoProduct extends ComarDao<ComarProduct> {

    public ComarProduct getByCode(String code) throws ComarDaoException;

    public List<ComarProduct> search(String strText) throws ComarDaoException;
}
