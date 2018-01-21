/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.core.data.ComarProductKite;
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
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import java.util.Collections;
import java.util.stream.Collectors;
import static javax.swing.Action.NAME;
import javax.swing.SwingUtilities;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelProduct extends ComarPanelCard {

    private WebPanel panelContent;
    private WebTable table;
    private ProductTableModel tableModel;
    private WebTextField textSearch;
    private WebButton buttonSearch;
    private WebButton buttonClear;

    public ComarPanelProduct() {
        initValues();
    }

    private void initValues() {
        setLayout(new BorderLayout());

        add(new ComarPanelSubtitle("Buscar Producto"), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);
    }

    private WebPanel buildContent() {
        panelContent = new WebPanel(new BorderLayout());
        panelContent.setBorder(new EmptyBorder(10, 10, 10, 10));

        WebPanel panel = new WebPanel();
        panelContent.add(panel, BorderLayout.CENTER);

        panel.setLayout(new BorderLayout());
        panel.add(buildTableOptions(), BorderLayout.NORTH);
        panel.add(buildTable(), BorderLayout.CENTER);
        panel.add(buildTableButtons(), BorderLayout.SOUTH);

        return panelContent;
    }

    private WebPanel buildTableOptions() {
        WebPanel panelSearch = new WebPanel(new FlowLayout(FlowLayout.LEFT));
        panelSearch.add(new WebLabel("Buscar"));

        textSearch = new WebTextField(20);
        panelSearch.add(textSearch);

        buttonSearch = new WebButton(new SearchAction());
        panelSearch.add(buttonSearch);

        buttonClear = new WebButton(new ClearAction());
        panelSearch.add(buttonClear);

        WebPanel panelButtons = new WebPanel(new FlowLayout(FlowLayout.CENTER));

        WebButton buttonAdd = new WebButton(new AddAction());
        buttonAdd.setFocusable(true);
        panelButtons.add(buttonAdd);
        
        WebButton buttonEdit = new WebButton(new EditAction());
        buttonEdit.setFocusable(true);
        panelButtons.add(buttonEdit);

        WebButton buttonDelete = new WebButton(new DeleteAction());
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

        tableModel = new ProductTableModel();
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

        WebPopupMenu popup = new WebPopupMenu();
        popup.add(new EditAction());
        popup.add(new DeleteAction());
        table.setComponentPopupMenu(popup);

        return panel;
    }

    private WebPanel buildTableButtons() {
        WebPanel panel = new WebPanel(new FlowLayout(FlowLayout.CENTER));

//        Web buttonAdd = new WebButton(new AddAction());
//        buttonAdd.setFocusable(true);
//        panel.add(buttonAdd);
//
//        WebButton buttonEdit = new WebButton(new EditAction());
//        buttonEdit.setFocusable(true);
//        panel.add(buttonEdit);
//
//        WebButton buttonDelete = new WebButton(new DeleteAction());
//        buttonDelete.setFocusable(true);
//        panel.add(buttonDelete);
        return panel;
    }

    private class ProductTableModel extends AbstractTableModel {

        private String[] columnNames = new String[]{"Codigo", "Nombre", "Categoria", "Unidad", "Formato"};

        private List<ComarProduct> products;

        public List<ComarProduct> getProducts() {
            return products;
        }

        public void setProducts(List<ComarProduct> products) {
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
            ComarProduct p = products.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return p.getCode();
                case 1:
                    return p.getName();
                case 2:
                    return p.getCategory() != null ? p.getCategory().getName() : "";
                case 3:
                    return p.getUnit().getName();
                case 4:
                    return p.getDecimalFormat().getName();
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
        String strText = this.textSearch.getText();
        List<ComarProduct> products = loadProducts(strText);
        tableModel.setProducts(products);
        tableModel.fireTableDataChanged();
        ComarUtils.showInfo(products.size() + " productos encontrados");
    }

    private void clear() {
        this.textSearch.clear();
        this.tableModel.setProducts(null);
        this.tableModel.fireTableDataChanged();
    }

    private List<ComarProduct> loadProducts(String strText) {
        ComarService service = ComarSystem.getInstance().getService();
        Permazen db = service.getKitedb().get();

        List<ComarProduct> rows = Collections.EMPTY_LIST;
        JTransaction jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
        JTransaction.setCurrent(jtx);
        try {
            rows = strText.isEmpty()
                    ? ComarProductKite.getAll().stream().map(e -> (ComarProduct) e.copyOut("category")).collect(Collectors.toList())
                    : ComarProductKite.search(strText).stream().map(e -> (ComarProduct) e.copyOut("category")).collect(Collectors.toList());
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
//            int vrow = table.getSelectedRow();
//            if (vrow == -1) {
//                ComarUtils.showWarn("Seleccione un producto");
//                return;
//            }

//            int mrow = table.convertRowIndexToModel(vrow);
//            ComarProduct product = tableModel.getProducts().get(mrow);
            ComarDialogProductAdd dialog = new ComarDialogProductAdd(null);
            dialog.setSize(500, 400);
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
            ComarProduct product = tableModel.getProducts().get(mrow);
            ComarDialogProductEdit dialog = new ComarDialogProductEdit(null, product);
            dialog.setSize(500, 400);
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
                ComarUtils.showWarn("Seleccione al menos un producto");
                return;
            }

            int r = ComarUtils.showYesNo(null, "Desea eliminar los productos seleccionados?", "Eliminar");
            if (r == WebOptionPane.NO_OPTION) {
                return;
            }

            List<ComarProduct> list = new ArrayList<>();
            for (int i = 0; i < vrows.length; i++) {
                int vrow = vrows[i];
                int mrow = table.convertRowIndexToModel(vrow);
                ComarProduct product = tableModel.getProducts().get(mrow);
                list.add(product);
            }

            
            
        }

    }
}
