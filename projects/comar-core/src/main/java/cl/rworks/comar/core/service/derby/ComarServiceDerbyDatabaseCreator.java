/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.Metrica;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.model.impl.CategoriaEntityImpl;
import cl.rworks.comar.core.util.RunSqlScript;
import cl.rworks.comar.core.util.RunSqlScriptException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author aplik
 */
public class ComarServiceDerbyDatabaseCreator {

    public void create(DataSource datasource) {
        try {
            InputStream is = getClass().getResourceAsStream("/db.sql");
            RunSqlScript run = new RunSqlScript();
            run.run(datasource, is);
        } catch (RunSqlScriptException ex) {
            ex.printStackTrace();
        }

        try (Connection conn = datasource.getConnection()) {
            Metrica[] values = Metrica.values();
            for(Metrica metric: values){
                InsertMetrica.serve(conn, metric);
            }
            
            InsertCategoria.serve(conn, new CategoriaEntityImpl("Abarrotes"));
            InsertCategoria.serve(conn, new CategoriaEntityImpl("Bebidas"));
            InsertCategoria.serve(conn, new CategoriaEntityImpl("Alcoholes"));
            InsertCategoria.serve(conn, new CategoriaEntityImpl("Despensa"));
            InsertCategoria.serve(conn, new CategoriaEntityImpl("Varios"));
            InsertCategoria.serve(conn, new CategoriaEntityImpl("Electronica"));
            InsertCategoria.serve(conn, new CategoriaEntityImpl("Plásticos"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ComarServiceException ex) {
            ex.printStackTrace();
        }
    }
}
