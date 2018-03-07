/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.core.data.ComarCategoryKite;
import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.model.ComarMetric;
import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.util.ComarPanelSubtitle;
import com.alee.extended.layout.FormLayout;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;
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
public class ComarPanelProductEditor extends WebPanel {

    private WebPanel panelContent;
    private WebPanel panelForm;
    //
    private WebTextField textCode;
    private WebTextField textName;
    private WebComboBox comboCategory;
    private WebComboBox comboMetric;
    //
    private WebPanel panelFormButtons;
    private int fontSize = ComarSystem.getInstance().getProperties().getNormalFontSize();

    public ComarPanelProductEditor() {
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
        panelForm.setMinimumSize(new Dimension(100, 100));
        panelForm.setPreferredSize(new Dimension(300, 250));
        panelForm.setMaximumSize(new Dimension(300, 300));
        panelForm.setAlignmentX(0.0f);

        textCode = new WebTextField(20);
        textCode.setFocusable(true);
        textCode.setFontSize(fontSize);

        WebLabel label = new WebLabel("Codigo");
        label.setFontSize(fontSize);
        panelForm.add(label);
        panelForm.add(textCode);

        textName = new WebTextField();
        textName.setFocusable(true);
        textName.setFontSize(fontSize);

        label = new WebLabel("Nombre");
        label.setFontSize(fontSize);
        panelForm.add(label);
        panelForm.add(textName);

        comboCategory = new WebComboBox();
        comboCategory.setFontSize(fontSize);

        label = new WebLabel("Categoria");
        label.setFontSize(fontSize);
        panelForm.add(label);
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

        comboMetric = new WebComboBox(ComarMetric.values());
        comboMetric.setFontSize(fontSize);

        label = new WebLabel("Medida");
        label.setFontSize(fontSize);
        panelForm.add(label);
        panelForm.add(comboMetric);

        return panelForm;
    }

    public void updateForm(ComarPanelProductRow row) {
        comboCategory.removeAllItems();
        comboCategory.setEnabled(true);
        loadCategories().forEach(e -> comboCategory.addItem(e));

        this.textCode.setText(row.getCode() != null ? row.getCode() : "");
        this.textName.setText(row.getName() != null ? row.getName() : "");
        this.comboCategory.setSelectedItem(row.getCategoryName());
        this.comboMetric.setSelectedItem(row.getMetric() != null ? row.getMetric() : ComarMetric.UNIDAD);
    }

    private List<String> loadCategories() {
        ComarService service = ComarSystem.getInstance().getService();
        Permazen permazen = service.getKitedb().get();

        JTransaction jtx = permazen.createTransaction(true, ValidationMode.AUTOMATIC);
        JTransaction.setCurrent(jtx);
        List<String> list;
        try {
            list = ComarCategoryKite.getAll().stream().map(e -> e.getName()).collect(Collectors.toList());
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
        panelFormButtons.setPreferredSize(new Dimension(300, 40));
        panelFormButtons.setMaximumSize(new Dimension(300, 50));
        panelFormButtons.setAlignmentX(0.0f);

        return panelFormButtons;
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

    public WebTextField getTextCode() {
        return textCode;
    }

    public WebTextField getTextName() {
        return textName;
    }

    public WebComboBox getComboCategory() {
        return comboCategory;
    }

    public WebComboBox getComboMetric() {
        return comboMetric;
    }
    
}
