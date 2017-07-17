/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import java.util.NavigableSet;
import java.util.Set;
import org.jsimpledb.JObject;
import org.jsimpledb.JTransaction;
import org.jsimpledb.annotation.JField;
import org.jsimpledb.annotation.JSimpleClass;
import org.jsimpledb.core.util.ObjDumper;

/**
 *
 * @author rgonzalez
 */
@JSimpleClass
public abstract class ComarCategory implements JObject {

    public static final String VARIOS = "Varios";

    @JField(indexed = true, unique = true)
    public abstract String getName();

    public abstract void setName(String name);

    public abstract Set<ComarProduct> getItems();

    public static NavigableSet<ComarCategory> getAll() {
        return JTransaction.getCurrent().getAll(ComarCategory.class);
    }

    public static ComarCategory create() {
        return JTransaction.getCurrent().create(ComarCategory.class);
    }

    public static ComarCategory create(String name) {
        ComarCategory cat = create();
        cat.setName(name);
        return cat;
    }

    public static ComarCategory getByName(String name) {
        NavigableSet<ComarCategory> list = JTransaction.getCurrent().queryIndex(ComarCategory.class, "name", String.class).asMap().get(name);
        return list == null ? null : list.iterator().next();
    }

    public static ComarCategory getVarios() {
        return getByName(VARIOS);
    }

    public static ComarCategory createVarios() {
        ComarCategory category = ComarCategory.create();
        category.setName(ComarCategory.VARIOS);
        return category;
    }

    @Override
    public String toString() {
        return ObjDumper.toString(this.getTransaction().getTransaction(), this.getObjId(), 16);
    }

}
