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
import cl.rworks.comar.core.model.MetricaEntity;
import cl.rworks.comar.core.model.ProductoEntity;

/**
 *
 * @author aplik
 */
public class WorkspaceCreator {

    public Workspace create(ComarService service) {
        Workspace ws = new Workspace();
        checkMetrics(service);

        List<CategoryModel> categoryNodes = loadCategoryNodes(service);
        ws.setCategoryNodes(categoryNodes);

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

    private List<CategoryModel> loadCategoryNodes(ComarService service) {
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

        List<CategoryModel> categoryNodes = new ArrayList<>();
        Map<String, CategoryModel> index = new HashMap<>();
        for (CategoriaEntity centity : centities) {
            CategoryModel cnode = new CategoryModel(centity);
            categoryNodes.add(cnode);
            index.put(UUIDUtils.toString(centity.getId()), cnode);
        }

        for (ProductoEntity pentity : pentities) {
            if (pentity.getCategoriaId() == null) {
                throw new RuntimeException("Producto sin categoria: " + pentity);
            }
            
            CategoryModel cnode = index.get(UUIDUtils.toString(pentity.getCategoriaId()));
            ProductModel pmodel = new ProductModel(pentity);
            cnode.addProduct(pmodel);
        }
        
        return categoryNodes;
    }
}
