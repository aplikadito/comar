/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import com.alee.extended.panel.WebAccordion;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelAdministrationCore extends WebPanel {

    private MenuButton[] menuButtons = new MenuButton[]{
        new MenuButton("Agregar"),
        new MenuButton("Editar"),
        new MenuButton("Eliminar"),
        new MenuButton("Agregar"),
        new MenuButton("Editar"),
        new MenuButton("Eliminar")
    };
    private MenuButton selected;

    public ComarPanelAdministrationCore() {
        initValues();
    }

    private void initValues() {
        setLayout(new BorderLayout());

        WebPanel panelProducts = new WebPanel();
        panelProducts.setBackground(Color.BLACK);
        panelProducts.setLayout(new BoxLayout(panelProducts, BoxLayout.PAGE_AXIS));
        panelProducts.add(menuButtons[0]);
        panelProducts.add(menuButtons[1]);
        panelProducts.add(menuButtons[2]);

        WebPanel panelStock = new WebPanel();
        panelStock.setLayout(new BoxLayout(panelStock, BoxLayout.PAGE_AXIS));
        panelStock.add(menuButtons[3]);
        panelStock.add(menuButtons[4]);
        panelStock.add(menuButtons[5]);

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
    }

    private class MenuButton extends WebLabel {

        private final Color BLUE = new Color(0, 51, 153);
        
        public MenuButton(String text) {
            super(text);
            setOpaque(true);
            setHorizontalAlignment(SwingConstants.CENTER);
            setForeground(Color.WHITE);
            setBackground(Color.BLACK);
            setBorder(new EmptyBorder(20, 10, 20, 10));
            setFontSize(12);
            setBoldFont();

            setPreferredHeight(30);
            setMaximumSize(new Dimension(2000, 2000));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    select(MenuButton.this);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (selected != MenuButton.this) {
                        setForeground(Color.BLACK);
                        setBackground(Color.WHITE);
                    } else {
                        setForeground(Color.WHITE);
                        setBackground(BLUE);
                    }

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (selected != MenuButton.this) {
                        setForeground(Color.WHITE);
                        setBackground(Color.BLACK);
                    } else {
                        setForeground(Color.WHITE);
                        setBackground(BLUE);
                    }
                }

            });

        }

        private void select(MenuButton selected) {
            setSelected(selected);
            for (MenuButton button : menuButtons) {
                if (button == selected) {
                    button.setForeground(Color.WHITE);
                    button.setBackground(BLUE);
                } else {
                    button.setForeground(Color.WHITE);
                    button.setBackground(Color.BLACK);
                }
            }
        }

    }

    private void setSelected(MenuButton selected) {
        this.selected = selected;
    }

}
