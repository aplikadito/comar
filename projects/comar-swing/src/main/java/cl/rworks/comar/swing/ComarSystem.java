/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing;

import cl.rworks.comar.swing.admnistration.ComarActionAdministration;
import cl.rworks.comar.swing.admnistration.ComarPanelAdministration;
import cl.rworks.comar.swing.options.ComarActionOptions;
import cl.rworks.comar.swing.options.ComarPanelOptions;
import cl.rworks.comar.swing.pointofsell.ComarActionPointOfSell;
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

        frame.addCard(panelPointOfSell, "POS", new ComarActionPointOfSell());
        frame.addCard(panelAdmnistration, "ADM", new ComarActionAdministration());
        frame.addCard(panelOptions, "OPT", new ComarActionOptions());

        frame.getPanelCard().showCard("ADM");

    }

}
