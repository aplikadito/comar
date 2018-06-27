/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing;

import cl.rworks.comar.core.controller.ComarController;
import cl.rworks.comar.core.controller.ComarControllerException;
import io.permazen.JField;
import io.permazen.JTransaction;
import io.permazen.Permazen;
import io.permazen.ValidationMode;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import cl.rworks.comar.core.controller.permazen.service.ComarProductHistorialPermazen;

/**
 *
 * @author aplik
 */
public class Database {

    public static void main(String[] args) {
        System.setProperty("user.dir", "D:\\trabajo\\comar\\home");

        ComarSystem system = ComarSystem.getInstance();
        ComarController controller = system.getService().getController();

        try {
            controller.createTransaction();
            NavigableSet<ComarProductHistorialPermazen> nav = ComarProductHistorialPermazen.getAll();
            nav.stream().forEach(e -> {
                SortedMap<String, JField> map = e.getJClass().getJFieldsByName();
                Set<Map.Entry<String, JField>> entrySet = map.entrySet();
                for (Map.Entry<String, JField> ex : entrySet) {
                    System.out.print(map.get(ex.getKey()).getValue(e));
                    System.out.print("\t");
                }
                System.out.println();

            });
        } catch (ComarControllerException e) {
            e.printStackTrace();
        } finally {
            controller.endTransaction();
        }
    }
}
