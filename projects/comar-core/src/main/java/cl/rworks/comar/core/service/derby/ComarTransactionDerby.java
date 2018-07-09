/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.service.ComarTransaction;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author aplik
 */
public class ComarTransactionDerby extends ComarTransaction {

    private Connection connection;

    public ComarTransactionDerby(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void commit() throws ComarServiceException {
        try {
            connection.commit();
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    @Override
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException ex) {
//            throw new ComarControllerException("Error", ex);
        }
    }

    @Override
    public void endTransaction() throws ComarServiceException {
        try {
            if (connection != null) {
                try {
                    if (!connection.isClosed()) {
                        connection.close();
                    }
                } catch (SQLException ex) {
                    throw new ComarServiceException("Error", ex);
                }
            }
        } finally {
            connection = null;
        }
    }

}
