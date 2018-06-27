/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service;

import cl.rworks.comar.core.controller.ComarController;

/**
 *
 * @author rgonzalez
 */
public interface ComarService {

    int PERMAZEN = 0;
    int DERBY = 1;

    ComarController getController();

}
