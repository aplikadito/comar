/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.controller.permazen;

import cl.rworks.comar.core.controller.permazen.service.ComarSellPermazen;
import cl.rworks.comar.core.controller.permazen.service.ComarProductHistorialPermazen;
import cl.rworks.comar.core.controller.permazen.service.ComarProductPermazen;
import cl.rworks.comar.core.model.ComarProduct;
import java.util.List;
import io.permazen.JTransaction;
import io.permazen.Permazen;
import io.permazen.ValidationMode;
import java.util.ArrayList;
import cl.rworks.comar.core.controller.ComarController;
import cl.rworks.comar.core.controller.ComarControllerException;
import cl.rworks.comar.core.model.ComarMetric;
import cl.rworks.comar.core.model.ComarProductHistorial;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 *
 * @author aplik
 */
public class ComarControllerPermazen implements ComarController {

    private Permazen dataSource;

    private Class[] modelClasses = new Class[]{
        ComarProductPermazen.class,
        ComarProductHistorialPermazen.class,
        ComarSellPermazen.class
    };

    public ComarControllerPermazen() {
    }

    @Override
    public void startup(int dbOption) {
        switch (dbOption) {
            case MEMORY:
                dataSource = ComarControllerPermazenUtils.createPermazen(ComarControllerPermazenUtils.createOnMemoryDatabase(), modelClasses);
                break;
            case DISK:
                dataSource = ComarControllerPermazenUtils.createPermazen(ComarControllerPermazenUtils.createDerbyDatabase("storage"), modelClasses);
                break;
            case SERVER:
//                dataSource = KiteUtils.createPermazen(KiteUtils.createMysqlDatabase(null), modelClasses);
//                break;
                throw new RuntimeException("opcion no soportada aun");
            default:
                throw new RuntimeException("opcion no soportada: " + dataSource);
        }
    }

    @Override
    public void createTransaction() {
        JTransaction jtx = dataSource.createTransaction(true, ValidationMode.AUTOMATIC);
        JTransaction.setCurrent(jtx);
    }

    @Override
    public void commit() {
        JTransaction.getCurrent().commit();
    }

    @Override
    public void rollback() {
        JTransaction.getCurrent().rollback();
    }

    @Override
    public void endTransaction() {
        JTransaction.setCurrent(null);
    }

    @Override
    public List<ComarProduct> loadProducts(String str) {
        List<ComarProduct> rows = new ArrayList<>();
        if (str.isEmpty()) {
            ComarProductPermazen.getAll().stream().forEach((ComarProductPermazen p) -> {
                rows.add((ComarProduct) p.copyOut());
            });
        } else {
            ComarProductPermazen.search(str).stream().forEach((ComarProductPermazen p) -> {
                rows.add((ComarProduct) p.copyOut());
            });
        }
        return rows;
    }

    @Override
    public ComarProduct getProductByCode(String code) throws ComarControllerException {
        return ComarProductPermazen.getByCode(code);
    }

    @Override
    public void addProduct(ComarProduct product) {
        String code = product.getCode();
        String description = product.getDescription();
        ComarMetric metric = product.getMetric();
        BigDecimal buyPrice = product.getBuyPrice();
        BigDecimal sellPrice = product.getSellPrice();
        BigDecimal stock = product.getStock();
        BigDecimal tax = product.getTax();

        ComarProduct dbProduct = ComarProductPermazen.create();
        dbProduct.setCode(code);
        dbProduct.setDescription(description);
        dbProduct.setMetric(metric);
        dbProduct.setBuyPrice(buyPrice);
        dbProduct.setSellPrice(sellPrice);
        dbProduct.setStock(stock);
        dbProduct.setTax(tax);

//        LocalDateTime now = LocalDateTime.now();
//        ComarProductHistorialPermazen.create(code, now, "NEW", ComarProductProperties.CODE.name(), "", code);
//        ComarProductHistorialPermazen.create(code, now, "NEW", ComarProductProperties.DESCRIPTION.name(), "", description);
//        ComarProductHistorialPermazen.create(code, now, "NEW", ComarProductProperties.METRIC.name(), "", metric.name());
//        ComarProductHistorialPermazen.create(code, now, "NEW", ComarProductProperties.BUYPRICE.name(), "", ComarNumberFormat.formatDbl(buyPrice));
//        ComarProductHistorialPermazen.create(code, now, "NEW", ComarProductProperties.SELLPRICE.name(), "", ComarNumberFormat.formatDbl(sellPrice));
//        ComarProductHistorialPermazen.create(code, now, "NEW", ComarProductProperties.STOCK.name(), "", ComarNumberFormat.formatDbl(stock));
    }

    @Override
    public void updateProduct(String oldCode, LocalDateTime now, String action, ComarProduct changes) throws ComarControllerException {
        ComarProduct dbProduct = ComarProductPermazen.getByCode(oldCode);
        if (dbProduct == null) {
            throw new ComarControllerException("Codigo del producto no encontrado: " + oldCode);
        }

//        if (!oldCode.equals(changes.getCode())) {
//            dbProduct.setCode(changes.getCode());
//            ComarProductHistorialPermazen.create(dbProduct.getCode(), now, action, ComarProductProperties.CODE.name(), oldCode, changes.getCode());
//        }
//
//        if (!dbProduct.getDescription().equals(changes.getDescription())) {
//            dbProduct.setDescription(changes.getDescription());
//            ComarProductHistorialPermazen.create(oldCode, now, action, ComarProductProperties.DESCRIPTION.name(), dbProduct.getDescription(), changes.getDescription());
//        }
//
//        if (!dbProduct.getMetric().equals(changes.getMetric())) {
//            dbProduct.setMetric(changes.getMetric());
//            ComarProductHistorialPermazen.create(oldCode, now, action, ComarProductProperties.METRIC.name(), dbProduct.getMetric().name(), changes.getMetric().name());
//        }
//
//        if (dbProduct.getBuyPrice() != changes.getBuyPrice()) {
//            dbProduct.setBuyPrice(changes.getBuyPrice());
//            ComarProductHistorialPermazen.create(oldCode, now, action, ComarProductProperties.BUYPRICE.name(), ComarNumberFormat.formatDbl(dbProduct.getBuyPrice()), ComarNumberFormat.formatDbl(changes.getBuyPrice()));
//        }
//
//        if (dbProduct.getSellPrice() != changes.getSellPrice()) {
//            dbProduct.setSellPrice(changes.getSellPrice());
//            ComarProductHistorialPermazen.create(oldCode, now, action, ComarProductProperties.SELLPRICE.name(), ComarNumberFormat.formatDbl(dbProduct.getSellPrice()), ComarNumberFormat.formatDbl(changes.getSellPrice()));
//        }
//
//        if (dbProduct.getStock() != changes.getStock()) {
//            dbProduct.setStock(changes.getStock());
//            ComarProductHistorialPermazen.create(oldCode, now, action, ComarProductProperties.STOCK.name(), ComarNumberFormat.formatDbl(dbProduct.getStock()), ComarNumberFormat.formatDbl(changes.getStock()));
//        }
    }

    public boolean validateCode(String code) throws ComarControllerException {
        try {
            return ComarProductPermazen.getByCode(code) == null;
        } catch (Exception ex) {
            throw new ComarControllerException("Error", ex);
        }
    }

    @Override
    public List<ComarProductHistorial> searchProductHistorial(String text) {
        return ComarProductHistorialPermazen.getAll().stream().collect(Collectors.toList());
    }
}
