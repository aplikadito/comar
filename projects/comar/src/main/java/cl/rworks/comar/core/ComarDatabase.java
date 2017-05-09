/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import org.jsimpledb.kv.derby.DerbyKVDatabase;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.jsimpledb.JSimpleDB;
import org.jsimpledb.JSimpleDBFactory;
import org.jsimpledb.core.Database;

/**
 *
 * @author rgonzalez
 */
public class ComarDatabase {

    private final JSimpleDB delegate;

    public ComarDatabase() {
        EmbeddedDataSource ds = new EmbeddedDataSource();
        ds.setDatabaseName("storage;create=true");

        DerbyKVDatabase dbDerby = new DerbyKVDatabase();
        dbDerby.setDataSource(ds);
        dbDerby.start();

        delegate = new JSimpleDBFactory().
                setDatabase(new Database(dbDerby)).
                setModelClasses(ComarCategory.class).
                setSchemaVersion(-1).
                newJSimpleDB();

    }

    public JSimpleDB getDelegate() {
        return delegate;
    }

}
