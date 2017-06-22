/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import java.util.Set;
import org.jsimpledb.JTransaction;
import org.jsimpledb.ValidationMode;

/**
 *
 * @author rgonzalez
 */
public class ComarDatabaseTest {

    public static void main(String[] args) {
        ComarDatabase db = ComarContext.getInstance().getDatabase();
        JTransaction jtx;

        jtx = db.startTransaction();
        JTransaction.setCurrent(jtx);
        try {
//            ComarItem.findByName("p2").;
//            for (ComarItem item : result) {
//                System.out.println(item.getName());
//            }

        } finally {
            db.endTransaction();
        }

    }

}
