/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.pointofsell;

import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.util.BorderLayoutPanel;
import cl.rworks.comar.swing.util.ComarLabel;
import cl.rworks.comar.swing.util.ComarPanelCard;
import cl.rworks.comar.swing.util.ComarPanelTitle;
import cl.rworks.comar.swing.util.ComarTable;
import cl.rworks.comar.swing.util.ComarTextField;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelPointOfSell extends ComarPanelCard {

    private ComarTextField textCode = new ComarTextField(20);
    private ComarTable table = new ComarTable();
    private TableModel model = new TableModel();
    private ComarTextField textTotal = new ComarTextField(10);

    public ComarPanelPointOfSell() {
        setLayout(new BorderLayout());
        add(new ComarPanelTitle("Punto de Venta"), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);
    }

    public WebPanel buildContent() {
        BorderLayoutPanel panel = new BorderLayoutPanel();

        panel.getPanelNorth().add(new WebPanel(new FlowLayout()) {
            {
                add(new ComarLabel("Codigo"));
                add(textCode);
            }

        });

        panel.getPanelCenter().add(new WebPanel() {
            {
                table.setModel(model);
                table.setFontSize(ComarSystem.getInstance().getProperties().getNormalFontSize());
                add(new WebScrollPane(table));
            }
        }, BorderLayout.CENTER);

        panel.getPanelSouth().add(new WebPanel(new FlowLayout()) {
            {
                add(new ComarLabel("Total"));
                
                textTotal.setEditable(false);
                textTotal.setText("<total>");
                add(textTotal);
            }
        });

        return panel;
    }

    private class TableModel extends AbstractTableModel {

        private String[] colNames = new String[]{
            "Codigo",
            "Nombre"
        };
        private List<Row> rows;

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
                    return row.getName();
                default:
                    return "";
            }
        }

    }

    private class Row {

        private String code;
        private String name;

        public Row(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}
