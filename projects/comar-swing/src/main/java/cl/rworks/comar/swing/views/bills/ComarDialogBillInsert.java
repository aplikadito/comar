/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.bills;

import cl.rworks.comar.swing.model.ComarBill;
import cl.rworks.comar.swing.util.ComarException;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarPanelOptionsArea;
import cl.rworks.comar.swing.util.ComarUtils;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.rootpane.WebDialog;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author aplik
 */
public class ComarDialogBillInsert extends WebDialog {

    private ComarPanelBillInsert panel;
    private ComarPanelOptionsArea panelButtonsArea;
    //
    private ComarBill bill = null;
    private boolean ok = false;

    public ComarDialogBillInsert(Window window, String title) {
        super(window, title, ModalityType.APPLICATION_MODAL);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel = new ComarPanelBillInsert(), BorderLayout.CENTER);
        getContentPane().add(initButtonsArea(), BorderLayout.SOUTH);
    }

    private ComarPanel initButtonsArea() {
        panelButtonsArea = new ComarPanelOptionsArea();
        panelButtonsArea.addRight(new WebButton("Aceptar", e -> okAction()));
        panelButtonsArea.addRight(new WebButton("Cancelar", e -> closeAction()));

        return panelButtonsArea;
    }

    public void showMe() {
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public ComarBill getBill() {
        return bill;
    }

    public boolean isOk() {
        return ok;
    }

    private void okAction() {
        try {
            bill = panel.getBill();
            ok = true;
            this.dispose();
        } catch (ComarException ex) {
            ComarUtils.showWarn(this, ex.getMessage());
        }
    }

    private void closeAction() {
        ok = false;
        bill = null;
        this.dispose();
    }

    public ComarPanelBillInsert getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                WebLookAndFeel.install();
                ComarDialogBillInsert dialog = new ComarDialogBillInsert(null, "");
                dialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });

                dialog.showMe();
            }
        });
    }

}
