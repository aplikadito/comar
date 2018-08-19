/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.products;

import cl.rworks.comar.swing.util.CharsetUtils;
import com.alee.extended.layout.FormLayout;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author aplik
 */
public class Test {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                WebLookAndFeel.install();
                CharsetUtils.resume();

                WebPanel panel = new WebPanel(new FormLayout());
                panel.add(new WebLabel("Nombre"));
                panel.add(new WebTextField());
                panel.add(new WebLabel("Impuesto"));
                panel.add(new WebTextField());
                Object name = WebOptionPane.showConfirmDialog(null, panel, "Editar Categoria", WebOptionPane.OK_CANCEL_OPTION, WebOptionPane.PLAIN_MESSAGE);
                System.out.println("'" + name + "'");
            }
        });
    }

}
