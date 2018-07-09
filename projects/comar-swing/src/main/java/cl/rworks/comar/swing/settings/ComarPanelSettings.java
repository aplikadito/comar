/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.settings;

import cl.rworks.comar.swing.util.ComarPanelTitle;
import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.properties.ComarProperties;
import cl.rworks.comar.swing.util.ComarButton;
import cl.rworks.comar.swing.util.ComarLabel;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarTextField;
import cl.rworks.comar.swing.util.ComarUtils;
import com.alee.extended.layout.FormLayout;
import com.alee.laf.button.WebButton;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.spinner.WebSpinner;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelSettings extends ComarPanel {

    private ComarProperties properties = ComarSystem.getInstance().getProperties();
    private ComarTextField textIva;
    private WebSpinner spinnerNormal;
    private WebCheckBox checkHelpActive;
    private ComarLabel labelColorBannerValue;
    private ComarLabel labelColorBackgroundValue;

    public ComarPanelSettings() {
        setLayout(new BorderLayout());

        add(new ComarPanelTitle("Opciones"), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);
    }

    private ComarPanel buildContent() {

        ComarPanel panelMain = new ComarPanel();
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.PAGE_AXIS));
//        panelMain.setMinimumSize(new Dimension(400, 100));
//        panelMain.setPreferredSize(new Dimension(400, 100));
        panelMain.setMaximumSize(new Dimension(450, 2000));
        panelMain.setAlignmentX(0.0f);
        panelMain.setAlignmentY(0.0f);
        panelMain.setBorder(new EmptyBorder(10, 10, 10, 10));

        ComarPanel panelForm = new ComarPanel(new FormLayout(false, true, 10, 10));
        panelForm.setMaximumSize(new Dimension(450, 250));
        panelMain.add(panelForm, BorderLayout.CENTER);

        // IVA
        ComarLabel labelIva = new ComarLabel("IVA (%)");
        textIva = new ComarTextField(10);
        textIva.setText(ComarUtils.formatIva(properties.getPercentualIva()));
        textIva.setHorizontalAlignment(SwingUtilities.RIGHT);
        panelForm.add(labelIva);
        panelForm.add(textIva);

        // FONT SIZE
        ComarLabel labelFontSize = new ComarLabel("Tamaño Letra");
        spinnerNormal = new WebSpinner(new SpinnerNumberModel(properties.getFontSize(), 12, 30, 1));
        spinnerNormal.setFontSize(properties.getFontSize());
        panelForm.add(labelFontSize);
        panelForm.add(spinnerNormal);

        // COLOR BANNER
        labelColorBannerValue = new ComarLabel();
        labelColorBannerValue.setOpaque(true);
        labelColorBannerValue.setBackground(properties.getBannerColor());
        labelColorBannerValue.setBorder(new LineBorder(Color.BLACK));
        labelColorBannerValue.setPreferredSize(new Dimension(22, 22));

        ComarPanel panelBanner = new ComarPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelBanner.add(labelColorBannerValue);
        panelBanner.add(new WebButton(new ChangeColorAction(labelColorBannerValue, properties.getBannerColor())));

        panelForm.add(new ComarLabel("Color Titulo"));
        panelForm.add(panelBanner);

        // COLOR BACKGROUND
        labelColorBackgroundValue = new ComarLabel();
        labelColorBackgroundValue.setOpaque(true);
        labelColorBackgroundValue.setBackground(properties.getBackgroundColor());
        labelColorBackgroundValue.setBorder(new LineBorder(Color.BLACK));
        labelColorBackgroundValue.setPreferredSize(new Dimension(22, 22));

        ComarPanel panelBackground = new ComarPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelBackground.add(labelColorBackgroundValue);
        panelBackground.add(new WebButton(new ChangeColorAction(labelColorBackgroundValue, properties.getBackgroundColor())));

        panelForm.add(new ComarLabel("Color Fondo"));
        panelForm.add(panelBackground);

        // HELP ACTIVE
        ComarLabel labelHelpActive = new ComarLabel("Mostrar Ayuda");
        checkHelpActive = new WebCheckBox();
        checkHelpActive.setSelected(properties.isHelpActive());
        panelForm.add(labelHelpActive);
        panelForm.add(checkHelpActive);

        // BOTONES
        ComarButton buttonApply = new ComarButton("Aplicar");
        buttonApply.addActionListener(new ApplyListener());

        ComarButton buttonFactoryValues = new ComarButton("Reset");
        buttonFactoryValues.addActionListener(new FactoryValuesListener());

        ComarPanel panelButtons = new ComarPanel(new FlowLayout(FlowLayout.CENTER));
        panelButtons.add(buttonApply);
        panelButtons.add(buttonFactoryValues);
        panelMain.add(Box.createVerticalStrut(20));
        panelMain.add(panelButtons);
        
        panelMain.add(Box.createVerticalGlue());

        ComarPanel panelAux = new ComarPanel();
        panelAux.setBorder(new EmptyBorder(30, 30, 30, 30));
        panelAux.setLayout(new BoxLayout(panelAux, BoxLayout.PAGE_AXIS));
        panelAux.add(panelMain);

        return panelAux;
    }

    private class ApplyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int iva = 0;
            try {
                String strText = textIva.getText();
                iva = Integer.parseInt(strText);
            } catch (NumberFormatException ex) {
                ComarUtils.showWarn(ComarPanelSettings.this, "IVA no valido");
                return;
            }

            if (iva < 0 || iva > 100) {
                ComarUtils.showWarn(ComarPanelSettings.this, "IVA debe ser un valor entre 0 y 100");
                return;
            }

            int normal = (Integer) spinnerNormal.getValue();
            Color bannerColor = labelColorBannerValue.getBackground();
            Color backgroundColor = labelColorBackgroundValue.getBackground();

            boolean helpActive = checkHelpActive.isSelected();

            properties.setPercentualIva(iva);
            properties.setFontSize(normal);
            properties.setFontSize(normal);
            properties.setBannerColor(bannerColor);
            properties.setBackgroundColor(backgroundColor);
            properties.setHelpActive(helpActive);
            properties.save();
            ComarUtils.showInfo("Los cambios se haran efectivos al reiniciar la aplicacion");
        }
    }

    private class FactoryValuesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int response = ComarUtils.showYesNo(null, "Desea cargar los valores por defecto de la aplicaciones?", "Pregunta");
            if (response == JOptionPane.YES_OPTION) {
                properties.loadDefaultValues();
                properties.save();
                ComarUtils.showInfo("Los cambios se haran efectivos al reiniciar la aplicacion");
            }

        }
    }

    private class ChangeColorAction extends AbstractAction {

        private ComarLabel label;
        private Color color;

        private ChangeColorAction(ComarLabel label, Color color) {
            this.label = label;
            this.color = color;

            putValue(NAME, "Cambiar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Color value = JColorChooser.showDialog(null, "Color", color);
            if (value != null) {
                label.setBackground(value);
            }
        }

    }
}
