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
import cl.rworks.comar.swing.model.ComarControllerException;
import cl.rworks.comar.swing.model.ComarWorkspace;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author aplik
 */
public class ComarPanelBillsController {

    private static final Logger LOG = LoggerFactory.getLogger(ComarPanelBillsController.class);

    public List<ComarBill> searchBills(int[] value) {
        int year = value[0];
        int month = value[1];
        int day = value[2];

        ComarWorkspace ws = ComarSystem.getInstance().getWorkspace();
        List<ComarBill> bills = ws.getBills();

        List<ComarBill> list = bills.stream()
                .filter(e -> year != -1 ? e.getEntity().getFecha().getYear() == year : true)
                .filter(e -> month != -1 ? e.getEntity().getFecha().getMonthValue() == month : true)
                .filter(e -> day != -1 ? e.getEntity().getFecha().getDayOfMonth() == day : true)
                .collect(Collectors.toList());

        return list;
    }

    public void addBill(ComarBill bill) throws ComarControllerException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.insertFactura(bill.getEntity());
            tx.commit();

            ComarWorkspace ws = ComarSystem.getInstance().getWorkspace();
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
            throw new ComarControllerException("Error", ex);
        }
    }

    public void deleteBills(List<ComarBill> bills) throws ComarControllerException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.deleteFacturas(bills.stream().map(e -> e.getEntity()).collect(Collectors.toList()));
            tx.commit();

            ComarWorkspace ws = ComarSystem.getInstance().getWorkspace();
            ws.deleteBills(bills);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException("Error", ex);
        }
    }

}
