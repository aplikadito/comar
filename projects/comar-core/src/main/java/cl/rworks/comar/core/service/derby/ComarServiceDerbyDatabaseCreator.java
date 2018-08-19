/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.core.model.Metrica;
import cl.rworks.comar.core.model.ProductoEntity;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.model.impl.CategoriaEntityImpl;
import cl.rworks.comar.core.model.impl.ProductoEntityImpl;
import cl.rworks.comar.core.util.RunSqlScript;
import cl.rworks.comar.core.util.RunSqlScriptException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author aplik
 */
public class ComarServiceDerbyDatabaseCreator {

    public void create(DataSource datasource) {
        create(datasource, true);
    }

    public void create(DataSource datasource, boolean debug) {
        try {
            InputStream is = getClass().getResourceAsStream("/db.sql");
            RunSqlScript run = new RunSqlScript();
            run.setDebug(debug);
            run.run(datasource, is);
        } catch (RunSqlScriptException ex) {
            ex.printStackTrace();
        }

        try (Connection conn = datasource.getConnection()) {
            Metrica[] values = Metrica.values();
            for (Metrica metric : values) {
                InsertMetrica.serve(conn, metric);
            }

            CategoriaEntityImpl defaultCategory = new CategoriaEntityImpl("Varios");
            
            InsertCategoria.serve(conn, CategoriaEntityImpl.create("Abarrotes"));
            InsertCategoria.serve(conn, CategoriaEntityImpl.create("Bebidas"));
            InsertCategoria.serve(conn, CategoriaEntityImpl.create("Bebidas analcohólicas", CategoriaEntity.DEFAULT_IMPUESTO_PRINCIPAL, new BigDecimal(0.10)));
            InsertCategoria.serve(conn, CategoriaEntityImpl.create("Bebidas azucaradas", CategoriaEntity.DEFAULT_IMPUESTO_PRINCIPAL, new BigDecimal(0.18)));
            InsertCategoria.serve(conn, CategoriaEntityImpl.create("Licores", CategoriaEntity.DEFAULT_IMPUESTO_PRINCIPAL, new BigDecimal(0.315)));
            InsertCategoria.serve(conn, CategoriaEntityImpl.create("Vinos", CategoriaEntity.DEFAULT_IMPUESTO_PRINCIPAL, new BigDecimal(0.205)));
            InsertCategoria.serve(conn, CategoriaEntityImpl.create("Cigarros", BigDecimal.ZERO, BigDecimal.ZERO));
            InsertCategoria.serve(conn, defaultCategory, false);
            
            ProductoEntity producto = ProductoEntityImpl.create("1", "Varios", BigDecimal.ZERO);
            producto.setIncluirEnBoleta(true);
            producto.setPrecioVentaFijo(false);
            InsertProducto.serve(conn, producto, defaultCategory);
            
            ProductoEntity productoNoBoleta = ProductoEntityImpl.create("2", "Varios (No en Boleta)", BigDecimal.ZERO);
            productoNoBoleta.setIncluirEnBoleta(false);
            productoNoBoleta.setPrecioVentaFijo(false);
            InsertProducto.serve(conn, productoNoBoleta, defaultCategory);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ComarServiceException ex) {
            ex.printStackTrace();
        }
    }
}
