/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing;

import com.alee.extended.statusbar.WebMemoryBar;
import com.alee.extended.statusbar.WebStatusBar;
import com.alee.extended.time.WebClock;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import javax.swing.BoxLayout;

/**
 *
 * @author rgonzalez
 */
public class ComarStatusBar extends WebPanel {

    private WebStatusBar statusBar;
    //
    private WebMemoryBar memoryBar;
    private WebLabel labelStatus;
    private WebClock clock;
    
    public ComarStatusBar() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        statusBar = new WebStatusBar();
        
        memoryBar = new WebMemoryBar();
        statusBar.add(memoryBar);
        
        labelStatus = new WebLabel("");
        statusBar.addFill(labelStatus);

        clock = new WebClock();
        clock.setTimePattern("dd/MM/yy HH:mm:ss");
        clock.start();
        statusBar.addToEnd(clock);
        
        add(statusBar);
    }

    public WebClock getClock() {
        return clock;
    }

    public WebLabel getLabelStatus() {
        return labelStatus;
    }
    
}
