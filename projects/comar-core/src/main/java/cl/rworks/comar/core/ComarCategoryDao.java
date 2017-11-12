/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import cl.rworks.kite.KiteDb;

/**
 *
 * @author rgonzalez
 */
public class ComarCategoryDao {

    private KiteDb database;
    
    ComarCategoryDao(KiteDb database) {
        this.database = database;
    }
    
}
