/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author aplik
 */
public class ComarMenuButtonMouseAdapter extends MouseAdapter {

    //
    private ComarMenuButton button;

    public ComarMenuButtonMouseAdapter(ComarMenuButton button) {
        this.button = button;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        button.setState(ComarMenuButton.SELECTED);
        button.updateStateUi();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        button.setState(ComarMenuButton.HOVER);
        button.updateStateUi();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        button.setState(ComarMenuButton.EXIT);
        button.updateStateUi();
    }
}
