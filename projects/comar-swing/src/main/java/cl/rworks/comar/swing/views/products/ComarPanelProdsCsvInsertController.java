/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.products;

import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.core.model.ProductoEntity;
import cl.rworks.comar.core.model.impl.CategoriaEntityImpl;
import cl.rworks.comar.core.model.impl.ProductoEntityImpl;
import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.service.ComarTransaction;
import cl.rworks.comar.core.util.ComarCharset;
import cl.rworks.comar.swing.main.ComarSystem;
import cl.rworks.comar.swing.model.ComarCategory;
import cl.rworks.comar.swing.model.ComarControllerException;
import cl.rworks.comar.swing.model.ComarProduct;
import cl.rworks.comar.swing.model.ComarEntityManager;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author aplik
 */
public class ComarPanelProdsCsvInsertController {

    private static final Logger LOG = LoggerFactory.getLogger(ComarPanelProdsCsvInsertController.class);

    public Object[] load(File selectedFile, ComarCharset charset) throws ComarControllerException {
        ComarEntityManager em = ComarSystem.getInstance().getEntityManager();
        ComarService service = ComarSystem.getInstance().getService();

        List<CsvRow> rows = readCsvFile(selectedFile, charset);
        List<ComarCategory> catToAdd = new ArrayList<>();
        Map<String, ComarProduct> prodToAdd = new HashMap<>();
        for (CsvRow row : rows) {
            String catName = row.getCategoria();
            ComarCategory cat = em.getCategoryByName(catName);
            if (cat == null) {
                cat = new ComarCategory(CategoriaEntityImpl.create(catName));
                catToAdd.add(cat);
            }

            ComarProduct p = em.getProductByCode(row.getCode());
            if (p == null) {
                if (prodToAdd.containsKey(row.getCode())) {
                    throw new ComarControllerException("El archivo csv contiene codigos de producto duplicados. Modifique el archivo y vuelva a realizar el proceso");
                }

                ProductoEntity e = ProductoEntityImpl.create(row.getCode(), row.getDescription(), row.getPrecio());
                prodToAdd.put(row.getCode(), new ComarProduct(e, cat));
            }
        }

        // INSERCION CATEGORIAS QUE NO EXISTEN
        if (catToAdd.size() > 0) {
            try (ComarTransaction tx = service.createTransaction()) {
                for (ComarCategory cat : catToAdd) {
                    service.insertCategoria(cat.getEntity());
                }
                tx.commit();

                for (ComarCategory c : catToAdd) {
                    em.addCategory(c);
                }
            } catch (ComarServiceException ex) {
                LOG.error("Error 'insertProducts'", ex);
                throw new ComarControllerException(ex.getMessage());
            }
        }

        // INSERCION PRODUCTOS QUE NO EXISTEN
        if (prodToAdd.size() > 0) {
            Map<ComarCategory, List<ComarProduct>> map = new HashMap<>();
            for (ComarProduct p : prodToAdd.values()) {
                ComarCategory category = p.getCategory();
                List<ComarProduct> list = map.get(category);
                if (list == null) {
                    list = new ArrayList<>();
                    map.put(category, list);
                }
                list.add(p);
            }

            try (ComarTransaction tx = service.createTransaction()) {
                Set<Map.Entry<ComarCategory, List<ComarProduct>>> entrySet = map.entrySet();
                for (Map.Entry<ComarCategory, List<ComarProduct>> e : entrySet) {
                    ComarCategory cat = e.getKey();
                    List<ComarProduct> list = e.getValue();
                    List<ProductoEntity> entities = list.stream().map(x -> x.getEntity()).collect(Collectors.toList());
                    service.insertProductoBatch(entities, cat.getEntity());
                }
                tx.commit();

                for (ComarProduct p : prodToAdd.values()) {
                    em.addProduct(p);
                }
            } catch (ComarServiceException ex) {
                LOG.error("Error 'insertProducts'", ex);
                throw new ComarControllerException(ex.getMessage());
            }
        }

        return new Object[]{catToAdd, new ArrayList<>(prodToAdd.values())};
    }

    public List<CsvRow> readCsvFile(File selectedFile, ComarCharset charset) throws ComarControllerException {
        try (Stream<String> lines = Files.lines(Paths.get(selectedFile.getAbsolutePath()), Charset.forName(charset.getName()));) {
            List<CsvRow> list = new ArrayList<>();
            lines.skip(1).forEach((String e) -> {
                String[] split = e.split(";");
                String code = "";
                String description = "";
                BigDecimal precio = BigDecimal.ZERO;
                String categoria = "Varios";

                if (split.length == 2) {
                    code = split[0].trim();
                    description = split[1].trim();
                } else if (split.length == 4) {
                    code = split[0].trim();
                    description = split[1].trim();
                    precio = !split[2].trim().isEmpty() ? new BigDecimal(split[2].trim()) : BigDecimal.ZERO;
                    categoria = !split[3].trim().isEmpty() ? split[3].trim() : "Varios";
                }

                list.add(new CsvRow(code, description, precio, categoria));
            });
            return list;
        } catch (java.io.UncheckedIOException ex) {
            LOG.error("Error java.io.UncheckedIOException 'loadFile'. Posible error de codificacion.");
            throw new ComarControllerException("Error al leer el archivo CSV. Verifique la codificacion seleccionada.");
        } catch (IOException ex) {
            LOG.error("Error IOException 'loadFile'", ex);
            throw new ComarControllerException("Error al leer el archivo CSV. Verifique el tipo de archivo y su formato");
        }
    }

    public void insertCategories(List<CsvRow> rows) throws ComarControllerException {
        ComarEntityManager em = ComarSystem.getInstance().getEntityManager();

    }

//    public Object[] checkCsvProducts(List<ComarProduct> csvProducts) {
//        ComarEntityManager ws = ComarSystem.getInstance().getEntityManager();
//        List<ComarCategory> categories = ws.getCategories();
//        Map<String, ComarProduct> index = new HashMap<>();
//        categories.stream().flatMap(e -> e.getProducts().stream()).forEach(e -> index.put(e.getEntity().getCodigo(), e));
//
//        List<ComarProduct> existsYes = new ArrayList<>();
//        List<ComarProduct> existsNo = new ArrayList<>();
//        for (ComarProduct csvp : csvProducts) {
//            if (index.containsKey(csvp.getEntity().getCodigo())) {
//                existsYes.add(csvp);
//            } else {
//                existsNo.add(csvp);
//            }
//        }
//
//        return new Object[]{existsYes, existsNo};
//    }
//    public void insertProducts(List<CsvRow> rows) throws ComarControllerException {
//
//        ComarService service = ComarSystem.getInstance().getService();
//        try (ComarTransaction tx = service.createTransaction()) {
//
//            service.insertProductoBatch(existsNo.stream().map(e -> e.getEntity()).collect(Collectors.toList()), category.getEntity());
//            tx.commit();
//
//            existsNo.stream().forEach(e -> {
//                e.setCategory(category);
//                ComarSystem.getInstance().getEntityManager().addProduct(e);
//            });
//        } catch (ComarServiceException ex) {
//            LOG.error("Error 'insertProducts'", ex);
//            throw new ComarControllerException(ex.getMessage());
//        }
//    }
//    public List<ComarCategory> getCategories() {
//        ComarEntityManager ws = ComarSystem.getInstance().getEntityManager();
//        return ws.getCategories();
//    }
//
//    public ComarCategory getDefaultCategory() {
//        List<ComarCategory> cats = getCategories();
//        for (ComarCategory cat : cats) {
//            if (cat.getEntity().getNombre().equals(CategoriaEntity.DEFAULT_CATEGORY)) {
//                return cat;
//            }
//        }
//        throw new RuntimeException("Categoria '" + CategoriaEntity.DEFAULT_CATEGORY + "' no encontrada");
//
//    }
}
