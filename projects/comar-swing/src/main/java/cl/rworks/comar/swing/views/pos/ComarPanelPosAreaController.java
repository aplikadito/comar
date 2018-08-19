/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.pos;

import cl.rworks.comar.core.model.VentaEntity;
import cl.rworks.comar.core.model.VentaUnidadEntity;
import cl.rworks.comar.core.model.impl.VentaEntityImpl;
import cl.rworks.comar.core.model.impl.VentaUnidadEntityImpl;
import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.service.ComarTransaction;
import cl.rworks.comar.swing.main.ComarSystem;
import cl.rworks.comar.swing.model.ComarControllerException;
import cl.rworks.comar.swing.model.ComarEntityManager;
import cl.rworks.comar.swing.model.ComarProduct;
import cl.rworks.comar.swing.model.ComarSell;
import cl.rworks.comar.swing.model.ComarSellUnit;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author aplik
 */
public class ComarPanelPosAreaController {

    private static final Logger LOG = LoggerFactory.getLogger(ComarPanelPosAreaController.class);
    private ComarEntityManager em;

    public ComarPanelPosAreaController() {
        em = ComarSystem.getInstance().getEntityManager();
    }

    public ComarPanelPosRow getProduct(String code) throws ComarServiceException {
        ComarProduct product = em.getProductByCode(code);
        if (product != null) {
            ComarPanelPosRow row = new ComarPanelPosRow(product);
            row.setPrice(product.getEntity().getPrecioVentaActual());
            row.setCount(BigDecimal.ONE);
            return row;
        } else {
            throw new ComarServiceException("Producto no encontrado: '" + code + "'");
        }

    }

    public void addSell(String strCodigo, LocalDate time, List<ComarPanelPosRow> rows) throws ComarControllerException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {

            VentaEntity venta = VentaEntityImpl.create(strCodigo, LocalDate.now());
            ComarSell sell = new ComarSell(venta);
            service.insertVenta(sell.getEntity());
            em.addSell(sell);

            for (ComarPanelPosRow row : rows) {
                ComarProduct product = row.getProduct();
                BigDecimal price = row.getPrice();
                BigDecimal count = row.getCount();
//                BigDecimal subtotal = price.multiply(count);
//                totalEnVoleta = totalEnVoleta.add(subtotal);

                VentaUnidadEntity unidad = VentaUnidadEntityImpl.create(venta, product.getEntity(), price, count);
                service.insertVentaUnidad(unidad, sell.getEntity(), product.getEntity());
                em.addSellUnit(new ComarSellUnit(unidad, sell, product));
            }

            tx.commit();
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException("Error: " + ex.getMessage(), ex);
        }
    }
}
