/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing;

import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import java.awt.Color;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelTitle extends WebPanel {

    public ComarPanelTitle(String title) {
        
        WebLabel labelTitle = new WebLabel(title);
        labelTitle.setBoldFont();
        labelTitle.setFontSize(20);
        labelTitle.setForeground(Color.WHITE);
        
        add(labelTitle);
        setMinimumHeight(40);
        setPreferredHeight(40);
        setMaximumHeight(40);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(Color.BLACK);
    }

}
