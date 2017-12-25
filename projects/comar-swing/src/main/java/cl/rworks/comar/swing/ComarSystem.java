/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing;

import cl.rworks.comar.swing.util.ComarPanelCardContainerAction;
import cl.rworks.comar.swing.admnistration.ComarPanelAdministration;
import cl.rworks.comar.swing.options.ComarPanelOptions;
import cl.rworks.comar.swing.pointofsell.ComarPanelPointOfSell;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

/**
 *
 * @author rgonzalez
 */
public class ComarSystem {

    private static ComarSystem instance;
    //
    private ComarFrame frame;

    public static ComarSystem getInstance() {
        instance = instance == null ? new ComarSystem() : instance;
        return instance;
    }

    public void setFrame(ComarFrame frame) {
        this.frame = frame;
    }

    public ComarFrame getFrame() {
        return frame;
    }

    public void startup() {
        ComarPanelPointOfSell panelPointOfSell = new ComarPanelPointOfSell();
        ComarPanelAdministration panelAdmnistration = new ComarPanelAdministration();
        ComarPanelOptions panelOptions = new ComarPanelOptions();

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEventDispatcher() {

            StringBuilder sb = new StringBuilder();

            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                String actualCardName = frame.getPanelCard().getActualCardName();
                if (actualCardName.equals("POS")) {
                    if (KeyEvent.KEY_TYPED == e.getID()) {
                        System.out.println("Got key event!");

                        sb.append(e.getKeyChar());
                        panelPointOfSell.getLabelKeyboard().setText(sb.toString());
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        });

        frame.addCard("POS", "Punto de Venta", panelPointOfSell, new ComarPanelCardContainerAction(frame.getPanelCard(), "POS"));
        frame.addCard("ADM", "Administracion", panelAdmnistration, new ComarPanelCardContainerAction(frame.getPanelCard(), "ADM"));
        frame.addCard("OPT", "Opciones", panelOptions, new ComarPanelCardContainerAction(frame.getPanelCard(), "OPT"));

        frame.getPanelCard().showCard("ADM");
    }

}
