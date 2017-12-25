/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.swing.util.ComarMenuButton;
import cl.rworks.comar.swing.util.ComarMenuButtonManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aplik
 */
public class ComarPanelAdministrationMenuManager implements ComarMenuButtonManager {

    private Map<String, ComarMenuButton> menuButtons;
    private ComarMenuButton selectedMenuButton;

    public ComarPanelAdministrationMenuManager() {
        this.menuButtons = new HashMap<>();
    }

    @Override
    public void putMenuButton(String id, final ComarMenuButton button) {
        this.menuButtons.put(id, button);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                select(button);
            }
        });
    }

    @Override
    public ComarMenuButton getMenuButton(String id) {
        return menuButtons.get(id);
    }

    private void select(ComarMenuButton button) {
        this.selectedMenuButton = button;
        for (ComarMenuButton menuButton : menuButtons.values()) {
            menuButton.setSelected(selectedMenuButton == menuButton);
            menuButton.updateStateUi();
        }
    }

}
