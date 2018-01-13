/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.data;

import cl.rworks.comar.core.model.ComarSell;
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
public interface ComarSellKite extends JObject, ComarSell {

    @JField(indexed = true, unique = true)
    @Override
    String getCode();

    @Override
    void setCode(String code);

    public static ComarSellKite create() {
        JTransaction jtx = JTransaction.getCurrent();
        return jtx.create(ComarSellKite.class);
    }

    public static NavigableSet<ComarSellKite> getAll() {
        JTransaction jtx = JTransaction.getCurrent();
        return jtx.getAll(ComarSellKite.class);
    }

    public static void insert(ComarSellKite p) {
        ComarSellKite pp = create();
        pp.setId(pp.getObjId().asLong());
        pp.setCode(p.getCode());
        p.setId(pp.getId());
    }

    public static ComarCategoryKite get(Long id) {
        JTransaction jtx = JTransaction.getCurrent();
        ObjId oid = new ObjId(id);
        return (ComarCategoryKite) jtx.get(oid);
    }

    public static ComarSellKite getByCode(String code) {
        JTransaction jtx = JTransaction.getCurrent();
        NavigableSet<ComarSellKite> result = jtx.queryIndex(ComarSellKite.class, "code", String.class).asMap().get(code);
        return result != null ? (ComarSellKite) result.first() : null;
    }

    public static void delete(ComarSell p) {
        JTransaction jtx = JTransaction.getCurrent();
        ObjId oid = new ObjId(p.getId());
        JObject pp = jtx.get(oid);
        jtx.delete(pp);
    }

    public static NavigableSet<ComarSellKite> search(final String text) {
        if (text == null) {
            return new TreeSet<>();
        }

        String ttext = text.trim();
        NavigableSet<ComarSellKite> all = ComarSellKite.getAll();
        if (!ttext.isEmpty()) {
            Pattern pattern = Pattern.compile(".*" + ttext + ".*");
            Predicate<ComarSellKite> filter = e -> pattern.matcher(e.getCode()).matches();
            Stream<ComarSellKite> stream = all.stream().filter(filter);
            return stream.collect(Collectors.toCollection(TreeSet::new));
        } else {
            return all;
        }

    }
}
