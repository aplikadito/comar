/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author aplik
 */
public class ComarActionSimple extends ComarAction {

    private ActionListener listener;

    public ComarActionSimple(String name, ActionListener listener) {
        super(name);
        this.listener = listener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        listener.actionPerformed(e);
    }

}
