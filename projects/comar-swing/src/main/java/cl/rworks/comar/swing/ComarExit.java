/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing;

import com.alee.extended.time.WebClock;

/**
 *
 * @author rgonzalez
 */
public class ComarExit {

    public void exit() {
        ComarFrame frame = ComarSystem.getInstance().getFrame();
        WebClock clock = frame.getStatusBar().getClock();
        clock.stop();
        System.exit(0);
    }
}
