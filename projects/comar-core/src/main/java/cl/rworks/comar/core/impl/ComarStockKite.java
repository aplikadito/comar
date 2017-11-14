/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.impl;

import cl.rworks.comar.core.model.ComarStock;
import io.permazen.JObject;
import io.permazen.JTransaction;
import io.permazen.annotation.JField;
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
public interface ComarStockKite extends JObject, ComarStock {

    @JField(indexed = true)
    default Long getId() {
        return getObjId() != null ? getObjId().asLong() : null;
    }

    void setId(Long id);

    @JField(indexed = true)
    String getCode();

    void setCode(String code);
    
    public static ComarStockKite create() {
        JTransaction jtx = JTransaction.getCurrent();
        return jtx.create(ComarStockKite.class);
    }

    public static NavigableSet<ComarStockKite> getAll() {
        JTransaction jtx = JTransaction.getCurrent();
        return jtx.getAll(ComarStockKite.class);
    }

    public static void insert(ComarStock p) {
        ComarStockKite pp = create();
        pp.setId(pp.getObjId().asLong());
        pp.setCode(p.getCode());
        p.setId(pp.getId());
    }

    public static ComarStockKite get(Long id) {
        JTransaction jtx = JTransaction.getCurrent();
        ObjId oid = new ObjId(id);
        return (ComarStockKite) jtx.get(oid);
    }

    public static ComarStockKite getByCode(String code) {
        JTransaction jtx = JTransaction.getCurrent();
        NavigableSet<ComarCategoryKite> result = jtx.queryIndex(ComarCategoryKite.class, "name", String.class).asMap().get(code);
        return result != null ? (ComarStockKite) result.first() : null;
    }
    
    public static void delete(ComarStock p) {
        JTransaction jtx = JTransaction.getCurrent();
        ObjId oid = new ObjId(p.getId());
        JObject pp = jtx.get(oid);
        jtx.delete(pp);
    }

    public static NavigableSet<ComarStockKite> search(final String text) {
        if (text == null) {
            return new TreeSet<>();
        }

        JTransaction jtx = JTransaction.getCurrent();
        String ttext = text.trim();
        NavigableSet<ComarStockKite> all = jtx.getAll(ComarStockKite.class);
        if (!ttext.isEmpty()) {
            Pattern pattern = Pattern.compile(".*" + ttext + ".*");
            Predicate<ComarStockKite> filterName = e -> pattern.matcher(e.getCode()).matches();
            Stream<ComarStockKite> stream = all.stream().filter(filterName);
            return stream.collect(Collectors.toCollection(TreeSet::new));
        } else {
            return all;
        }

    }
}
