/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.TestUtils;
import cl.rworks.comar.core.service.ComarServiceException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author aplik
 */
public class DeleteProductoTest01 {
    
    public static void main(String[] args) {
        try (Connection conn = TestUtils.createConnection()) {
            try {
                DeleteProducto.serve(conn, "xxx");
                GetAllProducto.serve(conn).forEach(System.out::println);
            } catch (ComarServiceException e) {
                e.printStackTrace();
            } finally {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
