/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.batches;

import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarPanelTitle;
import java.awt.BorderLayout;

/**
 *
 * @author aplik
 */
public class ComarPanelBatches extends ComarPanel {

    public ComarPanelBatches() {
        setLayout(new BorderLayout());
        add(new ComarPanelTitle("Lotes"), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);
    }

    private ComarPanel buildContent() {
        ComarPanel panel = new ComarPanel();
        return panel;
    }

}
