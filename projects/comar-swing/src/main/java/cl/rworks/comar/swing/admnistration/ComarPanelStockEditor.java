/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.core.model.ComarStockEntry;
import cl.rworks.comar.swing.ComarSystem;
import com.alee.extended.layout.FormLayout;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author aplik
 */
public class ComarPanelStockEditor extends WebPanel {

    private ComarStockEntry stock;
    //
    private int fontSize = ComarSystem.getInstance().getProperties().getNormalFontSize();
    //
    private WebPanel panelButtons;
    private WebTable table;
    private WebTextField textCode;
    private WebButton buttonOk;

    public ComarPanelStockEditor() {
        setLayout(new BorderLayout());
        add(build(), BorderLayout.CENTER);
        add(buildButtons(), BorderLayout.SOUTH);
    }

    private WebPanel build() {
        WebPanel panel = new WebPanel(new BorderLayout());

        WebPanel panelForm = new WebPanel(new FormLayout());
        panelForm.setPreferredSize(new Dimension(300, 100));
        panelForm.setMaximumSize(new Dimension(300, 100));
        panelForm.setAlignmentX(0.0f);
        panelForm.setAlignmentY(0.0f);

        WebPanel panelAux = new WebPanel();
        panelAux.setLayout(new BoxLayout(panelAux, BoxLayout.PAGE_AXIS));
        panelAux.setBorder(new EmptyBorder(10,10,10,10));
        panelAux.add(panelForm);
        panel.add(panelAux, BorderLayout.NORTH);

        WebLabel labelCode = new WebLabel("Identificador");
        labelCode.setFontStyle(fontSize);
        panelForm.add(labelCode);

        textCode = new WebTextField();
        textCode.setFontStyle(fontSize);
        panelForm.add(textCode);

        table = new WebTable();
        panel.add(new WebScrollPane(table), BorderLayout.CENTER);
        
        return panel;
    }

    private WebPanel buildButtons() {
        panelButtons = new WebPanel(new FlowLayout());

        buttonOk = new WebButton("Aceptar");
        panelButtons.add(buttonOk);

        return panelButtons;
    }

    public void updateForm(ComarStockEntry stock) {

    }
}
