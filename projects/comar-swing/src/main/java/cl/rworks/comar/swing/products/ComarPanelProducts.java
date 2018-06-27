/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.products;

import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarPanelCard;
import cl.rworks.comar.swing.util.ComarPanelTitle;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.managers.style.StyleId;
import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelProducts extends ComarPanelCard {

//    private DateTimeFormatter df = new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy HH:mm:ss").toFormatter();
    public ComarPanelProducts() {
        setLayout(new BorderLayout());
        add(new ComarPanelTitle("Productos"), BorderLayout.NORTH);

        WebTabbedPane tabbed = new WebTabbedPane(WebTabbedPane.LEFT);
//        tabbed.addTab("Productos", new ComarPanelProductsInventory());
        tabbed.addTab("Buscar", new ComarPanelProductsSearch());
        tabbed.addTab("Agregar", new ComarPanel());
        tabbed.addTab("Historial", new ComarPanelProductsHistorial());
//        tabbed.addTab("Historial", new ComarPanelProductsHistorial());

        tabbed.setFontSize(ComarSystem.getInstance().getProperties().getFontSize());

        ComarPanel panelContent = new ComarPanel(new BorderLayout());
        panelContent.setBorder(new EmptyBorder(30, 30, 30, 30));
        panelContent.add(tabbed, BorderLayout.CENTER);
        add(panelContent, BorderLayout.CENTER);
    }

}
