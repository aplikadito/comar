/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.sells;

import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.util.ComarPanelCard;
import cl.rworks.comar.swing.util.ComarPanelTitle;
import com.alee.laf.tabbedpane.WebTabbedPane;
import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelSells extends ComarPanelCard {

//    private DateTimeFormatter df = new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy HH:mm:ss").toFormatter();

    public ComarPanelSells() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(30, 30, 30, 30));

        add(new ComarPanelTitle("Ventas"), BorderLayout.NORTH);
        
        WebTabbedPane tabbed = new WebTabbedPane();
        tabbed.addTab("Ventas", new ComarPanelSellsAll());
        tabbed.addTab("Buscar", new ComarPanelSellsSearch());
//        tabbed.addTab("Historial", new ComarPanelProductsHistorial());
        
        tabbed.setFontSize(ComarSystem.getInstance().getProperties().getFontSize());
        
        add(tabbed, BorderLayout.CENTER);
    }
    
}
