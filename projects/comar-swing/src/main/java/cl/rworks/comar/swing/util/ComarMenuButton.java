/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ComarMenuButton extends WebLabel {

    public static final int HOVER = 0;
    public static final int EXIT = 1;
    //
    private String code;
    private String title;
    private ComarPanelCardContainer cardContainer;
    private WebPanel card;
    //
    private int state = EXIT;
    private boolean selected = false;

    public ComarMenuButton(String code, String title, ComarPanelCardContainer cardContainer, WebPanel card) {
        super(title);
        this.code = code;
        this.title = title;
        this.cardContainer = cardContainer;
        this.card = card;

        setOpaque(true);
        setHorizontalAlignment(SwingConstants.CENTER);
        setForeground(Color.WHITE);
        setBackground(Color.BLACK);
        setBorder(new EmptyBorder(20, 10, 20, 10));
        setFontSize(12);
        setBoldFont();

        setPreferredHeight(30);
        setMaximumSize(new Dimension(2000, 2000));
        addMouseListener(new MenuMouseAdapter());
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

    private class MenuMouseAdapter extends MouseAdapter {

        public MenuMouseAdapter() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            setSelected(true);
            updateStateUi();
            cardContainer.showCard(code);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            setState(ComarMenuButton.HOVER);
            updateStateUi();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setState(ComarMenuButton.EXIT);
            updateStateUi();
        }
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public WebPanel getCard() {
        return card;
    }

}
