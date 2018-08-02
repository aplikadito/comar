/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.products;

import cl.rworks.comar.core.model.impl.ProductoEntityImpl;
import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.service.ComarTransaction;
import cl.rworks.comar.core.util.ComarCharset;
import cl.rworks.comar.swing.main.ComarSystem;
import cl.rworks.comar.swing.model.ComarCategory;
import cl.rworks.comar.swing.model.ComarControllerException;
import cl.rworks.comar.swing.model.ComarProduct;
import cl.rworks.comar.swing.model.ComarWorkspace;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public List<ComarProduct> readCsvFile(File selectedFile, ComarCharset charset) throws ComarControllerException {
        try (Stream<String> lines = Files.lines(Paths.get(selectedFile.getAbsolutePath()), Charset.forName(charset.getName()));) {
            List<ComarProduct> list = new ArrayList<>();
            lines.skip(1).forEach((String e) -> {
                String[] split = e.split(";");
                if (split.length >= 2) {
                    String code = split[0].trim();
                    String description = split[1].trim();

                    ComarProduct p = new ComarProduct(ProductoEntityImpl.create(code, description));
                    list.add(p);
                }
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

    public Object[] checkCsvProducts(List<ComarProduct> csvProducts) {
        ComarWorkspace ws = ComarSystem.getInstance().getWorkspace();
        List<ComarCategory> categories = ws.getCategories();
        Map<String, ComarProduct> index = new HashMap<>();
        categories.stream().flatMap(e -> e.getProducts().stream()).forEach(e -> index.put(e.getEntity().getCodigo(), e));

        List<ComarProduct> existsYes = new ArrayList<>();
        List<ComarProduct> existsNo = new ArrayList<>();
        for (ComarProduct csvp : csvProducts) {
            if (index.containsKey(csvp.getEntity().getCodigo())) {
                existsYes.add(csvp);
            } else {
                existsNo.add(csvp);
            }
        }

        return new Object[]{existsYes, existsNo};
    }

    public void insertProducts(List<ComarProduct> existsNo, ComarCategory category) throws ComarControllerException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.insertProductosPorCsv(existsNo.stream().map(e -> e.getEntity()).collect(Collectors.toList()), category.getEntity());
            tx.commit();

            existsNo.stream().forEach(e -> category.addProduct(e));
        } catch (ComarServiceException ex) {
            LOG.error("Error 'insertProducts'", ex);
            throw new ComarControllerException(ex.getMessage());
        }
    }

    public List<ComarCategory> getCategories() {
        ComarWorkspace ws = ComarSystem.getInstance().getWorkspace();
        return ws.getCategories();
    }
}
