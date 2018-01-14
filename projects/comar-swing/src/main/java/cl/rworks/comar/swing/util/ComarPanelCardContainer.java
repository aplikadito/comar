/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import com.alee.laf.panel.WebPanel;
import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelCardContainer extends WebPanel {

    private Map<String, ComarPanelCard> map = new HashMap<>();
    private CardLayout layout = new CardLayout();
    private String actualCardName;

    public ComarPanelCardContainer() {
        setLayout(layout);
    }

    public final void addCard(String id, ComarPanelCard card) {
        map.put(id, card);
        add(card, id);
    }

    public final void showCard(String cardName) {
        if (actualCardName != null) {
            map.get(actualCardName).hide();
        }

        this.actualCardName = cardName;
        map.get(actualCardName).preload();
        layout.show(this, cardName);
    }

    public String getActualCardName() {
        return actualCardName;
    }

    public ComarPanelCard getCard(String cardName) {
        return map.get(cardName);
    }

}
