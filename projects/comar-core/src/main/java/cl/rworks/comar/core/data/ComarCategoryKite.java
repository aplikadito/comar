/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.data;

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
        ComarCategoryKite odb = jtx.create(ComarCategoryKite.class);
        odb.setId(odb.getObjId().asLong());
        return odb;
    }

    public static ComarCategoryKite create(String name) {
        JTransaction jtx = JTransaction.getCurrent();
        ComarCategoryKite cc = jtx.create(ComarCategoryKite.class);
        cc.setName(name);
        return cc;
    }

    public static NavigableSet<ComarCategoryKite> getAll() {
        JTransaction jtx = JTransaction.getCurrent();
        return jtx.getAll(ComarCategoryKite.class);
    }

    public static void update(ComarCategory o) {
        JTransaction jtx = JTransaction.getCurrent();
        ComarCategory odb = (ComarCategory) jtx.get(new ObjId(o.getId()));
        update(o, odb);
    }

    public static void update(ComarCategory source, ComarCategory destiny) {
        destiny.setName(source.getName());
    }

    public static ComarCategoryKite get(Long id) {
        JTransaction jtx = JTransaction.getCurrent();
        ObjId oid = new ObjId(id);
        return (ComarCategoryKite) jtx.get(oid);
    }

    public static ComarCategoryKite getByName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        JTransaction jtx = JTransaction.getCurrent();
        NavigableSet<ComarCategoryKite> result = jtx.queryIndex(ComarCategoryKite.class, "name", String.class).asMap().get(name);
        return result != null ? (ComarCategoryKite) result.first() : null;
    }

    public static void delete(ComarCategory c) {
        JTransaction jtx = JTransaction.getCurrent();
        ObjId oid = new ObjId(c.getId());
        ComarCategoryKite pp = (ComarCategoryKite) jtx.get(oid);

        NavigableSet<ComarProductKite> set = getProducts(pp);
        if (set != null) {
            set.stream().forEach(e -> e.setCategory(null));
        }

        jtx.delete(pp);
    }

    public static NavigableSet<ComarProductKite> getProducts(ComarCategory category) {
        JTransaction jtx = JTransaction.getCurrent();
        NavigableSet<ComarProductKite> map = jtx.queryIndex(ComarProductKite.class, "category", ComarCategory.class).asMap().get(category);
        return map != null && !map.isEmpty() ? map : null;
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
