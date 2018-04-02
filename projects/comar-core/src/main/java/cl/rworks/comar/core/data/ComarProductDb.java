/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.data;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.model.ComarMetric;
import io.permazen.JObject;
import io.permazen.JTransaction;
import io.permazen.annotation.JField;
import io.permazen.annotation.OnCreate;
import io.permazen.annotation.PermazenType;
import io.permazen.core.ObjId;
import java.util.Collections;
import java.util.List;
import java.util.NavigableSet;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author rgonzalez
 */
@PermazenType
public abstract class ComarProductDb implements JObject, ComarProduct {

    @JField(indexed = true, unique = true)
    public abstract String getCode();

    public abstract void setCode(String code);

    @OnCreate
    void onCreate() {
        setMetric(ComarMetric.UNIDAD);
    }

    public String toString() {
        return String.format("[%s, %s, %s, %s, %s]", getCode(), getName(), getMetric(), getBuyPrice(), getSellPrice());
    }

    public static ComarProduct create() {
        JTransaction jtx = JTransaction.getCurrent();
        ComarProduct o = jtx.create(ComarProductDb.class);
        o.setMetric(ComarMetric.UNIDAD);
        return o;
    }

    public static NavigableSet<ComarProductDb> getAll() {
        JTransaction jtx = JTransaction.getCurrent();
        NavigableSet<ComarProductDb> products = jtx.getAll(ComarProductDb.class);
        return products;
    }

    public static ComarProductDb get(Object id) throws ComarServiceException {
        if (id == null) {
            return null;
        }

        if (!(id instanceof Long)) {
            throw new ComarServiceException("El id no es Long");
        }

        Long longId = (Long) id;

        JTransaction jtx = JTransaction.getCurrent();
        return (ComarProductDb) jtx.get(new ObjId(longId));
    }

    public static ComarProductDb getByCode(String code) {
        if (code == null || code.isEmpty()) {
            return null;
        }

        JTransaction jtx = JTransaction.getCurrent();
        NavigableSet<ComarProductDb> result = jtx.queryIndex(ComarProductDb.class, "code", String.class).asMap().get(code);
        return result != null ? result.first() : null;
    }

    public static void delete(ComarProductDb p) {
        if (p == null) {
            return;
        }

        JTransaction jtx = JTransaction.getCurrent();
        jtx.delete(p);
    }

    public static List<ComarProduct> search(final String text) {
        if (text == null || text.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        JTransaction jtx = JTransaction.getCurrent();
        String ttext = text.trim();
        NavigableSet<ComarProduct> all = jtx.getAll(ComarProduct.class);
        if (!ttext.isEmpty()) {
            Pattern pattern = Pattern.compile(".*" + ttext + ".*");
            Predicate<ComarProduct> filterCode = e -> pattern.matcher(e.getCode()).matches();
            Predicate<ComarProduct> filterName = e -> pattern.matcher(e.getName()).matches();
            Predicate<ComarProduct> filter = e -> filterCode.test(e) || filterName.test(e);
            return all.stream().filter(filter).collect(Collectors.toList());
        } else {
            return all.stream().collect(Collectors.toList());
        }

    }

    public static boolean existsCode(String code) {
        ComarProduct product = getByCode(code);
        return product != null;
    }
}
