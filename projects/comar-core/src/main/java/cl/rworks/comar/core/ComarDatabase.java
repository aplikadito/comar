/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import org.jsimpledb.kv.derby.DerbyKVDatabase;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.apache.log4j.Logger;
import org.jsimpledb.JSimpleDB;
import org.jsimpledb.JSimpleDBFactory;
import org.jsimpledb.JTransaction;
import org.jsimpledb.ValidationMode;
import org.jsimpledb.core.Database;

/**
 *
 * @author rgonzalez
 */
public class ComarDatabase {

    private static final Logger LOG = Logger.getLogger(ComarDatabase.class);
    private final JSimpleDB delegate;

    public ComarDatabase() {
        EmbeddedDataSource ds = new EmbeddedDataSource();
        ds.setDatabaseName("storage;create=true");

        DerbyKVDatabase dbDerby = new DerbyKVDatabase();
        dbDerby.setDataSource(ds);
        dbDerby.start();

        delegate = new JSimpleDBFactory().
                setDatabase(new Database(dbDerby)).
                setModelClasses(ComarCategory.class, ComarItem.class).
                setSchemaVersion(-1).
                newJSimpleDB();
    }

    public JTransaction startTransaction() {
        return get().createTransaction(true, ValidationMode.AUTOMATIC);
    }

    public void endTransaction() {
        JTransaction.setCurrent(null);
    }

    public Object execute(ComarDatabaseTask task) {
        JTransaction jtx = startTransaction();
        JTransaction.setCurrent(jtx);
        try {
            return task.execute(jtx);
        } finally {
            endTransaction();
        }
    }

    public JSimpleDB get() {
        return delegate;
    }

}
