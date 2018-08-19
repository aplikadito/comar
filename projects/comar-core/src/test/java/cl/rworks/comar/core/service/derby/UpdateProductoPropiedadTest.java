/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.TestUtils;
import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.core.model.Metrica;
import cl.rworks.comar.core.service.ComarServiceException;
import java.sql.Connection;
import java.sql.SQLException;
import cl.rworks.comar.core.model.ProductoEntity;

/**
 *
 * @author aplik
 */
public class UpdateProductoPropiedadTest {
    
    public static void main(String[] args) {
        try (Connection conn = TestUtils.createConnection()) {
            try {
                CategoriaEntity categoria = InsertCategoria.serve(conn, "ccc");
                
                ProductoEntity producto = InsertProducto.serve(conn, "xxx", categoria);
                GetAllProducto.serve(conn).forEach(System.out::println);
//                
                System.out.println("");
                UpdateProductoPropiedad.serve(conn, producto, "CODIGO", "yyy");
                UpdateProductoPropiedad.serve(conn, producto, "DESCRIPCION", "mate");
                UpdateProductoPropiedad.serve(conn, producto, "METRICA", Metrica.CENTIMETROS_CUBICOS);
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
