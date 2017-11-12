/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import cl.rworks.kite.KiteDb;
import io.permazen.core.ObjId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rgonzalez
 */
public class ComarProductDao {

    private static final Logger LOG = LoggerFactory.getLogger(ComarProductDao.class);
    //
    private KiteDb database;

    ComarProductDao(KiteDb database) {
        this.database = database;
    }

    public ComarProduct create() throws ComarServiceException {
        return (ComarProduct) database.execute(jtx -> {
            ComarProductKite pp = (ComarProductKite) jtx.create(ComarProductKite.class).copyOut();
            jtx.rollback();
            return pp;
        });
    }

    void insert(final ComarProduct p) throws ComarServiceException {
        database.execute(jtx -> {
            ComarProductKite pp = jtx.create(ComarProductKite.class);
            pp.setId(pp.getObjId().asLong());
            pp.setCode(p.getCode());
            pp.setName(p.getName());
            pp.setDecimalFormat(p.getDecimalFormat());
            pp.setUnit(p.getUnit());

            p.setId(pp.getId());
            jtx.commit();
            LOG.info("Producto agregado: [{}, {}]", pp.getCode(), pp.getName());
            return null;
        });
    }

    void update(final ComarProduct p) throws ComarServiceException {
        if (p == null) {
            throw new ComarServiceException("Elemento nulo");
        }

        if (p.getId() == null) {
            throw new ComarServiceException("El elemento aun no ha sido persistido");
        }

        database.execute(jtx -> {
            ComarProductKite pp = (ComarProductKite) jtx.get(new ObjId(p.getId()));
            pp.setCode(p.getCode());
            pp.setName(p.getName());
            pp.setDecimalFormat(p.getDecimalFormat());
            pp.setUnit(p.getUnit());

            jtx.commit();
            LOG.info("Producto editado: [{}, {}]", pp.getCode(), pp.getName());
            return null;
        });
    }

    public ComarProduct get(Long id) throws ComarServiceException {
        if (id == null) {
            return null;
        }

        return (ComarProduct) database.execute(jtx -> {
            ObjId oid = new ObjId(id);
            ComarProductKite p = (ComarProductKite) jtx.get(oid);
            ComarProductKite pp = p != null ? (ComarProductKite) p.copyOut() : null;
            jtx.rollback();
            return pp;

        });
    }

    public ComarProduct getByCode(String code) throws ComarServiceException {
        return (ComarProduct) database.execute(jtx -> {
            NavigableSet<ComarProduct> result = jtx.queryIndex(ComarProduct.class, "code", String.class).asMap().get(code);
            ComarProductKite p = result != null ? (ComarProductKite) result.first() : null;
            ComarProductKite pp = p != null ? (ComarProductKite) p.copyOut() : null;
            jtx.rollback();
            return pp;
        });
    }

    public List<ComarProduct> getAll() throws ComarServiceException {
        return (List<ComarProduct>) database.execute(jtx -> {
            NavigableSet<ComarProductKite> products = jtx.getAll(ComarProductKite.class);
            List<ComarProduct> list = new ArrayList<>();
            for (ComarProductKite p : products) {
                ComarProductKite pp = (ComarProductKite) p.copyOut();
                list.add(pp);
            }

            jtx.rollback();
            return list;

        });
    }

    public Set<ComarProduct> search(final String text) throws ComarServiceException {
        return (Set<ComarProduct>) database.execute(jtx -> {
            if (text == null) {
                return Collections.EMPTY_SET;
            }

            String ttext = text.trim();
            NavigableSet<ComarProductKite> all = jtx.getAll(ComarProductKite.class);
            if (!ttext.isEmpty()) {
                Pattern pattern = Pattern.compile(".*" + ttext + ".*");
                Predicate<ComarProductKite> filterCode = e -> pattern.matcher(e.getCode()).matches();
                Predicate<ComarProductKite> filterName = e -> pattern.matcher(e.getName()).matches();
                Predicate<ComarProductKite> filter = e -> filterCode.test(e) || filterName.test(e);
                Stream<ComarProductKite> stream = all.stream().filter(filter);
                return stream.collect(Collectors.toSet());
            } else {
                return all;
            }

        });

    }

}
