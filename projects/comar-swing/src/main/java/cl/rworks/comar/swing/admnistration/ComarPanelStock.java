/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.core.data.ComarStockEntryKite;
import cl.rworks.comar.core.model.ComarStockEntry;
import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.util.ComarPanelCard;
import cl.rworks.comar.swing.util.ComarPanelSubtitle;
import cl.rworks.kite.KiteDb;
import com.alee.laf.panel.WebPanel;
import io.permazen.JTransaction;
import io.permazen.Permazen;
import io.permazen.ValidationMode;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author aplik
 */
public class ComarPanelStock extends ComarPanelCard {

    private ComarPanelBaseEditor panelEditor;

    public ComarPanelStock() {
        setLayout(new BorderLayout());

//        add(new ComarPanelSubtitle("Inventario"), BorderLayout.NORTH);
        add(build(), BorderLayout.CENTER);
    }

    private WebPanel build() {
        WebPanel panel = new WebPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(0, 10, 10, 10));

        panelEditor = new ComarPanelBaseEditor();
        panel.add(panelEditor, BorderLayout.CENTER);

        panelEditor.getButtonAdd().setAction(new AddAction());

        return panel;
    }

    private class AddAction extends AbstractAction {
        
        public AddAction(){
            putValue(NAME, "Agregar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JDialog dialog = new JDialog(null, "Agregar Stock", Dialog.ModalityType.APPLICATION_MODAL);
            dialog.getContentPane().setLayout(new BorderLayout());

            Permazen db = ComarSystem.getInstance().getService().getKitedb().get();
            JTransaction jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
            JTransaction.setCurrent(jtx);
            ComarStockEntry entry;
            try {
                entry = (ComarStockEntry) ComarStockEntryKite.create().copyOut("");
            } finally {
                jtx.rollback();
                JTransaction.setCurrent(null);
            }

            ComarPanelStockEditor panel = new ComarPanelStockEditor();
            panel.updateForm(entry);
            dialog.getContentPane().add(panel);

            dialog.setSize(800, 600);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }

    }

}
