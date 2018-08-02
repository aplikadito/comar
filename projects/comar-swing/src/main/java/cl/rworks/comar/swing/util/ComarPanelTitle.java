/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import cl.rworks.comar.swing.main.ComarSystem;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelTitle extends WebPanel {

    private static final Color BANNER_COLOR = new Color(35, 80, 35);
    //
    private WebLabel labelTitle;
    private WebLabel labelTitleEast;

    public ComarPanelTitle(String title) {
        labelTitle = new WebLabel(title);
        labelTitle.setBoldFont();
        labelTitle.setFontSize(24);
        labelTitle.setForeground(Color.WHITE);

        labelTitleEast = new WebLabel("");
        labelTitleEast.setBoldFont();
        labelTitleEast.setFontSize(24);
        labelTitleEast.setForeground(Color.WHITE);

        setLayout(new BorderLayout());
        add(labelTitle, BorderLayout.WEST);
        add(labelTitleEast, BorderLayout.EAST);
        setMinimumHeight(24 + 25);
        setPreferredHeight(24 + 25);
        setMaximumHeight(24 + 25);
        setBorder(new EmptyBorder(10, 10, 10, 10));
//        setBackground(Color.BLACK);
//        setBackground(ComarSystem.getInstance().getProperties().getBannerColor());
        setBackground(BANNER_COLOR);
    }

    public void setTitle(String title) {
        this.labelTitle.setText(title);
    }

    public void setTitleEast(String title) {
        this.labelTitleEast.setText(title);
    }
}
