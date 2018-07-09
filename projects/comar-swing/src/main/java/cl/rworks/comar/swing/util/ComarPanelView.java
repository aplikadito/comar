/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author aplik
 */
public class ComarPanelView extends ComarPanel {

    private ComarPanel panelContent;

    public ComarPanelView(String title) {
        setLayout(new BorderLayout());
        add(new ComarPanelTitle(title), BorderLayout.NORTH);

        panelContent = new ComarPanel(new BorderLayout());
        panelContent.setBorder(new EmptyBorder(30, 30, 30, 30));
        add(panelContent, BorderLayout.CENTER);
    }

    public ComarPanel getPanelContent() {
        return panelContent;
    }
    
}
