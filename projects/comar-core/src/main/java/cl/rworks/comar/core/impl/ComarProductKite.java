/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.impl;

import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.model.ComarDecimalFormat;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.model.ComarUnit;
import cl.rworks.comar.core.service.ComarDaoException;
import io.permazen.JObject;
import io.permazen.JTransaction;
import io.permazen.annotation.JField;
import io.permazen.annotation.OnCreate;
import io.permazen.annotation.PermazenType;
import io.permazen.core.ObjId;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author rgonzalez
 */
@PermazenType
public abstract class ComarProductKite implements JObject, ComarProduct {

    @JField(indexed = true, unique = true)
    public abstract Long getId();

    public abstract void setId(Long id);

    @JField(indexed = true, unique = true)
    public abstract String getCode();

    public abstract void setCode(String code);

    @OnCreate
    void onCreate() {
        setDecimalFormat(ComarDecimalFormat.ZERO);
        setUnit(ComarUnit.UNIDAD);
    }

    public String toString() {
        return String.format("[%s, %s, %s, %s, %s, %s]", getId(), getCode(), getName(), getUnit(), getDecimalFormat(), getCategory());
    }

    public static ComarProductKite create() {
        JTransaction jtx = JTransaction.getCurrent();
        ComarProductKite o = jtx.create(ComarProductKite.class);
        o.setId(o.getObjId().asLong());
        o.setUnit(ComarUnit.UNIDAD);
        o.setDecimalFormat(ComarDecimalFormat.ZERO);
        return o;
    }

    public static NavigableSet<ComarProductKite> getAll() {
        JTransaction jtx = JTransaction.getCurrent();
        NavigableSet<ComarProductKite> products = jtx.getAll(ComarProductKite.class);
        for (ComarProductKite p : products) {
            System.out.println(p.getCategory());
        }
        return products;
    }

    public static void update(ComarProduct p) throws ComarDaoException {
        if (p == null) {
            throw new ComarDaoException("Producto nulo");
        }

        System.out.println(p.getId());
        JTransaction jtx = JTransaction.getCurrent();
        ComarProduct odb = (ComarProduct) jtx.get(new ObjId(p.getId()));
        update(p, odb);
    }

    public static void update(ComarProduct source, ComarProduct destiny) {
        destiny.setCode(source.getCode());
        destiny.setName(source.getName());
        destiny.setDecimalFormat(source.getDecimalFormat());
        destiny.setUnit(source.getUnit());

        ComarCategory c = source.getCategory();
        if (c != null) {
            ComarCategoryKite cc = ComarCategoryKite.get(c.getId());
            destiny.setCategory(cc);
        }
    }

    public static ComarProductKite get(Object id) throws ComarDaoException {
        if (id == null) {
            return null;
        }

        if (!(id instanceof Long)) {
            throw new ComarDaoException("El id no es Long");
        }

        Long longId = (Long) id;

        JTransaction jtx = JTransaction.getCurrent();
        return (ComarProductKite) jtx.get(new ObjId(longId));
    }

    public static ComarProductKite getByCode(String code) {
        JTransaction jtx = JTransaction.getCurrent();
        NavigableSet<ComarProductKite> result = jtx.queryIndex(ComarProductKite.class, "code", String.class).asMap().get(code);
        return result != null ? result.first() : null;
    }

    public static void delete(ComarProduct p) {
        if (p == null) {
            return;
        }

        JTransaction jtx = JTransaction.getCurrent();
        ObjId oid = new ObjId(p.getId());
        JObject pp = jtx.get(oid);
        jtx.delete(pp);
    }

    public static NavigableSet<ComarProductKite> search(final String text) {
        if (text == null || text.isEmpty()) {
            return new TreeSet<>();
        }

        JTransaction jtx = JTransaction.getCurrent();
        String ttext = text.trim();
        NavigableSet<ComarProductKite> all = jtx.getAll(ComarProductKite.class);
        if (!ttext.isEmpty()) {
            Pattern pattern = Pattern.compile(".*" + ttext + ".*");
            Predicate<ComarProductKite> filterCode = e -> pattern.matcher(e.getCode()).matches();
            Predicate<ComarProductKite> filterName = e -> pattern.matcher(e.getName()).matches();
            Predicate<ComarProductKite> filter = e -> filterCode.test(e) || filterName.test(e);
            Stream<ComarProductKite> stream = all.stream().filter(filter);

            TreeSet<ComarProductKite> collect = stream.collect(Collectors.toCollection(TreeSet::new));
            return collect;
        } else {
            return all;
        }

    }

    public static boolean existsCode(String code) {
        ComarProductKite product = getByCode(code);
        return product != null;
    }
}
