/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.core.data.ComarCategoryKite;
import cl.rworks.comar.core.data.ComarProductKite;
import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.swing.properties.ComarProperties;
import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.util.ComarIconLoader;
import cl.rworks.comar.swing.util.ComarPanelCard;
import cl.rworks.comar.swing.util.ComarPanelSubtitle;
import cl.rworks.comar.swing.util.ComarUtils;
import com.alee.extended.layout.FormLayout;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;
import io.permazen.JTransaction;
import io.permazen.Permazen;
import io.permazen.ValidationMode;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelCategoryAdd extends ComarPanelCard {

    private WebPanel panelContent;
    private WebPanel panelForm;
    private WebPanel panelFormButtons;
    //
    private WebTextField textName;
    private WebTextField textTax;
    //
    private DecimalFormat df = new DecimalFormat("#0%");

    public ComarPanelCategoryAdd() {
        initValues();
    }

    private void initValues() {
        setLayout(new BorderLayout());

        add(new ComarPanelSubtitle("Agregar Categoria"), BorderLayout.NORTH);
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

        textName = new WebTextField();
        textName.setFocusable(true);
        panelForm.add(new WebLabel("Nombre"));
        panelForm.add(textName);

        textTax = new WebTextField();
        textTax.setFocusable(true);
        textTax.setText(df.format(ComarSystem.getInstance().getProperties().getDefaultTax()));
        panelForm.add(new WebLabel("Impuestos"));
        panelForm.add(textTax);

        return panelForm;
    }

    private WebPanel buildFormButtons() {
        panelFormButtons = new WebPanel(new FlowLayout());
        panelFormButtons.setMinimumSize(new Dimension(300, 30));
        panelFormButtons.setPreferredSize(new Dimension(300, 30));
        panelFormButtons.setMaximumSize(new Dimension(300, 30));
        panelFormButtons.setAlignmentX(0.0f);

        WebButton buttonOk = new WebButton(new AddAction());
        buttonOk.setFocusable(true);
        panelFormButtons.add(buttonOk);

        WebButton buttonClear = new WebButton(new ClearAction());
        buttonClear.setFocusable(true);
        panelFormButtons.add(buttonClear);

        return panelFormButtons;
    }

    public void updateForm() {
    }

    public JPanel getPanelFormButtons() {
        return panelFormButtons;
    }

    private class AddAction extends AbstractAction {

        public AddAction() {
            putValue(NAME, "Agregar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ComarService service = ComarSystem.getInstance().getService();
            Permazen db = service.getKitedb().get();

            boolean validate = true;
            String strName = textName.getText();
            if (strName == null || strName.isEmpty()) {
                TooltipManager.showOneTimeTooltip(textName, null, ComarIconLoader.load(ComarIconLoader.ERROR), "Nombre nulo o vacio", TooltipWay.trailing);
                validate = false;
            }

            double tax = -1;
            String strTax = textTax.getText();
            if (strTax == null || strTax.isEmpty()) {
                TooltipManager.showOneTimeTooltip(textTax, null, ComarIconLoader.load(ComarIconLoader.ERROR), "Impuesto nulo o vacio", TooltipWay.trailing);
                validate = false;
            } else {
                try {
                    tax = df.parse(strTax).doubleValue();
                    if (tax < 0 || tax > 1) {
                        tax = -1;
                        TooltipManager.showOneTimeTooltip(textTax, null, ComarIconLoader.load(ComarIconLoader.ERROR), "Impuesto con formato erroneo", TooltipWay.trailing);
                        validate = false;
                    }
                } catch (ParseException ex) {
                    TooltipManager.showOneTimeTooltip(textTax, null, ComarIconLoader.load(ComarIconLoader.ERROR), "Impuesto con formato erroneo", TooltipWay.trailing);
                    validate = false;
                }
            }

            JTransaction jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
            JTransaction.setCurrent(jtx);
            try {
                ComarProductKite product = ComarProductKite.getByCode(strName);
                if (product != null) {
                    TooltipManager.showOneTimeTooltip(textName, null, ComarIconLoader.load(ComarIconLoader.ERROR), "El nombre ya existe", TooltipWay.trailing);
                    validate = false;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                jtx.rollback();
                JTransaction.setCurrent(null);
            }

            if (validate) {
                jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
                JTransaction.setCurrent(jtx);
                try {

                    ComarCategory cat = ComarCategoryKite.create();
                    cat.setName(strName);
                    cat.setTax(tax);
                    jtx.commit();

                    ComarUtils.showInfo("Categoria agregada");
                    clear();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    ComarUtils.showWarn(ex.getMessage());
                } finally {
                    JTransaction.setCurrent(null);
                }
            }
        }

    }

    private class ClearAction extends AbstractAction {

        public ClearAction() {
            putValue(NAME, "Limpiar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            clear();
        }

    }

    private void clear() {
        this.textName.clear();
    }

}
