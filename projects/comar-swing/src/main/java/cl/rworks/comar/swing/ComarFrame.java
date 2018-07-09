/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing;

import cl.rworks.comar.swing.views.pointofsell.ComarPanelPointOfSellArea;
import cl.rworks.comar.swing.views.sells.ComarPanelSells;
import cl.rworks.comar.swing.settings.ComarPanelSettings;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.IconLoader;
import cl.rworks.comar.swing.views.batches.ComarPanelBatches;
import cl.rworks.comar.swing.views.inventory.ComarPanelInventoryArea;
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
    private JMenu menuNavigate;
    private JMenu menuInventory;
    //
    private String actualCardName;
    private Map<String, ComarPanel> panelMap = new HashMap<>();

    public ComarFrame() {
        super("Comar " + VERSION);
        initComponents();
        initValues();
    }

    private void initComponents() {
        setIconImage(IconLoader.load("/comar_32.png"));
        setLayout(new BorderLayout());

        ComarPanel panelContent = new ComarPanel(new BorderLayout());
        add(new WebScrollPane(panelContent), BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        menuFile = new JMenu("Archivo");
        menuFile.add(new JMenuItem(new ExitAction()));
        menuBar.add(menuFile);

        menuNavigate = new JMenu("Navegar");
        menuBar.add(menuNavigate);

        menuInventory = new JMenu("Inventario");
        menuBar.add(menuInventory);

        panelCard = new ComarPanel();
        panelCard.setLayout(new CardLayout());
        panelContent.add(panelCard, BorderLayout.CENTER);

        statusBar = new ComarStatusBar();
        panelContent.add(statusBar, BorderLayout.SOUTH);
    }

    private void initValues() {
        addCard("POS", "Punto de Venta", new ComarPanelPointOfSellArea(), menuNavigate);
        addCard("SELL", "Ventas", new ComarPanelSells(), menuNavigate);
        addCard("OPT", "Opciones", new ComarPanelSettings(), menuNavigate);

        addCard("INV", "Inventario", new ComarPanelInventoryArea(), menuInventory);
        addCard("BATCHES", "Lotes", new ComarPanelBatches(), menuInventory);

        actualCardName = "POS";
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
