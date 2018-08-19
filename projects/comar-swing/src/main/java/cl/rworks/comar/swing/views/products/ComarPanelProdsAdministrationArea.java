/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.products;

import cl.rworks.comar.swing.model.ComarCategory;
import cl.rworks.comar.swing.model.ComarProduct;
import cl.rworks.comar.core.model.Metrica;
import cl.rworks.comar.core.util.ComarNumberFormat;
import cl.rworks.comar.swing.main.ComarSystem;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarPanelOptionsArea;
import cl.rworks.comar.swing.util.ComarPanelTitle;
import cl.rworks.comar.swing.util.ComarPanelView;
import cl.rworks.comar.swing.util.ComarUtils;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.splitpane.WebSplitPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import cl.rworks.comar.swing.model.ComarControllerException;
import cl.rworks.comar.swing.util.BigDecimalTableRenderer;
import cl.rworks.comar.swing.util.ComarActionSimple;
import cl.rworks.comar.swing.util.ComarLookup;
import cl.rworks.comar.swing.util.PercentualTableModel;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.laf.separator.WebSeparator;
import javax.swing.JPopupMenu;
import cl.rworks.comar.swing.util.WebTextFieldFactory;
import com.alee.extended.layout.FormLayout;
import com.alee.laf.optionpane.WebOptionPane;
import java.awt.Cursor;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author aplik
 */
public class ComarPanelProdsAdministrationArea extends ComarPanelView {

    private ComarPanelProdsAdministrationController controller = new ComarPanelProdsAdministrationController();
    //
    private WebSplitPane split;
    private WebTable tableCategories;
    private TableModelCategories tableModelCategories;
    //
    private WebTable tableProducts;
    private TableModelProducts tableModelProducts;
    private ComarPanelTitle panelProductTitle;
    private WebTextField textProductCode;
    private WebButton buttonAddProduct;
    private WebTextField textSearch;
    private TableRowSorter sorter;
    //
    private ComarCategory selectedCategory = null;

    public ComarPanelProdsAdministrationArea() {
        super("Administrar Productos");
        initComponents();
    }

    private void initComponents() {
        ComarPanel panelContent = new ComarPanel();
        panelContent.setLayout(new BoxLayout(panelContent, BoxLayout.PAGE_AXIS));
        getPanelContent().add(panelContent, BorderLayout.CENTER);

        split = new WebSplitPane(WebSplitPane.HORIZONTAL_SPLIT, initLeft(), initRight());
        split.setDividerLocation(450);
        panelContent.add(split);

        ComarLookup lookup = ComarSystem.getInstance().getLookup();
        lookup.register(ComarLookup.PRODUCT_CSVUPDATE, e -> csvUpdateAction());
    }

    private ComarPanel initLeft() {
        ComarPanel panelLeft = new ComarPanel(new BorderLayout());

        // LEFT
        tableModelCategories = new TableModelCategories();
        tableCategories = new WebTable(tableModelCategories);
        tableCategories.setDefaultRenderer(BigDecimal.class, new BigDecimalTableRenderer());
        tableCategories.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableCategories.getColumnModel().getColumn(1).setPreferredWidth(100);
        tableCategories.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableCategories.getColumnModel().getColumn(3).setPreferredWidth(100);
        tableCategories.getColumnModel().getColumn(4).setPreferredWidth(50);

        panelLeft.setBorder(new TitledBorder("Categorias"));
        panelLeft.add(new WebScrollPane(tableCategories), BorderLayout.CENTER);
        tableModelCategories.setRows(controller.getCategories());

        ComarPanelOptionsArea panelCategoryButtons = new ComarPanelOptionsArea();
        panelCategoryButtons.addLeft(new WebButton("Todos", e -> selectAllAction()));
        panelCategoryButtons.addRight(new WebButton("Agregar", e -> addCategoryAction()));
        panelCategoryButtons.addRight(new WebButton("Editar", e -> editCategoryAction()));
        panelCategoryButtons.addRight(new WebButton("Eliminar", e -> deleteCategoryAction()));
        panelLeft.add(panelCategoryButtons, BorderLayout.SOUTH);

        // TREE
        tableCategories.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedCategory = getSelectedCategory();
                updateForSelectedCategory();
            }
        });

        JPopupMenu popupTree = new JPopupMenu();
        popupTree.add(new ComarActionSimple("Editar", e -> editCategoryAction()));
        popupTree.add(new WebSeparator());
        popupTree.add(new ComarActionSimple("Eliminar", e -> deleteCategoryAction()));
        tableCategories.setComponentPopupMenu(popupTree);

        return panelLeft;
    }

    private ComarPanel initRight() {
        ComarPanel panelRight = new ComarPanel(new BorderLayout());

        panelProductTitle = new ComarPanelTitle("");
        panelRight.add(panelProductTitle, BorderLayout.NORTH);

        WebPanel panelTableContent = new WebPanel(new BorderLayout());
        panelTableContent.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelRight.add(panelTableContent, BorderLayout.CENTER);

        ComarPanelOptionsArea panelButtons = new ComarPanelOptionsArea();
        panelButtons.addLeft(new WebLabel("Codigo"));
        panelButtons.addLeft(textProductCode = new WebTextFieldFactory().cols(30).actionListener(e -> addProductAction()).create());
        panelButtons.addLeft(buttonAddProduct = new WebButton("Agregar", e -> addProductAction()));
        panelButtons.addRight(new WebButton("Eliminar", e -> deleteProductsAction()));
        panelTableContent.add(panelButtons, BorderLayout.NORTH);

        // TABLA
        tableModelProducts = new TableModelProducts();
        tableProducts = new WebTable(tableModelProducts);
        ComarUtils.initTable(tableProducts);
        panelTableContent.add(new WebScrollPane(tableProducts), BorderLayout.CENTER);

        WebPopupMenu popupTable = new WebPopupMenu();
//        popup.add(new WebButton("Eliminar", e -> deleteProductsAction()));
        popupTable.add(new ComarActionSimple("Mover a", e -> moveProducts()));
        popupTable.add(new WebSeparator());
        popupTable.add(new ComarActionSimple("Eliminar", e -> deleteProductsAction()));
        tableProducts.setComponentPopupMenu(popupTable);

        sorter = new TableRowSorter(tableModelProducts);
        tableProducts.setRowSorter(sorter);

        ComarPanelOptionsArea panelSearch = new ComarPanelOptionsArea();
        panelSearch.addLeft(new WebLabel("Buscar"));
        panelSearch.addLeft(textSearch = new WebTextFieldFactory().cols(30).actionListener(e -> searchAction()).create());
        panelSearch.addLeft(new WebButton("Buscar", e -> searchAction()));
        panelTableContent.add(panelSearch, BorderLayout.SOUTH);

        return panelRight;
    }

    private class TableModelCategories extends AbstractTableModel implements PercentualTableModel {

        private Object[][] cols = new Object[][]{
            {"Nombre", String.class, false, false},
            {"IVA", BigDecimal.class, false, true},
            {"Imp. Extra", BigDecimal.class, false, true},
            {"% Ganancia", BigDecimal.class, false, true},
            {"#", Integer.class, false, false}
        };
        private List<ComarCategory> rows = null;

        public List<ComarCategory> getRows() {
            return rows;
        }

        public void setRows(List<ComarCategory> rows) {
            this.rows = rows;
        }

        @Override
        public int getRowCount() {
            return rows != null ? rows.size() : 0;
        }

        @Override
        public int getColumnCount() {
            return cols.length;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return (Class<?>) cols[columnIndex][1];
        }

        @Override
        public String getColumnName(int columnIndex) {
            return (String) cols[columnIndex][0];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            ComarCategory row = rows.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return row.getEntity().getNombre();
                case 1:
                    return row.getEntity().getImpuestoPrincipal();
                case 2:
                    return row.getEntity().getImpuestoSecundario();
                case 3:
                    return row.getEntity().getPorcentajeGanancia();
                case 4:
                    return row.getProducts().size();
                default:
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return (Boolean) cols[columnIndex][2];
        }
        
        @Override
        public boolean isPercentual(int rowIndex, int columnIndex) {
            return (Boolean) cols[columnIndex][3];
        }

        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
        }
    }

    private class TableModelProducts extends AbstractTableModel {

        private Object[][] cols = new Object[][]{
            {"Codigo", String.class, true},
            {"Descripcion", String.class, true},
            {"Precio Venta", BigDecimal.class, true},
            {"Unidad", Metrica.class, true},
            {"Incluir En Boleta", Boolean.class, true},
            {"Precio de Venta Fijo", Boolean.class, true},
            {"Stock Comprado", BigDecimal.class, false},
            {"Stock Vendido", BigDecimal.class, false},
            {"Stock Actual", BigDecimal.class, false},
            {"Categoria", String.class, false}
        };
        private List<ComarProduct> rows = null;

        public List<ComarProduct> getRows() {
            return rows;
        }

        public void setRows(List<ComarProduct> rows) {
            this.rows = rows;
        }

        @Override
        public int getRowCount() {
            return rows != null ? rows.size() : 0;
        }

        @Override
        public int getColumnCount() {
            return cols.length;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return (Class<?>) cols[columnIndex][1];
        }

        @Override
        public String getColumnName(int columnIndex) {
            return (String) cols[columnIndex][0];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            ComarProduct pmodel = rows.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return pmodel.getEntity().getCodigo();
                case 1:
                    return pmodel.getEntity().getDescripcion();
                case 2:
                    return pmodel.getEntity().getPrecioVentaActual();
                case 3:
                    return pmodel.getEntity().getMetrica();
                case 4:
                    return pmodel.getEntity().isIncluirEnBoleta();
                case 5:
                    return pmodel.getEntity().isPrecioVentaFijo();
                case 6:
                    return pmodel.getEntity().getStockComprado();
                case 7:
                    return pmodel.getEntity().getStockVendido();
                case 8:
                    return pmodel.getEntity().getStockComprado().subtract(pmodel.getEntity().getStockVendido());
                case 9:
                    return pmodel.getCategory().getEntity().getNombre();
                default:
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return (Boolean) cols[columnIndex][2];
        }

        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
            ComarProduct product = rows.get(rowIndex);
            try {
                switch (columnIndex) {
                    case 0:
                        String code = (String) value;
                        controller.updateProductCode(product, code);
                        fireTableCellUpdated(rowIndex, columnIndex);
                        break;
                    case 1:
                        String desc = (String) value;
                        controller.updateProductDescription(product, desc);
                        fireTableCellUpdated(rowIndex, columnIndex);
                        break;
                    case 2:
                        BigDecimal precioVenta = (BigDecimal) value;
                        if (precioVenta != null) {
                            controller.updateProductSellPrice(product, precioVenta);
                            fireTableCellUpdated(rowIndex, columnIndex);
                        }
                        break;
                    case 3:
                        Metrica metric = (Metrica) value;
                        controller.updateProductMetric(product, metric);
                        fireTableCellUpdated(rowIndex, columnIndex);
                        break;
                    case 4:
                        Boolean incluirEnBoleta = (Boolean) value;
                        controller.updateProductIncludeInSell(product, incluirEnBoleta);
                        fireTableCellUpdated(rowIndex, columnIndex);
                        break;
                    case 5:
                        Boolean precioVentaFijo = (Boolean) value;
                        controller.updateProductPrecioVentaFijo(product, precioVentaFijo);
                        fireTableCellUpdated(rowIndex, columnIndex);
                        break;
                    default:
                        break;
                }
            } catch (ComarControllerException ex) {
                ComarUtils.showWarn(ComarPanelProdsAdministrationArea.this, ex.getMessage());
            }

        }
    }

    public void updateForSelectedCategory() {
        String str = "";
        if (selectedCategory != null) {
            str = selectedCategory.getEntity().getNombre();
        } else {
            str = "Todas las categorias";
        }
        panelProductTitle.setTitle(str);

        List<ComarProduct> products = controller.getProducts(selectedCategory);
        this.tableModelProducts.setRows(products);
        this.tableModelProducts.fireTableDataChanged();

        this.textProductCode.setEnabled(selectedCategory != null);
        this.buttonAddProduct.setEnabled(selectedCategory != null);
    }

    private ComarCategory getSelectedCategory() {
        int vrow = tableCategories.getSelectedRow();
        if (vrow == -1) {
            return null;
        }

        int mrow = tableCategories.convertRowIndexToModel(vrow);
        ComarCategory category = tableModelCategories.getRows().get(mrow);
        if (category == null) {
            return null;
        }

        return category;
    }

    public void selectAllAction() {
        selectedCategory = null;
        updateForSelectedCategory();
    }

    public void addCategoryAction() {
        String name = (String) WebOptionPane.showInputDialog(this, "Nombre", "Agregar Categoria", WebOptionPane.PLAIN_MESSAGE, null, null, "");
        if (name != null) {
            if (name.isEmpty()) {
                ComarUtils.showWarn(this, "Ingrese un nombre para la categoria");
                return;
            }

//            if (name.equals(CategoriaEntity.DEFAULT_CATEGORY)) {
//                ComarUtils.showWarn(this, "Este nombre se encuentra reservado por el sistema. Ingrese un nombre diferente para la categoria");
//                return;
//            }
            try {
                controller.insertCategory(name);
                tableModelCategories.setRows(controller.getCategories());
                tableModelCategories.fireTableDataChanged();
            } catch (ComarControllerException ex) {
                ComarUtils.showWarn(this, ex.getMessage());
            }
        }
    }

    public void editCategoryAction() {
        if (selectedCategory == null) {
            return;
        }

//        if (selectedCategory.getEntity().getNombre().equals(CategoriaEntity.DEFAULT_CATEGORY)) {
//            ComarUtils.showWarn(this, "Esta categoria se encuentra reservada por el sistema y no puede ser editada");
//            return;
//        }
//        String name = (String) WebOptionPane.showInputDialog(this, "Nombre", "Editar Categoria", WebOptionPane.PLAIN_MESSAGE, null, null, selectedCategory.getEntity().getNombre());
        CategoryEditorPanel panel = new CategoryEditorPanel(selectedCategory);
        int response = WebOptionPane.showConfirmDialog(null, panel, "Editar Categoria", WebOptionPane.OK_CANCEL_OPTION, WebOptionPane.PLAIN_MESSAGE);
        if (response == WebOptionPane.YES_OPTION) {
            WebTextField textName = panel.getTextName();
            String name = textName.getText().trim();
            if (name.isEmpty()) {
                ComarUtils.showWarn(this, "Ingrese un nombre para la categoria");
                return;
            }

            try {
                BigDecimal tax1 = getPercentual(panel.getTextTaxMain(), "IVA");
                BigDecimal tax2 = getPercentual(panel.getTextTaxSecondary(), "Imp. Extra");
                BigDecimal profit = getPercentual(panel.getTextProfit(), "% Ganancia");
                
                controller.updateCategory(selectedCategory, name, tax1, tax2, profit);
                tableModelCategories.setRows(controller.getCategories());
                tableModelCategories.fireTableDataChanged();

                tableModelProducts.setRows(controller.getProducts(selectedCategory));
                tableModelProducts.fireTableDataChanged();
            } catch (ComarControllerException ex) {
                ComarUtils.showWarn(this, ex.getMessage());
            }
        }
    }

    private BigDecimal getPercentual(WebTextField text, String property) throws ComarControllerException {
        String strTax = text.getText().trim();
        if (strTax.isEmpty()) {
            throw new ComarControllerException("Ingrese un valor valido para: " + property);
        }

        BigDecimal tax;
        try {
            tax = ComarNumberFormat.parsePercentual(strTax);
        } catch (ParseException ex) {
            throw new ComarControllerException("Ingrese un valor valido para: " + property);
        }

        return tax;
    }

    public void deleteCategoryAction() {
        if (selectedCategory == null) {
            ComarUtils.showWarn(null, "Seleccione una categoria");
            return;
        }

//        if (selectedCategory.getEntity().getNombre().equals(CategoriaEntity.DEFAULT_CATEGORY)) {
//            ComarUtils.showWarn(this, "Esta categoria se encuentra reservada por el sistema y no puede ser eliminada");
//            return;
//        }
        if (!selectedCategory.getProducts().isEmpty()) {
            ComarUtils.showInfo(null, "Antes de eliminar una categoria, elimine o mueva los productos asociados a ella");
            return;
        }

        int response = ComarUtils.showYesNo(ComarPanelProdsAdministrationArea.this, "Desea eliminar la categoria seleccionada?", "Eliminar");
        if (response != WebOptionPane.YES_OPTION) {
            return;
        }

        try {
            controller.deleteCategory(selectedCategory);
            this.selectedCategory = null;

            this.panelProductTitle.setTitle("");
            this.tableModelProducts.fireTableDataChanged();

            tableModelCategories.setRows(controller.getCategories());
            tableModelCategories.fireTableDataChanged();
        } catch (ComarControllerException ex) {
            ex.printStackTrace();
            ComarUtils.showWarn(null, ex.getMessage());
        }

    }

    public void addProductAction() {
        if (selectedCategory == null) {
            ComarUtils.showWarn(this, "Seleccione una categoria");
            return;
        }

        String code = this.textProductCode.getText();
        if (code.isEmpty()) {
            ComarUtils.showWarn(this, "Ingrese un codigo para el nuevo producto");
            return;
        }

        try {
            controller.insertProduct(code, selectedCategory);
            tableModelProducts.setRows(controller.getProducts(selectedCategory));
            tableModelProducts.fireTableDataChanged();
            tableCategories.updateUI();
            textProductCode.clear();
        } catch (ComarControllerException ex) {
            ComarUtils.showWarn(this, ex.getMessage());
        }
    }

    public List<ComarProduct> getSelectedProductsFromTable() {
        List<ComarProduct> list = new ArrayList<>();
        int[] vrows = tableProducts.getSelectedRows();
        for (int i = 0; i < vrows.length; i++) {
            int vrow = vrows[i];
            int mrow = tableProducts.convertRowIndexToModel(vrow);
            ComarProduct pnode = tableModelProducts.getRows().get(mrow);
            list.add(pnode);
        }
        return list;
    }

    public void deleteProductsAction() {
        List<ComarProduct> selectedProducts = getSelectedProductsFromTable();
        if (selectedProducts.isEmpty()) {
            ComarUtils.showWarn(null, "Seleccione al menos un producto");
            return;
        }

        int response = ComarUtils.showYesNo(this, "Desea eliminar los products seleccionados?", "Eliminar");
        if (response != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            controller.deleteProducts(selectedProducts);
            tableModelProducts.setRows(controller.getProducts(selectedCategory));
            tableModelProducts.fireTableDataChanged();
            tableCategories.updateUI();
        } catch (ComarControllerException ex) {
            ComarUtils.showWarn(null, ex.getMessage());
        } finally {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    private void moveProducts() {
        List<ComarProduct> selectedProducts = getSelectedProductsFromTable();
        if (selectedProducts.isEmpty()) {
            ComarUtils.showWarn(null, "Seleccione al menos un producto");
            return;
        }

        Object[] values = controller.getCategories().toArray();
        Object response = WebOptionPane.showInputDialog(this, "Categoria", "Mover a", WebOptionPane.PLAIN_MESSAGE, null, values, values[0]);
        if (response != null) {
            try {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                ComarCategory category = (ComarCategory) response;
                controller.moveProducts(selectedProducts, category);
                tableModelProducts.setRows(controller.getProducts(selectedCategory));
                tableModelProducts.fireTableDataChanged();

                tableModelCategories.fireTableDataChanged();
            } catch (ComarControllerException ex) {
                ComarUtils.showWarn(null, ex.getMessage());
            } finally {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }

    public void searchAction() {
        String text = textSearch.getText();
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text + "", 0, 1));
        sorter.sort();
    }

    private void csvUpdateAction() {
        tableModelCategories.setRows(controller.getCategories());
        tableModelCategories.fireTableDataChanged();
    }

    private class CategoryEditorPanel extends WebPanel {

        private WebTextField textName;
        private WebTextField textTaxMain;
        private WebTextField textTaxSecondary;
        private WebTextField textProfit;

        public CategoryEditorPanel() {
            this(null);
        }

        public CategoryEditorPanel(ComarCategory category) {
            setLayout(new FormLayout());
            add(new WebLabel("Nombre"));
            add(textName = new WebTextField());
            add(new WebLabel("IVA"));
            add(textTaxMain = new WebTextField());
            add(new WebLabel("Imp. Extra"));
            add(textTaxSecondary = new WebTextField());
            add(new WebLabel("% Ganancia"));
            add(textProfit = new WebTextField());

            if (category != null) {
                textName.setText(category.getEntity().getNombre());
                textTaxMain.setText(ComarNumberFormat.formatPercentual(category.getEntity().getImpuestoPrincipal()));
                textTaxSecondary.setText(ComarNumberFormat.formatPercentual(category.getEntity().getImpuestoSecundario()));
                textProfit.setText(ComarNumberFormat.formatPercentual(category.getEntity().getPorcentajeGanancia()));
            }
        }

        public WebTextField getTextName() {
            return textName;
        }

        public WebTextField getTextTaxMain() {
            return textTaxMain;
        }

        public WebTextField getTextTaxSecondary() {
            return textTaxSecondary;
        }

        public WebTextField getTextProfit() {
            return textProfit;
        }

    }

}
