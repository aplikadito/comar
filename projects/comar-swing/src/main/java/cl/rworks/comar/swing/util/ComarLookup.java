/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aplik
 */
public class ComarLookup {

    public static final String PRODUCT_CSVUPDATE = "PRODUCT_CSVUPDATE";
    //
    private Map<String, List<ComarLookupListener>> map = new HashMap<>();

    public void register(String action, ComarLookupListener listener) {
        List<ComarLookupListener> list = map.get(action);
        if (list == null) {
            list = new ArrayList<ComarLookupListener>();
            map.put(action, list);
        }
        list.add(listener);
    }

    public void fire(String action, Object data) {
        List<ComarLookupListener> list = map.get(action);
        if (list != null) {
            for (ComarLookupListener listener : list) {
                listener.action(data);
            }
        }
    }
}
