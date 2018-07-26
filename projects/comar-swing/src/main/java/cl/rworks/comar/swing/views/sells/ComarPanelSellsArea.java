/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.sells;

import cl.rworks.comar.swing.main.ComarSystem;
import cl.rworks.comar.swing.model.ComarBill;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarPanelDate;
import cl.rworks.comar.swing.util.ComarPanelView;
import cl.rworks.comar.swing.util.ComarTable;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;
import java.awt.BorderLayout;
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
    private ComarPanelDate panelSearch;
    private final ComarPanelSellsController controller = new ComarPanelSellsController();
    private ComarTable table;
    private TableModel tableModel;

    public ComarPanelSellsArea() {
        super("Ventas");

        initComponents();
        initToolTipHelp();
    }

    private void initComponents() {
        ComarPanel panelContent = new ComarPanel(new BorderLayout());
        getPanelContent().add(panelContent, BorderLayout.CENTER);

        panelSearch = new ComarPanelDate();
        panelContent.add(panelSearch);

//        panelSearch.getButtonSearch().addActionListener(e -> searchAction());
    }

    private void initToolTipHelp() {
        if (ComarSystem.getInstance().getProperties().isHelpActive()) {
//            TooltipManager.addTooltip(panelSearch.getButtonSearch(), TOOLTIP_SEARCH, TooltipWay.down, 0);
        }
    }

    private void searchAction() {
        LOG.debug("Buscando ... ");
        System.out.println("Buscando ... ");
        String day = (String) panelSearch.getComboYear().getSelectedItem();
        String month = (String) panelSearch.getComboMonth().getSelectedItem();
        String year = (String) panelSearch.getComboDay().getSelectedItem();

        List<ComarBill> bills = controller.findBills(day, month, year);

    }
}
