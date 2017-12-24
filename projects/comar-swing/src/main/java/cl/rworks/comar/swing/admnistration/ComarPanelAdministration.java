/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.swing.ComarPanelTitle;
import com.alee.laf.panel.WebPanel;
import java.awt.BorderLayout;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelAdministration extends WebPanel {

    public ComarPanelAdministration() {
        initValues();
    }

    private void initValues() {
        setLayout(new BorderLayout());
        add(new ComarPanelTitle("Administracion"), BorderLayout.NORTH);
        add(new ComarPanelAdministrationCore(), BorderLayout.CENTER);
    }
}
