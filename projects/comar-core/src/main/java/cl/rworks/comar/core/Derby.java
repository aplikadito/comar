/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author rgonzalez
 */
public class Derby {

    public Derby() {
    }

    public void test() {
        Connection conn = null;
        Statement st;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

            conn = DriverManager.getConnection("jdbc:derby:storage");
            st = conn.createStatement();
            
//            ResultSet rs = st.executeQuery("select * from KV");
//            while (rs.next()) {
//                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//                    String columnName = rs.getMetaData().getColumnName(i);
//                    System.out.print(rs.getString(columnName));
//                    System.out.print("    ");
//                }
//                System.out.println();
//            }
            
            st = conn.createStatement();
            boolean execute = st.execute("drop table KV");
            System.out.println(execute);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Derby derby = new Derby();
        derby.test();
    }
}
