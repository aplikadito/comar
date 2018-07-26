/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.sells;

import cl.rworks.comar.core.model.Metrica;
import cl.rworks.comar.swing.main.ComarSystem;
import cl.rworks.comar.swing.util.ComarComboBox;
import cl.rworks.comar.swing.util.ComarCount;
import cl.rworks.comar.swing.util.ComarLabel;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarPanelView;
import cl.rworks.comar.swing.util.ComarTable;
import com.alee.laf.button.WebButton;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;
import java.awt.BorderLayout;
import java.math.BigDecimal;
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
          private WebButton buttonSearch;
          private ComarPanel panelFilter = new ComarPanel();
//          private ComarPanel panelContent;
          private final List<String> listDay = new ArrayList<>();
          private final List<String> listMonth = new ArrayList<>();
          private final List<String> listYear = new ArrayList<>();
          private ComarComboBox comarComboDay;
          private ComarComboBox comarComboMonth;
          private ComarComboBox comarComboYear;
          private final ComarPanelSellsAreaController controller = new ComarPanelSellsAreaController();
          private ComarTable table;
          private TableModel tableModel;

          public ComarPanelSellsArea() {
                    super("Ventas");

                    initComponents();
                    initToolTipHelp();
          }

          private void initComponents() {
                    ComarPanel panelContent = new ComarPanel();
                    getPanelContent().add(panelContent, BorderLayout.NORTH);
                    ComarPanel panelSearch = new ComarPanel();
                    panelFilter = getPanelFilter("Filtro :");
                    buttonSearch = new WebButton("Buscar", e -> seachProductAction());
                    panelSearch.add(panelFilter);
                    panelSearch.add(buttonSearch);
                    panelContent.add(panelSearch);
                    ComarPanel panelTable = new ComarPanel(new BorderLayout());
                    table = new ComarTable();
                    tableModel = new TableModel();
                    table.setModel(tableModel);
                    panelTable.add(new WebScrollPane(table), BorderLayout.CENTER);

                    panelContent.add(panelSearch);
                    getPanelContent().add(panelTable, BorderLayout.CENTER);

          }

          private ComarPanel getPanelFilter(String labelText) {
                    ComarLabel labelFilter = new ComarLabel(labelText);
                    panelFilter.add(labelFilter);
                    /**
                     * Inicialmente agregaba int, pero no pude hacer que con un renderer el 0 me lo trnsformara en un - para mostrar
                     * enel combo, corté por lo sano y deje los combos de strings
                     */

                    listDay.add("-");
                    for (int i = 1; i < 32; i++) {
                              listDay.add(String.valueOf(i));
                    }
                    listMonth.add("-");
                    for (int i = 1; i < 13; i++) {
                              listMonth.add(String.valueOf(i));
                    }
                    listYear.add("-");
                    for (int i = 2018; i < 2032; i++) {
                              listYear.add(String.valueOf(i));
                    }
                    comarComboDay = new ComarComboBox(listDay);
                    comarComboMonth = new ComarComboBox(listMonth);
                    comarComboYear = new ComarComboBox(listYear);
                    panelFilter.add(comarComboDay);
                    panelFilter.add(comarComboMonth);
                    panelFilter.add(comarComboYear);
                    return panelFilter;
          }

          private void initToolTipHelp() {
                    if (ComarSystem.getInstance().getProperties().isHelpActive()) {
                              TooltipManager.addTooltip(buttonSearch, TOOLTIP_SEARCH, TooltipWay.down, 0);
                    }
          }

          private void seachProductAction() {
                    LOG.info("Buscando ... ");
                    String day = (String) comarComboDay.getSelectedItem();
                    String month = (String) comarComboMonth.getSelectedItem();
                    String year = (String) comarComboYear.getSelectedItem();
                    List<Object> findProducts = controller.findProducts(day, month, year);
                    List<Row> rows = new ArrayList<>();
                    findProducts.stream().map((o) -> new Row(o.toString(), o.toString(), BigDecimal.valueOf(1))).map((row) -> {
                              row.setCount(BigDecimal.valueOf(1));
                              return row;
                    }).forEachOrdered((row) -> {
                              rows.add(row);
                    });
                    tableModel.setRows(rows);
                    tableModel.fireTableStructureChanged();

          }

          /**
           *
           */
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

          /**
           *
           */
          private class Row {

                    private String code;
                    private String description;
                    private Metrica metric;
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

                    public Metrica getMetric() {
                              return metric;
                    }

                    public void setMetric(Metrica metric) {
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
}
