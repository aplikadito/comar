/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing;

import cl.rworks.comar.swing.util.ComarPanelCardContainer;
import com.alee.laf.rootpane.WebFrame;
import java.awt.BorderLayout;
import javax.swing.Action;
import javax.swing.JPanel;

/**
 *
 * @author rgonzalez
 */
public class ComarFrame extends WebFrame {

    private ComarMenuBar menuBar;
    private ComarPanelCardContainer panelCard;
    private ComarStatusBar statusBar;

    public ComarFrame() {
        initValues();
    }

    private void initValues() {
        menuBar = new ComarMenuBar();
        setJMenuBar(menuBar);

        setLayout(new BorderLayout());

        panelCard = new ComarPanelCardContainer();
        add(panelCard, BorderLayout.CENTER);

        statusBar = new ComarStatusBar();
        add(statusBar, BorderLayout.SOUTH);
    }

    public void showMe() {
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public ComarPanelCardContainer getPanelCard() {
        return panelCard;
    }

    public ComarStatusBar getStatusBar() {
        return statusBar;
    }

    public final void addCard(String id, JPanel card, Action action) {
        this.menuBar.addCard(action);
        this.panelCard.addCard(id, card);
    }

}
