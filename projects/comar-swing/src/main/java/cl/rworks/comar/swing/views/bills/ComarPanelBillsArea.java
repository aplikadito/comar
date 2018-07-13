/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.bills;

import cl.rworks.comar.swing.main.ComarSystem;
import cl.rworks.comar.swing.model.BillUnitModel;
import cl.rworks.comar.swing.model.Workspace;
import cl.rworks.comar.swing.util.ComarActionSimple;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarPanelButtonsArea;
import cl.rworks.comar.swing.util.ComarPanelTitle;
import cl.rworks.comar.swing.util.ComarPanelView;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.splitpane.WebSplitPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author aplik
 */
public class ComarPanelBillsArea extends ComarPanelView {

    private ComarPanel panelContent;
    private ComarPanelBillsAreaController controller;
    //
    private ComarPanelTitle panelBatchTitle;
    private WebTextField textProductCode;
    private WebButton buttonAddProduct;
    private WebButton buttonDeleteProducts;
    private WebTable tableProducts;
    private TableModelBillUnits tableModelProducts;

    public ComarPanelBillsArea() {
        super("Facturas");
        initComponents();
    }

    private void initComponents() {
        Workspace ws = ComarSystem.getInstance().getWorkspace();
        controller = new ComarPanelBillsAreaController();

        panelContent = new ComarPanel();

        panelContent.setLayout(new BoxLayout(panelContent, BoxLayout.PAGE_AXIS));
        getPanelContent().add(panelContent, BorderLayout.CENTER);

        ComarPanel panelSplitLeft = new ComarPanel(new BorderLayout());
        ComarPanel panelSplitRight = new ComarPanel(new BorderLayout());

        WebSplitPane split = new WebSplitPane(WebSplitPane.HORIZONTAL_SPLIT, panelSplitLeft, panelSplitRight);
        split.setDividerLocation(350);
        panelContent.add(split);

        // LEFT
        // RIGHT
        WebPanel panelRightContent = new WebPanel();
        panelSplitRight.add(panelRightContent, BorderLayout.NORTH);

        panelBatchTitle = new ComarPanelTitle("");
        panelRightContent.add(panelBatchTitle, BorderLayout.NORTH);

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
        tableModelProducts = new TableModelBillUnits();
        tableProducts = new WebTable(tableModelProducts);
        tableProducts.setCellSelectionEnabled(true);

        WebPopupMenu popupTable = new WebPopupMenu();
//        popup.add(new WebButton("Eliminar", e -> deleteProductsAction()));
        popupTable.add(new ComarActionSimple("Eliminar", e -> deleteProductsAction()));
        tableProducts.setComponentPopupMenu(popupTable);

        panelTableContent.add(new WebScrollPane(tableProducts), BorderLayout.CENTER);

    }

    private class TableModelBillUnits extends AbstractTableModel {

        private String[] colNames = new String[]{"Codigo", "Descripcion", "Precio", "Cantidad", "Subtotal"};
        private List<BillUnitModel> billUnits = null;

        public List<BillUnitModel> getBillUnits() {
            return billUnits;
        }

        public void setProducts(List<BillUnitModel> products) {
            this.billUnits = products;
        }

        @Override
        public int getRowCount() {
            return billUnits != null ? billUnits.size() : 0;
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
                    return BigDecimal.class;
                case 3:
                    return BigDecimal.class;
                case 4:
                    return BigDecimal.class;
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
            BillUnitModel billUnit = billUnits.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return billUnit.getEntity().getCodigo();
                case 1:
                    return billUnit.getEntity().getDescripcion();
                case 2:
                    return billUnit.getEntity().getPrecioCompra();
                case 3:
                    return billUnit.getEntity().getCantidad();
                case 4: {
                    BigDecimal buyPrice = billUnit.getEntity().getPrecioCompra();
                    BigDecimal quantity = billUnit.getEntity().getCantidad();
                    return buyPrice.multiply(quantity);
                }
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
                    return false;
                case 2:
                    return true;
                case 3:
                    return true;
                case 4:
                    return false;
                default:
                    return false;
            }
        }

        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
            BillUnitModel billUnit = billUnits.get(rowIndex);
//            try {
                switch (columnIndex) {
                    case 0:
                        String code = (String) value;
//                        billUnit.updateCode(code);
                        fireTableCellUpdated(rowIndex, columnIndex);
                        break;
                    case 2:
                        BigDecimal buyPrice = (BigDecimal) value;
//                        billUnit.updateBuyPrice(buyPrice);
                        fireTableCellUpdated(rowIndex, columnIndex);
                        break;
                    case 3:
                        BigDecimal quantity = (BigDecimal) value;
//                        billUnit.updateQuantity(quantity);
                        fireTableCellUpdated(rowIndex, columnIndex);
                        break;
                    default:
                        break;
                }
//            } catch (ModelException ex) {
//                ComarUtils.showWarn(ComarPanelFacturasArea.this, ex.getMessage());
//            }
        }
    }

    private void addProductAction() {
    }

    private void deleteProductsAction() {
    }

}
