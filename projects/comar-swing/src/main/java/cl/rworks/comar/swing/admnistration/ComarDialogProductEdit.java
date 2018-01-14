/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.core.model.ComarProduct;
import com.alee.laf.rootpane.WebDialog;
import java.awt.BorderLayout;
import java.awt.Window;

/**
 *
 * @author aplik
 */
public class ComarDialogProductEdit extends WebDialog {

    public ComarDialogProductEdit(Window window, ComarProduct product){
        super(window, "Editar Producto", ModalityType.APPLICATION_MODAL);
        getContentPane().setLayout(new BorderLayout());
        
        ComarPanelProductEdit panel = new ComarPanelProductEdit();
        panel.updateForm(product);
        getContentPane().add(panel);
    }
}
