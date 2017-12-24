/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing;

import com.alee.laf.WebLookAndFeel;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author rgonzalez
 */
public class ComarCore {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                WebLookAndFeel.install();

                ComarFrame frame = new ComarFrame();
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                frame.addWindowListener(new ComarFrameListener());
                
                ComarSystem.getInstance().setFrame(frame);
                ComarSystem.getInstance().startup();
                
                frame.showMe();
            }
        });
    }

}
