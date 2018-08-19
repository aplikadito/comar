/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.TestUtils;
import cl.rworks.comar.core.model.VentaEntity;
import cl.rworks.comar.core.model.impl.VentaEntityImpl;
import cl.rworks.comar.core.service.ComarServiceException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author aplik
 */
public class InsertVentaTest {

    public static void main(String[] args) {
        try (Connection conn = TestUtils.createConnection()) {

            try {
                VentaEntity factura = VentaEntityImpl.create("0001", LocalDate.now());
                InsertVenta.serve(conn, factura);
                
                GetAllVenta.serve(conn).stream().forEach(System.out::println);
                
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
