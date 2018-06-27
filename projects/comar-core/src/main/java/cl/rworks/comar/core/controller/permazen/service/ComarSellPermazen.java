/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.controller.permazen.service;

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
public interface ComarSellPermazen extends JObject, ComarSell {

    @JField(indexed = true, unique = true)
    @Override
    String getCode();

    @Override
    void setCode(String code);

    public static ComarSellPermazen create() {
        JTransaction jtx = JTransaction.getCurrent();
        return jtx.create(ComarSellPermazen.class);
    }

    public static NavigableSet<ComarSellPermazen> getAll() {
        JTransaction jtx = JTransaction.getCurrent();
        return jtx.getAll(ComarSellPermazen.class);
    }

    public static void insert(ComarSellPermazen p) {
        ComarSellPermazen pp = create();
        pp.setId(pp.getObjId().asLong());
        pp.setCode(p.getCode());
        p.setId(pp.getId());
    }

    public static ComarSellPermazen getByCode(String code) {
        JTransaction jtx = JTransaction.getCurrent();
        NavigableSet<ComarSell> result = jtx.queryIndex(ComarSell.class, "code", String.class).asMap().get(code);
        return result != null ? (ComarSellPermazen) result.first() : null;
    }

    public static void delete(ComarSellPermazen p) {
        JTransaction jtx = JTransaction.getCurrent();
        ObjId oid = new ObjId(p.getId());
        JObject pp = jtx.get(oid);
        jtx.delete(pp);
    }

    public static NavigableSet<ComarSellPermazen> search(final String text) {
        if (text == null) {
            return new TreeSet<>();
        }

        String ttext = text.trim();
        NavigableSet<ComarSellPermazen> all = ComarSellPermazen.getAll();
        if (!ttext.isEmpty()) {
            Pattern pattern = Pattern.compile(".*" + ttext + ".*");
            Predicate<ComarSell> filter = e -> pattern.matcher(e.getCode()).matches();
            Stream<ComarSellPermazen> stream = all.stream().filter(filter);
            return stream.collect(Collectors.toCollection(TreeSet::new));
        } else {
            return all;
        }

    }
}
