/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.TestUtils;
import cl.rworks.comar.core.model.FacturaEntity;
import cl.rworks.comar.core.model.impl.FacturaEntityImpl;
import cl.rworks.comar.core.service.ComarServiceException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

/**
 *
 * @author aplik
 */
public class InsertFacturaTest {

    public static void main(String[] args) {
        try (Connection conn = TestUtils.createConnection()) {

            try {
                FacturaEntity factura = FacturaEntityImpl.create("0001", LocalDate.now());
                InsertFactura.serve(conn, factura);
                
                GetAllFactura.serve(conn).stream().forEach(System.out::println);
                
            } catch (ComarServiceException ex) {
                ex.printStackTrace();
            } finally {
                conn.rollback();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}
