/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.sells;

import cl.rworks.comar.swing.main.ComarSystem;
import cl.rworks.comar.swing.model.ComarSell;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarPanelDate;
import cl.rworks.comar.swing.util.ComarPanelFactory;
import cl.rworks.comar.swing.util.ComarPanelView;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.splitpane.WebSplitPane;
import com.alee.laf.table.WebTable;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;
import java.awt.BorderLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelSellsArea extends ComarPanelView {

    private static final Logger LOG = LoggerFactory.getLogger(ComarPanelSellsArea.class);

    private static final String TOOLTIP_SEARCH = "Ingrese en que rango de fechas\n quiere realizar la busqueda";
    private final ComarPanelSellsController controller = new ComarPanelSellsController();
    //
    // LEFT AREA
    //
    private ComarPanelDate panelDate;
    private WebButton buttonSearchSells;
    private WebTable tableSells;
    private TableModelSells tableModelSells;
    //
    // RIGHT AREA
    //

    public ComarPanelSellsArea() {
        super("Ventas Por Fecha");

        initComponents();
        initToolTipHelp();
    }

    private void initComponents() {
        ComarPanel panelContent = new ComarPanelFactory().boxLayoutPage().create();
        getPanelContent().add(panelContent, BorderLayout.CENTER);

        WebSplitPane split = new WebSplitPane(WebSplitPane.HORIZONTAL_SPLIT, initLeft(), initRight());
        split.setDividerLocation(350);
        panelContent.add(split);
    }

    private ComarPanel initLeft() {
        ComarPanel panelLeft = new ComarPanel(new BorderLayout());

        panelDate = new ComarPanelDate();
        buttonSearchSells = new WebButton("Buscar", e -> searchAction());
        panelLeft.add(new ComarPanelFactory().flowLayoutLeft().add(panelDate, buttonSearchSells).create(), BorderLayout.NORTH);

        tableSells = new WebTable();
        tableModelSells = new TableModelSells();
        tableSells.setModel(tableModelSells);
        panelLeft.add(new WebScrollPane(tableSells), BorderLayout.CENTER);

        return panelLeft;
    }

    private ComarPanel initRight() {
        ComarPanel panelRight = new ComarPanel(new BorderLayout());
        return panelRight;
    }

    private void initToolTipHelp() {
        if (ComarSystem.getInstance().getProperties().isHelpActive()) {
            TooltipManager.addTooltip(buttonSearchSells, TOOLTIP_SEARCH, TooltipWay.down, 0);
        }
    }

    private void searchAction() {
        LOG.info("Buscando ... ");

        int[] value = panelDate.getValue();
        List<ComarSell> rows = controller.searchSells(value);
//        List<Row> rows = new ArrayList<>();
//        findProducts.stream().map((o) -> new Row(o.toString(), o.toString(), BigDecimal.valueOf(1))).map((row) -> {
//            row.setCount(BigDecimal.valueOf(1));
//            return row;
//        }).forEachOrdered((row) -> {
//            rows.add(row);
//        });
        tableModelSells.setRows(rows);
        tableModelSells.fireTableStructureChanged();
    }

    private class TableModelSells extends AbstractTableModel {

        private String[] colNames = new String[]{"Codigo", "Fecha"};
        private List<ComarSell> rows = new ArrayList<>();

        public List<ComarSell> getRows() {
            return rows;
        }

        public void setRows(List<ComarSell> rows) {
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
                    return LocalDate.class;
                default:
                    return String.class;
            }
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            ComarSell row = rows.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return row.getEntity().getCodigo();
                case 1:
                    return row.getEntity().getFecha();
                default:
                    return "";
            }
        }
    }

//    private class Row {
//
//        private ComarSell sell;
//
//        public Row(String code, LocalDate date) {
//            this.code = code;
//            this.date = date;
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
//        public LocalDate getDate() {
//            return date;
//        }
//
//        public void setDate(LocalDate date) {
//            this.date = date;
//        }
//
//    }
}
