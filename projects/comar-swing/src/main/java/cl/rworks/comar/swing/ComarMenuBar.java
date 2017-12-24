/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing;

import cl.rworks.comar.swing.admnistration.ComarActionAdministration;
import cl.rworks.comar.swing.options.ComarActionOptions;
import cl.rworks.comar.swing.pointofsell.ComarActionPointOfSell;
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
//        menuCards.add(new WebMenuItem(new ComarActionPointOfSell()));
//        menuCards.add(new WebMenuItem(new ComarActionAdministration()));
//        menuCards.add(new WebMenuItem(new ComarActionOptions()));
        
        add(menuCards);
    }

    public void addCard(Action action) {
        menuCards.add(new WebMenuItem(action));
    }
}
