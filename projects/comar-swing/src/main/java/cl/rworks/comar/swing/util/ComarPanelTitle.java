/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import cl.rworks.comar.swing.ComarSystem;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import java.awt.Color;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelTitle extends WebPanel {

    private int fontSize = ComarSystem.getInstance().getProperties().getLargeFontSize();
    
    public ComarPanelTitle(String title) {
        
        WebLabel labelTitle = new WebLabel(title);
        labelTitle.setBoldFont();
        labelTitle.setFontSize(fontSize);
        labelTitle.setForeground(Color.WHITE);
        
        add(labelTitle);
        setMinimumHeight(fontSize + 20);
        setPreferredHeight(fontSize + 20);
        setMaximumHeight(fontSize + 20);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(Color.BLACK);
    }

}
