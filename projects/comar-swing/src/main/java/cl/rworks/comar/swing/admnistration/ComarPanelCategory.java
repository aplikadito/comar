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
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.separator.WebSeparator;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import io.permazen.JTransaction;
import io.permazen.Permazen;
import io.permazen.ValidationMode;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import java.util.NavigableSet;
import javax.swing.SwingUtilities;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelCategory extends ComarPanelCard {

    private WebPanel panelCenter;
    private WebTable table;
    private TableModel tableModel;
    private WebTextField textSearch;
    private WebButton buttonSearch;
    private WebButton buttonClear;
    //
    private DecimalFormat df = new DecimalFormat("#0%");
    //
    private int textFontSize = 18;
    private int tableFontSize = 20;
//    private int tableHeaderFontSize = 20;
//    private int tableDefaultFontSize = 20;
//    private int tableBodyRowHeight = 22;

    public ComarPanelCategory() {
        initValues();
    }

    private void initValues() {
        setLayout(new BorderLayout());

        add(new ComarPanelSubtitle("Buscar Categoria"), BorderLayout.NORTH);

        panelCenter = new WebPanel(new BorderLayout());
        panelCenter.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(panelCenter, BorderLayout.CENTER);

        WebPanel panelContent = new WebPanel();
        panelContent.setLayout(new BorderLayout());
        panelContent.add(buildTableOptions(), BorderLayout.NORTH);
        panelContent.add(buildTable(), BorderLayout.CENTER);
        panelContent.add(buildTableButtons(), BorderLayout.SOUTH);
        panelCenter.add(panelContent, BorderLayout.CENTER);
    }

    private WebPanel buildTableOptions() {
        WebPanel panelSearch = new WebPanel(new FlowLayout(FlowLayout.LEFT));
        WebLabel labelSearch = new WebLabel("Buscar");
        labelSearch.setFontSize(textFontSize);
        panelSearch.add(labelSearch);

        textSearch = new WebTextField(20);
        textSearch.setFontSize(textFontSize);
        panelSearch.add(textSearch);

        buttonSearch = new WebButton(new SearchAction());
        buttonSearch.setFontSize(textFontSize);
        panelSearch.add(buttonSearch);

        buttonClear = new WebButton(new ClearAction());
        buttonClear.setFontSize(textFontSize);
        panelSearch.add(buttonClear);

        WebPanel panelButtons = new WebPanel(new FlowLayout(FlowLayout.CENTER));

        WebButton buttonAdd = new WebButton(new AddAction());
        buttonAdd.setFontSize(textFontSize);
        buttonAdd.setFocusable(true);
        panelButtons.add(buttonAdd);

        WebButton buttonEdit = new WebButton(new EditAction());
        buttonEdit.setFontSize(textFontSize);
        buttonEdit.setFocusable(true);
        panelButtons.add(buttonEdit);

        WebButton buttonDelete = new WebButton(new DeleteAction());
        buttonDelete.setFontSize(textFontSize);
        buttonDelete.setFocusable(true);
        panelButtons.add(buttonDelete);

        WebPanel panel = new WebPanel(new BorderLayout());
        panel.add(panelSearch, BorderLayout.WEST);
        panel.add(panelButtons, BorderLayout.EAST);
        return panel;
    }

    private WebPanel buildTable() {
        WebPanel panel = new WebPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        tableModel = new TableModel();
        table = new WebTable(tableModel);
        panel.add(new WebScrollPane(table));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                    new EditAction().actionPerformed(null);
                }
            }
        });
        
        // CONFIGURACION TABLA
        table.getTableHeader().setFont(table.getTableHeader().getFont().deriveFont((float) tableFontSize));
        table.setFontSize(tableFontSize);
        table.setRowHeight(tableFontSize);

        WebPopupMenu popup = new WebPopupMenu();
        popup.add(new EditAction());
        popup.add(new DeleteAction());
        popup.add(new WebSeparator());
        table.setComponentPopupMenu(popup);

        return panel;
    }

    private WebPanel buildTableButtons() {
        WebPanel panel = new WebPanel(new FlowLayout(FlowLayout.CENTER));
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
        String strText = this.textSearch.getText();
        List<Row> rows = loadCategories(strText);
        tableModel.setItems(rows);
        tableModel.fireTableDataChanged();
        ComarUtils.showInfo(rows.size() + " categorias encontradas");
    }

    private void clear() {
        this.textSearch.clear();
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
            int vrow = table.getSelectedRow();
            if (vrow == -1) {
                ComarUtils.showWarn("Seleccione un producto");
                return;
            }

            int mrow = table.convertRowIndexToModel(vrow);
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
            int[] vrows = table.getSelectedRows();
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
                int mrow = table.convertRowIndexToModel(vrow);
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
