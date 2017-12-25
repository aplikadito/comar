/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.swing.util.ComarMenuButton;
import com.alee.extended.panel.WebAccordion;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.progressbar.WebProgressBar;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BoxLayout;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelAdministrationCore extends WebPanel {

    private ComarPanelAdministrationMenuManager manager;

    public ComarPanelAdministrationCore() {
        initValues();
    }

    private void initValues() {
        setLayout(new BorderLayout());

        this.manager = new ComarPanelAdministrationMenuManager();
        this.manager.putMenuButton("PRODUCTS_ADD", new ComarMenuButton("Agregar"));
        this.manager.putMenuButton("PRODUCTS_EDIT", new ComarMenuButton("Editar"));
        this.manager.putMenuButton("PRODUCTS_DELETE", new ComarMenuButton("Eliminar"));
        
        this.manager.putMenuButton("STOCK_ADD", new ComarMenuButton("Agregar"));
        this.manager.putMenuButton("STOCK_EDIT", new ComarMenuButton("Editar"));
        this.manager.putMenuButton("STOCK_DELETE", new ComarMenuButton("Eliminar"));
        
        WebPanel panelProducts = new WebPanel();
        panelProducts.setBackground(Color.BLACK);
        panelProducts.setLayout(new BoxLayout(panelProducts, BoxLayout.PAGE_AXIS));
        panelProducts.add(manager.getMenuButton("PRODUCTS_ADD"));
        panelProducts.add(manager.getMenuButton("PRODUCTS_EDIT"));
        panelProducts.add(manager.getMenuButton("PRODUCTS_DELETE"));

        WebPanel panelStock = new WebPanel();
        panelStock.setLayout(new BoxLayout(panelStock, BoxLayout.PAGE_AXIS));
        panelStock.add(manager.getMenuButton("STOCK_ADD"));
        panelStock.add(manager.getMenuButton("STOCK_EDIT"));
        panelStock.add(manager.getMenuButton("STOCK_DELETE"));

        WebAccordion ac = new WebAccordion();
        ac.setFillSpace(false);
        ac.setMinimumWidth(200);
        ac.addPane("Productos", panelProducts);
        ac.addPane("Inventario", panelStock);
        ac.addPane("Menu03", new WebButton("Eliminar"));
        add(ac, BorderLayout.WEST);

        WebPanel panelCenter = new WebPanel();
        panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.PAGE_AXIS));
        panelCenter.add(new WebLabel("Contenido 01"));
        panelCenter.add(new WebLabel("Contenido02"));
        add(panelCenter, BorderLayout.CENTER);

        WebProgressBar p = new WebProgressBar();
    }

}
