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
import cl.rworks.comar.data.service.ComarDaoFactory;
import cl.rworks.comar.data.service.ComarDaoException;
import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.util.ComarPanelSubtitle;
import cl.rworks.comar.swing.util.ComarUtils;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import cl.rworks.comar.data.service.ComarDaoProduct;
import cl.rworks.comar.data.service.ComarDaoService;
import java.util.ArrayList;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelProductSearch extends WebPanel {

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

    private class ProductTableModel extends AbstractTableModel {

        private String[] columnNames = new String[]{"Codigo", "Nombre", "Categoria", "Unidad", "Formato"};

        private List<ProductRow> products;

        public List<ProductRow> getProducts() {
            return products;
        }

        public void setProducts(List<ProductRow> products) {
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
            ProductRow p = products.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return p.getCode();
                case 1:
                    return p.getName();
                case 2:
                    return p.getCategoryName();
                case 3:
                    return p.getUnit().getName();
                case 4:
                    return p.getFormat().getName();
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
        if (strText.isEmpty()) {
            try {
                List<ProductRow> products = loadProducts(strText);
                tableModel.setProducts(products);
                tableModel.fireTableDataChanged();
            } catch (ComarDaoException ex) {
                ComarUtils.showWarn(ex.getMessage());
            }
        } else {

        }
    }

    private void clear() {
        this.textSearch.clear();
    }

    private List<ProductRow> loadProducts(String strText) throws ComarDaoException {
        List<ProductRow> rows = new ArrayList<>();

        ComarDaoService daoService = ComarSystem.getInstance().getService();
        try {
            daoService.openTransaction();

            ComarDaoProduct daoProduct = ComarDaoFactory.getDaoProduct();
            List<ComarProduct> list = strText.isEmpty() ? daoProduct.getAll() : daoProduct.search(strText);
            for (ComarProduct p : list) {
                ComarCategory c = (ComarCategory) daoProduct.instance(p, "category");

                ProductRow row = new ProductRow();
                row.setCode(p.getCode());
                row.setName(p.getName());
                row.setCategoryName(c != null ? c.getName() : "");
                row.setUnit(p.getUnit());
                row.setFormat(p.getDecimalFormat());
                row.setIdProduct(p.getId());
                row.setIdCategory(c != null ? c.getId() : null);

                rows.add(row);
            }

            daoService.rollback();
        } catch (ComarDaoException ex) {
            daoService.rollback();
            ex.printStackTrace();
        } finally {
            daoService.closeTransaction();
        }

        return rows;
    }

    private class ProductRow {

        private String code;
        private String name;
        private String categoryName;
        private ComarUnit unit;
        private ComarDecimalFormat format;
        //
        private Long idProduct;
        private Long idCategory;

        public ProductRow() {
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public ComarUnit getUnit() {
            return unit;
        }

        public void setUnit(ComarUnit unit) {
            this.unit = unit;
        }

        public ComarDecimalFormat getFormat() {
            return format;
        }

        public void setFormat(ComarDecimalFormat format) {
            this.format = format;
        }

        public Long getIdProduct() {
            return idProduct;
        }

        public void setIdProduct(Long idProduct) {
            this.idProduct = idProduct;
        }

        public Long getIdCategory() {
            return idCategory;
        }

        public void setIdCategory(Long idCategory) {
            this.idCategory = idCategory;
        }

    }
}
