/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.impl;

import cl.rworks.comar.core.model.ComarCategory;
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
 * @author aplik
 */
@PermazenType
public interface ComarCategoryKite extends JObject, ComarCategory {

    @JField(indexed = true, unique = true)
    @Override
    String getName();

    @Override
    void setName(String name);

    public static ComarCategoryKite create() {
        JTransaction jtx = JTransaction.getCurrent();
        return jtx.create(ComarCategoryKite.class);
    }

    public static NavigableSet<ComarCategoryKite> getAll() {
        JTransaction jtx = JTransaction.getCurrent();
        return jtx.getAll(ComarCategoryKite.class);
    }

    public static void insert(ComarCategory p) {
        ComarCategoryKite pp = create();
        pp.setId(pp.getObjId().asLong());
        pp.setName(p.getName());
        p.setId(pp.getId());
    }

    public static ComarCategoryKite get(Long id) {
        JTransaction jtx = JTransaction.getCurrent();
        ObjId oid = new ObjId(id);
        return (ComarCategoryKite) jtx.get(oid);
    }

    public static ComarCategoryKite getByName(String name) {
        JTransaction jtx = JTransaction.getCurrent();
        NavigableSet<ComarCategoryKite> result = jtx.queryIndex(ComarCategoryKite.class, "name", String.class).asMap().get(name);
        return result != null ? (ComarCategoryKite) result.first() : null;
    }

    public static void delete(ComarCategory p) {
        JTransaction jtx = JTransaction.getCurrent();
        ObjId oid = new ObjId(p.getId());
        JObject pp = jtx.get(oid);
        jtx.delete(pp);
    }

    public static NavigableSet<ComarCategoryKite> search(final String text) {
        if (text == null) {
            return new TreeSet<>();
        }

        JTransaction jtx = JTransaction.getCurrent();
        String ttext = text.trim();
        NavigableSet<ComarCategoryKite> all = jtx.getAll(ComarCategoryKite.class);
        if (!ttext.isEmpty()) {
            Pattern pattern = Pattern.compile(".*" + ttext + ".*");
            Predicate<ComarCategoryKite> filterName = e -> pattern.matcher(e.getName()).matches();
            Stream<ComarCategoryKite> stream = all.stream().filter(filterName);
            return stream.collect(Collectors.toCollection(TreeSet::new));
        } else {
            return all;
        }

    }
}
