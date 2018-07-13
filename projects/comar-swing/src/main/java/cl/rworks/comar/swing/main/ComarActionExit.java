/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.main;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;

/**
 *
 * @author rgonzalez
 */
public class ComarActionExit extends AbstractAction {

    public ComarActionExit() {
        putValue(NAME, "Salir");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ComarExit exit = new ComarExit();
        exit.exit();
    }

}
