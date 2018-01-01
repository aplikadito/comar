/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service;

import java.util.List;

/**
 *
 * @author rgonzalez
 */
public interface ComarDao<A> {

    A create() throws ComarDaoException;

    void update(A entity) throws ComarDaoException;

    void delete(A entity) throws ComarDaoException;

    A get(Object id) throws ComarDaoException;

    List<A> getAll() throws ComarDaoException;

    Object instance(A entity, String reference) throws ComarDaoException;

}
