/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import com.alee.laf.button.WebButton;
import com.alee.laf.rootpane.WebDialog;
import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Window;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;

/**
 *
 * @author aplik
 */
public class ComarDialogCategoryAdd extends WebDialog {

    public ComarDialogCategoryAdd(Window window) {
        super(window, "Agregar Categoria", ModalityType.APPLICATION_MODAL);
        getContentPane().setLayout(new BorderLayout());

        ComarPanelCategoryAdd panel = new ComarPanelCategoryAdd();

        WebButton buttonClose = new WebButton(new CloseAction());
        buttonClose.setFocusable(true);
        panel.getPanelFormButtons().add(buttonClose);

        panel.updateForm();
        getContentPane().add(panel);
    }

    private class CloseAction extends AbstractAction {

        public CloseAction() {
            putValue(NAME, "Cerrar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }

    }
}
