/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aplik
 */
public class ComarMenuButtonManager {

    private Map<String, ComarMenuButton> menuButtons;
    private ComarMenuButton selectedMenuButton;

    public ComarMenuButtonManager() {
        this.menuButtons = new HashMap<>();
    }

    public void putMenuButton(String id, final ComarMenuButton button) {
        this.menuButtons.put(id, button);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedMenuButton = button;
                for (ComarMenuButton menuButton : menuButtons.values()) {
                    menuButton.setSelected(selectedMenuButton == menuButton);
                    menuButton.updateStateUi();
                }
            }
        });
    }

    public ComarMenuButton getMenuButton(String id) {
        return menuButtons.get(id);
    }

}
