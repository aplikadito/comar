/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing;

import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.core.service.ComarServiceImpl;
import cl.rworks.comar.swing.properties.ComarProperties;
import cl.rworks.comar.swing.properties.ComarPropertiesImpl;
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
    private ComarService service;
    private ComarProperties properties;

    public static ComarSystem getInstance() {
        instance = instance == null ? new ComarSystem() : instance;
        return instance;
    }

    private ComarSystem() {
        System.out.println(System.getProperty("user.dir"));
        this.service = new ComarServiceImpl(ComarService.DISK);
        this.properties = new ComarPropertiesImpl();
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
        ComarSystem.getInstance().getProperties().save();
        startupDb();
        startupKeyboard();
    }

    private void startupDb() {
//        Permazen db = service.getDb().get();
//        JTransaction jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
//        JTransaction.setCurrent(jtx);
//        try {
//            ComarCategoryKite category = ComarCategoryKite.getByName("General");
//            if (category == null) {
//                ComarCategoryKite.create("General");
//            }
//            jtx.commit();
//        } catch (Exception ex) {
//            jtx.rollback();
//            ex.printStackTrace();
//        } finally {
//            JTransaction.setCurrent(null);
//        }
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

    public ComarProperties getProperties() {
        return properties;
    }
    
}
