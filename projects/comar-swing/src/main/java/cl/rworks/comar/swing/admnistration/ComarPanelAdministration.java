/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.util.ComarPanelCardContainer;
import cl.rworks.comar.swing.util.ComarPanelTitle;
import cl.rworks.comar.swing.util.ComarMenuButton;
import cl.rworks.comar.swing.util.ComarPanelCard;
import com.alee.extended.panel.WebAccordion;
import com.alee.extended.panel.WebCollapsiblePane;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.tabbedpane.WebTabbedPane;
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

    private WebPanel panelContent;
    private WebTabbedPane tabbed;

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

        this.tabbed = new WebTabbedPane();
        this.tabbed.addTab("Productos", new ComarPanelProduct());
        this.tabbed.addTab("Categorias", new ComarPanelCategory());
        this.tabbed.addTab("Inventario", new ComarPanelStock());
        
        this.tabbed.setFontSize(ComarSystem.getInstance().getProperties().getNormalFontSize());
        this.panelContent.add(tabbed, BorderLayout.CENTER);

        return panelContent;
    }

}
