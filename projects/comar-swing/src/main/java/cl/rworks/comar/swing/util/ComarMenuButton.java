/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import com.alee.laf.label.WebLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ComarMenuButton extends WebLabel {

    public static final int HOVER = 0;
    public static final int EXIT = 1;
    //
    private int state = EXIT;
    private boolean selected = false;

    public ComarMenuButton(String text) {
        this(text, null);
    }

    public ComarMenuButton(String text, AbstractAction action) {
        super(text);

        setOpaque(true);
        setHorizontalAlignment(SwingConstants.CENTER);
        setForeground(Color.WHITE);
        setBackground(Color.BLACK);
        setBorder(new EmptyBorder(20, 10, 20, 10));
        setFontSize(12);
        setBoldFont();

        setPreferredHeight(30);
        setMaximumSize(new Dimension(2000, 2000));
        addMouseListener(new ComarMenuButtonMouseAdapter(this, action));
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        updateStateUi();
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void updateStateUi() {
        if (isSelected()) {
            System.out.println("");
        }

        if (state == HOVER) {
            setForeground(Color.BLACK);
            setBackground(isSelected() ? ComarConstants.BLUE : Color.WHITE);
//            setBackground(Color.WHITE);
        } else {
            setForeground(Color.WHITE);
            setBackground(isSelected() ? ComarConstants.BLUE : Color.BLACK);
//            setBackground(Color.BLACK);
        }
    }

    private class ComarMenuButtonMouseAdapter extends MouseAdapter {

        private ComarMenuButton button;
        private Action action;

        public ComarMenuButtonMouseAdapter(ComarMenuButton button, Action action) {
            this.button = button;
            this.action = action;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            button.setSelected(true);
            button.updateStateUi();
            
            if (action != null) {
                action.actionPerformed(null);
            }

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

}
