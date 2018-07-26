/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.model;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.service.ComarTransaction;
import cl.rworks.comar.core.model.Metrica;
import java.util.List;
import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.core.util.UUIDUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.core.model.FacturaEntity;
import cl.rworks.comar.core.model.FacturaUnidadEntity;
import cl.rworks.comar.core.model.MetricaEntity;
import cl.rworks.comar.core.model.ProductoEntity;

/**
 *
 * @author aplik
 */
public class ComarWorkspaceCreator {

    public ComarWorkspace create(ComarService service) {
        ComarWorkspace ws = new ComarWorkspace();
        checkMetrics(service);

        ws.setCategories(loadCategories(service));
        ws.setBills(loadBills(service));

        return ws;
    }

    private void checkMetrics(ComarService service) {
        try (ComarTransaction tx = service.createTransaction()) {
            try {
                List<MetricaEntity> metrics = service.getAllMetrics();
                Metrica[] values = Metrica.values();
                if (values.length != metrics.size()) {
                    throw new RuntimeException(String.format("La cantidad de metricas no coinciden: enum=%s vs bd=%s", values.length, metrics.size()));
                }
            } catch (ComarServiceException e) {
                e.printStackTrace();
            } finally {
                tx.rollback();
            }
        } catch (ComarServiceException e) {
            e.printStackTrace();
        }
    }

    private List<ComarCategory> loadCategories(ComarService service) {
        List<CategoriaEntity> centities = new ArrayList<>();
        List<ProductoEntity> pentities = new ArrayList<>();
        try (ComarTransaction tx = service.createTransaction()) {
            try {
                centities = service.getAllCategorias();
                pentities = service.getAllProductos();
            } catch (ComarServiceException e) {
                e.printStackTrace();
            } finally {
                tx.rollback();
            }
        } catch (ComarServiceException e) {
            e.printStackTrace();
        }

        List<ComarCategory> categoryNodes = new ArrayList<>();
        Map<String, ComarCategory> index = new HashMap<>();
        for (CategoriaEntity centity : centities) {
            ComarCategory cnode = new ComarCategory(centity);
            categoryNodes.add(cnode);
            index.put(UUIDUtils.toString(centity.getId()), cnode);
        }

        for (ProductoEntity pentity : pentities) {
            if (pentity.getCategoriaId() == null) {
                throw new RuntimeException("Producto sin categoria: " + pentity);
            }

            ComarCategory cnode = index.get(UUIDUtils.toString(pentity.getCategoriaId()));
            ComarProduct pmodel = new ComarProduct(pentity);
            cnode.addProduct(pmodel);
        }

        return categoryNodes;
    }

    private List<ComarBill> loadBills(ComarService service) {
        List<FacturaEntity> ebills = new ArrayList<>();
        List<FacturaUnidadEntity> ebillUnits = new ArrayList<>();
        try (ComarTransaction tx = service.createTransaction()) {
            try {
                ebills = service.getAllFactura();
                ebillUnits = service.getAllFacturaUnidad();
            } catch (ComarServiceException e) {
                e.printStackTrace();
            } finally {
                tx.rollback();
            }
        } catch (ComarServiceException e) {
            e.printStackTrace();
        }

        List<ComarBill> bills = new ArrayList<>();
        Map<String, ComarBill> index = new HashMap<>();
        for (FacturaEntity e : ebills) {
            ComarBill bill = new ComarBill(e);
            bills.add(bill);
            index.put(UUIDUtils.toString(e.getId()), bill);
        }

        for (FacturaUnidadEntity e : ebillUnits) {
            if (e.getIdFactura() == null) {
                throw new RuntimeException("Elemento de factura sin factura asignada: " + e);
            }

            ComarBill bill = index.get(UUIDUtils.toString(e.getIdFactura()));
            ComarBillUnit unit = new ComarBillUnit(e);
            bill.addUnit(unit);
        }

        return bills;
    }
}
