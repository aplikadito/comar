/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author aplik
 */
public final class TestUtils {

    public static void close(Connection connection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private TestUtils(){
    }
    
    public static Connection createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            return DriverManager.getConnection("jdbc:derby:D:\\storage;create=true");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
