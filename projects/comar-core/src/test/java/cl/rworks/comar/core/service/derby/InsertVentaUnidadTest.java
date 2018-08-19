/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.TestUtils;
import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.core.model.ProductoEntity;
import cl.rworks.comar.core.model.VentaEntity;
import cl.rworks.comar.core.model.VentaUnidadEntity;
import cl.rworks.comar.core.model.impl.CategoriaEntityImpl;
import cl.rworks.comar.core.model.impl.ProductoEntityImpl;
import cl.rworks.comar.core.model.impl.VentaEntityImpl;
import cl.rworks.comar.core.model.impl.VentaUnidadEntityImpl;
import cl.rworks.comar.core.service.ComarServiceException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author aplik
 */
public class InsertVentaUnidadTest {

    public static void main(String[] args) {
        try (Connection conn = TestUtils.createConnection()) {

            try {
                CategoriaEntity categoria = CategoriaEntityImpl.create("ccc");
                InsertCategoria.serve(conn, categoria);

                ProductoEntity producto = ProductoEntityImpl.create("xxx");
                InsertProducto.serve(conn, producto, categoria);

                VentaEntity venta = VentaEntityImpl.create("fff", LocalDate.now());
                InsertVenta.serve(conn, venta);

                VentaUnidadEntity unidad = VentaUnidadEntityImpl.create(venta, producto, new BigDecimal("1000"), BigDecimal.ONE);
                InsertVentaUnidad.serve(conn, unidad, venta, producto);

                GetAllVentaUnidad.serve(conn).stream().forEach(System.out::println);

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
