/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.pointofsell;

import cl.rworks.comar.core.data.ComarProductDb;
import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.util.ComarButton;
import cl.rworks.comar.swing.util.ComarLabel;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarPanelCard;
import cl.rworks.comar.swing.util.ComarPanelTitle;
import cl.rworks.comar.swing.util.ComarTable;
import cl.rworks.comar.swing.util.ComarTextField;
import cl.rworks.comar.swing.util.ComarUtils;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;
import io.permazen.JTransaction;
import io.permazen.Permazen;
import io.permazen.ValidationMode;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelPointOfSell extends ComarPanelCard {

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

    public ComarPanelPointOfSell() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(30, 30, 30, 30));

        add(new ComarPanelTitle("Punto de Venta"), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);

        initToolTipHelp();
    }

    public ComarPanel buildContent() {
        ComarPanel panel = new ComarPanel(new BorderLayout());

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
        String text = textCode.getText();
        if (text == null || text.isEmpty()) {
            return;
        }

        Row row = null;
        Permazen db = ComarSystem.getInstance().getService().getDb();
        JTransaction jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
        JTransaction.setCurrent(jtx);
        try {
            ComarProductDb pdb = ComarProductDb.getByCode(text);
            if (pdb != null) {
                row = new Row();
                row.setCode(pdb.getCode());
                row.setDescription(pdb.getDescription());
                row.setCount(1);
                row.setSellPrice(pdb.getSellPrice());
            }
        } finally {
            jtx.rollback();
            JTransaction.setCurrent(null);
        }

        if (row != null) {
            tableModel.getRows().add(row);
            tableModel.fireTableDataChanged();
            table.setSelectedRow(tableModel.getRows().size() - 1);
            textCode.clear();
        } else {
            ComarUtils.showWarn(this, "Producto no encontrado: '" + text + "'");
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

        double count = editList.get(0).getCount();
        String msg = editList.size() == 1 ? editList.get(0).getDescription() : " ... varios";
        String strCount = ComarUtils.formatDbl(count);
        String str = (String) WebOptionPane.showInputDialog(ComarPanelPointOfSell.this, "Producto: " + msg, "Cantidad", JOptionPane.PLAIN_MESSAGE, null, null, strCount);
        if (str == null) {
            return;
        }

        try {
            count = ComarUtils.parseDbl(str);
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
                    return Double.class;
                case 3:
                    return Double.class;
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
        private double count;
        private double sellPrice;

        public Row() {
            this("", "", 0);
        }

        public Row(String code, String name, double sellPrice) {
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

        public double getCount() {
            return count;
        }

        public void setCount(double count) {
            this.count = count;
        }

        public double getSellPrice() {
            return sellPrice;
        }

        public void setSellPrice(double sellPrice) {
            this.sellPrice = sellPrice;
        }

        public double getSubtotal() {
            return count * sellPrice;
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
