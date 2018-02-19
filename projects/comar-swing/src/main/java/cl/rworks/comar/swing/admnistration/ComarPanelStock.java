/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.swing.util.ComarPanelCard;
import cl.rworks.comar.swing.util.ComarPanelSubtitle;
import com.alee.laf.panel.WebPanel;
import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author aplik
 */
public class ComarPanelStock extends ComarPanelCard {

    private ComarPanelBaseEditor panelEditor;

    public ComarPanelStock() {
        setLayout(new BorderLayout());

        add(new ComarPanelSubtitle("Inventario"), BorderLayout.NORTH);
        add(build(), BorderLayout.CENTER);
    }

    private WebPanel build() {
        WebPanel panel = new WebPanel();
        panel.setBorder(new EmptyBorder(0, 10, 10, 10));

        panelEditor = new ComarPanelBaseEditor();
        panel.add(panelEditor);
        return panel;
    }

}
