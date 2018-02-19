/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.core.data.ComarCategoryKite;
import cl.rworks.comar.core.data.ComarProductKite;
import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.model.ComarUnit;
import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.util.ComarIconLoader;
import cl.rworks.comar.swing.util.ComarPanelSubtitle;
import cl.rworks.comar.swing.util.ComarUtils;
import com.alee.extended.layout.FormLayout;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;
import io.permazen.JTransaction;
import io.permazen.Permazen;
import io.permazen.ValidationMode;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelProductEdit extends WebPanel {

    private WebPanel panelContent;
    private WebPanel panelForm;
    //
    private WebTextField textCode;
    private WebTextField textName;
    private WebComboBox comboCategory;
    private WebComboBox comboUnit;
    //
    private WebPanel panelFormButtons;

    public ComarPanelProductEdit() {
        initValues();
    }

    private void initValues() {
        setLayout(new BorderLayout());

        add(new ComarPanelSubtitle("Editar Producto"), BorderLayout.NORTH);
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

        textCode = new WebTextField(20);
        textCode.setFocusable(false);
        textCode.setEditable(false);
        textCode.setEnabled(false);
        textCode.setBoldFont();

        panelForm.add(new WebLabel("Codigo"));
        panelForm.add(textCode);

        textName = new WebTextField();
        textName.setFocusable(true);
        panelForm.add(new WebLabel("Nombre"));
        panelForm.add(textName);

        comboCategory = new WebComboBox();
        panelForm.add(new WebLabel("Categoria"));
        panelForm.add(comboCategory);

        comboCategory.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null) {
//                    ComarCategory c = (ComarCategory) value;
                    label.setText((String) value);
                } else {
                    label.setText(" ");
                }
                return label;
            }

        });

        comboUnit = new WebComboBox(ComarUnit.values());
        panelForm.add(new WebLabel("Unidad"));
        panelForm.add(comboUnit);

        return panelForm;
    }

    private List<ComarCategory> loadCategories() {
        ComarService service = ComarSystem.getInstance().getService();
        Permazen db = service.getKitedb().get();

        List<ComarCategory> list;
        JTransaction jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
        JTransaction.setCurrent(jtx);
        try {
            list = ComarCategoryKite.getAll().stream().map(e -> (ComarCategory) e.copyOut()).collect(Collectors.toList());
        } catch (Exception ex) {
            ex.printStackTrace();
            list = Collections.EMPTY_LIST;
        } finally {
            jtx.rollback();
            JTransaction.setCurrent(null);
        }
        return list;
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

    public void updateForm(ComarProduct productRef) {
        comboCategory.removeAllItems();
        comboCategory.setEnabled(true);

        comboCategory.addItem(" ");
        List<ComarCategory> cats = loadCategories();
        cats.forEach(e -> comboCategory.addItem(e.getName()));

        ComarService service = ComarSystem.getInstance().getService();
        Permazen db = service.getKitedb().get();
        JTransaction jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
        JTransaction.setCurrent(jtx);
        ComarProduct product;
        try {
            product = (ComarProduct) ComarProductKite.getByCode(productRef.getCode()).copyOut("category");
            jtx.rollback();
        } finally {
            JTransaction.setCurrent(null);
        }

        if (product == null) {
            throw new RuntimeException("Error grave, producto no encontrado: " + productRef.getCode() + " - " + productRef.getName());
        }

        this.textCode.setText(product.getCode());
        this.textName.setText(product.getName());
        if (product.getCategory() != null) {
            this.comboCategory.setSelectedItem(product.getCategory().getName());
        }
        this.comboUnit.setSelectedItem(product.getUnit());
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

            String catName = (String) comboCategory.getSelectedItem();
            ComarUnit unit = (ComarUnit) comboUnit.getSelectedItem();

            if (!validate) {
                return;
            }

            JTransaction jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
            JTransaction.setCurrent(jtx);
            try {
                ComarCategory cat = null;
                if (!catName.trim().isEmpty()) {
                    cat = ComarCategoryKite.getByName(catName);
                }

                ComarProduct product = ComarProductKite.getByCode(strCode);
                product.setName(strName);
                product.setCategory(cat);
                product.setUnit(unit);
                jtx.commit();

                ComarUtils.showInfo("Producto editado");
//                clear();
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
        this.textCode.clear();
        this.textName.clear();
        this.comboUnit.setSelectedIndex(0);
    }

    public WebPanel getPanelFormButtons() {
        return panelFormButtons;
    }

}
