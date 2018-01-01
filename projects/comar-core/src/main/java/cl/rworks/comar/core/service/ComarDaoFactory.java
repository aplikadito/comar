/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service;

import cl.rworks.comar.core.impl.ComarDaoCategoryImpl;
import cl.rworks.comar.core.impl.ComarDaoProductImpl;

/**
 *
 * @author rgonzalez
 */
public class ComarDaoFactory {

    public static ComarDaoProduct getDaoProduct() {
        return new ComarDaoProductImpl();
    }

    public static ComarDaoCategory getDaoCategory() {
        return new ComarDaoCategoryImpl();
    }

}
