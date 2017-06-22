/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.HashSet;
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
public abstract class ComarItem implements JObject {

    @JField(indexed = true, unique = true, uniqueExcludeNull = true)
    public abstract String getCode();

    public abstract void setCode(String code);

    @JField(indexed = true)
    public abstract String getName();

    public abstract void setName(String name);

    public abstract String getUnit();

    public abstract void setUnit(String unit);

    public abstract String getDecimalFormat();

    public abstract void setDecimalFormat(String decimalFormat);

    public abstract ComarCategory getCategory();

    public abstract void setCategory(ComarCategory category);

    public abstract double getBuyPrice();

    public abstract void setBuyPrice(double buyPrice);

    public abstract double getProfitRate();

    public abstract void setProfitRate(double profitRate);

    public abstract double getSellPrice();

    public abstract void setSellPrice(double sellPrice);

    public static ComarItem create() {
        return JTransaction.getCurrent().create(ComarItem.class);
    }

    public static ComarItem create(String name) {
        ComarItem item = create();
        item.setName(name);
        return item;
    }

    public static NavigableSet<ComarItem> getAll() {
        return JTransaction.getCurrent().getAll(ComarItem.class);
    }

    public static NavigableSet<ComarItem> findByName(String name) {
        if (name == null) {
            return Sets.unmodifiableNavigableSet(new TreeSet<ComarItem>());
        }

        name = name.trim();
        if (name.isEmpty()) {
            return Sets.unmodifiableNavigableSet(new TreeSet<ComarItem>());
        }

        NavigableSet<ComarItem> result = new TreeSet<>();
        Pattern pattern = Pattern.compile(name + ".*");
        Set<ComarItem> items = getAll();
        for (ComarItem item : items) {
            Matcher matcher = pattern.matcher(item.getName());
            if (matcher.matches()) {
                result.add(item);
            }
        }

        return result;
    }

}
