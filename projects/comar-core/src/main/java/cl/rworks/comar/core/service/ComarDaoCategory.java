/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service;

import cl.rworks.comar.core.model.ComarCategory;

/**
 *
 * @author rgonzalez
 */
public interface ComarDaoCategory extends ComarDao<ComarCategory> {

    public ComarCategory getByName(String name) throws ComarDaoException;
}
