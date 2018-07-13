/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author rgonzalez
 */
public class ComarFrameListener extends WindowAdapter {

    @Override
    public void windowClosing(WindowEvent e) {
        ComarExit exit = new ComarExit();
        exit.exit();
    }

}
