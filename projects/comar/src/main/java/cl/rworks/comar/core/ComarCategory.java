/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import java.util.NavigableSet;
import org.jsimpledb.JObject;
import org.jsimpledb.JTransaction;
import org.jsimpledb.annotation.JSimpleClass;

/**
 *
 * @author rgonzalez
 */
@JSimpleClass
public abstract class ComarCategory implements JObject {

    public abstract String getName();

    public abstract void setName(String name);

    public static NavigableSet<ComarCategory> getAll() {
        return JTransaction.getCurrent().getAll(ComarCategory.class);
    }

}
