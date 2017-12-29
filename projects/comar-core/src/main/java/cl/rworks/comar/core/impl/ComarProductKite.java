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
        return jtx.create(ComarProductKite.class);
    }

    public static NavigableSet<ComarProductKite> getAll() {
        JTransaction jtx = JTransaction.getCurrent();
        return jtx.getAll(ComarProductKite.class);
    }

    public static ComarProductKite insert(ComarProduct p) {
        ComarProductKite pp = create();
        pp.setId(pp.getObjId().asLong());
        update(pp, p);

        p.setId(pp.getId());
        return pp;
    }

    public static void update(ComarProductKite pp, ComarProduct p) {
        pp.setCode(p.getCode());
        pp.setName(p.getName());
        pp.setDecimalFormat(p.getDecimalFormat());
        pp.setUnit(p.getUnit());

        ComarCategory c = p.getCategory();
        if (c != null) {
            ComarCategoryKite cc = ComarCategoryKite.get(c.getId());
            pp.setCategory(cc);
        }
    }

    public static ComarProductKite get(Long id) {
        JTransaction jtx = JTransaction.getCurrent();
        ObjId oid = new ObjId(id);
        return (ComarProductKite) jtx.get(oid);
    }

    public static ComarProductKite getByCode(String code) {
        JTransaction jtx = JTransaction.getCurrent();
        NavigableSet<ComarProduct> result = jtx.queryIndex(ComarProduct.class, "code", String.class).asMap().get(code);
        return result != null ? (ComarProductKite) result.first() : null;
    }

    public static void delete(ComarProduct p) {
        JTransaction jtx = JTransaction.getCurrent();
        ObjId oid = new ObjId(p.getId());
        JObject pp = jtx.get(oid);
        jtx.delete(pp);
    }

    public static NavigableSet<ComarProductKite> search(final String text) {
        if (text == null) {
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
            return stream.collect(Collectors.toCollection(TreeSet::new));
        } else {
            return all;
        }

    }

    public static boolean existsCode(String code) {
        ComarProductKite product = getByCode(code);
        return product != null;
    }
}
