/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing;

import cl.rworks.comar.swing.admnistration.ComarPanelAdministration;
import cl.rworks.comar.swing.pointofsell.ComarPanelPointOfSell;
import cl.rworks.comar.swing.settings.ComarPanelSettings;
import cl.rworks.comar.swing.util.ComarPanelCard;
import cl.rworks.comar.swing.util.ComarPanelCardContainer;
import cl.rworks.comar.swing.util.IconLoader;
import com.alee.laf.rootpane.WebFrame;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.NAME;

/**
 *
 * @author rgonzalez
 */
public class ComarFrame extends WebFrame {

    private static final String VERSION = "v1.0";
    //
    private ComarMenuBar menuBar;
    private ComarPanelCardContainer panelCard;
    private ComarStatusBar statusBar;

    public ComarFrame() {
        super("Comar " + VERSION);
        initValues();
    }

    private void initValues() {
        setIconImage(IconLoader.load("/comar_32.png"));

        menuBar = new ComarMenuBar();
        setJMenuBar(menuBar);

        setLayout(new BorderLayout());

        panelCard = new ComarPanelCardContainer();
        add(panelCard, BorderLayout.CENTER);

        statusBar = new ComarStatusBar();
        add(statusBar, BorderLayout.SOUTH);

        ComarPanelPointOfSell panelPointOfSell = new ComarPanelPointOfSell();
        ComarPanelAdministration panelAdmnistration = new ComarPanelAdministration();
        ComarPanelSettings panelOptions = new ComarPanelSettings();

        addCard("POS", panelPointOfSell, new ShowViewAction("Punto de Venta", panelCard, "POS"));
        addCard("ADM", panelAdmnistration, new ShowViewAction("Administracion", panelCard, "ADM"));
        addCard("OPT", panelOptions, new ShowViewAction("Opciones", panelCard, "OPT"));
        
        panelCard.showCard("ADM");
    }

    public void showMe() {
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public ComarStatusBar getStatusBar() {
        return statusBar;
    }

    public void addCard(String id, ComarPanelCard card, Action action) {
        this.menuBar.addCard(action);
        this.panelCard.addCard(id, card);
    }

    public class ShowViewAction extends AbstractAction {

        private String title;
        private ComarPanelCardContainer cardContainer;
        private String id;

        public ShowViewAction(String title, ComarPanelCardContainer cardContainer, String id) {
            this.title = title;
            this.cardContainer = cardContainer;
            this.id = id;

            putValue(NAME, title);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            cardContainer.showCard(id);
        }

    }

}
