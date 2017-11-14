/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import org.slf4j.LoggerFactory;

/**
 *
 * @author rgonzalez
 */
public class ComarContext {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ComarContext.class);
    private static ComarContext instance;
    //
    private ComarProperties properties;
    private ComarService service;

    public static ComarContext getInstance() {
        instance = instance == null ? new ComarContext() : instance;
        return instance;
    }

    private ComarContext() {
        properties = new ComarProperties();
        service = new ComarServiceImpl("storage");
        init();
    }

    private void init() {
    }

    public ComarProperties getProperties() {
        return properties;
    }

}
