/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.TestUtils;
import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.core.model.FacturaEntity;
import cl.rworks.comar.core.model.FacturaUnidadEntity;
import cl.rworks.comar.core.model.ProductoEntity;
import cl.rworks.comar.core.model.impl.FacturaUnidadEntityImpl;
import cl.rworks.comar.core.service.ComarServiceException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author aplik
 */
public class DeleteProductoTest02 {

    public static void main(String[] args) {
        try (Connection conn = TestUtils.createConnection()) {
            try {
                CategoriaEntity categoria = InsertCategoria.serve(conn, "ccc");
                ProductoEntity producto = InsertProducto.serve(conn, "xxx", categoria);
                FacturaEntity factura = InsertFactura.serve(conn, "fff");
                FacturaUnidadEntity unit = InsertFacturaUnidad.serve(conn, new BigDecimal(100), new BigDecimal(1), factura, producto);
                GetAllProducto.serve(conn).forEach(System.out::println);

                DeleteProducto.serve(conn, producto);
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
