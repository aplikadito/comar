/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.main;

import cl.rworks.comar.swing.model.ComarWorkspace;
import cl.rworks.comar.swing.model.ComarWorkspaceCreator;
import cl.rworks.comar.swing.views.pos.ComarPanelPosArea;
import cl.rworks.comar.swing.properties.ComarProperties;
import cl.rworks.comar.swing.properties.ComarPropertiesImpl;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.core.service.ComarServiceFactory;

/**
 *
 * @author rgonzalez
 */
public class ComarSystem {

    private static ComarSystem instance;
    //
    private ComarFrame frame;
    private final ComarService service;
    private final ComarProperties properties;
    private ComarWorkspace workspace;

    public static ComarSystem getInstance() {
        instance = instance == null ? new ComarSystem() : instance;
        return instance;
    }

    private ComarSystem() {
        this.service = ComarServiceFactory.create(ComarService.DERBY);
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

    public ComarProperties getProperties() {
        return properties;
    }

    public void startup() {
        startupProperties();
        startupServices();
        startupKeyboard();
    }

    private void startupProperties() {
        properties.save();
    }

    private void startupServices() {
        service.startup(ComarService.DISK);
        workspace = new ComarWorkspaceCreator().create(service);
    }

    private void startupKeyboard() {
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (frame.getActualCardName().equals("POS")) {
                    ComarPanelPosArea panelPos = (ComarPanelPosArea) frame.getPanel("POS");
                    return panelPos.dispatchKeyEventPos(e);
                }
                return false;
            }

        });
    }

    public void exit() {
        System.exit(0);
    }

    public ComarWorkspace getWorkspace() {
        return workspace;
    }

}
