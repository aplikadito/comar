/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.permazen;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.service.ComarTransaction;
import io.permazen.JTransaction;

/**
 *
 * @author aplik
 */
public class ComarTransactionPermazen extends ComarTransaction {

    private JTransaction jtx;

    public ComarTransactionPermazen(JTransaction jtx) {
        this.jtx = jtx;
    }

    @Override
    public void commit() {
        jtx.commit();
    }

    @Override
    public void rollback() {
        jtx.rollback();
    }

    @Override
    public void endTransaction() throws ComarServiceException {
        JTransaction.setCurrent(null);
    }

}
