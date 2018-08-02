/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.bills;

import cl.rworks.comar.swing.model.ComarBill;
import cl.rworks.comar.swing.util.ComarException;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarPanelDate;
import com.alee.extended.layout.FormLayout;
import com.alee.laf.label.WebLabel;
import com.alee.laf.text.WebTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.LocalDate;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author aplik
 */
public class ComarPanelBillInsert extends ComarPanel {

    private ComarPanelBillInsertController controller = new ComarPanelBillInsertController();
    //
    private ComarPanel panelBillArea;
    private ComarPanelDate panelDate;
    private WebTextField textCode;

    public ComarPanelBillInsert() {

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        add(initBillArea(), BorderLayout.CENTER);
    }

    private ComarPanel initBillArea() {
        panelBillArea = new ComarPanel();
        panelBillArea.setLayout(new BoxLayout(panelBillArea, BoxLayout.PAGE_AXIS));
//        panelBillArea.setBorder(new TitledBorder("Factura"));

        ComarPanel form = new ComarPanel(new FormLayout(10, 10));
        form.add(new WebLabel("Fecha"));
        form.add(panelDate = new ComarPanelDate("", new FlowLayout(FlowLayout.LEFT)));
        form.add(new WebLabel("Identificador"));
        form.add(textCode = new WebTextField(30));

        form.setPreferredSize(new Dimension(300, 100));
        form.setMaximumSize(new Dimension(300, 100));
        form.setAlignmentX(0f);
        form.setAlignmentY(0f);

        panelBillArea.add(form, BorderLayout.CENTER);
        return panelBillArea;
    }

//    private ComarPanel initBillUnitArea() {
//        panelBillUnitArea = new ComarPanel(new BorderLayout());
//        panelBillUnitArea.setBorder(new TitledBorder("Productos"));
//
//        ComarPanelButtonsArea panel = new ComarPanelButtonsArea();
//        panel.addLeft(new WebLabel("Codigo"));
//        panel.addLeft(textProductCode = new WebTextFieldFactory().cols(20).actionListener(e -> addBillUnitAction()).create());
//        panel.addLeft(buttonAddUnit = new WebButton("Agregar", e -> addBillUnitAction()));
//        panel.addRight(buttonDeleteUnit = new WebButton("Eliminar", e -> deleteBillUnitAction()));
//        panelBillUnitArea.add(panel, BorderLayout.NORTH);
//
//        panelBillUnitArea.add(new WebScrollPane(tableBillUnits = new WebTable()), BorderLayout.CENTER);
//        tableModelBillUnits = new TableModelBillUnits();
//        tableBillUnits.setModel(tableModelBillUnits);
//        tableBillUnits.setCellSelectionEnabled(true);
////        tableBillUnits.setComponentPopupMenu(popupTable = new WebPopupMenu());
////        popupTable.add(new ComarActionSimple("Eliminar", e -> deleteProductsAction()));
//
//        return panelBillUnitArea;
//    }
//
//    private void addBillUnitAction() {
//        try {
//            String code = textProductCode.getText();
//            ComarBillUnit unit = controller.createBillUnit(code);
//            bill.addUnit(unit);
//            this.tableModelBillUnits.fireTableDataChanged();
//        } catch (ComarControllerException ex) {
//            ex.printStackTrace();
//            ComarUtils.showWarn(this, ex.getMessage());
//        }
//    }
//
//    private void deleteBillUnitAction() {
//    }
    public void updateForm(ComarBill bill) {
        updateForm(bill.getEntity().getFecha(), bill.getEntity().getCodigo());
    }

    public void updateForm(LocalDate date, String code) {
        this.panelDate.setDate(date);
        this.textCode.setText(code);
    }

    public ComarBill getBill() throws ComarException {
        ComarBill bill = new ComarBill();
        bill.getEntity().setFecha(panelDate.getDate());
        bill.getEntity().setCodigo(textCode.getText());
        return bill;
    }

//    public LocalDate getDate() throws ComarValidationException {
//        return this.panelDate.getLocalDate();
//    }
//
//    public String getCode() {
//        return this.textBillCode.getText();
//    }
//
//    public List<ComarBillUnit> getUnits() {
//        return units;
//    }
//    private class TableModelBillUnits extends AbstractTableModel {
//
//        private String[] colNames = new String[]{"Codigo", "Descripcion", "Precio", "Cantidad", "Subtotal"};
//
//        @Override
//        public int getRowCount() {
//            return bill.getUnits() != null ? bill.getUnits().size() : 0;
//        }
//
//        @Override
//        public int getColumnCount() {
//            return colNames.length;
//        }
//
//        @Override
//        public Class<?> getColumnClass(int columnIndex) {
//            switch (columnIndex) {
//                case 0:
//                    return String.class;
//                case 1:
//                    return String.class;
//                case 2:
//                    return BigDecimal.class;
//                case 3:
//                    return BigDecimal.class;
//                case 4:
//                    return BigDecimal.class;
//                default:
//                    return String.class;
//            }
//        }
//
//        @Override
//        public String getColumnName(int column) {
//            return colNames[column];
//        }
//
//        @Override
//        public Object getValueAt(int rowIndex, int columnIndex) {
//            ComarBillUnit billUnit = bill.getUnits().get(rowIndex);
//            switch (columnIndex) {
//                case 0:
//                    return billUnit.getEntity().getCodigoProducto();
//                case 1:
//                    return billUnit.getEntity().getDescripcionProducto();
//                case 2:
//                    return billUnit.getEntity().getPrecioCompra();
//                case 3:
//                    return billUnit.getEntity().getCantidad();
//                case 4: {
//                    BigDecimal buyPrice = billUnit.getEntity().getPrecioCompra();
//                    BigDecimal quantity = billUnit.getEntity().getCantidad();
//                    return buyPrice.multiply(quantity);
//                }
//                default:
//                    return "";
//            }
//        }
//
//        @Override
//        public boolean isCellEditable(int rowIndex, int columnIndex) {
//            switch (columnIndex) {
//                case 0:
//                    return false;
//                case 1:
//                    return false;
//                case 2:
//                    return true;
//                case 3:
//                    return true;
//                case 4:
//                    return false;
//                default:
//                    return false;
//            }
//        }
//
//        @Override
//        public void setValueAt(Object value, int rowIndex, int columnIndex) {
//            ComarBillUnit billUnit = bill.getUnits().get(rowIndex);
//            switch (columnIndex) {
//                case 2:
//                    BigDecimal buyPrice = (BigDecimal) value;
//                    billUnit.updateBuyPrice(buyPrice);
//                    fireTableCellUpdated(rowIndex, columnIndex);
//                    break;
//                case 3:
//                    BigDecimal quantity = (BigDecimal) value;
//                    billUnit.updateQuantity(quantity);
//                    fireTableCellUpdated(rowIndex, columnIndex);
//                    break;
//                default:
//                    break;
//            }
//        }
//    }

}
