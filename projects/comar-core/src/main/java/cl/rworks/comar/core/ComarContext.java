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
public interface ComarContext {

    public ComarProperties getProperties();

    public ComarService getService();
}
