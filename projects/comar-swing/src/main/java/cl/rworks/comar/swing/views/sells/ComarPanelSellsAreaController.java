/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.sells;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jcarmi
 */
public class ComarPanelSellsAreaController {

          private static final Logger LOG = LoggerFactory.getLogger(ComarPanelSellsAreaController.class);

          public List<Object> findProducts(String dayFilter, String monthFilter, String yearFilter) {
                    List<Object> list = new ArrayList<>();
                    list.add("Elemento 1");
                    list.add("Elemento 2");
                    list.add("Elemento 3");
                    return list;
          }

}
