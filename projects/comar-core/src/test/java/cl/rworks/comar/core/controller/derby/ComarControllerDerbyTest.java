/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.controller.derby;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.derby.jdbc.EmbeddedDataSource;

/**
 *
 * @author aplik
 */
public class ComarControllerDerbyTest {

    public static void main(String[] args) throws SQLException {
        EmbeddedDataSource ds = new EmbeddedDataSource();
        ds.setDatabaseName("D:\\testdb" + ";create=true");

        Connection conn = ds.getConnection();
        PreparedStatement ps = conn.prepareStatement("select 1 from dual");
        ps.executeQuery();

        conn.close();
    }

}
