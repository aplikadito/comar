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
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
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
    private ComarTextField textCode = new ComarTextField(20);
    private ComarTable table;
    private TableModel tableModel;
    //
    private ComarTextField textTotal;
    private ComarButton buttonAdd;
    private ComarButton buttonEdit;

    public ComarPanelPointOfSell() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(30, 30, 30, 30));

        add(new ComarPanelTitle("Punto de Venta"), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);

        initToolTipHelp();
        initShortcuts();
    }

    public ComarPanel buildContent() {
        ComarPanel panel = new ComarPanel(new BorderLayout());

        textCode.setFocusable(true);
        textCode.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
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
                editProduct();
            }
        });

        ComarPanel panelWest = new ComarPanel(new FlowLayout());
        panelWest.add(new ComarLabel("Codigo"));
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
                    editProduct();
                }
            }

        });

        panel.add(new ComarPanel(new BorderLayout()) {
            {
                table.setFontSize(ComarSystem.getInstance().getProperties().getFontSize());
                add(new WebScrollPane(table), BorderLayout.CENTER);
            }
        }, BorderLayout.CENTER);

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

    private void initShortcuts() {
        this.getInputMap(JPanel.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("F1"), "gotoCode");
        this.getActionMap().put("gotoCode", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textCode.requestFocus();
            }
        });

        this.getInputMap(JPanel.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("F2"), "edit");
        this.getActionMap().put("edit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editProduct();
            }
        });
    }

    private void addProduct() {
        String text = textCode.getText();

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

    private void editProduct() {
        table.requestFocus();

        int vrow = table.getSelectedRow();
        if (vrow < 0) {
            ComarUtils.showWarn(this, "Seleccione un producto de la tabla");
            return;
        }

        int mrow = table.convertRowIndexToModel(vrow);
        Row row = tableModel.getRows().get(mrow);

        double count = row.getCount();
        String strCount = ComarUtils.formatDbl(count);
        String str = (String) WebOptionPane.showInputDialog(ComarPanelPointOfSell.this, "Producto: " + row.getDescription(), "Cantidad", JOptionPane.PLAIN_MESSAGE, null, null, strCount);
        if (str == null) {
            return;
        }

        count = 1;
        try {
            count = ComarUtils.parseDbl(str);
        } catch (ParseException ex) {
            return;
        }

        row.setCount(count);

//        textCode.requestFocus();
    }

    public void loadCard() {
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
        private double subtotal;

        public Row() {
            this("", "");
        }

        public Row(String code, String name) {
            this.code = code;
            this.description = name;
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

        public double getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(double subtotal) {
            this.subtotal = subtotal;
        }

    }

}
