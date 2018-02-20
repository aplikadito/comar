/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JDialog;

/**
 *
 * @author aplik
 */
public class ComarCloseAction extends AbstractAction {

    private JDialog dialog;

    public ComarCloseAction(JDialog dialog) {
        this.dialog = dialog;
        putValue(NAME, "Cerrar");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dialog.dispose();
    }

}
