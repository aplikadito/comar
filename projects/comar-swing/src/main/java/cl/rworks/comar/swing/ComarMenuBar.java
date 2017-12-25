/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing;

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
    
    public ComarMenuBar() {
        WebMenu menuFile = new WebMenu("Archivo");
        menuFile.add(new WebMenuItem(new ComarActionExit()));
        add(menuFile);
        
        menuCards = new WebMenu("Ventanas");
        add(menuCards);
    }

    public void addCard(Action action) {
        menuCards.add(new WebMenuItem(action));
    }
}
