/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing;

import cl.rworks.comar.core.data.ComarProductHistorialDb;
import io.permazen.JField;
import io.permazen.JTransaction;
import io.permazen.Permazen;
import io.permazen.ValidationMode;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;

/**
 *
 * @author aplik
 */
public class Database {

    public static void main(String[] args) {
        System.setProperty("user.dir", "D:\\trabajo\\comar\\home");

        ComarSystem system = ComarSystem.getInstance();

        Permazen db = system.getService().getDb();
        JTransaction jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
        JTransaction.setCurrent(jtx);
        try {
            NavigableSet<ComarProductHistorialDb> nav = ComarProductHistorialDb.getAll();
            nav.stream().forEach(e -> {
                SortedMap<String, JField> map = e.getJClass().getJFieldsByName();
                Set<Map.Entry<String, JField>> entrySet = map.entrySet();
                for (Map.Entry<String, JField> ex : entrySet) {
                    System.out.print(map.get(ex.getKey()).getValue(e));
                    System.out.print("\t");
                }
                System.out.println();

            });

        } finally {
            jtx.rollback();
            JTransaction.setCurrent(null);
        }
    }
}
