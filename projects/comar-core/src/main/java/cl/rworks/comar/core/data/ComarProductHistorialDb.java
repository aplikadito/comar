/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.data;

import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.model.ComarProductHistorial;
import io.permazen.JObject;
import io.permazen.JTransaction;
import io.permazen.annotation.JField;
import io.permazen.annotation.PermazenType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NavigableSet;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@PermazenType
public interface ComarProductHistorialDb extends JObject, ComarProductHistorial {

    @JField(indexed = true)
    public String getCode();

    public void setCode(String code);

    @JField(indexed = true)
    public LocalDateTime getDateTime();

    public void setDateTime(LocalDateTime now);

    public static ComarProductHistorialDb create(String code, LocalDateTime now, String action, String property, String oldValue, String newValue) {
        JTransaction jtx = JTransaction.getCurrent();
        ComarProductHistorialDb historial = jtx.create(ComarProductHistorialDb.class);
        historial.setCode(code);
        historial.setDateTime(now);
        historial.setAction(action);
        historial.setProperty(property);
        historial.setOldValue(oldValue);
        historial.setNewValue(newValue);
        return historial;
    }

    public static NavigableSet<ComarProductHistorialDb> getAll() {
        JTransaction jtx = JTransaction.getCurrent();
        return jtx.getAll(ComarProductHistorialDb.class);
    }

    public static List<ComarProductHistorialDb> search(String strValue) {
        if (strValue == null) {
            return null;
        }

        JTransaction jtx = JTransaction.getCurrent();
        NavigableSet<ComarProductHistorialDb> all = jtx.getAll(ComarProductHistorialDb.class);
        if (!strValue.isEmpty()) {
            Pattern pattern = Pattern.compile(".*" + strValue + ".*");
            Predicate<ComarProductHistorialDb> filterCode = e -> pattern.matcher(e.getCode()).matches();
//            Predicate<ComarProduct> filterName = e -> pattern.matcher(e.getName()).matches();
//            Predicate<ComarProduct> filter = e -> filterCode.test(e) || filterName.test(e);
            return all.stream().filter(filterCode).collect(Collectors.toList());
        } else {
            return all.stream().collect(Collectors.toList());
        }
    }

}
