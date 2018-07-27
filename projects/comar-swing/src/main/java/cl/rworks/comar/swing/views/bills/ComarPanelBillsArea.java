/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.bills;

import cl.rworks.comar.swing.main.ComarSystem;
import cl.rworks.comar.swing.model.ComarBill;
import cl.rworks.comar.swing.model.ComarBillUnit;
import cl.rworks.comar.swing.model.ComarControllerException;
import cl.rworks.comar.swing.util.ComarActionSimple;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarPanelButtonsArea;
import cl.rworks.comar.swing.util.ComarPanelDate;
import cl.rworks.comar.swing.util.ComarPanelFactory;
import cl.rworks.comar.swing.util.ComarPanelTitle;
import cl.rworks.comar.swing.util.ComarPanelView;
import cl.rworks.comar.swing.util.ComarUtils;
import cl.rworks.comar.swing.util.WebTextFieldFactory;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.splitpane.WebSplitPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author aplik
 */
public class ComarPanelBillsArea extends ComarPanelView {

    private ComarPanel panelContent;
    private ComarPanelBillsController controller;
    //
    private ComarPanel panelLeft;
    private ComarPanel panelRight;
    //
    private ComarPanelDate panelDateFilter;
    private WebTable tableBills;
    private TableModelBills tableModelBills;
    private ComarPanelButtonsArea panelBillOptionsUp;
    private ComarPanelButtonsArea panelBillOptionsDown;
    //
    private ComarPanel panelRightTableArea;
    private ComarPanelButtonsArea panelRightTableButtonsArea;
    private ComarPanelTitle panelBatchTitle;
    private WebPopupMenu popupTable;
    private WebTextField textProductCode;
    private WebButton buttonAddProduct;
    private WebButton buttonDeleteProducts;
    private WebTable tableBillUnits;
    private TableModelBillUnits tableModelBillUnits;

    public ComarPanelBillsArea() {
        super("Facturas");
        initComponents();
    }

    private void initComponents() {
        controller = new ComarPanelBillsController();

        panelContent = new ComarPanelFactory().boxLayoutPage().create();
        getPanelContent().add(panelContent, BorderLayout.CENTER);

        WebSplitPane split = new WebSplitPane(WebSplitPane.HORIZONTAL_SPLIT, initLeft(), initRight());
        split.setDividerLocation(350);
        panelContent.add(split);
    }

    private ComarPanel initLeft() {
        panelLeft = new ComarPanelFactory().borderLayout().create();

        panelLeft.add(panelBillOptionsUp = new ComarPanelButtonsArea(), BorderLayout.NORTH);
        panelLeft.add(new WebScrollPane(tableBills = new WebTable()), BorderLayout.CENTER);
        panelLeft.add(panelBillOptionsDown = new ComarPanelButtonsArea(), BorderLayout.SOUTH);

        tableModelBills = new TableModelBills();
        tableBills.setModel(tableModelBills);
        tableBills.setCellSelectionEnabled(true);
//        tableBills.setComponentPopupMenu(popupTable = new WebPopupMenu());
//        popupTable.add(new ComarActionSimple("Eliminar", e -> deleteProductsAction()));

        panelBillOptionsUp.addCenter(panelDateFilter = new ComarPanelDate());
        panelBillOptionsUp.addCenter(new WebButton("Buscar", e -> searchAction()));

        panelBillOptionsDown.addRight(new WebButton("Agregar", e -> addBillAction()));
        panelBillOptionsDown.addRight(new WebButton("Eliminar", e -> deleteBillAction()));
//        panelDateFilter.getButtonSearch().addActionListener(e -> searchBillsAction());

        return panelLeft;
    }

    private ComarPanel initRight() {
        panelRight = new ComarPanelFactory().borderLayout().create();

        panelRight.add(panelBatchTitle = new ComarPanelTitle(""), BorderLayout.NORTH);
        panelRight.add(panelRightTableArea = new ComarPanelFactory().borderLayout().border(new EmptyBorder(10, 10, 10, 10)).create(), BorderLayout.CENTER);

        panelRightTableArea.add(panelRightTableButtonsArea = new ComarPanelButtonsArea(), BorderLayout.NORTH);
        panelRightTableArea.add(new WebScrollPane(tableBillUnits = new WebTable()), BorderLayout.CENTER);

        panelRightTableButtonsArea.addLeft(new WebLabel("Codigo"));
        panelRightTableButtonsArea.addLeft(textProductCode = new WebTextFieldFactory().cols(20).actionListener(e -> addProductAction()).create());
        panelRightTableButtonsArea.addLeft(buttonAddProduct = new WebButton("Agregar", e -> addProductAction()));
        panelRightTableButtonsArea.addRight(buttonDeleteProducts = new WebButton("Eliminar", e -> deleteProductsAction()));

        tableModelBillUnits = new TableModelBillUnits();
        tableBillUnits.setModel(tableModelBillUnits);
        tableBillUnits.setCellSelectionEnabled(true);
        tableBillUnits.setComponentPopupMenu(popupTable = new WebPopupMenu());
        popupTable.add(new ComarActionSimple("Eliminar", e -> deleteProductsAction()));

        return panelRight;
    }

    private class TableModelBills extends AbstractTableModel {

        private String[] colNames = new String[]{"Identificador", "Fecha"};
        private List<ComarBill> bills = null;

        public List<ComarBill> getBills() {
            return bills;
        }

        public void setBills(List<ComarBill> bills) {
            this.bills = bills;
        }

        @Override
        public int getRowCount() {
            return bills != null ? bills.size() : 0;
        }

        @Override
        public int getColumnCount() {
            return colNames.length;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return LocalDate.class;
                case 1:
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
            ComarBill bill = bills.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return bill.getEntity().getCodigo();
                case 1:
                    return bill.getEntity().getFecha();
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
                default:
                    return false;
            }
        }

        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
            ComarBill bill = bills.get(rowIndex);
//            try {
            switch (columnIndex) {
                case 0:
                    String code = (String) value;
//                        billUnit.updateCode(code);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    break;
                case 1:
                    LocalTime date = (LocalTime) value;
//                        billUnit.updateBuyPrice(buyPrice);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    break;
                default:
                    break;
            }
        }
    }

    private class TableModelBillUnits extends AbstractTableModel {

        private String[] colNames = new String[]{"Codigo", "Descripcion", "Precio", "Cantidad", "Subtotal"};
        private List<ComarBillUnit> billUnits = null;

        public List<ComarBillUnit> getBillUnits() {
            return billUnits;
        }

        public void setProducts(List<ComarBillUnit> products) {
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
            ComarBillUnit billUnit = billUnits.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return billUnit.getEntity().getCodigoProducto();
                case 1:
                    return billUnit.getEntity().getDescripcionProducto();
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

    }

    private void searchAction() {
        int[] value = panelDateFilter.getValue();
        List<ComarBill> bills = controller.searchBills(value);
        tableModelBills.setBills(bills);
        tableModelBills.fireTableDataChanged();
    }

    private void addBillAction() {
        ComarDialogBillInsert dialog = new ComarDialogBillInsert(ComarSystem.getInstance().getFrame());
        dialog.getPanel().updateForm(LocalDate.now(), "");
        dialog.showMe();

        if (dialog.isOk()) {
            try {
                ComarBill bill = dialog.getBill();
                controller.addBill(bill);
                searchAction();
            } catch (ComarControllerException ex) {
                ex.printStackTrace();
                ComarUtils.showWarn(this, ex.getMessage());
            }
        }
    }

    private List<ComarBill> getSelectedBills() {
        int[] vrows = this.tableBills.getSelectedRows();
        if (vrows.length == 0) {
            return null;
        }

        List<ComarBill> list = new ArrayList<>();
        for (int i = 0; i < vrows.length; i++) {
            int vrow = vrows[i];
            int mrow = this.tableBills.convertRowIndexToModel(vrow);
            ComarBill bill = this.tableModelBills.getBills().get(mrow);
            list.add(bill);
        }

        return list;
    }

//    private void editBillAction() {
//        ComarBill bill = getSelectedBill();
//        if (bill == null) {
//            ComarUtils.showInfo(this, "Seleccione una factura");
//            return;
//        }
//
//        ComarDialogBillInsert dialog = new ComarDialogBillInsert(ComarSystem.getInstance().getFrame(), bill);
//        dialog.showMe();
//
//        if (dialog.isOk()) {
//            try {
//                controller.updateBill(bill);
//            } catch (ComarControllerException ex) {
//                ex.printStackTrace();
//                ComarUtils.showWarn(this, ex.getMessage());
//            }
//        }
//    }
    private void deleteBillAction() {
        List<ComarBill> bills = getSelectedBills();
        if (bills == null) {
            ComarUtils.showInfo(this, "Seleccione al menos una factura");
            return;
        }

        int r = ComarUtils.showYesNo(this, "Desea eliminar las facturas seleccionadas?", "Eliminar Facturas");
        if (r == JOptionPane.YES_OPTION) {
            try {
                controller.deleteBills(bills);
                searchAction();
            } catch (ComarControllerException ex) {
                ComarUtils.showWarn(this, ex.getMessage());
            }
        }
    }

    private void addProductAction() {
    }

    private void deleteProductsAction() {
    }

}
