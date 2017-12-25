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
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelAdministration extends WebPanel {

    private ComarMenuButtonManager manager;
    private Map<String, WebPanel> views;
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

        this.manager = new ComarMenuButtonManager();
        this.manager.putMenuButton("PRODUCTS_ADD", new ComarMenuButton("Agregar Producto", new ShowViewAction("PRODUCTS_ADD")));
        this.manager.putMenuButton("PRODUCTS_EDIT", new ComarMenuButton("Editar", new ShowViewAction("PRODUCTS_EDIT")));
        this.manager.putMenuButton("PRODUCTS_DELETE", new ComarMenuButton("Eliminar", new ShowViewAction("PRODUCTS_DELETE")));
        this.manager.putMenuButton("STOCK_ADD", new ComarMenuButton("Agregar", new ShowViewAction("STOCK_ADD")));
        this.manager.putMenuButton("STOCK_EDIT", new ComarMenuButton("Editar", new ShowViewAction("STOCK_EDIT")));
        this.manager.putMenuButton("STOCK_DELETE", new ComarMenuButton("Eliminar", new ShowViewAction("STOCK_DELETE")));
        this.manager.putMenuButton("SELLS_ADD", new ComarMenuButton("Agregar", new ShowViewAction("SELLS_ADD")));
        this.manager.putMenuButton("SELLS_EDIT", new ComarMenuButton("Editar", new ShowViewAction("SELLS_EDIT")));
        this.manager.putMenuButton("SELLS_DELETE", new ComarMenuButton("Eliminar", new ShowViewAction("SELLS_DELETE")));

        this.views = new HashMap<>();
        this.views.put("PRODUCTS_ADD", new ComarPanelProductAdd());
        this.views.put("PRODUCTS_EDIT", new WebPanel(new WebLabel("PRODUCTS_EDIT")));
        this.views.put("PRODUCTS_DELETE", new WebPanel(new WebLabel("PRODUCTS_DELETE")));
        this.views.put("STOCK_ADD", new WebPanel(new WebLabel("STOCK_ADD")));
        this.views.put("STOCK_EDIT", new WebPanel(new WebLabel("STOCK_EDIT")));
        this.views.put("STOCK_DELETE", new WebPanel(new WebLabel("STOCK_DELETE")));
        this.views.put("SELLS_ADD", new WebPanel(new WebLabel("SELLS_ADD")));
        this.views.put("SELLS_EDIT", new WebPanel(new WebLabel("SELLS_EDIT")));
        this.views.put("SELLS_DELETE", new WebPanel(new WebLabel("SELLS_DELETE")));

        buildMenu();
        buildCardContainer();

        return panelContent;
    }

    private void buildMenu() {
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
    }

    private void buildCardContainer() {
        panelCardContainer = new ComarPanelCardContainer();
        views.entrySet().forEach(e -> panelCardContainer.addCard(e.getKey(), e.getValue()));
        panelContent.add(panelCardContainer, BorderLayout.CENTER);
    }

    private class ShowViewAction extends AbstractAction {

        private final String id;

        public ShowViewAction(String id) {
            this.id = id;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            panelCardContainer.showCard(id);
        }

    }
}
