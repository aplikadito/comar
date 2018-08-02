/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.main;

import cl.rworks.comar.swing.views.pos.ComarPanelPosArea;
import cl.rworks.comar.swing.views.sells.ComarPanelSellsArea;
import cl.rworks.comar.swing.views.pos.ComarPanelPosSettingsArea;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarIconLoader;
import cl.rworks.comar.swing.views.bills.ComarPanelBillsArea;
import cl.rworks.comar.swing.views.products.ComarPanelProdsCsvInsertArea;
import cl.rworks.comar.swing.views.products.ComarPanelProdsByCategoryArea;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author rgonzalez
 */
public class ComarFrame extends WebFrame {

    private static final String VERSION = "v1.0";
    //
    private ComarPanel panelCard;
    private ComarStatusBar statusBar;
    private JMenu menuFile;
    private JMenu menuPointOfSell;
    private JMenu menuProducts;
    private JMenu menuBills;
    private JMenu menuSells;
//    private JMenu menuInventory;
    //
    private String actualCardName;
    private Map<String, ComarPanel> panelMap = new HashMap<>();

    public ComarFrame() {
        super("Comar " + VERSION);
        initComponents();
        initValues();
    }

    private void initComponents() {
        setIconImage(ComarIconLoader.load("/comar_32.png"));
        setLayout(new BorderLayout());

        ComarPanel panelContent = new ComarPanel(new BorderLayout());
        add(new WebScrollPane(panelContent), BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        menuFile = new JMenu("Archivo");
        menuFile.add(new JMenuItem(new ExitAction()));
        menuBar.add(menuFile);

        menuBar.add(menuPointOfSell = new JMenu("Punto de Venta"));
        menuBar.add(menuProducts = new JMenu("Productos"));
        menuBar.add(menuBills = new JMenu("Facturas"));
        menuBar.add(menuSells = new JMenu("Ventas"));

        panelCard = new ComarPanel();
        panelCard.setLayout(new CardLayout());
        panelContent.add(panelCard, BorderLayout.CENTER);

        statusBar = new ComarStatusBar();
        panelContent.add(statusBar, BorderLayout.SOUTH);
    }

    private void initValues() {
        addCard("POS_POS", "Punto de Venta", new ComarPanelPosArea(), menuPointOfSell);
        addCard("POS_OPT", "Opciones", new ComarPanelPosSettingsArea(), menuPointOfSell);

        addCard("PRODS_CATEGORIES", "Por Categoria", new ComarPanelProdsByCategoryArea(), menuProducts);
        addCard("PRODS_CSV", "Agregar via CSV", new ComarPanelProdsCsvInsertArea(), menuProducts);

        addCard("BILLS_", "Por Fecha", new ComarPanelBillsArea(), menuBills);

        addCard("SELLS", "Por Fecha", new ComarPanelSellsArea(), menuSells);

        actualCardName = "POS_POS";
    }

    public void showMe() {
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public ComarStatusBar getStatusBar() {
        return statusBar;
    }

    public void addCard(String id, String name, ComarPanel panel, JMenu menu) {
        panelCard.add(panel, id);
        menu.add(new JMenuItem(new ShowViewAction(id, name)));
        panelMap.put(id, panel);
    }

    public String getActualCardName() {
        return actualCardName;
    }

    public ComarPanel getPanel(String id) {
        return this.panelMap.get(id);
    }

    public class ShowViewAction extends AbstractAction {

        private String title;
        private String id;

        public ShowViewAction(String id, String title) {
            this.title = title;
            this.id = id;

            putValue(NAME, title);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            actualCardName = id;
            CardLayout layout = (CardLayout) panelCard.getLayout();
            layout.show(panelCard, id);
        }

    }

    public class ExitAction extends AbstractAction {

        public ExitAction() {
            putValue(NAME, "Salir");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ComarSystem.getInstance().exit();
        }

    }
}
