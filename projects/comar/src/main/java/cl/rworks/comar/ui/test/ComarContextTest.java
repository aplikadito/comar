/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui.test;

import cl.rworks.comar.core.model.ComarContext;
import cl.rworks.comar.core.model.ComarProperties;
import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.impl.ComarServiceImpl;
import cl.rworks.comar.core.model.ComarProduct;

/**
 *
 * @author aplik
 */
public class ComarContextTest implements ComarContext {

    private ComarPropertiesTest properties;
    private ComarService service;

    public ComarContextTest() {
        this.properties = new ComarPropertiesTest();

        this.service = new ComarServiceImpl();
        initDatabase();
    }

    private void initDatabase() {
        try {
            ComarProduct p = this.service.getServiceProduct().create();
            p.setCode("0001");
            p.setName("mate");
            this.service.getServiceProduct().insert(p);

            p = this.service.getServiceProduct().create();
            p.setCode("0002");
            p.setName("cafe");
            this.service.getServiceProduct().insert(p);

            p = this.service.getServiceProduct().create();
            p.setCode("0003");
            p.setName("harina");
            this.service.getServiceProduct().insert(p);

        } catch (ComarServiceException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ComarProperties getProperties() {
        return properties;
    }

    @Override
    public ComarService getService() {
        return service;
    }

}
