/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service;

import io.permazen.Permazen;

/**
 *
 * @author rgonzalez
 */
public interface ComarService {

    int MEMORY = 0;
    int DISK = 1;
    int MYSQL = 2;

    Permazen getDb();
    
}
