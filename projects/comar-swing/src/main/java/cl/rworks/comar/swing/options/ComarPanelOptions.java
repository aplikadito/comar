/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.options;

import cl.rworks.comar.swing.ComarFrame;
import cl.rworks.comar.swing.util.ComarPanelTitle;
import cl.rworks.comar.swing.ComarSystem;
import com.alee.extended.layout.FormLayout;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.spinner.WebSpinner;
import com.alee.laf.text.WebTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelOptions extends WebPanel {

    private WebPanel panelForm;

    public ComarPanelOptions() {
        setLayout(new BorderLayout());
        add(new ComarPanelTitle("Opciones"), BorderLayout.NORTH);

        panelForm = new WebPanel(new FormLayout(false, true, 10, 10));
//        panelForm.setBorder(new EmptyBorder(10, 10, 10, 10));

        WebLabel label = new WebLabel("Tamaño Letra");

        WebSpinner spinner = new WebSpinner(new SpinnerNumberModel(10, 10, 30, 1));
        panelForm.add(label);
        panelForm.add(spinner);

        panelForm.add(new WebLabel("Nombre"));
        panelForm.add(new WebTextField());

//        panelTitled.setContent(panelForm);

        add(panelForm, BorderLayout.CENTER);

        WebButton webButton = new WebButton("Aplicar");
        webButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                FontUIResource font = (FontUIResource) UIManager.get("Label.font");
                Font deriveFont = new Font(font.getFamily(), font.getStyle(), (Integer) spinner.getValue());
                UIManager.put("Label.font", new FontUIResource(deriveFont));

                ComarFrame frame = ComarSystem.getInstance().getFrame();
                SwingUtilities.updateComponentTreeUI(frame);
            }
        });
        add(new WebPanel(new FlowLayout(FlowLayout.RIGHT), webButton), BorderLayout.SOUTH);
    }
}
