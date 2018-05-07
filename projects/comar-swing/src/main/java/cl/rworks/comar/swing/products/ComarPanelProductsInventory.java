/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.products;

import cl.rworks.comar.swing.util.ComarButton;
import cl.rworks.comar.swing.util.ComarLabel;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarTable;
import cl.rworks.comar.swing.util.ComarTextField;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.splitpane.WebSplitPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author aplik
 */
public class ComarPanelProductsInventory extends ComarPanel {

    private TableModel model;
    private ComarPanel panelSearch;
    private ComarPanel panelLeft;
    private ComarPanel panelRight;
    private ComarPanelProductEditor panelProductEditor;

    public ComarPanelProductsInventory() {
        setLayout(new BorderLayout());
//        setBorder(new EmptyBorder(0, 50, 50, 50));

        buildPanelSearch();
        add(panelSearch, BorderLayout.NORTH);

        buildLeftPanel();
        buildRightPanel();
        WebSplitPane split = new WebSplitPane(WebSplitPane.HORIZONTAL_SPLIT, panelLeft, panelRight);
        split.setDividerLocation(1300);
        add(split, BorderLayout.CENTER);
    }

    private void buildPanelSearch() {
        panelSearch = new ComarPanel(new FlowLayout(FlowLayout.LEFT)) {
            {
                add(new ComarLabel("Buscar"));
                add(new ComarTextField(20));
                add(new ComarButton("Aceptar"));
            }
        };
    }

    private void buildLeftPanel() {
        panelLeft = new ComarPanel(new BorderLayout());
        this.model = new TableModel();
        ComarTable table = new ComarTable(model);
        panelLeft.add(new WebScrollPane(table), BorderLayout.CENTER);
    }

    private void buildRightPanel() {
        Dimension minSize = new Dimension(20, 20);
        Dimension maxSize = new Dimension(600, 500);

        panelProductEditor = new ComarPanelProductEditor();
        panelProductEditor.setBorder(new LineBorder(Color.BLUE));
        panelProductEditor.setMinimumSize(minSize);
        panelProductEditor.setMaximumSize(maxSize);

        ComarPanel panelButtons = new ComarPanel(new FlowLayout());
        panelButtons.setMinimumSize(minSize);
        panelButtons.setMaximumSize(maxSize);
        panelButtons.setBorder(new LineBorder(Color.RED));
        panelButtons.add(new ComarButton("Agregar"));
        panelButtons.add(new ComarButton("Editar"));

        panelRight = new ComarPanel();
//        panelEdition.setMaximumSize(new Dimension(600, 600));
        panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.PAGE_AXIS));
        panelRight.add(panelProductEditor);
        panelRight.add(panelButtons);
        panelRight.add(Box.createVerticalBox());
    }

    private class TableModel extends AbstractTableModel {

        private String[] colNames = new String[]{"Codigo", "Nombre", "Unidad", "Precio Compra", "Precio Venta", "Stock"};
        private List<ComarPanelProductRow> rows;

        public List<ComarPanelProductRow> getRows() {
            return rows;
        }

        public void setRows(List<ComarPanelProductRow> rows) {
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
        public Class<?> getColumnClass(int columnIndex) {
            return super.getColumnClass(columnIndex); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getColumnName(int column) {
            return colNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            ComarPanelProductRow row = rows.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return row.getCode();
                case 1:
                    return row.getName();
                case 2:
                    return row.getMetric();
                case 3:
                    return row.getBuyPrice();
                case 4:
                    return row.getSellPrice();
                case 5:
                    return row.getStock();
                default:
                    return "";
            }
        }

    }

//    private class Row {
//
//        private String code;
//        private String name;
//
//        public Row(String code, String name) {
//            this.code = code;
//            this.name = name;
//        }
//
//        public String getCode() {
//            return code;
//        }
//
//        public void setCode(String code) {
//            this.code = code;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//    }
}
