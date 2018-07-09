/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import com.alee.laf.panel.WebPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JComponent;

/**
 *
 * @author aplik
 */
public class ComarPanelButtonsArea extends ComarPanel {

    private ComarPanel panelLeft;
    private ComarPanel panelRight;

    public ComarPanelButtonsArea() {
        setLayout(new BorderLayout());
        panelLeft = new ComarPanel(new FlowLayout(FlowLayout.LEFT));
        panelRight = new ComarPanel(new FlowLayout(FlowLayout.RIGHT));
        add(panelLeft, BorderLayout.WEST);
        add(panelRight, BorderLayout.EAST);
    }

    public ComarPanel getPanelLeft() {
        return panelLeft;
    }

    public ComarPanel getPanelRight() {
        return panelRight;
    }

    public WebPanel addLeft(Component... components) {
        this.panelLeft.add(components);
        return this;
    }

    public WebPanel addRight(Component... components) {
        this.panelRight.add(components);
        return this;
    }

}
