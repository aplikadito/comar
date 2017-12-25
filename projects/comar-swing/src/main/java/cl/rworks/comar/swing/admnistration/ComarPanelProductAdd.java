/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.swing.util.ComarPanelSubtitle;
import com.alee.extended.layout.FormLayout;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelProductAdd extends WebPanel {

    private WebPanel panelContent;
    private WebPanel panelForm;

    public ComarPanelProductAdd() {
        initValues();
    }

    private void initValues() {
        setLayout(new BorderLayout());

        add(new ComarPanelSubtitle("Agregar Producto"), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);
    }

    private WebPanel buildContent() {
        panelContent = new WebPanel(new BorderLayout());
        panelContent.setBorder(new EmptyBorder(10, 10, 10, 10));

        WebPanel panelFormContainer = new WebPanel();
        panelFormContainer.setLayout(new BoxLayout(panelFormContainer, BoxLayout.PAGE_AXIS));

        panelFormContainer.add(buildForm());
        panelFormContainer.add(buildFormButtons());
        panelContent.add(panelFormContainer, BorderLayout.CENTER);

        return panelContent;
    }

    private WebPanel buildForm() {
        panelForm = new WebPanel(new FormLayout(false, true, 10, 10));
        panelForm.setMinimumSize(new Dimension(300, 100));
        panelForm.setPreferredSize(new Dimension(300, 100));
        panelForm.setMaximumSize(new Dimension(300, 100));
        panelForm.setAlignmentX(0.0f);

        panelForm.add(new WebLabel("Codigo"));
        panelForm.add(new WebTextField());

        panelForm.add(new WebLabel("Nombre"));
        panelForm.add(new WebTextField());

        return panelForm;
    }

    private WebPanel buildFormButtons() {
        WebPanel panelFormButtons = new WebPanel(new FlowLayout());
        panelFormButtons.setMinimumSize(new Dimension(300, 30));
        panelFormButtons.setPreferredSize(new Dimension(300, 30));
        panelFormButtons.setMaximumSize(new Dimension(300, 30));
        panelFormButtons.setAlignmentX(0.0f);

        panelFormButtons.add(new WebButton("Crear"));

        return panelFormButtons;
    }

}
