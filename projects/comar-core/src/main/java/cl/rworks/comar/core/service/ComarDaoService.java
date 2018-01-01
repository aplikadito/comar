/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service;

/**
 *
 * @author rgonzalez
 */
public interface ComarDaoService {

    public void openTransaction();

    public Object execute(ComarDaoQuery job) throws ComarDaoException;

    public void closeTransaction();

    public void commit();

    public void rollback();
}
