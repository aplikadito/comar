/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.core.model.ComarProduct;
import com.alee.laf.button.WebButton;
import com.alee.laf.rootpane.WebDialog;
import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author aplik
 */
public class ComarDialogProductEdit extends WebDialog {

    public ComarDialogProductEdit(Window window, ComarProduct product) {
        super(window, "Editar Producto", ModalityType.APPLICATION_MODAL);
        getContentPane().setLayout(new BorderLayout());

        ComarPanelProductEdit panel = new ComarPanelProductEdit();

        WebButton buttonClose = new WebButton(new CloseAction());
        buttonClose.setFocusable(true);
        panel.getPanelFormButtons().add(buttonClose);

        panel.updateForm(product);
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