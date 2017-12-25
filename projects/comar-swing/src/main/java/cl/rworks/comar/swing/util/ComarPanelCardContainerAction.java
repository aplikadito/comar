/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelCardContainerAction extends AbstractAction {

    private String id;
    private ComarPanelCardContainer cardContainer;

    public ComarPanelCardContainerAction(ComarPanelCardContainer cardContainer, String id) {
        this.cardContainer = cardContainer;
        this.id = id;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cardContainer.showCard(id);
    }

}
