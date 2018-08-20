/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.sells;

import cl.rworks.comar.swing.main.ComarSystem;
import cl.rworks.comar.swing.model.ComarBill;
import cl.rworks.comar.swing.model.ComarSell;
import cl.rworks.comar.swing.model.ComarSellUnit;
import cl.rworks.comar.swing.util.ComarActionSimple;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarPanelDate;
import cl.rworks.comar.swing.util.ComarPanelFactory;
import cl.rworks.comar.swing.util.ComarPanelOptionsArea;
import cl.rworks.comar.swing.util.ComarPanelTitle;
import cl.rworks.comar.swing.util.ComarPanelView;
import cl.rworks.comar.swing.util.ComarUtils;
import cl.rworks.comar.swing.util.WebTextFieldFactory;
import cl.rworks.comar.swing.views.bills.ComarPanelBillsArea;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.splitpane.WebSplitPane;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
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
    private ComarPanelDate panelSellDateSearch;
    private WebTable tableSells;
    private TableModelSells tableModelSells;
    private WebTextField textSellCodeSearch;
    //
    // RIGHT AREA
    //
    private ComarPanelTitle panelSelectedBillTitle;
    private ComarPanel panelRightTableArea;
    private WebTable tableSellUnits;
    private TableModelSellUnits tableModelSellUnits;
    private TableRowSorter sorter;
    private WebTextField textSearch;
    //
    private ComarSell selectedSell;

    public ComarPanelSellsArea() {
        super("Administrar Ventas");
        initComponents();
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

        WebTabbedPane tabbed = new WebTabbedPane();
        ComarPanelOptionsArea panelSearchBillByDate = new ComarPanelOptionsArea();
        panelSearchBillByDate.addCenter(panelSellDateSearch = new ComarPanelDate());
        panelSearchBillByDate.addCenter(new WebButton("Buscar", e -> searchBillByDateAction()));
        tabbed.addTab("Por Fecha", panelSearchBillByDate);

        ComarPanelOptionsArea panelSearchBillByCode = new ComarPanelOptionsArea();
        panelSearchBillByCode.addCenter(textSellCodeSearch = new WebTextField(20));
        panelSearchBillByCode.addCenter(new WebButton("Buscar", e -> searchBillByCodeAction()));
        tabbed.addTab("Por Codigo", panelSearchBillByCode);

        tableSells = new WebTable();
        tableModelSells = new TableModelSells();
        tableSells.setModel(tableModelSells);

        tableSells.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                setSelectedSell(getSelectedSell());
            }

        });

        panelLeft.setBorder(new TitledBorder("Ventas"));
        panelLeft.add(tabbed, BorderLayout.NORTH);
        panelLeft.add(new WebScrollPane(tableSells), BorderLayout.CENTER);

        return panelLeft;
    }

    private ComarPanel initRight() {
        ComarPanel panelRight = new ComarPanelFactory().borderLayout().create();

        panelRight.add(panelSelectedBillTitle = new ComarPanelTitle(""), BorderLayout.NORTH);
        panelRight.add(panelRightTableArea = new ComarPanelFactory().borderLayout().border(new EmptyBorder(10, 10, 10, 10)).create(), BorderLayout.CENTER);

        panelRightTableArea.add(new WebScrollPane(tableSellUnits = new WebTable()), BorderLayout.CENTER);

        tableModelSellUnits = new TableModelSellUnits();
        tableSellUnits.setModel(tableModelSellUnits);
        ComarUtils.initTable(tableSellUnits);

//        popupTableBills.add(new ComarActionSimple("Eliminar", e -> deleteBillAction()));
        sorter = new TableRowSorter(tableModelSellUnits);
        tableSellUnits.setRowSorter(sorter);

        ComarPanelOptionsArea panelSearch = new ComarPanelOptionsArea();
        panelSearch.addLeft(new WebLabel("Buscar"));
        panelSearch.addLeft(textSearch = new WebTextFieldFactory().cols(30).actionListener(e -> searchSellUnitAction()).create());
        panelSearch.addLeft(new WebButton("Buscar", e -> searchSellUnitAction()));
        panelRightTableArea.add(panelSearch, BorderLayout.SOUTH);

        return panelRight;
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

    private class TableModelSellUnits extends AbstractTableModel {

        private Object[][] cols = new Object[][]{
            {"Codigo", String.class, false},
            {"Descripcion", String.class, false},
            {"Precio Venta", BigDecimal.class, false},
            {"Cantidad", BigDecimal.class, false},
            {"Subtotal", BigDecimal.class, false}
        };

        @Override
        public int getRowCount() {
            List<ComarSellUnit> units = selectedSell != null ? selectedSell.getUnits() : null;
            return units != null ? units.size() : 0;
        }

        @Override
        public int getColumnCount() {
            return cols.length;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return (Class) cols[columnIndex][1];
        }

        @Override
        public String getColumnName(int columnIndex) {
            return (String) cols[columnIndex][0];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            List<ComarSellUnit> units = selectedSell.getUnits();
            ComarSellUnit unit = units.get(rowIndex);

            BigDecimal price = unit.getEntity().getPrecioVenta();
            BigDecimal quantity = unit.getEntity().getCantidad();
            BigDecimal subtotal = price.multiply(quantity);
            switch (columnIndex) {
                case 0:
                    return unit.getProduct().getEntity().getCodigo();
                case 1:
                    return unit.getProduct().getEntity().getDescripcion();
                case 2:
                    return price;
                case 3:
                    return quantity;
                case 4:
                    return subtotal;
                default:
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return (Boolean) cols[columnIndex][2];
        }
        
//        @Override
//        public boolean isPercentual(int rowIndex, int columnIndex) {
//            return (Boolean) cols[columnIndex][3];
//        }

    }

    private ComarSell getSelectedSell() {
        int vrow = this.tableSells.getSelectedRow();
        if (vrow == -1) {
            return null;
        }

        int mrow = this.tableSells.convertRowIndexToModel(vrow);
        return this.tableModelSells.getRows().get(mrow);
    }
    
    private void setSelectedSell(ComarSell sell) {
        selectedSell = sell;
        if (selectedSell != null) {
            LocalDate date = selectedSell.getEntity().getFecha();
            String code = selectedSell.getEntity().getCodigo();
            panelSelectedBillTitle.setTitle(String.format("%s: %s", "Venta", code));
            panelSelectedBillTitle.setTitleEast(String.format("%s", date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
            tableModelSellUnits.fireTableDataChanged();
        } else {
            panelSelectedBillTitle.setTitle("");
            panelSelectedBillTitle.setTitleEast("");
            tableModelSellUnits.fireTableDataChanged();
        }

    }
    
     private void searchBillByDateAction() {
        int[] value = panelSellDateSearch.getValue();
        List<ComarSell> sells = controller.searchSellsByDate(value);
        tableModelSells.setRows(sells);
        tableModelSells.fireTableDataChanged();

        setSelectedSell(null);
    }

    private void searchBillByCodeAction() {
        ComarUtils.showWarn(this, "Funcionalidad temporalmente deshabilitada");
    }

    public void searchSellUnitAction() {
        String text = textSearch.getText();
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text + "", 0, 1));
        sorter.sort();
    }
}
