/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.settings;

import cl.rworks.comar.swing.util.ComarPanelTitle;
import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.properties.ComarProperties;
import cl.rworks.comar.swing.util.ComarLabel;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarPanelCard;
import cl.rworks.comar.swing.util.ComarUtils;
import com.alee.extended.layout.FormLayout;
import com.alee.laf.button.WebButton;
import com.alee.laf.checkbox.WebCheckBox;
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
    private WebCheckBox checkHelpActive;

    public ComarPanelSettings() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(30, 30, 30, 30));

        add(new ComarPanelTitle("Opciones"), BorderLayout.NORTH);
        add(build(), BorderLayout.CENTER);
    }

    private ComarPanel build() {
        ComarProperties properties = ComarSystem.getInstance().getProperties();

        ComarPanel panelMain = new ComarPanel(new BorderLayout());
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.PAGE_AXIS));
//        panelMain.setMinimumSize(new Dimension(400, 100));
//        panelMain.setPreferredSize(new Dimension(400, 100));
        panelMain.setMaximumSize(new Dimension(450, 2000));
        panelMain.setAlignmentX(0.0f);
        panelMain.setAlignmentY(0.0f);
        panelMain.setBorder(new EmptyBorder(10, 10, 10, 10));

        ComarPanel panelForm = new ComarPanel(new FormLayout(false, true, 10, 10));
        panelMain.add(panelForm, BorderLayout.CENTER);

        // FONT SIZE
        ComarLabel labelFontSize = new ComarLabel("Tamaño Letra");
        labelFontSize.setFontSize(properties.getFontSize());
        spinnerNormal = new WebSpinner(new SpinnerNumberModel(properties.getFontSize(), 12, 30, 1));
        spinnerNormal.setFontSize(properties.getFontSize());
        panelForm.add(labelFontSize);
        panelForm.add(spinnerNormal);

        // HELP ACTIVE
        ComarLabel labelHelpActive = new ComarLabel("Mostrar Ayuda");
        checkHelpActive = new WebCheckBox();
        checkHelpActive.setSelected(properties.isHelpActive());
        panelForm.add(labelHelpActive);
        panelForm.add(checkHelpActive);

        WebButton webButton = new WebButton("Aplicar");
        webButton.setFontSize(properties.getFontSize());
        webButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int normal = (Integer) spinnerNormal.getValue();
                boolean helpActive = checkHelpActive.isSelected();

                ComarSystem.getInstance().getProperties().setFontSize(normal);
                ComarSystem.getInstance().getProperties().setHelpActive(helpActive);
                ComarSystem.getInstance().getProperties().save();

                ComarUtils.showInfo("Los cambios ser haran efectivos al reiniciar la aplicacion");
            }
        });

        ComarPanel panelButtons = new ComarPanel(new FlowLayout(FlowLayout.RIGHT));
        panelButtons.add(webButton);
        panelForm.add(panelButtons);

        ComarPanel panelAux = new ComarPanel();
        panelAux.setLayout(new BoxLayout(panelAux, BoxLayout.PAGE_AXIS));
        panelAux.add(panelMain);

        return panelAux;
    }
}
