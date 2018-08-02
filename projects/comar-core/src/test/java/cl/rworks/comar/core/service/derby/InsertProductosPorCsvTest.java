/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.TestUtils;
import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.core.model.ProductoEntity;
import cl.rworks.comar.core.model.impl.CategoriaEntityImpl;
import cl.rworks.comar.core.model.impl.ProductoEntityImpl;
import cl.rworks.comar.core.service.ComarServiceException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aplik
 */
public class InsertProductosPorCsvTest {

    public static void main(String[] args) {
        try (Connection conn = TestUtils.createConnection()) {
            try {
                CategoriaEntity c = new CategoriaEntityImpl("ccc");
                InsertCategoria.serve(conn, c);

                List<ProductoEntity> productos = new ArrayList<>();
                productos.add(ProductoEntityImpl.create("0000", "0000"));
                productos.add(ProductoEntityImpl.create("0001", "0001"));
                productos.add(ProductoEntityImpl.create("0002", "0002"));
                productos.add(ProductoEntityImpl.create("0003", "0003"));
                productos.add(ProductoEntityImpl.create("0004", "0004"));
                productos.add(ProductoEntityImpl.create("0005", "0005"));
                productos.add(ProductoEntityImpl.create("0006", "0006"));
                productos.add(ProductoEntityImpl.create("0007", "0007"));
                
                InsertProductosPorCsv.serve(conn, productos, c, 4);
                
                GetAllProductos.serve(conn).forEach(System.out::println);
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
