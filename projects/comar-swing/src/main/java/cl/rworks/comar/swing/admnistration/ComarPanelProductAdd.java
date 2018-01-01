/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.model.ComarDecimalFormat;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.model.ComarUnit;
import cl.rworks.comar.core.service.ComarDaoCategory;
import cl.rworks.comar.core.service.ComarDaoException;
import cl.rworks.comar.core.service.ComarDaoFactory;
import cl.rworks.comar.core.service.ComarDaoProduct;
import cl.rworks.comar.core.service.ComarDaoService;
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
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;
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
public class ComarPanelProductAdd extends WebPanel {

    private WebPanel panelContent;
    private WebPanel panelForm;
    //
    private WebTextField textCode;
    private WebTextField textName;
    private WebComboBox comboCategory;
    private WebComboBox comboUnit;
    private WebComboBox comboFormat;

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

        comboCategory = new WebComboBox(loadCategories());
        panelForm.add(new WebLabel("Categoria"));
        panelForm.add(comboCategory);

        comboCategory.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null) {
                    ComarCategory c = (ComarCategory) value;
                    label.setText(c.getName());
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

    private List<ComarCategory> loadCategories() {
        ComarDaoService service = ComarSystem.getInstance().getDaoService();

        try {
            service.openTransaction();
            ComarDaoCategory daoCat = ComarDaoFactory.getDaoCategory();
            List<ComarCategory> cats = daoCat.getAll();

            service.rollback();
            return cats;
        } catch (ComarDaoException ex) {
            ex.printStackTrace();
            return Collections.EMPTY_LIST;
        } finally {
            service.closeTransaction();
        }
    }

    private WebPanel buildFormButtons() {
        WebPanel panelFormButtons = new WebPanel(new FlowLayout());
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
            ComarDaoService daoService = ComarSystem.getInstance().getDaoService();

            boolean validate = true;
            String strCode = textCode.getText();
            if (strCode == null || strCode.isEmpty()) {
                TooltipManager.showOneTimeTooltip(textCode, null, ComarIconLoader.load(ComarIconLoader.ERROR), "Codigo nulo o vacio", TooltipWay.trailing);
                validate = false;
            }

            try {
                daoService.openTransaction();

                ComarProduct product = ComarDaoFactory.getDaoProduct().getByCode(strCode);
                if (product != null) {
                    TooltipManager.showOneTimeTooltip(textCode, null, ComarIconLoader.load(ComarIconLoader.ERROR), "El codigo ya existe", TooltipWay.trailing);
                    validate = false;
                }
                daoService.rollback();
            } catch (ComarDaoException ex) {
                daoService.rollback();
                ex.printStackTrace();
            } finally {
                daoService.closeTransaction();
            }

            String strName = textName.getText();
            if (strName == null || strName.isEmpty()) {
                TooltipManager.showOneTimeTooltip(textName, null, ComarIconLoader.load(ComarIconLoader.ERROR), "Nombre nulo o vacio", TooltipWay.trailing);
                validate = false;
            }

            ComarCategory category = (ComarCategory) comboCategory.getSelectedItem();
            ComarUnit unit = (ComarUnit) comboUnit.getSelectedItem();
            ComarDecimalFormat format = (ComarDecimalFormat) comboFormat.getSelectedItem();

            if (validate) {
                try {
                    daoService.openTransaction();
                    ComarDaoProduct daoProduct = ComarDaoFactory.getDaoProduct();
                    ComarProduct product = daoProduct.create();
                    product.setCode(strCode);
                    product.setName(strName);
                    product.setCategory(category);
                    product.setUnit(unit);
                    product.setDecimalFormat(format);
                    daoService.commit();

                    ComarUtils.showInfo("Producto Agregado");
                    clear();
                } catch (ComarDaoException ex) {
                    daoService.rollback();
                    ex.printStackTrace();
                    ComarUtils.showWarn(ex.getMessage());
                } finally {
                    daoService.closeTransaction();
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

}
