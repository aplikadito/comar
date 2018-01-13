/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing;

import cl.rworks.comar.service.ComarService;
import cl.rworks.comar.swing.admnistration.ComarPanelAdministration;
import cl.rworks.comar.swing.options.ComarPanelOptions;
import cl.rworks.comar.swing.pointofsell.ComarPanelPointOfSell;
import cl.rworks.comar.swing.util.ComarPanelCardContainer;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author rgonzalez
 */
public class ComarSystem {

    private static ComarSystem instance;
    //
    private ComarFrame frame;
    private ComarService service;

    public static ComarSystem getInstance() {
        instance = instance == null ? new ComarSystem() : instance;
        return instance;
    }

    private ComarSystem() {
        this.service = new ComarService("storage");
    }

    public void setFrame(ComarFrame frame) {
        this.frame = frame;
    }

    public ComarFrame getFrame() {
        return frame;
    }

    public ComarService getService() {
        return service;
    }

    public void startup() {
        startupCards();
        startupKeyboard();

        frame.getPanelCard().showCard("ADM");
    }

    private void startupCards() {
        ComarPanelPointOfSell panelPointOfSell = new ComarPanelPointOfSell();
        ComarPanelAdministration panelAdmnistration = new ComarPanelAdministration();
        ComarPanelOptions panelOptions = new ComarPanelOptions();

        frame.addCard("POS", panelPointOfSell, new ShowViewAction("Punto de Venta", frame.getPanelCard(), "POS"));
        frame.addCard("ADM", panelAdmnistration, new ShowViewAction("Administracion", frame.getPanelCard(), "ADM"));
        frame.addCard("OPT", panelOptions, new ShowViewAction("Opciones", frame.getPanelCard(), "OPT"));
    }

    private void startupKeyboard() {
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEventDispatcher() {

            StringBuilder sb = new StringBuilder();

            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
//                String actualCardName = frame.getPanelCard().getActualCardName();
//                if (actualCardName.equals("POS")) {
//                    if (KeyEvent.KEY_TYPED == e.getID()) {
//                        System.out.println("Got key event!");
//
//                        sb.append(e.getKeyChar());
//                        panelPointOfSell.getLabelKeyboard().setText(sb.toString());
//                        return false;
//                    } else {
//                        return true;
//                    }
//                } else {
//                    return true;
//                }
                return false;
            }
        });
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
