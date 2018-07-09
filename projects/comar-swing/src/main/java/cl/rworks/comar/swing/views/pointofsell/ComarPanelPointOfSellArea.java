/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.pointofsell;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.service.ComarTransaction;
import cl.rworks.comar.core.model.ComarMetric;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.util.ComarButton;
import cl.rworks.comar.swing.util.ComarCount;
import cl.rworks.comar.swing.util.ComarLabel;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarPanelTitle;
import cl.rworks.comar.swing.util.ComarTable;
import cl.rworks.comar.swing.util.ComarTextField;
import cl.rworks.comar.swing.util.ComarUtils;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import cl.rworks.comar.core.service.ComarService;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelPointOfSellArea extends ComarPanel {

    private static final String TOOLTIP_CODE = "Ingrese el codigo del producto y presione ENTER\nPuede presionar F1 para obtener el foco del texto";
    private static final String TOOLTIP_ADD = "Agregar el producto a la venta";
    private static final String TOOLTIP_EDIT = "Editar la cantidad del producto seleccionado\nPuede presionar F2 para editar el producto seleccionado";
    //
    private ComarLabel labelCode;
    private ComarTextField textCode;
    private ComarTable table;
    private TableModel tableModel;
    private ComarTextField textTotal;
    private ComarButton buttonAdd;
    private ComarButton buttonEdit;

    public ComarPanelPointOfSellArea() {
        setLayout(new BorderLayout());
//        setBorder(new EmptyBorder(30, 30, 30, 30));

        add(new ComarPanelTitle("Punto de Venta"), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);

        initToolTipHelp();
    }

    public ComarPanel buildContent() {
        ComarPanel panel = new ComarPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        labelCode = new ComarLabel("Codigo");

        textCode = new ComarTextField(20);
        textCode.setFocusable(true);
        textCode.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    addProduct();
                }
            }

        });

        buttonAdd = new ComarButton("Agregar");
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        buttonEdit = new ComarButton("Editar");
        buttonEdit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editProducts();
            }
        });

        ComarPanel panelWest = new ComarPanel(new FlowLayout());
        panelWest.add(labelCode);
        panelWest.add(textCode);
        panelWest.add(buttonAdd);
        panelWest.add(buttonEdit);

        ComarPanel panelEast = new ComarPanel(new FlowLayout(FlowLayout.RIGHT));

        ComarPanel panelNorth = new ComarPanel(new BorderLayout());
        panelNorth.add(panelWest, BorderLayout.WEST);
        panelNorth.add(panelEast, BorderLayout.EAST);
        panel.add(panelNorth, BorderLayout.NORTH);

        tableModel = new TableModel();
        table = new ComarTable(tableModel);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                    editProducts();
                }
            }
        });

        this.table.setDefaultRenderer(ComarCount.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                Row orow = tableModel.getRows().get(row);

                String str;
                BigDecimal bd = (BigDecimal) value;
                if (orow.getMetric() == ComarMetric.UNIDADES) {
                    str = ComarUtils.formatInt(bd.setScale(0, RoundingMode.HALF_UP).intValue());
                } else {
                    str = ComarUtils.formatDbl(bd.setScale(3, RoundingMode.HALF_UP).doubleValue());
                }

                label.setText(str);
                label.setHorizontalAlignment(SwingUtilities.RIGHT);

                return label;
            }

        });

        JPopupMenu popup = new JPopupMenu();
        popup.add(new EditAction());
        popup.add(new RemoveAction());
        table.setComponentPopupMenu(popup);

        ComarPanel panelTable = new ComarPanel(new BorderLayout());
        panelTable.add(new WebScrollPane(table), BorderLayout.CENTER);
        panel.add(panelTable, BorderLayout.CENTER);

        textTotal = new ComarTextField(10);
        textTotal.setFontSize(36);
        panel.add(new ComarPanel(new FlowLayout()) {
            {
                ComarLabel labelTotal = new ComarLabel("Total");
                labelTotal.setFontSize(36);
                add(labelTotal);

                textTotal.setEditable(false);
                add(textTotal);
            }
        }, BorderLayout.EAST);

        return panel;
    }

    private void initToolTipHelp() {
        if (ComarSystem.getInstance().getProperties().isHelpActive()) {
            TooltipManager.addTooltip(textCode, TOOLTIP_CODE, TooltipWay.down, 0);
            TooltipManager.addTooltip(buttonAdd, TOOLTIP_ADD, TooltipWay.down, 0);
            TooltipManager.addTooltip(buttonEdit, TOOLTIP_EDIT, TooltipWay.down, 0);
        }
    }

    private void addProduct() {
        String code = textCode.getText().trim();
        if (code == null || code.isEmpty()) {
            return;
        }

        try {
            Row row = findProduct(code);
            tableModel.getRows().add(row);
            tableModel.fireTableDataChanged();
            table.setSelectedRow(tableModel.getRows().size() - 1);
            textCode.clear();
        } catch (ComarServiceException e) {
            ComarUtils.showWarn(this, "Producto no encontrado: '" + code + "'");
        }
    }

    private Row findProduct(String code) throws ComarServiceException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            ComarProduct product = service.getProductByCode(code);
            if (product != null) {
                Row row = new Row();
                row.setCode(product.getCode());
                row.setDescription(product.getDescription());
                row.setCount(BigDecimal.ONE);
                row.setMetric(product.getMetric());
                row.setSellPrice(product.getSellPrice());
                return row;
            } else {
                throw new ComarServiceException("Producto no encontrado: '" + code + "'");
            }
        } catch (ComarServiceException e) {
            throw new ComarServiceException(e.getMessage());
        }

    }

    private void editProducts() {
        int[] vrows = table.getSelectedRows();
        if (vrows.length <= 0) {
//            ComarUtils.showWarn(this, "Seleccione un producto de la tabla");
            return;
        }

        List<Row> editList = new ArrayList<>();
        for (int i = 0; i < vrows.length; i++) {
            int mrow = table.convertRowIndexToModel(vrows[i]);
            Row row = tableModel.getRows().get(mrow);
            editList.add(row);
        }

        BigDecimal count = editList.get(0).getCount();
        String msg = editList.size() == 1 ? editList.get(0).getDescription() : " ... varios";
        String str = (String) WebOptionPane.showInputDialog(ComarPanelPointOfSellArea.this, "Producto: " + msg, "Cantidad", JOptionPane.PLAIN_MESSAGE, null, null, count.toString());
        if (str == null) {
            return;
        }

        try {
            count = ComarUtils.parse(str);
        } catch (ParseException ex) {
            return;
        }

        for (Row row : editList) {
            row.setCount(count);
        }
        tableModel.fireTableDataChanged();
    }

    private void removeProducts() {
        int[] vrows = table.getSelectedRows();
        if (vrows.length <= 0) {
//            ComarUtils.showWarn(this, "Seleccione un producto de la tabla");
            return;
        }

        List<Row> deleteList = new ArrayList<>();
        for (int i = 0; i < vrows.length; i++) {
            int mrow = table.convertRowIndexToModel(vrows[i]);
            Row row = tableModel.getRows().get(mrow);
            deleteList.add(row);
        }

        tableModel.getRows().removeAll(deleteList);
        tableModel.fireTableDataChanged();
    }

    public void loadCard() {
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

        private String[] colNames = new String[]{
            "Codigo",
            "Descripcion",
            "Cantidad",
            "Subtotal"
        };
        private List<Row> rows = new ArrayList<>();

        public List<Row> getRows() {
            return rows;
        }

        public void setRows(List<Row> rows) {
            this.rows = rows;
        }

        @Override
        public int getRowCount() {
            return rows != null ? rows.size() : 0;
        }

        @Override
        public int getColumnCount() {
            return colNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return colNames[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return String.class;
                case 1:
                    return String.class;
                case 2:
                    return ComarCount.class;
                case 3:
                    return BigDecimal.class;
                default:
                    return String.class;
            }
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Row row = rows.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return row.getCode();
                case 1:
                    return row.getDescription();
                case 2:
                    return row.getCount();
                case 3:
                    return row.getSubtotal();
                default:
                    return "";
            }
        }
    }

    private class Row {

        private String code;
        private String description;
        private ComarMetric metric;
        private BigDecimal count;
        private BigDecimal sellPrice;

        public Row() {
            this("", "", BigDecimal.ZERO);
        }

        public Row(String code, String name, BigDecimal sellPrice) {
            this.code = code;
            this.description = name;
            this.sellPrice = sellPrice;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public ComarMetric getMetric() {
            return metric;
        }

        public void setMetric(ComarMetric metric) {
            this.metric = metric;
        }

        public BigDecimal getCount() {
            return count;
        }

        public void setCount(BigDecimal count) {
            this.count = count;
        }

        public BigDecimal getSellPrice() {
            return sellPrice;
        }

        public void setSellPrice(BigDecimal sellPrice) {
            this.sellPrice = sellPrice;
        }

        public BigDecimal getSubtotal() {
            return sellPrice.multiply(count);
        }

    }

    private class EditAction extends AbstractAction {

        public EditAction() {
            putValue(NAME, "Editar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            editProducts();
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

}
