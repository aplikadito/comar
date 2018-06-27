/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service;

import cl.rworks.comar.core.controller.permazen.ComarControllerPermazen;
import cl.rworks.comar.core.controller.ComarController;
import cl.rworks.comar.core.controller.derby.ComarControllerDerby;

/**
 *
 * @author rgonzalez
 */
public class ComarServiceImpl implements ComarService {

    private ComarController controller;

    public ComarServiceImpl(int apiOption) {
        if (apiOption == PERMAZEN) {
            controller = new ComarControllerPermazen();
        } else if (apiOption == DERBY) {
            controller = new ComarControllerDerby();
        }
    }

    @Override
    public ComarController getController() {
        return controller;
    }
}
