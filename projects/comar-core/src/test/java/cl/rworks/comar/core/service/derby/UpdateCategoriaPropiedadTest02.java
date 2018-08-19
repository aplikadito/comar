/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.TestUtils;
import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.core.service.ComarServiceException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author aplik
 */
public class UpdateCategoriaPropiedadTest02 {
    
    public static void main(String[] args) {
        try (Connection conn = TestUtils.createConnection()) {
            try {
                CategoriaEntity c = InsertCategoria.serve(conn, "ccc");
                GetAllCategoria.serve(conn).stream().forEach(System.out::println);
                System.out.println("");
                
                UpdateCategoriaPropiedad.serve(conn, c, "NOMBRE", "ddd");
                UpdateCategoriaPropiedad.serve(conn, c, "IMPUESTOPRINCIPAL", new BigDecimal("-0.20"));
//                UpdateCategoriaPropiedad.serve(conn, c, "IMPUESTOSECUNDARIO", new BigDecimal("0.24"));
//                UpdateCategoriaPropiedad.serve(conn, c, "PORCENTAJEGANANCIA", new BigDecimal("0.30"));
                GetAllCategoria.serve(conn).stream().forEach(System.out::println);
                
            } catch (ComarServiceException e) {
                System.out.println("OK: " + e.getMessage());
            } finally {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }    
}
