/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.core.data.ComarProductHistorialDb;
import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.util.BorderLayoutPanel;
import com.alee.laf.table.WebTable;
import io.permazen.JTransaction;
import io.permazen.Permazen;
import io.permazen.ValidationMode;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author aplik
 */
public class ComarPanelHistorial extends BorderLayoutPanel {

    private BaseEditorPanel panelEditor;
    private TableModel tableModel;

    public ComarPanelHistorial() {
        this.panelEditor = new BaseEditorPanel();
        getPanelCenter().add(panelEditor, BorderLayout.CENTER);

        this.tableModel = new TableModel();
        WebTable table = panelEditor.getTable();
        table.setModel(tableModel);
//        table.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
//                    new EditAction().actionPerformed(null);
//                }
//            }
//        });

//        WebPopupMenu popup = new WebPopupMenu();
//        popup.add(new EditAction());
////        popup.add(new AddStockAction());
////        popup.add(new WebSeparator());
//        popup.add(new DeleteAction());
//        table.setComponentPopupMenu(popup);
//
        this.panelEditor.getButtonSearch().setAction(new SearchAction());
//        this.panelEditor.getButtonClear().setAction(new ClearAction());
        this.panelEditor.getButtonAdd().setVisible(false);
        this.panelEditor.getButtonEdit().setVisible(false);
        this.panelEditor.getButtonDelete().setVisible(false);

    }

    private class TableModel extends AbstractTableModel {

        private String[] colNames = new String[]{
            "Codigo",
            "Fecha",
            "Accion",
            "Propiedad",
            "Valor Antiguo",
            "Valor Nuevo"
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
                    return row.getDate();
                case 2:
                    return row.getAction();
                case 3:
                    return row.getProperty();
                case 4:
                    return row.getOldValue();
                case 5:
                    return row.getNewValue();
                default:
                    return "";
            }
        }

    }

    private class Row {

        private String code;
        private String date;
        private String action;
        private String property;
        private String oldValue;
        private String newValue;

        public Row(String code, String date, String action, String property, String oldValue, String newValue) {
            this.code = code;
            this.date = date;
            this.action = action;
            this.property = property;
            this.oldValue = oldValue;
            this.newValue = newValue;
        }

        public String getCode() {
            return code;
        }

        public String getDate() {
            return date;
        }

        public String getAction() {
            return action;
        }

        public String getProperty() {
            return property;
        }

        public String getOldValue() {
            return oldValue;
        }

        public String getNewValue() {
            return newValue;
        }

    }

    private class SearchAction extends AbstractAction {

        public SearchAction() {
            putValue(NAME, "Buscar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String text = panelEditor.getTextSearch().getText();
            Permazen db = ComarSystem.getInstance().getService().getDb();
            JTransaction jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
            JTransaction.setCurrent(jtx);
            try {

                List<Row> rows = new ArrayList<>();
                for (ComarProductHistorialDb element : ComarProductHistorialDb.search(text)) {
                    rows.add(toRow(element));
                }

                tableModel.setRows(rows);
                tableModel.fireTableDataChanged();
            } finally {
                jtx.rollback();
                JTransaction.setCurrent(null);
            }
        }

    }

    private Row toRow(ComarProductHistorialDb e) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy HH:mm:ss").toFormatter();
        String strDateTime = e.getDateTime().format(formatter);
        return new Row(e.getCode(), strDateTime, e.getAction(), e.getProperty(), e.getOldValue(), e.getNewValue());
    }
}
