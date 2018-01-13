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
    //
    private String code;
    private String title;
    //
    private int state = EXIT;
    private boolean selected = false;

    public ComarMenuButton(String code, String title) {
        super(title);
        this.code = code;
        this.title = title;

        setOpaque(true);
        setHorizontalAlignment(SwingConstants.CENTER);
        setForeground(Color.WHITE);
        setBackground(Color.BLACK);
        setBorder(new EmptyBorder(20, 10, 20, 10));
        setFontSize(12);
        setBoldFont();

        setPreferredHeight(30);
        setMaximumSize(new Dimension(2000, 2000));
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
        if (state == HOVER) {
            setForeground(Color.BLACK);
            setBackground(isSelected() ? ComarConstants.BLUE : Color.WHITE);
        } else {
            setForeground(Color.WHITE);
            setBackground(isSelected() ? ComarConstants.BLUE : Color.BLACK);
        }
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

}
