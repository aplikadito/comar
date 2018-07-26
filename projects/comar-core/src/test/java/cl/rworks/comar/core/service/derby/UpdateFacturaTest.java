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

/**
 *
 * @author aplik
 */
public class UpdateFacturaTest {

    public static void main(String[] args) {
        try (Connection conn = TestUtils.createConnection()) {
            try {
                FacturaEntity factura = FacturaEntityImpl.create("fff", LocalDate.now());
                InsertFactura.serve(conn, factura);

                factura.setCodigo("12345");
                factura.setFecha(LocalDate.of(2018, 1, 1));
                UpdateFactura.serve(conn, factura);
//                conn.commit();

                GetAllFactura.serve(conn).forEach(System.out::println);
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
