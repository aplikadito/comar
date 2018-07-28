/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.bills;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.swing.main.ComarSystem;
import cl.rworks.comar.swing.model.ComarBill;
import cl.rworks.comar.swing.model.ComarBillUnit;
import cl.rworks.comar.swing.model.ComarControllerException;
import cl.rworks.comar.swing.model.ComarProduct;
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
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.splitpane.WebSplitPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private ComarPanelTitle panelSelectedBillTitle;
    private WebPopupMenu popupTableBills;
    private WebTextField textProductCode;
    private WebButton buttonAddProduct;
    private WebButton buttonDeleteProducts;
    private WebTable tableBillUnits;
    private TableModelBillUnits tableModelBillUnits;
    //
    private ComarBill selectedBill;

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

        panelBillOptionsUp.addCenter(panelDateFilter = new ComarPanelDate());
        panelBillOptionsUp.addCenter(new WebButton("Buscar", e -> searchAction()));

        tableModelBills = new TableModelBills();
        tableBills.setModel(tableModelBills);
        tableBills.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                setSelectedBill(getSelectedBill());
            }

        });

        tableBills.setComponentPopupMenu(popupTableBills = new WebPopupMenu());
        popupTableBills.add(new ComarActionSimple("Editar", e -> editBillAction()));
        popupTableBills.add(new ComarActionSimple("Eliminar", e -> deleteBillAction()));

        panelBillOptionsDown.addRight(new WebButton("Agregar", e -> addBillAction()));
        panelBillOptionsDown.addRight(new WebButton("Eliminar", e -> deleteBillAction()));
//        panelDateFilter.getButtonSearch().addActionListener(e -> searchBillsAction());

        return panelLeft;
    }

    private ComarPanel initRight() {
        panelRight = new ComarPanelFactory().borderLayout().create();

        panelRight.add(panelSelectedBillTitle = new ComarPanelTitle(""), BorderLayout.NORTH);
        panelRight.add(panelRightTableArea = new ComarPanelFactory().borderLayout().border(new EmptyBorder(10, 10, 10, 10)).create(), BorderLayout.CENTER);

        panelRightTableArea.add(panelRightTableButtonsArea = new ComarPanelButtonsArea(), BorderLayout.NORTH);
        panelRightTableArea.add(new WebScrollPane(tableBillUnits = new WebTable()), BorderLayout.CENTER);

        panelRightTableButtonsArea.addLeft(new WebLabel("Codigo"));
        panelRightTableButtonsArea.addLeft(textProductCode = new WebTextFieldFactory().cols(20).actionListener(e -> addBillUnitAction()).create());
        panelRightTableButtonsArea.addLeft(buttonAddProduct = new WebButton("Agregar", e -> addBillUnitAction()));
        panelRightTableButtonsArea.addRight(buttonDeleteProducts = new WebButton("Eliminar", e -> deleteBillUnitsAction()));

        tableModelBillUnits = new TableModelBillUnits();
        tableBillUnits.setModel(tableModelBillUnits);
        tableBillUnits.setCellSelectionEnabled(true);
        tableBillUnits.setComponentPopupMenu(popupTableBills = new WebPopupMenu());
        popupTableBills.add(new ComarActionSimple("Eliminar", e -> deleteBillAction()));

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

    }

    private class TableModelBillUnits extends AbstractTableModel {

        private String[] colNames = new String[]{"Codigo", "Descripcion", "Precio", "Cantidad", "Subtotal"};

        private boolean contains(String code) {
            List<ComarBillUnit> units = selectedBill != null ? selectedBill.getUnits() : null;
            if (units != null) {
                return units.stream().anyMatch(e -> e.getEntity().getCodigoProducto().equals(code));
            } else {
                return false;
            }

        }

        @Override
        public int getRowCount() {
            List<ComarBillUnit> units = selectedBill != null ? selectedBill.getUnits() : null;
            return units != null ? units.size() : 0;
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
            List<ComarBillUnit> units = selectedBill.getUnits();
            ComarBillUnit billUnit = units.get(rowIndex);
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

        setSelectedBill(null);
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

    private void editBillAction() {
        ComarBill updateBill = getSelectedBill();
        if (updateBill == null) {
            return;
        }

        ComarDialogBillInsert dialog = new ComarDialogBillInsert(ComarSystem.getInstance().getFrame());
        dialog.getPanel().updateForm(updateBill.getEntity().getFecha(), updateBill.getEntity().getCodigo());
        dialog.showMe();

        if (dialog.isOk()) {
            try {
                ComarBill bill = dialog.getBill();
                updateBill.getEntity().setFecha(bill.getEntity().getFecha());
                updateBill.getEntity().setCodigo(bill.getEntity().getCodigo());
                controller.updateBill(updateBill);
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

    private ComarBill getSelectedBill() {
        int vrow = this.tableBills.getSelectedRow();
        if (vrow == -1) {
            return null;
        }

        int mrow = this.tableBills.convertRowIndexToModel(vrow);
        return this.tableModelBills.getBills().get(mrow);
    }

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

    private void addBillUnitAction() {
        if (selectedBill == null) {
            ComarUtils.showWarn(this, "Seleccione una factura");
            return;
        }

        String code = this.textProductCode.getText();
        if (code == null || code.isEmpty()) {
            ComarUtils.showWarn(this, "Ingrese un codigo de producto existente");
            return;
        }

        if (tableModelBillUnits.contains(code)) {
            return;
        }

        ComarProduct product = controller.getProduct(code);
        if (product == null) {
            ComarUtils.showWarn(this, "Producto no encontrado");
            return;
        }

        try {
            ComarBillUnit unit = new ComarBillUnit(product);
            controller.addBillUnit(unit, selectedBill);
            tableModelBillUnits.fireTableDataChanged();
        } catch (ComarControllerException ex) {
            ComarUtils.showWarn(this, ex.getMessage());
        }

    }

    private void deleteBillUnitsAction() {
        if (selectedBill == null) {
            ComarUtils.showWarn(this, "Seleccione una factura");
            return;
        }

        List<ComarBillUnit> units = getSelectedBillUnits();
        if (units == null) {
            ComarUtils.showWarn(this, "Seleccione al menos un elemento de la tabla");
            return;
        }

        int r = ComarUtils.showYesNo(this, "Desea eliminar los elementos seleccionados?", "Eliminar");
        if (r == WebOptionPane.YES_OPTION) {
            try {
                controller.deleteBillUnits(units, selectedBill);
                tableModelBillUnits.fireTableDataChanged();
            } catch (ComarControllerException ex) {
                ComarUtils.showWarn(this, ex.getMessage());
            }
        }
    }

    private void setSelectedBill(ComarBill sBill) {
        selectedBill = sBill;
        if (selectedBill != null) {
            LocalDate date = selectedBill.getEntity().getFecha();
            String code = selectedBill.getEntity().getCodigo();
            panelSelectedBillTitle.setTitle(String.format("%s:%s", "Codigo", code));
            panelSelectedBillTitle.setTitleEast(String.format("%s:%s", "Fecha", date));
            tableModelBillUnits.fireTableDataChanged();
        } else {
            panelSelectedBillTitle.setTitle("");
            panelSelectedBillTitle.setTitleEast("");
            tableModelBillUnits.fireTableDataChanged();
        }

    }

    private List<ComarBillUnit> getSelectedBillUnits() {
        if (selectedBill == null) {
            return null;
        }

        int[] vrows = this.tableBillUnits.getSelectedRows();
        if (vrows.length == 0) {
            return null;
        }

        List<ComarBillUnit> units = new ArrayList<>();
        for (int i = 0; i < vrows.length; i++) {
            int vrow = vrows[i];
            int mrow = tableBillUnits.convertRowIndexToModel(vrow);
            ComarBillUnit unit = selectedBill.getUnits().get(mrow);
            units.add(unit);
        }

        return units;
    }
}
