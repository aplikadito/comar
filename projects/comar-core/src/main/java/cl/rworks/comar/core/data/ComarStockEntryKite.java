/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.data;

import cl.rworks.comar.core.model.ComarProduct;
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
import cl.rworks.comar.core.model.ComarStockEntry;
import cl.rworks.comar.core.service.ComarServiceException;

/**
 *
 * @author rgonzalez
 */
@PermazenType
public interface ComarStockEntryKite extends JObject, ComarStockEntry {

    @JField(indexed = true)
    default Long getId() {
        return getObjId() != null ? getObjId().asLong() : null;
    }

    void setId(Long id);

    @JField(indexed = true)
    String getCode();

    void setCode(String code);
    
    public static ComarStockEntryKite create() {
        JTransaction jtx = JTransaction.getCurrent();
        return jtx.create(ComarStockEntryKite.class);
    }

    public static NavigableSet<ComarStockEntryKite> getAll() {
        JTransaction jtx = JTransaction.getCurrent();
        return jtx.getAll(ComarStockEntryKite.class);
    }

    public static ComarStockEntryKite get(Long id) {
        JTransaction jtx = JTransaction.getCurrent();
        ObjId oid = new ObjId(id);
        return (ComarStockEntryKite) jtx.get(oid);
    }

    public static ComarStockEntryKite getByCode(String code) {
        JTransaction jtx = JTransaction.getCurrent();
        NavigableSet<ComarCategoryKite> result = jtx.queryIndex(ComarCategoryKite.class, "name", String.class).asMap().get(code);
        return result != null ? (ComarStockEntryKite) result.first() : null;
    }
    
    public static void delete(ComarStockEntry p) {
        JTransaction jtx = JTransaction.getCurrent();
        ObjId oid = new ObjId(p.getId());
        JObject pp = jtx.get(oid);
        jtx.delete(pp);
    }

    public static NavigableSet<ComarStockEntryKite> search(final String text) {
        if (text == null) {
            return new TreeSet<>();
        }

        JTransaction jtx = JTransaction.getCurrent();
        String ttext = text.trim();
        NavigableSet<ComarStockEntryKite> all = jtx.getAll(ComarStockEntryKite.class);
        if (!ttext.isEmpty()) {
            Pattern pattern = Pattern.compile(".*" + ttext + ".*");
            Predicate<ComarStockEntryKite> filterName = e -> pattern.matcher(e.getCode()).matches();
            Stream<ComarStockEntryKite> stream = all.stream().filter(filterName);
            return stream.collect(Collectors.toCollection(TreeSet::new));
        } else {
            return all;
        }

    }
}
