/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import com.alee.laf.label.WebLabel;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ComarMenuButton extends WebLabel {

    public static final int HOVER = 0;
    public static final int EXIT = 1;
    public static final int SELECTED = 2;
    //
    private int state = EXIT;
    private boolean selected = false;

    public ComarMenuButton(String text) {
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
        addMouseListener(new ComarMenuButtonMouseAdapter(this));
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
            setBackground(ComarConstants.BLUE);
        } else {
            setBackground(Color.BLACK);
        }

        if (state == HOVER) {
            setForeground(Color.BLACK);
            setBackground(Color.WHITE);
        } else {
            setForeground(Color.WHITE);
            setBackground(Color.BLACK);
        }
    }

}
