/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.controller.permazen;

import com.mysql.cj.jdbc.MysqlDataSource;
import io.permazen.Permazen;
import io.permazen.PermazenFactory;
import io.permazen.core.Database;
import io.permazen.kv.KVDatabase;
import io.permazen.kv.derby.DerbyKVDatabase;
import io.permazen.kv.mysql.MySQLKVDatabase;
import io.permazen.kv.simple.SimpleKVDatabase;
import java.util.Properties;
import org.apache.derby.jdbc.EmbeddedDataSource;

/**
 *
 * @author rgonzalez
 */
public final class ComarControllerPermazenUtils {

    private ComarControllerPermazenUtils() {
    }

    public static SimpleKVDatabase createOnMemoryDatabase() {
        return new SimpleKVDatabase();
    }

    public static DerbyKVDatabase createDerbyDatabase(String dbname) {
        EmbeddedDataSource ds = new EmbeddedDataSource();
        ds.setDatabaseName(dbname + ";create=true");

        DerbyKVDatabase dbDerby = new DerbyKVDatabase();
        dbDerby.setDataSource(ds);
        dbDerby.start();

        return dbDerby;
    }

    public static MySQLKVDatabase createMysqlDatabase(Properties properties) {
        MysqlDataSource ds = new MysqlDataSource();

        MySQLKVDatabase dbDerby = new MySQLKVDatabase();
        dbDerby.setDataSource(ds);
        dbDerby.start();

        return dbDerby;
    }

    public static Permazen createPermazen(KVDatabase database, Class<?>... modelClasses) {
        return new PermazenFactory().
                setDatabase(new Database(database)).
                setModelClasses(modelClasses).
                setSchemaVersion(-1).
                newPermazen();
    }

}
