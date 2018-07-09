/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service;

import java.io.Closeable;

/**
 *
 * @author aplik
 */
public abstract class ComarTransaction implements Closeable {

    public abstract void commit() throws ComarServiceException;

    public abstract void rollback();

    public abstract void endTransaction() throws ComarServiceException;

    @Override
    public void close() throws ComarServiceException {
        endTransaction();
    }
}
