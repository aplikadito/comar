/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import com.google.common.collect.Sets;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsimpledb.JObject;
import org.jsimpledb.JTransaction;
import org.jsimpledb.annotation.JField;
import org.jsimpledb.annotation.JSimpleClass;

/**
 *
 * @author rgonzalez
 */
@JSimpleClass
public abstract class ComarProduct implements JObject {

    @JField(indexed = true, unique = true, uniqueExcludeNull = true)
    public abstract String getCode();

    public abstract void setCode(String code);

    @JField(indexed = true)
    public abstract String getName();

    public abstract void setName(String name);

    public abstract ComarUnit getUnit();

    public abstract void setUnit(ComarUnit unit);

    public abstract ComarDecimalFormat getDecimalFormat();

    public abstract void setDecimalFormat(ComarDecimalFormat decimalFormat);

    public abstract ComarCategory getCategory();

    public abstract void setCategory(ComarCategory category);

    public static ComarProduct create() {
        return JTransaction.getCurrent().create(ComarProduct.class);
    }

    public static ComarProduct create(String name) {
        ComarProduct item = create();
        item.setName(name);
        return item;
    }

    public static NavigableSet<ComarProduct> getAll() {
        return JTransaction.getCurrent().getAll(ComarProduct.class);
    }

    public static NavigableSet<ComarProduct> findByName(String name) {
        if (name == null) {
            return Sets.unmodifiableNavigableSet(new TreeSet<ComarProduct>());
        }

        name = name.trim();
        if (name.isEmpty()) {
            return Sets.unmodifiableNavigableSet(new TreeSet<ComarProduct>());
        }

        NavigableSet<ComarProduct> result = new TreeSet<>();
        Pattern pattern = Pattern.compile(name + ".*");
        Set<ComarProduct> items = getAll();
        for (ComarProduct item : items) {
            Matcher matcher = pattern.matcher(item.getName());
            if (matcher.matches()) {
                result.add(item);
            }
        }

        return result;
    }

}
