/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.model.impl.ComarCategoryImpl;
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
            InsertCategory.serve(conn, new ComarCategoryImpl("Abarrotes"));
            InsertCategory.serve(conn, new ComarCategoryImpl("Bebidas"));
            InsertCategory.serve(conn, new ComarCategoryImpl("Alcoholes"));
            InsertCategory.serve(conn, new ComarCategoryImpl("Despensa"));
            InsertCategory.serve(conn, new ComarCategoryImpl("Varios"));
            InsertCategory.serve(conn, new ComarCategoryImpl("Electronica"));
            InsertCategory.serve(conn, new ComarCategoryImpl("Plásticos"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ComarServiceException ex) {
            ex.printStackTrace();
        }
    }
}
