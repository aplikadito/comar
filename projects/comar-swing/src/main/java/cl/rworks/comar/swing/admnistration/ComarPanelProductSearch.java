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
import java.util.List;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelProductSearch extends ComarPanelCard {

    private WebPanel panelContent;
    private WebTable table;
    private ProductTableModel tableModel;
    private WebTextField textSearch;
    private WebButton buttonSearch;

    public ComarPanelProductSearch() {
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
        panel.add(buildTableStatus(), BorderLayout.SOUTH);

        return panelContent;
    }

    private WebPanel buildTableOptions() {
        WebPanel panel = new WebPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new WebLabel("Buscar"));

        textSearch = new WebTextField(20);
        panel.add(textSearch);

        buttonSearch = new WebButton(new SearchAction());
        panel.add(buttonSearch);

        return panel;
    }

    private WebPanel buildTable() {
        WebPanel panel = new WebPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
//        panel.setLayout(new BorderLayout());

        tableModel = new ProductTableModel();
        table = new WebTable(tableModel);
        panel.add(new WebScrollPane(table));

        return panel;
    }

    private WebPanel buildTableStatus() {
        WebPanel panelFormButtons = new WebPanel(new FlowLayout(FlowLayout.LEFT));
//        panelFormButtons.setMinimumSize(new Dimension(300, 30));
//        panelFormButtons.setPreferredSize(new Dimension(300, 30));
//        panelFormButtons.setMaximumSize(new Dimension(300, 30));
//        panelFormButtons.setAlignmentX(0.0f);

//        WebButton buttonOk = new WebButton(new AddAction());
//        buttonOk.setFocusable(true);
//        panelFormButtons.add(buttonOk);
//        
//        WebButton buttonClear = new WebButton(new ClearAction());
//        buttonClear.setFocusable(true);
//        panelFormButtons.add(buttonClear);
        return panelFormButtons;
    }

    @Override
    public void updateCard() {
    }

    @Override
    public void hideCard() {
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

}
