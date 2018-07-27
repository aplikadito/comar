/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.sells;

import cl.rworks.comar.swing.main.ComarSystem;
import cl.rworks.comar.swing.model.ComarSell;
import cl.rworks.comar.swing.model.ComarWorkspace;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jcarmi
 */
public class ComarPanelSellsController {

    private static final Logger LOG = LoggerFactory.getLogger(ComarPanelSellsController.class);

    public List<ComarSell> searchSells(int[] value) {
        int year = value[0];
        int month = value[1];
        int day = value[2];

        ComarWorkspace ws = ComarSystem.getInstance().getWorkspace();
        List<ComarSell> sells = ws.getSells();

        List<ComarSell> list = sells.stream()
                .filter(e -> year != -1 ? e.getEntity().getFecha().getYear() == year : true)
                .filter(e -> month != -1 ? e.getEntity().getFecha().getMonthValue() == month : true)
                .filter(e -> day != -1 ? e.getEntity().getFecha().getDayOfMonth() == day : true)
                .collect(Collectors.toList());

        return list;
    }

}
