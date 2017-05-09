/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

/**
 *
 * @author rgonzalez
 */
public class ComarContext {

    private final ComarProperties properties;
    private final ComarDatabase database;

    public ComarContext() {
        this.properties = new ComarProperties();
        this.database = new ComarDatabase();
    }

    public ComarProperties getProperties() {
        return properties;
    }

    public ComarDatabase getDatabase() {
        return database;
    }

}
