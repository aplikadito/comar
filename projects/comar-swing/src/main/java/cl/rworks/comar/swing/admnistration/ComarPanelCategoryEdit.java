/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.core.data.ComarCategoryKite;
import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.util.ComarIconLoader;
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
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelCategoryEdit extends WebPanel {

    private WebPanel panelContent;
    private WebPanel panelForm;
    //
    private WebTextField textName;
    //
    private WebPanel panelFormButtons;
    private String oldName = null;

    public ComarPanelCategoryEdit() {
        initValues();
    }

    private void initValues() {
        setLayout(new BorderLayout());

        add(new ComarPanelSubtitle("Editar Categoria"), BorderLayout.NORTH);
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
        panelForm.setMaximumSize(new Dimension(300, 200));
        panelForm.setAlignmentX(0.0f);

        textName = new WebTextField();
        textName.setFocusable(true);
        panelForm.add(new WebLabel("Nombre"));
        panelForm.add(textName);

        return panelForm;
    }

    private WebPanel buildFormButtons() {
        panelFormButtons = new WebPanel(new FlowLayout());
        panelFormButtons.setMinimumSize(new Dimension(300, 30));
        panelFormButtons.setPreferredSize(new Dimension(300, 30));
        panelFormButtons.setMaximumSize(new Dimension(300, 30));
        panelFormButtons.setAlignmentX(0.0f);

        WebButton buttonOk = new WebButton(new OkAction());
        buttonOk.setFocusable(true);
        panelFormButtons.add(buttonOk);

        WebButton buttonClear = new WebButton(new ClearAction());
        buttonClear.setFocusable(true);
        panelFormButtons.add(buttonClear);

        return panelFormButtons;
    }

    public void updateForm(ComarCategory category) {
        this.textName.setText(category.getName());
        this.textName.selectAll();

        this.oldName = category.getName();
    }

    private class OkAction extends AbstractAction {

        public OkAction() {
            putValue(NAME, "Aceptar");
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

            JTransaction jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
            JTransaction.setCurrent(jtx);

            try {
                ComarCategory cat = ComarCategoryKite.getByName(strName);
                if (cat != null) {
                    TooltipManager.showOneTimeTooltip(textName, null, ComarIconLoader.load(ComarIconLoader.ERROR), "El nombre ya existe", TooltipWay.trailing);
                    validate = false;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                ComarUtils.showWarn(ex.getMessage());
            } finally {
                jtx.rollback();
                JTransaction.setCurrent(null);
            }

            if (oldName == null) {
                ComarUtils.showWarn("Nombre original de la categoria no encontrada");
            }

            if (!validate) {
                return;
            }

            jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
            JTransaction.setCurrent(jtx);
            try {
                ComarCategory cat = ComarCategoryKite.getByName(oldName);
                cat.setName(strName);
                jtx.commit();

                ComarUtils.showInfo("Categoria editada");
            } catch (Exception ex) {
                jtx.rollback();
                ex.printStackTrace();
                ComarUtils.showWarn(ex.getMessage());
            } finally {
                JTransaction.setCurrent(null);
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

    public WebPanel getPanelFormButtons() {
        return panelFormButtons;
    }

}
