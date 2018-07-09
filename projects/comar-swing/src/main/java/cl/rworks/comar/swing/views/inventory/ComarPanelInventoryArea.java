/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.inventory;

import cl.rworks.comar.swing.model.ComarInventoryNodeProduct;
import cl.rworks.comar.swing.model.ComarInventoryNodeRoot;
import cl.rworks.comar.core.model.ComarMetric;
import cl.rworks.comar.core.model.impl.ComarCategoryImpl;
import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.model.ComarWorkspace;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarPanelButtonsArea;
import cl.rworks.comar.swing.util.ComarPanelTitle;
import cl.rworks.comar.swing.util.ComarPanelView;
import cl.rworks.comar.swing.util.ComarUtils;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.splitpane.WebSplitPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import cl.rworks.comar.swing.model.ComarInventoryNodeCategory;
import cl.rworks.comar.swing.model.ComarModelException;
import cl.rworks.comar.swing.util.ComarActionSimple;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.separator.WebSeparator;
import javax.swing.DefaultCellEditor;
import javax.swing.JPopupMenu;

/**
 *
 * @author aplik
 */
public class ComarPanelInventoryArea extends ComarPanelView {

    private ComarWorkspace ws;
    private ComarInventoryNodeRoot rootNode;
    private ComarPanel panelContent;
    private WebSplitPane split;
    private ComarPanel panelSplitLeft;
    private ComarPanel panelSplitRight;
    private JTree tree;
    private WebTable tableProducts;
    private TableProductsModel tableModelProducts;
    private ComarPanelTitle panelProductTitle;
    private WebTextField textProductCode;
    private WebButton buttonAddProduct;
    private WebButton buttonDeleteProducts;
    //
    private ComarInventoryNodeCategory selectedCategory = null;

    public ComarPanelInventoryArea() {
        super("Inventario");
        initComponents();
    }

    private void initComponents() {
        ws = ComarSystem.getInstance().getWorkspace();
        rootNode = ws.getInventoryRootNode();

        panelContent = new ComarPanel();

        panelContent.setLayout(new BoxLayout(panelContent, BoxLayout.PAGE_AXIS));
        getPanelContent().add(panelContent, BorderLayout.CENTER);

        panelSplitLeft = new ComarPanel(new BorderLayout());
        panelSplitRight = new ComarPanel(new BorderLayout());

        split = new WebSplitPane(WebSplitPane.HORIZONTAL_SPLIT, panelSplitLeft, panelSplitRight);
        split.setDividerLocation(350);
        panelContent.add(split);

        // LEFT
        tree = new JTree(new DefaultTreeModel(rootNode));
//        tree.setCellRenderer(new DefaultTreeCellRenderer() {
//            @Override
//            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
//                JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
//                return label;
//            }
//        });
        panelSplitLeft.add(new WebScrollPane(tree), BorderLayout.CENTER);

        ComarPanelButtonsArea panelCategoryButtons = new ComarPanelButtonsArea();
        panelCategoryButtons.addRight(new WebButton("Agregar", e -> AddCategoryAction()));
        panelCategoryButtons.addRight(new WebButton("Eliminar", e -> deleteCategoryAction()));
        panelSplitLeft.add(panelCategoryButtons, BorderLayout.SOUTH);

        // TREE
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadProductsForSelectedCategory();
            }

        });

        JPopupMenu popupTree = new JPopupMenu();
        popupTree.add(new ComarActionSimple("Eliminar", e -> deleteCategoryAction()));
        tree.setComponentPopupMenu(popupTree);

        // RIGHT
        WebPanel panelRightContent = new WebPanel();
        panelSplitRight.add(panelRightContent, BorderLayout.NORTH);

        panelProductTitle = new ComarPanelTitle("");
        panelRightContent.add(panelProductTitle, BorderLayout.NORTH);

        WebPanel panelTableContent = new WebPanel(new BorderLayout());
        panelTableContent.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelRightContent.add(panelTableContent, BorderLayout.CENTER);

        // BOTONERA PRODUCTOS
        ComarPanelButtonsArea panelProductButtons = new ComarPanelButtonsArea();
        textProductCode = new WebTextField(30);
        textProductCode.addActionListener(e -> addProductAction());

        buttonAddProduct = new WebButton("Agregar", e -> addProductAction());
        buttonDeleteProducts = new WebButton("Eliminar", e -> deleteProductsAction());

        panelProductButtons.addLeft(new WebLabel("Codigo"));
        panelProductButtons.addLeft(textProductCode);
        panelProductButtons.addLeft(buttonAddProduct);
        panelProductButtons.addRight(buttonDeleteProducts);
        panelTableContent.add(panelProductButtons, BorderLayout.NORTH);

        // TABLA
        tableModelProducts = new TableProductsModel();
        tableProducts = new WebTable(tableModelProducts);
        tableProducts.setCellSelectionEnabled(true);
        tableProducts.setDefaultEditor(ComarMetric.class, new DefaultCellEditor(createComarMetricComboBox()));

        WebPopupMenu popupTable = new WebPopupMenu();
//        popup.add(new WebButton("Eliminar", e -> deleteProductsAction()));
        popupTable.add(new ComarActionSimple("Mover a", e -> moveAction()));
        popupTable.add(new WebSeparator());
        popupTable.add(new ComarActionSimple("Eliminar", e -> deleteProductsAction()));
        tableProducts.setComponentPopupMenu(popupTable);

        panelTableContent.add(new WebScrollPane(tableProducts), BorderLayout.CENTER);
    }

    private WebComboBox createComarMetricComboBox() {
        WebComboBox combo = new WebComboBox();
        ComarMetric[] metrics = ComarMetric.values();
        for (ComarMetric metric : metrics) {
            combo.addItem(metric);
        }
        return combo;
    }

    private class TableProductsModel extends AbstractTableModel {

        private String[] colNames = new String[]{"Codigo", "Descripcion", "Unidad", "Categoria"};
        private List<ComarInventoryNodeProduct> products = null;

        public List<ComarInventoryNodeProduct> getProducts() {
            return products;
        }

        public void setProducts(List<ComarInventoryNodeProduct> products) {
            this.products = products;
        }

        @Override
        public int getRowCount() {
            return products != null ? products.size() : 0;
        }

        @Override
        public int getColumnCount() {
            return colNames.length;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return String.class;
                case 1:
                    return String.class;
                case 2:
                    return ComarMetric.class;
                case 3:
                    return String.class;
                default:
                    return String.class;
            }
        }

        @Override
        public String getColumnName(int column) {
            return colNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            ComarInventoryNodeProduct pnode = products.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return pnode.getModel().getCode();
                case 1:
                    return pnode.getModel().getDescription();
                case 2:
                    return pnode.getModel().getMetric();
                case 3:
                    return pnode.getCategory().getModel().getName();
                default:
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return true;
                case 1:
                    return true;
                case 2:
                    return true;
                case 3:
                    return false;
                default:
                    return false;
            }
        }

        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
            ComarInventoryNodeProduct pnode = products.get(rowIndex);
            try {
                switch (columnIndex) {
                    case 0:
                        String code = (String) value;
                        pnode.updateCode(code);
                        fireTableCellUpdated(rowIndex, columnIndex);
                        break;
                    case 1:
                        String desc = (String) value;
                        pnode.updateDescription(desc);
                        fireTableCellUpdated(rowIndex, columnIndex);
                        break;
                    case 2:
                        ComarMetric metric = (ComarMetric) value;
                        pnode.updateMetric(metric);
                        fireTableCellUpdated(rowIndex, columnIndex);
                        break;
                    default:
                        break;
                }
            } catch (ComarModelException ex) {
                ComarUtils.showWarn(ComarPanelInventoryArea.this, ex.getMessage());
            }

        }
    }

    public void loadProductsForSelectedCategory() {
        selectedCategory = getSelectedCategory();
        String str = selectedCategory != null ? selectedCategory.getModel().getName() : "";
        panelProductTitle.setTitle(str);

        this.tableModelProducts.setProducts(rootNode.getProducts(selectedCategory));
        this.tableModelProducts.fireTableDataChanged();

        this.textProductCode.setEnabled(selectedCategory != null);
        this.buttonAddProduct.setEnabled(selectedCategory != null);
        this.buttonDeleteProducts.setEnabled(selectedCategory != null);
    }

    private ComarInventoryNodeCategory getSelectedCategory() {
        TreePath path = tree.getSelectionPath();
        if (path == null) {
            return null;
        }

        Object selectedObject = path.getLastPathComponent();
        if (selectedObject == null) {
            return null;
        }

        if (!(selectedObject instanceof ComarInventoryNodeCategory)) {
            return null;
        }

        return (ComarInventoryNodeCategory) selectedObject;
    }

    public void AddCategoryAction() {
        AddCategoryDialog dialog = new AddCategoryDialog(ComarSystem.getInstance().getFrame());
        dialog.showMe();

        if (dialog.isOk()) {
            tree.updateUI();
        }
    }

    public class AddCategoryDialog extends WebDialog {

        private CategoryEditorPanel panelEditor;
        private boolean ok = false;

        public AddCategoryDialog(Window window) {
            super(window, "Agregar Categoria", ModalityType.APPLICATION_MODAL);
            initValues();
            ComarUtils.installCloseKey(this, e -> closeAction());
        }

        private void initValues() {
            setLayout(new BorderLayout());

            panelEditor = new CategoryEditorPanel();
            panelEditor.setBorder(new EmptyBorder(10, 10, 10, 10));
            panelEditor.updateForm(ComarCategoryImpl.create(""));
            add(panelEditor, BorderLayout.CENTER);

            ComarPanelButtonsArea panelButtons = new ComarPanelButtonsArea();
            panelButtons.addRight(new WebButton("Aceptar", e -> okAction()));
            panelButtons.addRight(new WebButton("Cerrar", e -> closeAction()));
            add(panelButtons, BorderLayout.SOUTH);
        }

        public void showMe() {
            setSize(400, 150);
            setLocationRelativeTo(null);
            setVisible(true);
        }

        private boolean isOk() {
            return ok;
        }

        public void okAction() {
            String name = panelEditor.getTextName().getText();
            if (name.isEmpty()) {
                ComarUtils.showWarn(panelEditor, "Ingrese un nombre para la categoria");
                return;
            }

            try {
                rootNode.insertCategory(name);
                ok = true;
                dispose();
            } catch (ComarModelException ex) {
                ComarUtils.showWarn(panelEditor, ex.getMessage());
            }
        }

        public void closeAction() {
            ok = false;
            dispose();
        }

    }

    public void deleteCategoryAction() {
        ComarInventoryNodeCategory cnode = getSelectedCategory();
        if (cnode == null) {
            ComarUtils.showWarn(null, "Seleccione una categoria");
            return;
        }

        if (!cnode.getProducts().isEmpty()) {
            ComarUtils.showWarn(null, "La categoria no debe poseer productos asociados");
            return;
        }

        int response = ComarUtils.showYesNo(ComarPanelInventoryArea.this, "Desea eliminar la categoria seleccionada?", "Eliminar");
        if (response != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            rootNode.removeCategory(cnode);
            
            this.tableModelProducts.setProducts(null);
            this.tableModelProducts.fireTableDataChanged();
            tree.updateUI();
        } catch (ComarModelException ex) {
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
            ComarInventoryNodeProduct pnode = selectedCategory.insertProduct(code);
            tableModelProducts.getProducts().add(pnode);
            tableModelProducts.fireTableDataChanged();
            tree.updateUI();
            textProductCode.clear();
        } catch (ComarModelException ex) {
            ex.printStackTrace();
            ComarUtils.showWarn(this, ex.getMessage());
        }
    }

    public List<ComarInventoryNodeProduct> getSelectedProductsFromTable() {
        List<ComarInventoryNodeProduct> list = new ArrayList<>();
        int[] vrows = tableProducts.getSelectedRows();
        for (int i = 0; i < vrows.length; i++) {
            int vrow = vrows[i];
            int mrow = tableProducts.convertRowIndexToModel(vrow);
            ComarInventoryNodeProduct pnode = tableModelProducts.getProducts().get(mrow);
            list.add(pnode);
        }
        return list;
    }

    public void deleteProductsAction() {
        List<ComarInventoryNodeProduct> selectedProducts = getSelectedProductsFromTable();
        if (selectedProducts.isEmpty()) {
            ComarUtils.showWarn(null, "Seleccione al menos un producto");
            return;
        }

        int response = ComarUtils.showYesNo(this, "Desea eliminar los products seleccionados?", "Eliminar");
        if (response != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            rootNode.deleteProducts(selectedProducts);
            tableModelProducts.getProducts().removeAll(selectedProducts);
            tableModelProducts.fireTableDataChanged();
        } catch (ComarModelException ex) {
            ex.printStackTrace();
            ComarUtils.showWarn(null, ex.getMessage());
        }
    }

    private void moveAction() {
        List<ComarInventoryNodeProduct> selectedProducts = getSelectedProductsFromTable();
        if (selectedProducts.isEmpty()) {
            ComarUtils.showWarn(null, "Seleccione al menos un producto");
            return;
        }

        Object[] values = rootNode.getCategories().toArray();
        Object response = WebOptionPane.showInputDialog(this, "Categoria", "Mover a", WebOptionPane.PLAIN_MESSAGE, null, values, values[0]);
        if (response != null) {
            try {
                ComarInventoryNodeCategory category = (ComarInventoryNodeCategory) response;
                rootNode.moveProductNodes(selectedProducts, category);
                tableModelProducts.getProducts().removeAll(selectedProducts);
                tableModelProducts.fireTableDataChanged();
            } catch (ComarModelException ex) {
                ex.printStackTrace();
                ComarUtils.showWarn(null, ex.getMessage());
            }
        }
    }
}
