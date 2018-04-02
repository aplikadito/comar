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
public interface ComarSellDb extends JObject, ComarSell {

    @JField(indexed = true, unique = true)
    @Override
    String getCode();

    @Override
    void setCode(String code);

    public static ComarSellDb create() {
        JTransaction jtx = JTransaction.getCurrent();
        return jtx.create(ComarSellDb.class);
    }

    public static NavigableSet<ComarSellDb> getAll() {
        JTransaction jtx = JTransaction.getCurrent();
        return jtx.getAll(ComarSellDb.class);
    }

    public static void insert(ComarSellDb p) {
        ComarSellDb pp = create();
        pp.setId(pp.getObjId().asLong());
        pp.setCode(p.getCode());
        p.setId(pp.getId());
    }

    public static ComarSellDb getByCode(String code) {
        JTransaction jtx = JTransaction.getCurrent();
        NavigableSet<ComarSell> result = jtx.queryIndex(ComarSell.class, "code", String.class).asMap().get(code);
        return result != null ? (ComarSellDb) result.first() : null;
    }

    public static void delete(ComarSellDb p) {
        JTransaction jtx = JTransaction.getCurrent();
        ObjId oid = new ObjId(p.getId());
        JObject pp = jtx.get(oid);
        jtx.delete(pp);
    }

    public static NavigableSet<ComarSellDb> search(final String text) {
        if (text == null) {
            return new TreeSet<>();
        }

        String ttext = text.trim();
        NavigableSet<ComarSellDb> all = ComarSellDb.getAll();
        if (!ttext.isEmpty()) {
            Pattern pattern = Pattern.compile(".*" + ttext + ".*");
            Predicate<ComarSell> filter = e -> pattern.matcher(e.getCode()).matches();
            Stream<ComarSellDb> stream = all.stream().filter(filter);
            return stream.collect(Collectors.toCollection(TreeSet::new));
        } else {
            return all;
        }

    }
}
