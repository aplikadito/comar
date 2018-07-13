/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.sells;

import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarPanelView;
import java.awt.BorderLayout;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelSellsArea extends ComarPanelView {

    private ComarPanel panelContent;

    public ComarPanelSellsArea() {
        super("Ventas");

        initComponents();
    }

    private void initComponents() {
        ComarPanel panelContent = new ComarPanel();
        getPanelContent().add(panelContent, BorderLayout.CENTER);
    }

}
