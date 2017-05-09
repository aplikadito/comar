/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author rgonzalez
 */
public class Derby {

    public Derby() {
    }

    public void test() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            Connection conn = DriverManager.getConnection("jdbc:derby:storage"); 
            Statement st = conn.createStatement();
            boolean execute = st.execute("select * from KV");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Derby derby = new Derby();
        derby.test();
    }
}
