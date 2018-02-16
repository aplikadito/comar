/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.settings;

import cl.rworks.comar.swing.util.ComarPanelTitle;
import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.properties.ComarProperties;
import cl.rworks.comar.swing.util.ComarPanelCard;
import cl.rworks.comar.swing.util.ComarUtils;
import com.alee.extended.layout.FormLayout;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.spinner.WebSpinner;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelSettings extends ComarPanelCard {

    private WebSpinner spinnerNormal;
    private WebSpinner spinnerMedium;
    private WebSpinner spinnerLarge;

    public ComarPanelSettings() {
        setLayout(new BorderLayout());
        add(new ComarPanelTitle("Opciones"), BorderLayout.NORTH);
        add(build(), BorderLayout.CENTER);
    }

    private WebPanel build() {
        ComarProperties properties = ComarSystem.getInstance().getProperties();

        WebPanel panelAux = new WebPanel();
        panelAux.setLayout(new BoxLayout(panelAux, BoxLayout.PAGE_AXIS));

        WebPanel panelMain = new WebPanel(new BorderLayout());
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.PAGE_AXIS));
        panelMain.setMinimumSize(new Dimension(300, 200));
        panelMain.setPreferredSize(new Dimension(300, 200));
        panelMain.setMaximumSize(new Dimension(300, 200));
        panelMain.setAlignmentX(0.0f);
        panelMain.setAlignmentY(0.0f);
//        panelMain.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        WebScrollPane pane = new WebScrollPane(panelMain);
        pane.setMinimumSize(new Dimension(400, 250));
        pane.setPreferredSize(new Dimension(400, 250));
        pane.setMaximumSize(new Dimension(400, 250));
        pane.setAlignmentX(0.0f);
        pane.setAlignmentY(0.0f);
        pane.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelAux.add(pane);

        WebPanel panelForm = new WebPanel(new FormLayout(false, true, 10, 10));
        panelMain.add(panelForm, BorderLayout.CENTER);

        WebLabel label = new WebLabel("Tamaño Letra Normal");
        label.setFontSize(properties.getNormalFontSize());

        spinnerNormal = new WebSpinner(new SpinnerNumberModel(properties.getNormalFontSize(), 10, 30, 1));
        spinnerNormal.setFontSize(properties.getNormalFontSize());
        panelForm.add(label);
        panelForm.add(spinnerNormal);

        label = new WebLabel("Tamaño Letra Mediana");
        label.setFontSize(properties.getNormalFontSize());

        spinnerMedium = new WebSpinner(new SpinnerNumberModel(properties.getMediumFontSize(), 10, 30, 1));
        spinnerMedium.setFontSize(properties.getNormalFontSize());
        panelForm.add(label);
        panelForm.add(spinnerMedium);

        label = new WebLabel("Tamaño Letra Grande");
        label.setFontSize(properties.getNormalFontSize());
        spinnerLarge = new WebSpinner(new SpinnerNumberModel(properties.getLargeFontSize(), 10, 30, 1));
        spinnerLarge.setFontSize(properties.getNormalFontSize());
        panelForm.add(label);
        panelForm.add(spinnerLarge);

        WebButton webButton = new WebButton("Aplicar");
        webButton.setFontSize(properties.getNormalFontSize());
        webButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int normal = (Integer) spinnerNormal.getValue();
                int medium = (Integer) spinnerMedium.getValue();
                int large = (Integer) spinnerLarge.getValue();

                ComarSystem.getInstance().getProperties().setNormalFontSize(normal);
                ComarSystem.getInstance().getProperties().setMediumFontSize(medium);
                ComarSystem.getInstance().getProperties().setLargeFontSize(large);
                ComarSystem.getInstance().getProperties().save();

                ComarUtils.showInfo("Los cambios ser haran efectivos al reiniciar la aplicacion");
            }
        });

        WebPanel panelButtons = new WebPanel(new FlowLayout(FlowLayout.RIGHT), webButton);
        panelMain.add(panelButtons, BorderLayout.SOUTH);

        return panelAux;
    }
}
