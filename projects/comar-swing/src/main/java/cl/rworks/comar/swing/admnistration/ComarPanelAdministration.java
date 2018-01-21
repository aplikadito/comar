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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private Map<String, MenuAndCard> codeToMenuAndCard;
    private MenuAndCard selectedMenuAndCard;

    public ComarPanelAdministration() {
        initValues();
    }

    private void initValues() {
        setLayout(new BorderLayout());
        add(new ComarPanelTitle("Administracion"), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);

        showCard("PRODUCTS");
    }

    private WebPanel buildContent() {
        panelContent = new WebPanel();
        panelContent.setLayout(new BorderLayout());

        panelCardContainer = new ComarPanelCardContainer();
        panelContent.add(panelCardContainer, BorderLayout.CENTER);

        List<MenuAndCard> buttons = new ArrayList<>();
        buttons.add(create("PRODUCTS", "Products", new ComarPanelProduct()));
        buttons.add(create("CATEGORIES", "Categorias", new ComarPanelCategory()));
        buttons.add(create("STOCK", "Inventario"));
        buttons.add(create("SELLS", "Ventas"));

        allMenuAndCard = new ArrayList<>();
        allMenuAndCard.addAll(buttons);

        codeToMenuAndCard = new HashMap<>();
        MenuMouseAdapter listener = new MenuMouseAdapter();
        for (MenuAndCard menuAndCard : allMenuAndCard) {
            panelCardContainer.addCard(menuAndCard.getCode(), menuAndCard.getCard());
            menuAndCard.getMenu().addMouseListener(listener);
            codeToMenuAndCard.put(menuAndCard.getCode(), menuAndCard);
        }

        WebPanel panels = new WebPanel();
        panels.setBackground(Color.BLACK);
        panels.setLayout(new BoxLayout(panels, BoxLayout.PAGE_AXIS));
        for (MenuAndCard menuAndCard : buttons) {
            panels.add(menuAndCard.getMenu());
        }
        
        WebAccordion ac = new WebAccordion();
        ac.setFillSpace(false);
        ac.setMinimumWidth(200);
        ac.addPane("Menu", panels);
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
            ComarMenuButton menu = (ComarMenuButton) e.getSource();
            showCard(menu.getCode());
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

    public void showCard(String code) {
        MenuAndCard menuAndCard = codeToMenuAndCard.get(code);

//        ComarMenuButton menu = menuAndCard.getMenu();
//        menu.setSelected(true);
//        menu.updateStateUi();
        if (selectedMenuAndCard != null) {
            selectedMenuAndCard.getMenu().setSelected(false);
            selectedMenuAndCard.getMenu().updateStateUi();
        }

        selectedMenuAndCard = menuAndCard;
        selectedMenuAndCard.getMenu().setSelected(true);
        selectedMenuAndCard.getMenu().updateStateUi();

//        selectedMenuButton = menu;
//        for (MenuAndCard item : allMenuAndCard) {
//            item.getMenu().setSelected(selectedMenuButton == item.getMenu());
//            item.getMenu().updateStateUi();
//        }

        panelCardContainer.showCard(menuAndCard.getCode());
    }

}
