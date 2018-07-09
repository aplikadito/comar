/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.model;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.service.ComarTransaction;
import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.model.ComarMetric;
import cl.rworks.comar.core.model.ComarMetricObject;
import cl.rworks.comar.core.model.ComarProduct;
import java.util.List;
import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.core.util.UUIDUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aplik
 */
public class ComarWorkspaceCreator {

    public ComarWorkspace create(ComarService service) {
        ComarWorkspace ws = new ComarWorkspace();
        checkMetrics(service);
        
        ComarInventoryNodeRoot inventoryRootNode = createInventory(service);
        ws.setInventoryRootNode(inventoryRootNode);
        
        return ws;
    }

    private void checkMetrics(ComarService service) {
        try (ComarTransaction tx = service.createTransaction()) {
            try {
                List<ComarMetricObject> metrics = service.getAllMetrics();
                ComarMetric[] values = ComarMetric.values();
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
    
    private ComarInventoryNodeRoot createInventory (ComarService service){
        List<ComarCategory> categories = new ArrayList<>();
        List<ComarProduct> products = new ArrayList<>();
        try (ComarTransaction tx = service.createTransaction()) {
            try {
                categories = service.getAllCategories();
                products = service.getAllProducts();
            } catch (ComarServiceException e) {
                e.printStackTrace();
            } finally {
                tx.rollback();
            }
        } catch (ComarServiceException e) {
            e.printStackTrace();
        }


        ComarInventoryNodeRoot irootNode = new ComarInventoryNodeRoot();
        Map<String, ComarInventoryNodeCategory> index = new HashMap<>();
        for (ComarCategory cmodel : categories) {
//                System.out.println(cmodel);
            ComarInventoryNodeCategory cnode = new ComarInventoryNodeCategory(cmodel);
            irootNode.addCategoryNode(cnode);

            index.put(UUIDUtils.toString(cmodel.getId()), cnode);
        }

        for (ComarProduct pmodel : products) {
//                System.out.println(pmodel);
            if (pmodel.getCategoryId() == null) {
                throw new RuntimeException("Producto sin categoria: " + pmodel);
            }

            ComarInventoryNodeCategory cnode = index.get(UUIDUtils.toString(pmodel.getCategoryId()));
            ComarInventoryNodeProduct pnode = new ComarInventoryNodeProduct(pmodel);
            cnode.addProductNode(pnode);
        }
        
        return irootNode;
    }
}
