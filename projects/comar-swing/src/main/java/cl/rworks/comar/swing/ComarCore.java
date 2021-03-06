/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing;

import cl.rworks.comar.swing.util.CharsetUtils;
import cl.rworks.comar.swing.main.ComarFrame;
import cl.rworks.comar.swing.main.ComarFrameListener;
import cl.rworks.comar.swing.main.ComarSystem;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.rootpane.WebFrame;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rgonzalez
 */
public class ComarCore {

    private static final Logger LOG = LoggerFactory.getLogger(ComarCore.class);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LOG.info("==========================");
                LOG.info("START");
                LOG.info("==========================");
                LOG.info("home: " + System.getProperty("user.dir"));
                WebLookAndFeel.install();
                CharsetUtils.resume();

                ComarSystem.getInstance().startup();

                ComarFrame frame = new ComarFrame();
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                frame.addWindowListener(new ComarFrameListener());
                ComarSystem.getInstance().setFrame(frame);

                frame.setExtendedState(WebFrame.MAXIMIZED_BOTH);
                frame.setVisible(true);
//                frame.showMe();
            }
        });
    }

}
