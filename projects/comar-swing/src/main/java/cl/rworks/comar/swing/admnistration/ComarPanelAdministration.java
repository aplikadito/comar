/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.swing.util.ComarPanelCardContainer;
import cl.rworks.comar.swing.util.ComarPanelTitle;
import cl.rworks.comar.swing.util.ComarMenuButton;
import cl.rworks.comar.swing.util.ComarPanelCard;
import com.alee.extended.panel.WebAccordion;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelAdministration extends ComarPanelCard {

    private ComarPanelCardContainer panelCardContainer;
    private WebPanel panelContent;
    //
    private List<MenuAndCard> allMenuAndCard;
    private ComarMenuButton selectedMenuButton;

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

        List<MenuAndCard> productButtons = new ArrayList<>();
        productButtons.add(create("PRODUCTS_SEARCH", "Buscar", new ComarPanelProductSearch()));
        productButtons.add(create("PRODUCTS_ADD", "Agregar", new ComarPanelProductAdd()));
        productButtons.add(create("PRODUCTS_EDIT", "Editar", new ComarPanelProductEdit()));
        productButtons.add(create("PRODUCTS_DELETE", "Eliminar"));

        List<MenuAndCard> stockButtons = new ArrayList<>();
        stockButtons.add(create("STOCK_ADD", "Agregar"));
        stockButtons.add(create("STOCK_EDIT", "Editar"));
        stockButtons.add(create("STOCK_DELETE", "Eliminar"));

        List<MenuAndCard> sellsButtons = new ArrayList<>();
        sellsButtons.add(create("SELLS_ADD", "Agregar"));
        sellsButtons.add(create("SELLS_EDIT", "Editar"));
        sellsButtons.add(create("SELLS_DELETE", "Eliminar"));

        allMenuAndCard = new ArrayList<>();
        allMenuAndCard.addAll(productButtons);
        allMenuAndCard.addAll(stockButtons);
        allMenuAndCard.addAll(sellsButtons);

        MenuMouseAdapter listener = new MenuMouseAdapter();
        for (MenuAndCard menuAndCard: allMenuAndCard) {
            panelCardContainer.addCard(menuAndCard.getCode(), menuAndCard.getCard());
            menuAndCard.getMenu().addMouseListener(listener);
        }

        WebPanel panelProducts = new WebPanel();
        panelProducts.setBackground(Color.BLACK);
        panelProducts.setLayout(new BoxLayout(panelProducts, BoxLayout.PAGE_AXIS));
        for (MenuAndCard menuAndCard : productButtons) {
            panelProducts.add(menuAndCard.getMenu());
        }

        WebPanel panelStock = new WebPanel();
        panelStock.setLayout(new BoxLayout(panelStock, BoxLayout.PAGE_AXIS));
        for (MenuAndCard menuAndCard : stockButtons) {
            panelStock.add(menuAndCard.getMenu());
        }

        WebPanel panelSells = new WebPanel();
        panelSells.setLayout(new BoxLayout(panelSells, BoxLayout.PAGE_AXIS));
        for (MenuAndCard menuAndCard : sellsButtons) {
            panelSells.add(menuAndCard.getMenu());
        }

        WebAccordion ac = new WebAccordion();
        ac.setFillSpace(false);
        ac.setMinimumWidth(200);
        ac.addPane("Productos", panelProducts);
        ac.addPane("Inventario", panelStock);
        ac.addPane("Ventas", panelSells);
        panelContent.add(ac, BorderLayout.WEST);

        return panelContent;
    }

    private MenuAndCard create(String code, String name) {
        return create(code, name, new EmptyCard(name));
    }

    private MenuAndCard create(String code, String name, ComarPanelCard card) {
        ComarMenuButton menu = new ComarMenuButton(code, name);
        return new MenuAndCard(code, menu, card);
    }

    public ComarPanelCardContainer getPanelCardContainer() {
        return panelCardContainer;
    }

    private class MenuAndCard {

        private String code;
        private ComarMenuButton menu;
        private ComarPanelCard card;

        public MenuAndCard(String code, ComarMenuButton menu, ComarPanelCard card) {
            this.code = code;
            this.menu = menu;
            this.card = card;
        }

        public String getCode() {
            return code;
        }

        public ComarMenuButton getMenu() {
            return menu;
        }

        public ComarPanelCard getCard() {
            return card;
        }

    }

    private class EmptyCard extends ComarPanelCard {

        public EmptyCard(String title) {
            add(new WebLabel(title));
        }
    }

    private class MenuMouseAdapter extends MouseAdapter {

        public MenuMouseAdapter() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            ComarMenuButton button = (ComarMenuButton) e.getSource();
            button.setSelected(true);
            button.updateStateUi();
            
            selectedMenuButton = button;
            for (MenuAndCard item : allMenuAndCard) {
                item.getMenu().setSelected(selectedMenuButton == item.getMenu());
                item.getMenu().updateStateUi();
            }
            
            panelCardContainer.showCard(button.getCode());
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            ComarMenuButton button = (ComarMenuButton) e.getSource();
            button.setState(ComarMenuButton.HOVER);
            button.updateStateUi();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            ComarMenuButton button = (ComarMenuButton) e.getSource();
            button.setState(ComarMenuButton.EXIT);
            button.updateStateUi();
        }
    }

    public void showCard(String cardName) {

    }

}
