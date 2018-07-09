/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import org.apache.derby.jdbc.EmbeddedDataSource;

/**
 *
 * @author aplik
 */
public class RunSqlScriptTest {

    public static void main(String[] args) throws Exception {
        EmbeddedDataSource ds = new EmbeddedDataSource();
        ds.setDatabaseName("D:\\storage;create=true");
//        ds.getConnection().prepareStatement("SELECT 1 FROM SYSIBM.SYSDUMMY1");

        ComarServiceDerbyDatabaseCreator creator = new ComarServiceDerbyDatabaseCreator();
        creator.create(ds);
    }
}
