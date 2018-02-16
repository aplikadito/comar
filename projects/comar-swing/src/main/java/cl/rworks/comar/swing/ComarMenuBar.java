/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing;

import cl.rworks.comar.swing.properties.ComarProperties;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import javax.swing.Action;

/**
 *
 * @author rgonzalez
 */
public class ComarMenuBar extends WebMenuBar {

    private WebMenu menuCards;
    private int normalFontSize = ComarSystem.getInstance().getProperties().getNormalFontSize();

    public ComarMenuBar() {
        WebMenu menuFile = new WebMenu("Archivo");
        menuFile.setFontSize(normalFontSize);

        WebMenuItem item = new WebMenuItem(new ComarActionExit());
        item.setFontSize(normalFontSize);
        menuFile.add(item);
        add(menuFile);

        menuCards = new WebMenu("Ventanas");
        menuCards.setFontSize(normalFontSize);
        add(menuCards);
    }

    public void addCard(Action action) {
        WebMenuItem item = new WebMenuItem(action);
        item.setFontSize(normalFontSize);
        menuCards.add(item);
    }
}
