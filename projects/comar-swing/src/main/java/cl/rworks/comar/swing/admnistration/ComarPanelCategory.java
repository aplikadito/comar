/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.core.data.ComarCategoryKite;
import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.util.ComarPanelCard;
import cl.rworks.comar.swing.util.ComarPanelSubtitle;
import cl.rworks.comar.swing.util.ComarUtils;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.table.WebTable;
import io.permazen.JTransaction;
import io.permazen.Permazen;
import io.permazen.ValidationMode;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import java.util.NavigableSet;
import javax.swing.SwingUtilities;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelCategory extends ComarPanelCard {

    private ComarPanelBaseEditor panelEditor;
    private TableModel tableModel;
    //
    private DecimalFormat df = new DecimalFormat("#0%");

    public ComarPanelCategory() {
        setLayout(new BorderLayout());
//        add(new ComarPanelSubtitle("Categorías"), BorderLayout.NORTH);
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

        private String[] columnNames = new String[]{"Nombre", "Impuestos", "Productos"};

        private List<Row> items;

        public List<Row> getItems() {
            return items;
        }

        public void setItems(List<Row> items) {
            this.items = items;
        }

        @Override
        public int getRowCount() {
            return items != null ? items.size() : 0;
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
            Row row = items.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return row.getCategory().getName();
                case 1:
                    return df.format(row.getCategory().getTax());
                case 2:
                    return Integer.toString(row.getProductCount());
                default:
                    return "";
            }
        }

    }

    private class Row {

        private ComarCategory category;
        private int productCount;

        public Row(ComarCategory category, int productCount) {
            this.category = category;
            this.productCount = productCount;
        }

        public ComarCategory getCategory() {
            return category;
        }

        public int getProductCount() {
            return productCount;
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
        String strText = panelEditor.getTextSearch().getText();
        List<Row> rows = loadCategories(strText);
        tableModel.setItems(rows);
        tableModel.fireTableDataChanged();
        ComarUtils.showInfo(rows.size() + " categorias encontradas");
    }

    private void clear() {
        this.panelEditor.getTextSearch().clear();
        this.tableModel.setItems(null);
        this.tableModel.fireTableDataChanged();
    }

    private List<Row> loadCategories(String strText) {
        ComarService service = ComarSystem.getInstance().getService();
        Permazen db = service.getKitedb().get();

        final List<Row> rows = new ArrayList<>();
        JTransaction jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
        JTransaction.setCurrent(jtx);
        try {
            if (strText.trim().isEmpty()) {
                ComarCategoryKite.getAll().stream().forEach(e -> {
                    ComarCategory category = (ComarCategory) e.copyOut();
                    NavigableSet<ComarProduct> nav = ComarCategoryKite.getProducts(category);
                    int productCount = nav != null ? nav.size() : 0;
                    Row row = new Row(category, productCount);
                    rows.add(row);
                });
            } else {
                ComarCategoryKite.search(strText).stream().forEach(e -> {
                    ComarCategory category = (ComarCategory) e.copyOut();
                    NavigableSet<ComarProduct> nav = ComarCategoryKite.getProducts(category);
                    int productCount = nav != null ? nav.size() : 0;
                    Row row = new Row(category, productCount);
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
            ComarDialogCategoryAdd dialog = new ComarDialogCategoryAdd(null);
            dialog.setSize(550, 300);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
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
            Row row = tableModel.getItems().get(mrow);
            ComarCategory cat = row.getCategory();
            ComarDialogCategoryEdit dialog = new ComarDialogCategoryEdit(null, cat);
            dialog.setSize(550, 400);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
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
                ComarUtils.showWarn("Seleccione al menos una categoria");
                return;
            }

            int r = ComarUtils.showYesNo(null, "Desea eliminar las categorias seleccionadas?", "Eliminar");
            if (r == WebOptionPane.NO_OPTION) {
                return;
            }

            List<Row> list = new ArrayList<>();
            for (int i = 0; i < vrows.length; i++) {
                int vrow = vrows[i];
                int mrow = panelEditor.getTable().convertRowIndexToModel(vrow);
                Row row = tableModel.getItems().get(mrow);
                list.add(row);
            }

            Permazen db = ComarSystem.getInstance().getService().getKitedb().get();
            JTransaction jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
            JTransaction.setCurrent(jtx);
            try {
                for (Row row : list) {
                    ComarCategory c = row.getCategory();
                    ComarCategoryKite.delete(c);
                }
                jtx.commit();

                tableModel.getItems().removeAll(list);
                tableModel.fireTableDataChanged();

                ComarUtils.showInfo("Categorias eliminadas correctamente");
            } catch (Exception ex) {
                ex.printStackTrace();
                ComarUtils.showInfo(ex.getMessage());
            } finally {
                JTransaction.setCurrent(null);
            }

        }

    }
}
