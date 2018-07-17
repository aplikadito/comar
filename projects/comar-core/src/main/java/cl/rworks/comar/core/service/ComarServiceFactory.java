/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service;

import cl.rworks.comar.core.service.derby.ComarServiceDerby;

/**
 *
 * @author aplik
 */
public final class ComarServiceFactory {

    private ComarServiceFactory() {
    }

    public static ComarService create(int option) {
        switch (option) {
            case ComarService.DERBY:
                return new ComarServiceDerby();
//            case ComarService.PERMAZEN:
//                return new ComarServicePermazen();
            default:
                throw new RuntimeException("Opcion no soportada: " + option);
        }
    }
}
