/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.border.Border;

/**
 *
 * @author aplik
 */
public class ComarPanelFactory {

    private ComarPanel panel = new ComarPanel();

    public ComarPanelFactory borderLayout() {
        panel.setLayout(new BorderLayout());
        return this;
    }

    public ComarPanelFactory boxLayoutLine() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        return this;
    }

    public ComarPanelFactory boxLayoutPage() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        return this;
    }
    
    public ComarPanelFactory flowLayoutLeft() {
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        return this;
    }

    public ComarPanelFactory flowLayoutCenter() {
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        return this;
    }
    
    public ComarPanelFactory flowLayoutRight() {
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        return this;
    }

    public ComarPanelFactory border(Border border) {
        panel.setBorder(border);
        return this;
    }

    public ComarPanelFactory add(Component... components) {
        panel.add(components);
        return this;
    }

    public ComarPanelFactory addNorth(Component c) {
        panel.add(c, BorderLayout.NORTH);
        return this;
    }

    public ComarPanelFactory addSouth(Component c) {
        panel.add(c, BorderLayout.SOUTH);
        return this;
    }

    public ComarPanel create() {
        return panel;
    }

}
