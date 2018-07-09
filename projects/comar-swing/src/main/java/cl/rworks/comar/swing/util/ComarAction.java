/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import javax.swing.AbstractAction;

/**
 *
 * @author aplik
 */
public abstract class ComarAction extends AbstractAction {

    public ComarAction(String name) {
        putValue(NAME, name);
    }
    
}
