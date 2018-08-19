/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.pos;

import cl.rworks.comar.core.model.Metrica;
import cl.rworks.comar.core.model.VentaEntity;
import cl.rworks.comar.core.model.VentaUnidadEntity;
import cl.rworks.comar.core.model.impl.VentaEntityImpl;
import cl.rworks.comar.core.model.impl.VentaUnidadEntityImpl;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.util.ComarNumberFormat;
import cl.rworks.comar.swing.model.ComarControllerException;
import cl.rworks.comar.swing.model.ComarProduct;
import cl.rworks.comar.swing.model.ComarSell;
import cl.rworks.comar.swing.model.ComarSellUnit;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarUtils;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import cl.rworks.comar.swing.util.ComarPanelOptionsArea;
import cl.rworks.comar.swing.util.ComarPanelView;
import com.alee.extended.layout.FormLayout;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.table.renderers.WebTableCellRenderer;
import java.awt.Color;
import java.awt.Font;
import java.math.BigInteger;
import java.math.MathContext;
import java.time.LocalDate;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelPosArea extends ComarPanelView {

    private static final String TOOLTIP_CODE = "Ingrese el codigo del producto y presione ENTER\nPresione F2 para terminar la venta";
    //
    private ComarPanelPosAreaController controller = new ComarPanelPosAreaController();
    //
    private ComarPosTextField textCode;
    private ComarPosTable table;
    private TableModel tableModel;
    private ComarPosLabel labelTotalKey;
    private ComarPosLabel labelTotalValue;
    private ComarPosLabel labelTotalRedondeoKey;
    private ComarPosLabel labelTotalRedondeoValue;
//    private ComarPosLabel labelBoletaKey;
//    private ComarPosLabel labelBoletaValue;

    public ComarPanelPosArea() {
        super("Punto de Venta");
        getPanelContent().add(buildContent(), BorderLayout.CENTER);
        initToolTipHelp();
    }

    public ComarPanel buildContent() {
        ComarPanel panel = new ComarPanel(new BorderLayout());

        textCode = new ComarPosTextField(15);
        textCode.setFocusable(true);
        textCode.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    addProduct();
                }
            }

        });

        ComarPanelOptionsArea optionsArea = new ComarPanelOptionsArea();
        optionsArea.setBorder(new EmptyBorder(0, 10, 10, 10));
        optionsArea.addLeft(new ComarPosLabel("Codigo"));
        optionsArea.addLeft(textCode);
        optionsArea.addLeft(new ComarPosButton("Agregar", e -> addProduct()));
        optionsArea.addLeft(new WebLabel("      "));
        optionsArea.addLeft(new ComarPosButton("Terminar Venta", e -> endSell()));
        panel.add(optionsArea, BorderLayout.NORTH);

        tableModel = new TableModel();
        table = new ComarPosTable(tableModel);

        Object[][] cols = tableModel.getCols();
        for (int i = 0; i < cols.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth((Integer) cols[i][3]);
        }

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                    editProducts();
                }
            }
        });

        this.table.setDefaultRenderer(String.class, new WebTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                WebLabel label = (WebLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                label.setOpaque(false);
//                ComarPanelPosRow orow = tableModel.getRows().get(row);

//                String str;
//                BigDecimal bd = (BigDecimal) value;
//                if (orow.getMetric() == Metrica.UNIDADES) {
//                    str = ComarUtils.formatInt(bd.setScale(0, RoundingMode.HALF_UP).intValue());
//                } else {
//                    str = ComarUtils.formatDbl(bd.setScale(3, RoundingMode.HALF_UP).doubleValue());
//                }
//
//                label.setText(str);
                label.setText((String) value);
                label.setHorizontalAlignment(SwingUtilities.RIGHT);
//                label.setForeground(Color.BLUE);
//                label.setBackground(Color.WHITE);

                if (isSelected) {
                    label.setBackground(Color.LIGHT_GRAY);
                } else {
                    label.setBackground(Color.WHITE);
                }

//                if (column == 2 || column == 3 || column == 4) {
                if (column == 4) {
                    label.setFontStyle(Font.BOLD);
                } else {
                    label.setFontStyle(Font.PLAIN);
                }

                return label;
            }

        });

        JPopupMenu popup = new JPopupMenu();
        popup.add(new RemoveAction());
        table.setComponentPopupMenu(popup);

        ComarPanel panelTable = new ComarPanel(new BorderLayout());
        panelTable.add(new WebScrollPane(table), BorderLayout.CENTER);
        panel.add(panelTable, BorderLayout.CENTER);

        labelTotalKey = new ComarPosLabel("Total: ");
        labelTotalKey.setFontSize(36);
        labelTotalValue = new ComarPosLabel("0");
        labelTotalValue.setPreferredSize(300, 50);
        labelTotalValue.setFontSize(36);
        labelTotalValue.setHorizontalAlignment(SwingUtilities.RIGHT);

        labelTotalRedondeoKey = new ComarPosLabel("Redondeo: ");
        labelTotalRedondeoKey.setFontSize(36);
        labelTotalRedondeoValue = new ComarPosLabel("0");
        labelTotalRedondeoValue.setPreferredSize(300, 50);
        labelTotalRedondeoValue.setFontSize(36);
        labelTotalRedondeoValue.setHorizontalAlignment(SwingUtilities.RIGHT);

        ComarPanel panelSouthArea = new ComarPanel(new BorderLayout());
        ComarPanelOptionsArea panelTotal = new ComarPanelOptionsArea();
        panelTotal.addRight(labelTotalKey);
        panelTotal.addRight(labelTotalValue);
        panelSouthArea.add(panelTotal, BorderLayout.NORTH);
        ComarPanelOptionsArea panelTotalRedondeo = new ComarPanelOptionsArea();
        panelTotalRedondeo.addRight(labelTotalRedondeoKey);
        panelTotalRedondeo.addRight(labelTotalRedondeoValue);
        panelSouthArea.add(panelTotalRedondeo, BorderLayout.SOUTH);

        panel.add(panelSouthArea, BorderLayout.SOUTH);

        return panel;
    }

    private void initToolTipHelp() {
//        if (ComarSystem.getInstance().getProperties().isHelpActive()) {
        TooltipManager.addTooltip(textCode, TOOLTIP_CODE, TooltipWay.down, 0);
//        }
    }

    private void addProduct() {
        String code = textCode.getText().trim();
        if (code == null || code.isEmpty()) {
            return;
        }

        try {
            ComarPanelPosRow row = controller.getProduct(code);

            boolean go = true;
            if (!row.isFixedPrice()) {
                String value = WebOptionPane.showInputDialog(this, "Precio de Venta", "Agregar: " + row.getDescription(), WebOptionPane.QUESTION_MESSAGE);
                if (value != null) {
                    try {
                        BigDecimal price = ComarNumberFormat.parse(value);
                        row.setPrice(price);
                        go = true;
                    } catch (ParseException ex) {
                        go = false;
                    }
                } else {
                    go = false;
                }
            }

            if (go) {
                tableModel.addRow(row);
                tableModel.fireTableDataChanged();
//                table.setSelectedRow(tableModel.getRows().size() - 1);
                updateTotal();

                textCode.clear();
            }

        } catch (ComarServiceException e) {
            ComarUtils.showWarn(this, "Producto no encontrado: '" + code + "'");
        }
    }

    private void editProducts() {
        int[] vrows = table.getSelectedRows();
        if (vrows.length <= 0) {
//            ComarUtils.showWarn(this, "Seleccione un producto de la tabla");
            return;
        }

        List<ComarPanelPosRow> editList = new ArrayList<>();
        for (int i = 0; i < vrows.length; i++) {
            int mrow = table.convertRowIndexToModel(vrows[i]);
            ComarPanelPosRow row = tableModel.getRows().get(mrow);
            editList.add(row);
        }

        ComarPanelPosRow row = editList.get(0);
        if (row.isFixedPrice()) {
            BigDecimal count = row.getCount();
            String str = (String) WebOptionPane.showInputDialog(ComarPanelPosArea.this, "Cantidad", "Editar", JOptionPane.PLAIN_MESSAGE, null, null, ComarNumberFormat.format(count));
            if (str == null) {
                return;
            }

            try {
                count = ComarUtils.parse(str);
            } catch (ParseException ex) {
                return;
            }

            row.setCount(count);
        } else {
            BigDecimal price = row.getPrice();
            String str = (String) WebOptionPane.showInputDialog(ComarPanelPosArea.this, "Precio de Venta", "Editar", JOptionPane.PLAIN_MESSAGE, null, null, ComarNumberFormat.format(price));
            if (str == null) {
                return;
            }

            try {
                price = ComarUtils.parse(str);
            } catch (ParseException ex) {
                return;
            }

            row.setPrice(price);
        }

        updateTotal();
        tableModel.fireTableDataChanged();
    }

    private void updateTotal() {
        BigDecimal total = getTotal();
        String strTotal = ComarNumberFormat.format(total);
        this.labelTotalValue.setText(strTotal);

        BigDecimal totalRedondeo = getTotalRedondeo(total);
        String strTotalRedondeo = ComarNumberFormat.format(totalRedondeo);
        this.labelTotalRedondeoValue.setText(strTotalRedondeo);
    }

    private BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        List<ComarPanelPosRow> rows = tableModel.getRows();
        for (ComarPanelPosRow r : rows) {
            total = total.add(r.getSubtotal());
        }
        return total;
    }

    private BigDecimal getTotalRedondeo(BigDecimal total) {
        BigDecimal totalLow = new BigDecimal(total.intValue());
        totalLow = new BigDecimal(totalLow.divide(BigDecimal.TEN).intValue()).multiply(BigDecimal.TEN);

        BigDecimal totalRedondeo;
        if (total.add(totalLow.negate()).compareTo(new BigDecimal("5")) >= 0) {
            totalRedondeo = totalLow.add(BigDecimal.TEN);
        } else {
            totalRedondeo = totalLow;
        }
        return totalRedondeo;
    }

    private void removeProducts() {
        int[] vrows = table.getSelectedRows();
        if (vrows.length <= 0) {
//            ComarUtils.showWarn(this, "Seleccione un producto de la tabla");
            return;
        }

        List<ComarPanelPosRow> deleteList = new ArrayList<>();
        for (int i = 0; i < vrows.length; i++) {
            int mrow = table.convertRowIndexToModel(vrows[i]);
            ComarPanelPosRow row = tableModel.getRows().get(mrow);
            deleteList.add(row);
        }

        tableModel.getRows().removeAll(deleteList);
        tableModel.fireTableDataChanged();
    }

    public boolean dispatchKeyEventPos(KeyEvent e) {
        switch (e.getID()) {
            case KeyEvent.KEY_PRESSED:
                return keyPressed(e);
            case KeyEvent.KEY_RELEASED:
                return keyReleased(e);
            case KeyEvent.KEY_TYPED:
                return keyTyped(e);
            default:
                return false;
        }
    }

    public boolean keyPressed(KeyEvent e) {
//        System.out.println("keyPressed: " + e.getKeyCode());
        return false;
    }

    public boolean keyReleased(KeyEvent e) {
//        System.out.println("keyReleased: " + e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_F1) {
            textCode.requestFocus();
            return true;
        } else if (e.getKeyCode() == KeyEvent.VK_F2) {
            table.requestFocus();
            return true;
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            editProducts();
            return true;
        } else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            removeProducts();
            return true;
        }
        return false;
    }

    public boolean keyTyped(KeyEvent e) {
//        System.out.println("keyTyped: " + (e.getModifiers()));
        return false;
    }

    private class TableModel extends AbstractTableModel {

        private Object[][] cols = new Object[][]{
            {"Codigo", String.class, false, 200},
            {"Descripcion", String.class, false, 200},
            {"Precio Venta", String.class, false, 200},
            {"Cantidad", String.class, false, 200},
            {"Subtotal", String.class, false, 200},
            {"Precio Fijo", String.class, false, 50},
            {"En Boleta", String.class, false, 50}
        };

        private List<ComarPanelPosRow> rows = new ArrayList<>();

        public Object[][] getCols() {
            return cols;
        }

        public List<ComarPanelPosRow> getRows() {
            return rows;
        }

        public void setRows(List<ComarPanelPosRow> rows) {
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
        public String getColumnName(int column) {
            return (String) cols[column][0];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return (Class) cols[columnIndex][1];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return (Boolean) cols[columnIndex][2];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            ComarPanelPosRow row = rows.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return row.getCode();
                case 1:
                    return row.getDescription();
                case 2:
//                    return row.getPrice();
                    return ComarUtils.format(row.getPrice());
                case 3: {
                    String str;
                    BigDecimal bd = row.getCount();
                    if (row.getMetric() == Metrica.UNIDADES) {
                        str = ComarUtils.formatInt(bd.setScale(0, RoundingMode.HALF_UP).intValue());
                    } else {
                        str = ComarUtils.formatDbl(bd.setScale(3, RoundingMode.HALF_UP).doubleValue());
                    }
//                    return row.getCount();
                    return str;
                }
                case 4:
//                    return row.getSubtotal();
                    return ComarUtils.format(row.getSubtotal());
                case 5:
                    return row.isFixedPrice() ? "Si" : "No";
                case 6:
                    return row.isIncludeOnSell() ? "Si" : "No";
                default:
                    return "";
            }
        }

        private void addRow(ComarPanelPosRow row) {
            ComarPanelPosRow found = null;
            for (ComarPanelPosRow r : rows) {
                if (r.getCode().equals(row.getCode())) {
                    found = r;
                    break;
                }
            }

            if (found == null) {
                this.rows.add(row);
            } else {
                if (found.isFixedPrice()) {
                    found.setCount(found.getCount().add(row.getCount()));
                } else {
                    found.setPrice(found.getPrice().add(row.getPrice()));
                }
            }
        }
    }

    private class RemoveAction extends AbstractAction {

        public RemoveAction() {
            putValue(NAME, "Quitar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            removeProducts();
        }

    }

    private void endSell() {
        if (tableModel.getRows().size() <= 0) {
            ComarUtils.showWarn(this, "Ingrese productos para la venta");
            return;
        }

        String strValue = WebOptionPane.showInputDialog(this, "Total Pagado", "Terminar Venta", WebOptionPane.PLAIN_MESSAGE);
        if (strValue == null) {
            return;
        }

        BigDecimal totalPagado;
        try {
            totalPagado = ComarNumberFormat.parse(strValue);
        } catch (ParseException ex) {
            return;
        }

//        BigDecimal totalVendido = getTotal();
        BigDecimal totalVendidoRedondeo = getTotalRedondeo(getTotal());
        if (totalPagado.compareTo(totalVendidoRedondeo) < 0) {
            ComarUtils.showWarn(this, "El 'Total Pagado' es menor al 'Total Vendido'");
            return;
        }

        BigDecimal totalEnVoleta = BigDecimal.ZERO;
        List<ComarPanelPosRow> rows = tableModel.getRows();
        for (ComarPanelPosRow row : rows) {
            if (row.isIncludeOnSell()) {
                BigDecimal price = row.getPrice();
                BigDecimal count = row.getCount();
                BigDecimal subtotal = price.multiply(count);
                totalEnVoleta = totalEnVoleta.add(subtotal);
            }
        }

        CreateSellPanel panel = new CreateSellPanel(totalPagado, totalVendidoRedondeo, totalEnVoleta);
        int response = WebOptionPane.showConfirmDialog(this, panel, "Terminar Venta", WebOptionPane.OK_CANCEL_OPTION, WebOptionPane.PLAIN_MESSAGE);
        if (response == WebOptionPane.OK_OPTION) {

            String strCodigo = WebOptionPane.showInputDialog(this, "Codigo de Venta", "Terminar Venta", WebOptionPane.PLAIN_MESSAGE);
            if (strValue == null) {
                strCodigo = "";
            }
            
            try {
                controller.addSell(strCodigo, LocalDate.now(), rows);
            } catch (ComarControllerException e) {
                ComarUtils.showWarn(this, e.getMessage());
            }

        }

    }

    private class CreateSellPanel extends WebPanel {

        private CreateSellPanel(BigDecimal totalPagado, BigDecimal totalVendidoRedondeo, BigDecimal totalEnBoleta) {
            BigDecimal vuelto = totalPagado.add(totalVendidoRedondeo.negate());

            setLayout(new FormLayout());
            add(new ComarPosLabel("Total Pagado: "));
            add(new ComarPosLabel(ComarNumberFormat.format(totalPagado)));
            add(new ComarPosLabel("Total Vendido Redondeo: "));
            add(new ComarPosLabel(ComarNumberFormat.format(totalVendidoRedondeo)));
            add(new ComarPosLabel(" "));
            add(new ComarPosLabel(" "));
            add(new ComarPosLabel("Vuelto: "));
            add(new ComarPosLabel(ComarNumberFormat.format(vuelto)));
            add(new ComarPosLabel(" "));
            add(new ComarPosLabel(" "));
            add(new ComarPosLabel("Boleta Por: "));
            add(new ComarPosLabel(ComarNumberFormat.format(totalEnBoleta)));
            add(new WebLabel("Exento: "));
            add(new WebLabel(ComarNumberFormat.format(totalVendidoRedondeo.add(totalEnBoleta.negate()))));
            add(new ComarPosLabel(" "));
            add(new ComarPosLabel(" "));
        }

    }

}
