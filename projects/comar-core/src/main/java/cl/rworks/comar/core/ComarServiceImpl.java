/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import cl.rworks.kite.KiteDb;
import io.permazen.core.ObjId;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rgonzalez
 */
public class ComarServiceImpl implements ComarService {

    private static final Logger LOG = LoggerFactory.getLogger(ComarServiceImpl.class);

    private final Class<?>[] classes = new Class<?>[]{
        ComarProductKite.class,
        ComarStockKite.class,
        ComarSellKite.class,
        ComarCategoryKite.class
    };

    private KiteDb database;

    public ComarServiceImpl() {
        this("storage");
    }

    public ComarServiceImpl(String name) {
        this.database = name == null || name.isEmpty() ? new KiteDb(classes) : new KiteDb(name, classes);
    }

    public KiteDb getDatabase() {
        return database;
    }

    public ComarProduct createProduct() throws ComarServiceException {
        Object response = database.execute(jtx -> {

            ComarProductKite p = ComarProductKite.create();
            ComarProductKite pp = (ComarProductKite) p.copyOut();

            jtx.rollback();
            return pp;
        });
        return (ComarProduct) response;
    }

    public void insertProduct(final ComarProduct aux) throws ComarServiceException {
        database.execute(jtx -> {

            ComarProductKite persistence = ComarProductKite.create();
            persistence.setId(persistence.getObjId().asLong());
            persistence.setCode(aux.getCode());
            persistence.setName(aux.getName());
            persistence.setDecimalFormat(aux.getDecimalFormat());
            persistence.setUnit(aux.getUnit());

            aux.setId(persistence.getId());

            jtx.commit();
            LOG.info("Producto agregado: [{}, {}]", persistence.getCode(), persistence.getName());
            return null;
        });
    }

    public List<ComarProduct> getAllProducts() throws ComarServiceException {
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

    public ComarProduct getByIdProduct(final Long id) throws ComarServiceException {
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

    @Override
    public ComarProduct getByIdCode(String code) throws ComarServiceException {
        return (ComarProduct) database.execute(jtx -> {
            NavigableSet<ComarProduct> result = jtx.queryIndex(ComarProduct.class, "code", String.class).asMap().get("code");
            ComarProductKite p = result != null ? (ComarProductKite) result.first() : null;
            ComarProductKite pp = p != null ? (ComarProductKite) p.copyOut() : null;
            jtx.rollback();
            return pp;
        });

    }

}
