/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.swing.util.ComarPanelCardContainer;
import cl.rworks.comar.swing.util.ComarPanelTitle;
import cl.rworks.comar.swing.util.ComarMenuButton;
import cl.rworks.comar.swing.util.ComarMenuButtonManager;
import com.alee.extended.panel.WebAccordion;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BoxLayout;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelAdministration extends WebPanel {

    private ComarMenuButtonManager manager;
//    private Map<String, WebPanel> views;
    private ComarPanelCardContainer panelCardContainer;
    private WebPanel panelContent;

    public ComarPanelAdministration() {
        initValues();
    }

    private void initValues() {
        setLayout(new BorderLayout());
        add(new ComarPanelTitle("Administracion"), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);
    }

    private WebPanel buildContent() {
        panelContent = new WebPanel();
        panelContent.setLayout(new BorderLayout());

        panelCardContainer = new ComarPanelCardContainer();
        panelContent.add(panelCardContainer, BorderLayout.CENTER);

        ComarMenuButton[] buttons = new ComarMenuButton[]{
            new ComarMenuButton("PRODUCTS_SEARCH", "Buscar", panelCardContainer, new ComarPanelProductSearch()),
            new ComarMenuButton("PRODUCTS_ADD", "Agregar", panelCardContainer, new ComarPanelProductAdd()),
            new ComarMenuButton("PRODUCTS_EDIT", "Editar", panelCardContainer, new ComarPanelProductEdit()),
            new ComarMenuButton("PRODUCTS_DELETE", "Eliminar", panelCardContainer, new EmptyCard("PRODUCTS_DELETE")),
            //
            new ComarMenuButton("STOCK_ADD", "Agregar", panelCardContainer, new EmptyCard("STOCK_ADD")),
            new ComarMenuButton("STOCK_EDIT", "Editar", panelCardContainer, new EmptyCard("STOCK_EDIT")),
            new ComarMenuButton("STOCK_DELETE", "Eliminar", panelCardContainer, new EmptyCard("STOCK_DELETE")),
            //
            new ComarMenuButton("SELLS_ADD", "Agregar", panelCardContainer, new EmptyCard("SELLS_ADD")),
            new ComarMenuButton("SELLS_EDIT", "Editar", panelCardContainer, new EmptyCard("SELLS_EDIT")),
            new ComarMenuButton("SELLS_DELETE", "Eliminar", panelCardContainer, new EmptyCard("SELLS_DELETE"))
        };

        this.manager = new ComarMenuButtonManager();
        for (ComarMenuButton button : buttons) {
            manager.putMenuButton(button.getCode(), button);
            panelCardContainer.addCard(button.getCode(), button.getCard());
        }

        WebPanel panelProducts = new WebPanel();
        panelProducts.setBackground(Color.BLACK);
        panelProducts.setLayout(new BoxLayout(panelProducts, BoxLayout.PAGE_AXIS));
        panelProducts.add(manager.getMenuButton("PRODUCTS_SEARCH"));
        panelProducts.add(manager.getMenuButton("PRODUCTS_ADD"));
        panelProducts.add(manager.getMenuButton("PRODUCTS_EDIT"));
        panelProducts.add(manager.getMenuButton("PRODUCTS_DELETE"));

        WebPanel panelStock = new WebPanel();
        panelStock.setLayout(new BoxLayout(panelStock, BoxLayout.PAGE_AXIS));
        panelStock.add(manager.getMenuButton("STOCK_ADD"));
        panelStock.add(manager.getMenuButton("STOCK_EDIT"));
        panelStock.add(manager.getMenuButton("STOCK_DELETE"));

        WebPanel panelSells = new WebPanel();
        panelSells.setLayout(new BoxLayout(panelSells, BoxLayout.PAGE_AXIS));
        panelSells.add(manager.getMenuButton("SELLS_ADD"));
        panelSells.add(manager.getMenuButton("SELLS_EDIT"));
        panelSells.add(manager.getMenuButton("SELLS_DELETE"));

        WebAccordion ac = new WebAccordion();
        ac.setFillSpace(false);
        ac.setMinimumWidth(200);
        ac.addPane("Productos", panelProducts);
        ac.addPane("Inventario", panelStock);
        ac.addPane("Ventas", panelSells);
        panelContent.add(ac, BorderLayout.WEST);

        return panelContent;
    }

    private class EmptyCard extends WebPanel {

        public EmptyCard(String title) {
            add(new WebLabel(title));
        }

    }

}
