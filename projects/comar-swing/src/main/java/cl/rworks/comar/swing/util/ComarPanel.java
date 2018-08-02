/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import com.alee.laf.panel.WebPanel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

/**
 *
 * @author aplik
 */
public class ComarPanel extends WebPanel {

    private static final Color BACKGROUND_COLOR = new Color(240, 240, 240);

    public ComarPanel() {
        this(new FlowLayout());
    }

    public ComarPanel(LayoutManager layout) {
        super(layout);
//        setBackground(ComarSystem.getInstance().getProperties().getBackgroundColor());
        setBackground(BACKGROUND_COLOR);
    }
}
