/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.separator.WebSeparator;
import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelSubtitle extends WebPanel {

    public ComarPanelSubtitle(String title) {
        setLayout(new BorderLayout());

        WebLabel labelTitle = new WebLabel(title);
        labelTitle.setBoldFont();
        labelTitle.setFontSize(14);
//        labelTitle.setForeground(Color.WHITE);

        add(labelTitle, BorderLayout.PAGE_START);
//        add(new WebSeparator(), BorderLayout.PAGE_END);

        setMinimumHeight(40);
        setPreferredHeight(40);
        setMaximumHeight(40);
        setBorder(new EmptyBorder(10, 10, 10, 10));
//        setBackground(ComarConstants.BLUE);
    }

}