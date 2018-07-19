/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.sells;

import cl.rworks.comar.swing.main.ComarSystem;
import cl.rworks.comar.swing.util.ComarComboBox;
import cl.rworks.comar.swing.util.ComarLabel;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarPanelView;
import cl.rworks.comar.swing.util.ComarTable;
import com.alee.laf.button.WebButton;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.TableModel;
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
                    getPanelContent().add(panelContent, BorderLayout.CENTER);
                    ComarPanel panelSearch = new ComarPanel();
                    panelFilter = getPanelFilter("Filtro :");
                    buttonSearch = new WebButton("Buscar", e -> seachProductAction());
                    panelSearch.add(panelFilter);
                    panelSearch.add(buttonSearch);
                    panelContent.add(panelSearch);

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
                    LOG.debug("Buscando ... ");
                    System.out.println("Buscando ... ");
                    String day = (String) comarComboDay.getSelectedItem();
                    String month = (String) comarComboMonth.getSelectedItem();
                    String year = (String) comarComboYear.getSelectedItem();
                    List<Object> findProducts = controller.findProducts(day, month, year);

          }
}
