/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.pointofsell;

import cl.rworks.comar.swing.util.ComarPanelTitle;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import java.awt.BorderLayout;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelPointOfSell extends WebPanel {

    private WebLabel labelKeyboard = new WebLabel();

    public ComarPanelPointOfSell() {
        setLayout(new BorderLayout());
        add(new ComarPanelTitle("Punto de Venta"), BorderLayout.NORTH);

        add(labelKeyboard, BorderLayout.CENTER);
    }

    public WebLabel getLabelKeyboard() {
        return labelKeyboard;
    }

}
