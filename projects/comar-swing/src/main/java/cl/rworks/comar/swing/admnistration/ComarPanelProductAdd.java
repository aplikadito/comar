/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.core.data.ComarCategoryKite;
import cl.rworks.comar.core.data.ComarProductKite;
import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.model.ComarDecimalFormat;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.model.ComarUnit;
import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.util.ComarIconLoader;
import cl.rworks.comar.swing.util.ComarPanelCard;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
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
    private WebTextField textName;
    private WebComboBox comboCategory;
    private WebComboBox comboUnit;
    private WebComboBox comboFormat;
    //
    private WebPanel panelFormButtons;

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
        panelForm.setMaximumSize(new Dimension(300, 200));
        panelForm.setAlignmentX(0.0f);

        textCode = new WebTextField(20);
        textCode.setFocusable(true);
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
                    ComarCategory c = (ComarCategory) value;
                    label.setText(c.getName());
                } else {
                    label.setText(" ");
                }
                return label;
            }

        });

        comboUnit = new WebComboBox(ComarUnit.values());
        panelForm.add(new WebLabel("Unidad"));
        panelForm.add(comboUnit);

        comboFormat = new WebComboBox(ComarDecimalFormat.values());
        panelForm.add(new WebLabel("Formato"));
        panelForm.add(comboFormat);

        return panelForm;
    }

    public void updateForm() {
        comboCategory.removeAllItems();
        comboCategory.setEnabled(true);
        List<ComarCategory> cats = loadCategories();
        cats.forEach(e -> comboCategory.addItem(e));
    }

    private List<ComarCategory> loadCategories() {
        ComarService service = ComarSystem.getInstance().getService();
        Permazen permazen = service.getKitedb().get();

        JTransaction jtx = permazen.createTransaction(true, ValidationMode.AUTOMATIC);
        JTransaction.setCurrent(jtx);
        List<ComarCategory> list;
        try {
            list = ComarCategoryKite.getAll().stream().map(e -> (ComarCategory) e.copyOut()).collect(Collectors.toList());
        } catch (Exception ex) {
            ex.printStackTrace();
            list = new ArrayList<>();
        } finally {
            jtx.rollback();
            JTransaction.setCurrent(null);
        }

        list.add(0, null);
        return list;
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

    private class AddAction extends AbstractAction {

        public AddAction() {
            putValue(NAME, "Agregar");
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

            JTransaction jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
            JTransaction.setCurrent(jtx);
            try {
                ComarProductKite product = ComarProductKite.getByCode(strCode);
                if (product != null) {
                    TooltipManager.showOneTimeTooltip(textCode, null, ComarIconLoader.load(ComarIconLoader.ERROR), "El codigo ya existe", TooltipWay.trailing);
                    validate = false;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                jtx.rollback();
                JTransaction.setCurrent(null);
            }

            String strName = textName.getText();
            if (strName == null || strName.isEmpty()) {
                TooltipManager.showOneTimeTooltip(textName, null, ComarIconLoader.load(ComarIconLoader.ERROR), "Nombre nulo o vacio", TooltipWay.trailing);
                validate = false;
            }

            Object item = comboCategory.getSelectedItem();
            ComarCategory category = item instanceof ComarCategory ? (ComarCategory) item : null;
            ComarUnit unit = (ComarUnit) comboUnit.getSelectedItem();
            ComarDecimalFormat format = (ComarDecimalFormat) comboFormat.getSelectedItem();

            if (validate) {
                jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
                JTransaction.setCurrent(jtx);
                try {

                    ComarProduct product = ComarProductKite.create();
                    product.setCode(strCode);
                    product.setName(strName);
                    product.setCategory(category);
                    product.setUnit(unit);
                    product.setDecimalFormat(format);
                    jtx.commit();

                    ComarUtils.showInfo("Producto Agregado");
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
        this.textCode.clear();
        this.textName.clear();
    }

    public WebPanel getPanelFormButtons() {
        return panelFormButtons;
    }

}
