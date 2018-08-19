/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import cl.rworks.comar.core.service.derby.ComarServiceDerbyDatabaseCreator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.derby.jdbc.EmbeddedDataSource;

/**
 *
 * @author aplik
 */
public final class TestUtils {

    private TestUtils() {
    }

    public static Connection createConnection() {
        try {
//            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
//            Connection conn = DriverManager.getConnection("jdbc:derby:D:\\storage;create=true");
//            conn.setAutoCommit(false);

            EmbeddedDataSource ds = new EmbeddedDataSource();
            ds.setDatabaseName("memory:storage;create=true");

            ComarServiceDerbyDatabaseCreator creator = new ComarServiceDerbyDatabaseCreator();
            creator.create(ds, false);
            
            return ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
            }
        }
    }
}
