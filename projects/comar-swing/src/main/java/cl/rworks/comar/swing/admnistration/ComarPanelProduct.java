/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.core.data.ComarProductKite;
import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.model.ComarMetric;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.util.ComarCloseAction;
import cl.rworks.comar.swing.util.ComarIconLoader;
import cl.rworks.comar.swing.util.ComarPanelCard;
import cl.rworks.comar.swing.util.ComarPanelSubtitle;
import cl.rworks.comar.swing.util.ComarUtils;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;
import io.permazen.JTransaction;
import io.permazen.Permazen;
import io.permazen.ValidationMode;
import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import java.util.Collections;
import java.util.stream.Collectors;
import static javax.swing.Action.NAME;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelProduct extends ComarPanelCard {

    private ComarPanelBaseEditor panelEditor;
    private TableModel tableModel;

    public ComarPanelProduct() {
        setLayout(new BorderLayout());
        add(new ComarPanelSubtitle("Productos"), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);
    }

    private WebPanel buildContent() {
        WebPanel panel = new WebPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(0, 10, 10, 10));

        this.panelEditor = new ComarPanelBaseEditor();
        panel.add(panelEditor, BorderLayout.CENTER);

        this.tableModel = new TableModel();
        WebTable table = panelEditor.getTable();
        table.setModel(tableModel);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                    new EditAction().actionPerformed(null);
                }
            }
        });

        WebPopupMenu popup = new WebPopupMenu();
        popup.add(new EditAction());
        popup.add(new DeleteAction());
        table.setComponentPopupMenu(popup);

        this.panelEditor.getButtonSearch().setAction(new SearchAction());
        this.panelEditor.getButtonClear().setAction(new ClearAction());
        this.panelEditor.getButtonAdd().setAction(new AddAction());
        this.panelEditor.getButtonEdit().setAction(new EditAction());
        this.panelEditor.getButtonDelete().setAction(new DeleteAction());

        return panel;
    }

    private class TableModel extends AbstractTableModel {

        private String[] columnNames = new String[]{"Codigo", "Nombre", "Categoria", "Unidad"};

        private List<ComarPanelProductRow> products;

        public List<ComarPanelProductRow> getProducts() {
            return products;
        }

        public void setProducts(List<ComarPanelProductRow> products) {
            this.products = products;
        }

        @Override
        public int getRowCount() {
            return products != null ? products.size() : 0;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            ComarPanelProductRow p = products.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return p.getCode();
                case 1:
                    return p.getName();
                case 2:
                    return p.getCategoryName();
                case 3:
                    return p.getMetric().getName();
                default:
                    return "";
            }
        }

    }

    private class SearchAction extends AbstractAction {

        public SearchAction() {
            putValue(NAME, "Buscar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            search();
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

    private void search() {
        String strText = this.panelEditor.getTextSearch().getText();
        List<ComarPanelProductRow> products = loadProducts(strText);
        tableModel.setProducts(products);
        tableModel.fireTableDataChanged();
        ComarUtils.showInfo(products.size() + " productos encontrados");
    }

    private void clear() {
        this.panelEditor.getTextSearch().clear();
        this.tableModel.setProducts(null);
        this.tableModel.fireTableDataChanged();
    }

    private List<ComarPanelProductRow> loadProducts(String strText) {
        ComarService service = ComarSystem.getInstance().getService();
        Permazen db = service.getKitedb().get();

        List<ComarPanelProductRow> rows = new ArrayList<>();
        JTransaction jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
        JTransaction.setCurrent(jtx);
        try {
            if (strText.isEmpty()) {
                ComarProductKite.getAll().stream().forEach((ComarProductKite p) -> {
                    ComarPanelProductRow row = new ComarPanelProductRow();
                    row.setCode(p.getCode());
                    row.setName(p.getName());
                    row.setCategoryName(p.getCategory() != null ? p.getCategory().getName() : "");
                    row.setMetric(p.getMetric());
                    rows.add(row);
                });
            } else {
                ComarProductKite.search(strText).stream().forEach((ComarProductKite p) -> {
                    ComarPanelProductRow row = new ComarPanelProductRow();
                    row.setCode(p.getCode());
                    row.setName(p.getName());
                    row.setCategoryName(p.getCategory() != null ? p.getCategory().getName() : "");
                    row.setMetric(p.getMetric());
                    rows.add(row);
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jtx.rollback();
            JTransaction.setCurrent(null);
        }
        return rows;
    }

    private class AddAction extends AbstractAction {

        public AddAction() {
            putValue(NAME, "Agregar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JDialog dialog = new JDialog(null, "Agregar Producto", ModalityType.APPLICATION_MODAL);
            dialog.getContentPane().setLayout(new BorderLayout());

            ComarPanelProductEditor panel = new ComarPanelProductEditor();
            panel.updateForm(new ComarPanelProductRow());
            dialog.getContentPane().add(panel, BorderLayout.CENTER);

            WebButton buttonOk = new WebButton(new AddOkAction(dialog, panel));
            buttonOk.setFocusable(true);
            buttonOk.setFontSize(ComarSystem.getInstance().getProperties().getNormalFontSize());
            panel.getPanelFormButtons().add(buttonOk);

            WebButton buttonClose = new WebButton(new ComarCloseAction(dialog));
            buttonClose.setFocusable(true);
            buttonClose.setFontSize(ComarSystem.getInstance().getProperties().getNormalFontSize());
            panel.getPanelFormButtons().add(buttonClose);

            dialog.setSize(500, 400);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }

    }

    private class AddOkAction extends AbstractAction {

        private JDialog dialog;
        private ComarPanelProductEditor panel;

        private AddOkAction(JDialog dialog, ComarPanelProductEditor panel) {
            this.dialog = dialog;
            this.panel = panel;
            putValue(NAME, "Agregar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            WebTextField textCode = panel.getTextCode();
            WebTextField textName = panel.getTextName();
            WebComboBox comboCategory = panel.getComboCategory();
            WebComboBox comboMetric = panel.getComboMetric();

            ComarService service = ComarSystem.getInstance().getService();
            Permazen db = service.getKitedb().get();

            boolean validate = true;
            String strCode = textCode.getText().trim();
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
            ComarMetric unit = (ComarMetric) comboMetric.getSelectedItem();

            if (validate) {
                jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
                JTransaction.setCurrent(jtx);
                try {
                    ComarProduct product = ComarProductKite.create();
                    product.setCode(strCode);
                    product.setName(strName);
                    product.setCategory(category);
                    product.setMetric(unit);
                    jtx.commit();

//                    ComarUtils.showInfo("Producto Agregado");
                    search();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    ComarUtils.showWarn(ex.getMessage());
                } finally {
                    JTransaction.setCurrent(null);
                }

                dialog.dispose();
            }
        }

    }

    private class EditAction extends AbstractAction {

        public EditAction() {
            putValue(NAME, "Editar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int vrow = panelEditor.getTable().getSelectedRow();
            if (vrow == -1) {
                ComarUtils.showWarn("Seleccione un producto");
                return;
            }

            int mrow = panelEditor.getTable().convertRowIndexToModel(vrow);
            ComarPanelProductRow selected = tableModel.getProducts().get(mrow);
            Permazen db = ComarSystem.getInstance().getService().getKitedb().get();
            JTransaction jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
            JTransaction.setCurrent(jtx);
            ComarPanelProductRow row;
            try {
                ComarProductKite dbproduct = ComarProductKite.getByCode(selected.getCode());
                row = new ComarPanelProductRow();
                row.setCode(dbproduct.getCode());
                row.setName(dbproduct.getName());
                row.setCategoryName(dbproduct.getCategory() != null ? dbproduct.getCategory().getName() : "");
                row.setMetric(dbproduct.getMetric());
            } finally {
                jtx.rollback();
                JTransaction.setCurrent(null);
            }

            JDialog dialog = new JDialog(null, "Editar Producto", ModalityType.APPLICATION_MODAL);
            dialog.getContentPane().setLayout(new BorderLayout());

            ComarPanelProductEditor panel = new ComarPanelProductEditor();
            panel.updateForm(row);
            dialog.getContentPane().add(panel, BorderLayout.CENTER);

            WebButton buttonOk = new WebButton(new EditOkAction(dialog, panel, selected.getCode()));
            buttonOk.setFocusable(true);
            buttonOk.setFontSize(ComarSystem.getInstance().getProperties().getNormalFontSize());
            panel.getPanelFormButtons().add(buttonOk);

            WebButton buttonClose = new WebButton(new ComarCloseAction(dialog));
            buttonClose.setFocusable(true);
            buttonClose.setFontSize(ComarSystem.getInstance().getProperties().getNormalFontSize());
            panel.getPanelFormButtons().add(buttonClose);

            dialog.setSize(500, 400);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);

        }

    }

    private class EditOkAction extends AbstractAction {

        private JDialog dialog;
        private ComarPanelProductEditor panel;
        private String oldCode;

        private EditOkAction(JDialog dialog, ComarPanelProductEditor panel, String oldCode) {
            this.dialog = dialog;
            this.panel = panel;
            this.oldCode = oldCode;
            putValue(NAME, "Editar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            WebTextField textCode = panel.getTextCode();
            WebTextField textName = panel.getTextName();
            WebComboBox comboCategory = panel.getComboCategory();
            WebComboBox comboMetric = panel.getComboMetric();

            ComarService service = ComarSystem.getInstance().getService();
            Permazen db = service.getKitedb().get();

            boolean validate = true;
            String strCode = textCode.getText().trim();
            if (strCode == null || strCode.isEmpty()) {
                TooltipManager.showOneTimeTooltip(textCode, null, ComarIconLoader.load(ComarIconLoader.ERROR), "Codigo nulo o vacio", TooltipWay.trailing);
                validate = false;
            }

            if (!oldCode.equals(strCode)) {
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
            }

            String strName = textName.getText();
            if (strName == null || strName.isEmpty()) {
                TooltipManager.showOneTimeTooltip(textName, null, ComarIconLoader.load(ComarIconLoader.ERROR), "Nombre nulo o vacio", TooltipWay.trailing);
                validate = false;
            }

            Object item = comboCategory.getSelectedItem();
            ComarCategory category = item instanceof ComarCategory ? (ComarCategory) item : null;
            ComarMetric unit = (ComarMetric) comboMetric.getSelectedItem();

            if (validate) {
                JTransaction jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
                JTransaction.setCurrent(jtx);
                try {
                    ComarProductKite product = ComarProductKite.getByCode(oldCode);
                    product.setCode(strCode);
                    product.setName(strName);
                    product.setCategory(category);
                    product.setMetric(unit);
                    jtx.commit();

//                    ComarUtils.showInfo("Producto Editado");
                    search();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    ComarUtils.showWarn(ex.getMessage());
                } finally {
                    JTransaction.setCurrent(null);
                }

                dialog.dispose();
            }
        }

    }

    private class DeleteAction extends AbstractAction {

        public DeleteAction() {
            putValue(NAME, "Eliminar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int[] vrows = panelEditor.getTable().getSelectedRows();
            if (vrows.length == 0) {
                ComarUtils.showWarn("Seleccione al menos un producto");
                return;
            }

            int r = ComarUtils.showYesNo(null, "Desea eliminar los productos seleccionados?", "Eliminar");
            if (r == WebOptionPane.NO_OPTION) {
                return;
            }

            List<ComarPanelProductRow> list = new ArrayList<>();
            for (int i = 0; i < vrows.length; i++) {
                int vrow = vrows[i];
                int mrow = panelEditor.getTable().convertRowIndexToModel(vrow);
                ComarPanelProductRow row = tableModel.getProducts().get(mrow);
                list.add(row);
            }

            tableModel.getProducts().removeAll(list);
            tableModel.fireTableDataChanged();

        }

    }
}
