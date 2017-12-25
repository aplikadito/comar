/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import com.alee.laf.panel.WebPanel;
import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelCardContainer extends WebPanel {

    private CardLayout layout = new CardLayout();
    private String actualCardName;

    public ComarPanelCardContainer() {
        setLayout(layout);
    }

    public final void addCard(String id, JPanel card) {
        add(card, id);
    }

    public final void showCard(String cardName) {
        this.actualCardName = cardName;
        layout.show(this, cardName);
    }

    public String getActualCardName() {
        return actualCardName;
    }

}
