/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import com.alee.laf.panel.WebPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author aplik
 */
public class BorderLayoutPanel extends WebPanel {

    private WebPanel panelNorth;
    private WebPanel panelCenter;
    private WebPanel panelSouth;
    private WebPanel panelWest;
    private WebPanel panelEast;

    public BorderLayoutPanel() {
        setLayout(new BorderLayout());

        panelNorth = new WebPanel(new FlowLayout());
        panelCenter = new WebPanel(new BorderLayout());
        panelSouth = new WebPanel(new FlowLayout());
        panelWest = new WebPanel();
        panelEast = new WebPanel();

        panelWest.setLayout(new BoxLayout(panelWest, BoxLayout.PAGE_AXIS));
        panelEast.setLayout(new BoxLayout(panelEast, BoxLayout.PAGE_AXIS));
        
        panelCenter.setBorder(new EmptyBorder(0, 10, 10, 10));

        add(panelNorth, BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);
        add(panelSouth, BorderLayout.SOUTH);
        add(panelWest, BorderLayout.WEST);
        add(panelEast, BorderLayout.EAST);
    }

    public WebPanel getPanelNorth() {
        return panelNorth;
    }

    public WebPanel getPanelCenter() {
        return panelCenter;
    }

    public WebPanel getPanelSouth() {
        return panelSouth;
    }

    public WebPanel getPanelWest() {
        return panelWest;
    }

    public WebPanel getPanelEast() {
        return panelEast;
    }

}
