/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.bills;

import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.service.ComarTransaction;
import cl.rworks.comar.swing.main.ComarSystem;
import cl.rworks.comar.swing.model.ComarBill;
import cl.rworks.comar.swing.model.ComarBillUnit;
import cl.rworks.comar.swing.model.ComarControllerException;
import cl.rworks.comar.swing.model.ComarProduct;
import cl.rworks.comar.swing.model.ComarEntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author aplik
 */
public class ComarPanelBillsController {

    private static final Logger LOG = LoggerFactory.getLogger(ComarPanelBillsController.class);
    private ComarEntityManager ws;

    public ComarPanelBillsController() {
        ws = ComarSystem.getInstance().getEntityManager();
    }

    public List<ComarBill> searchBillsByDate(int[] value) {
        int year = value[0];
        int month = value[1];
        int day = value[2];

        ComarEntityManager ws = ComarSystem.getInstance().getEntityManager();
        List<ComarBill> bills = ws.getBills();

        List<ComarBill> list = bills.stream()
                .filter(e -> year != -1 ? e.getEntity().getFecha().getYear() == year : true)
                .filter(e -> month != -1 ? e.getEntity().getFecha().getMonthValue() == month : true)
                .filter(e -> day != -1 ? e.getEntity().getFecha().getDayOfMonth() == day : true)
                .collect(Collectors.toList());

        return list;
    }

    public List<ComarBill> searchBillsByCode(String text) {
        ComarEntityManager ws = ComarSystem.getInstance().getEntityManager();
        List<ComarBill> bills = ws.getBills();

        Pattern pattern = Pattern.compile(".*" + text + ".*");
        List<ComarBill> list = bills.stream()
                .filter(e -> pattern.matcher(e.getEntity().getCodigo()).matches())
                .collect(Collectors.toList());

        return list;
    }

    public void addBill(ComarBill bill) throws ComarControllerException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.insertFactura(bill.getEntity());
            tx.commit();

            ComarEntityManager ws = ComarSystem.getInstance().getEntityManager();
            ws.addBill(bill);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException("Error al ingresar factura", ex);
        }
    }

    public void updateBill(ComarBill bill) throws ComarControllerException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.updateFactura(bill.getEntity());
            tx.commit();
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException("Error: " + ex.getMessage(), ex);
        }
    }

    public void deleteBills(List<ComarBill> bills) throws ComarControllerException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.deleteFacturas(bills.stream().map(e -> e.getEntity()).collect(Collectors.toList()));
            tx.commit();
            ws.removeBills(bills);

            for (ComarBill bill : bills) {
                for (ComarBillUnit unit : bill.getUnits()) {
                    BigDecimal total = getTotal(unit.getProduct().getEntity().getCodigo());
                    service.updateProductoPropiedad(unit.getProduct().getEntity(), "STOCKCOMPRADO", total);
                    tx.commit();
                    unit.getProduct().getEntity().setStockComprado(total);
                }
            }

        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException("Error: " + ex.getMessage(), ex);
        }
    }

    public ComarProduct getProduct(String code) {
        ComarEntityManager ws = ComarSystem.getInstance().getEntityManager();
        return ws.getProductByCode(code);
    }

    public void addBillUnit(ComarBillUnit unit) throws ComarControllerException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.insertFacturaUnidad(unit.getEntity(), unit.getBill().getEntity(), unit.getProduct().getEntity());
            tx.commit();
            ws.addBillUnit(unit);

//            int total = getTotal(unit.getProduct().getEntity().getCodigo());
//            service.updateProductoPropiedad(unit.getProduct().getEntity(), "STOCKCOMPRADO", new BigDecimal(total));
//            tx.commit();
//            unit.getProduct().getEntity().setStockComprado(new BigDecimal(total));
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException("Error: " + ex.getMessage(), ex);
        }
    }

    public void deleteBillUnits(List<ComarBillUnit> units, ComarBill selectedBill) throws ComarControllerException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.deleteFacturaUnidades(units.stream().map(e -> e.getEntity()).collect(Collectors.toList()));
            tx.commit();
            selectedBill.removeUnits(units);

            for (ComarBillUnit billUnit : units) {
                BigDecimal total = getTotal(billUnit.getProduct().getEntity().getCodigo());
                service.updateProductoPropiedad(billUnit.getProduct().getEntity(), "STOCKCOMPRADO", total);
                tx.commit();
                billUnit.getProduct().getEntity().setStockComprado(total);
            }
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException("Error: " + ex.getMessage(), ex);
        }
    }

    public void updateBillUnitPrice(ComarBillUnit billUnit, BigDecimal precioCompra) throws ComarControllerException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.updateFacturaUnidadPropiedad(billUnit.getEntity(), "PRECIONETOCOMPRA", precioCompra);
            tx.commit();

            billUnit.getEntity().setPrecioNetoCompra(precioCompra);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException("Error: " + ex.getMessage(), ex);
        }
    }

    public void updateBillUnitQuatity(ComarBillUnit billUnit, BigDecimal cantidad) throws ComarControllerException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.updateFacturaUnidadPropiedad(billUnit.getEntity(), "CANTIDAD", cantidad);
            billUnit.getEntity().setCantidad(cantidad);

            BigDecimal total = getTotal(billUnit.getProduct().getEntity().getCodigo());
            service.updateProductoPropiedad(billUnit.getProduct().getEntity(), "STOCKCOMPRADO", total);
            tx.commit();

            billUnit.getProduct().getEntity().setStockComprado(total);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException("Error: " + ex.getMessage(), ex);
        }
    }

    private BigDecimal getTotal(String pcode) {
        BigDecimal total = BigDecimal.ZERO;
        List<ComarBill> bills = ws.getBills();
        for (ComarBill bill : bills) {
            List<ComarBillUnit> units = bill.getUnits();
            for (ComarBillUnit unit : units) {
                if (unit.getProduct().getEntity().getCodigo().equals(pcode)) {
                    total = total.add(unit.getEntity().getCantidad());
                }
            }
        }
        return total;
    }

}
