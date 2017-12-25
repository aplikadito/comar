/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.swing.util.ComarIconLoader;
import cl.rworks.comar.swing.util.ComarPanelSubtitle;
import cl.rworks.comar.swing.util.ComarValidation;
import cl.rworks.comar.swing.util.ComarValidationException;
import com.alee.extended.layout.FormLayout;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.notification.NotificationIcon;
import static com.alee.managers.notification.NotificationIcon.tip;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.notification.WebInnerNotification;
import com.alee.managers.notification.WebNotification;
import com.alee.managers.tooltip.TooltipManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelProductAdd extends WebPanel {

    private WebPanel panelContent;
    private WebPanel panelForm;
    //
    private WebTextField textCode;
    private WebLabel labelError;
    private WebTextField textName;

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

        textCode = new WebTextField(20);
        panelForm.add(new WebLabel("Codigo"));

        labelError = new WebLabel(ComarIconLoader.load(ComarIconLoader.ERROR));
        labelError.setVisible(false);
        Box box = Box.createHorizontalBox();
        box.add(textCode);
        box.add(labelError);
        panelForm.add(box);

        textName = new WebTextField();
        panelForm.add(new WebLabel("Nombre"));
        panelForm.add(textName);

        return panelForm;
    }

    private WebPanel buildFormButtons() {
        WebPanel panelFormButtons = new WebPanel(new FlowLayout());
        panelFormButtons.setMinimumSize(new Dimension(300, 30));
        panelFormButtons.setPreferredSize(new Dimension(300, 30));
        panelFormButtons.setMaximumSize(new Dimension(300, 30));
        panelFormButtons.setAlignmentX(0.0f);

        panelFormButtons.add(new WebButton(new AddAction()));
        panelFormButtons.add(new WebButton(new ClearAction()));

        return panelFormButtons;
    }

    private class AddAction extends AbstractAction {

        public AddAction() {
            putValue(NAME, "Agregar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean validate = true;
            String strCode = textCode.getText();
            if (strCode == null || strCode.isEmpty()) {
                TooltipManager.showOneTimeTooltip(textCode, null, ComarIconLoader.load(ComarIconLoader.ERROR), "Codigo nulo o vacio", TooltipWay.trailing);
                validate = false;
            }

            String strName = textName.getText();
            if (strName == null || strName.isEmpty()) {
                TooltipManager.showOneTimeTooltip(textName, null, ComarIconLoader.load(ComarIconLoader.ERROR), "Nombre nulo o vacio", TooltipWay.trailing);
                validate = false;
            }

            if (validate) {
                WebInnerNotification not = new WebInnerNotification();
                not.setContent("Producto Agregado");
                not.setDisplayTime(2000);
                
                NotificationManager.showInnerNotification(not);
            }
        }

    }

    private class ClearAction extends AbstractAction {

        public ClearAction() {
            putValue(NAME, "Limpiar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }

    }

}
