/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing;

import cl.rworks.comar.swing.products.ComarPanelProducts;
import cl.rworks.comar.swing.products.ComarPanelProductsHistorial;
import cl.rworks.comar.swing.pointofsell.ComarPanelPointOfSell;
import cl.rworks.comar.swing.sells.ComarPanelSells;
import cl.rworks.comar.swing.settings.ComarPanelSettings;
import cl.rworks.comar.swing.util.ComarButton;
import cl.rworks.comar.swing.util.ComarLabel;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarPanelCard;
import cl.rworks.comar.swing.util.IconLoader;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author rgonzalez
 */
public class ComarFrame extends WebFrame {

    private static final String VERSION = "v1.0";
    //
    private ComarPanel panelCard;
    private ComarStatusBar statusBar;
    //
    private ComarPanelPointOfSell panelPointOfSell;
    private ComarPanelProducts panelProducts;
    private ComarPanelSells panelSells;
    private ComarPanelSettings panelOptions;
    //
    private String actualCardName;

    public ComarFrame() {
        super("Comar " + VERSION);
        initValues();
    }

    private void initValues() {
        setIconImage(IconLoader.load("/comar_32.png"));
        setLayout(new BorderLayout());

        ComarPanel panelContent = new ComarPanel(new BorderLayout());
        add(new WebScrollPane(panelContent), BorderLayout.CENTER);

        // MENU
        ComarPanel panelMenu = new ComarPanel(new FlowLayout(FlowLayout.LEFT));
        panelMenu.setBackground(Color.WHITE);
        panelMenu.add(new ComarMenuButton(new ShowViewAction("POS", "Punto de Venta")));
        panelMenu.add(new ComarMenuButton(new ShowViewAction("PROD", "Productos")));
        panelMenu.add(new ComarMenuButton(new ShowViewAction("SELL", "Ventas")));
        panelMenu.add(new ComarMenuButton(new ShowViewAction("OPT", "Opciones")));

        // BANNER
        ComarPanel panelComar = new ComarPanel(new FlowLayout(FlowLayout.RIGHT));
        panelComar.setBackground(Color.WHITE);
        panelComar.add(new ComarLabel("Comar v1.0", 30));

        ComarPanel panelNorth = new ComarPanel(new BorderLayout());
        panelNorth.setBorder(new EmptyBorder(20, 50, 20, 50));
        panelNorth.setBackground(Color.WHITE);
        panelNorth.add(panelMenu, BorderLayout.WEST);
        panelNorth.add(panelComar, BorderLayout.EAST);
        panelContent.add(panelNorth, BorderLayout.NORTH);

        panelCard = new ComarPanel();
        panelCard.setLayout(new CardLayout());
        panelContent.add(panelCard, BorderLayout.CENTER);

        statusBar = new ComarStatusBar();
        panelContent.add(statusBar, BorderLayout.SOUTH);

        panelPointOfSell = new ComarPanelPointOfSell();
        panelProducts = new ComarPanelProducts();
        panelSells = new ComarPanelSells();
        panelOptions = new ComarPanelSettings();

        addCard("POS", panelPointOfSell);
        addCard("PROD", panelProducts);
        addCard("SELL", panelSells);
        addCard("OPT", panelOptions);

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

    public void addCard(String id, ComarPanelCard card) {
        this.panelCard.add(card, id);
    }

    public String getActualCardName() {
        return actualCardName;
    }

    public ComarPanelPointOfSell getPanelPointOfSell() {
        return panelPointOfSell;
    }

    public ComarPanelProducts getPanelProducts() {
        return panelProducts;
    }

    public ComarPanelSells getPanelHistorial() {
        return panelSells;
    }

    public ComarPanelSettings getPanelOptions() {
        return panelOptions;
    }

    private class ComarMenuButton extends ComarButton {

        private ComarMenuButton(ShowViewAction action) {
            super(action);
            setMinimumWidth(250);
        }

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

//            if (id.equals("POS")) {
//                panelPointOfSell.loadCard();
//            }
        }

    }
}
