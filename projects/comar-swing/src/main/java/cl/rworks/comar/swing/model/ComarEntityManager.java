/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.model;

import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.core.model.FacturaEntity;
import cl.rworks.comar.core.model.FacturaUnidadEntity;
import cl.rworks.comar.core.model.Metrica;
import cl.rworks.comar.core.model.MetricaEntity;
import cl.rworks.comar.core.model.ProductoEntity;
import cl.rworks.comar.core.model.VentaEntity;
import cl.rworks.comar.core.model.VentaUnidadEntity;
import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.service.ComarTransaction;
import cl.rworks.comar.core.util.UUIDUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aplik
 */
public class ComarEntityManager {

    private Map<String, ComarProduct> indexProduct;
    private Map<String, ComarCategory> indexCategory;
    private Map<String, ComarBill> indexBill;
    private Map<String, ComarBillUnit> indexBillUnit;
    private Map<String, ComarSell> indexSell;
    private Map<String, ComarSellUnit> indexSellUnit;
    //
    private List<ComarCategory> categories;
    private List<ComarProduct> products;
    private List<ComarBill> bills;
    private List<ComarBillUnit> billUnits;
    private List<ComarSell> sells;
    private List<ComarSellUnit> sellUnits;

    public ComarEntityManager(ComarService service) {
        checkMetrics(service);
        loadCategories(service);
        loadBills(service);
        loadSells(service);
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

    private void loadCategories(ComarService service) {
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

//        boolean foundDefaultCategory = false;

        this.categories = new ArrayList<>();
        this.products = new ArrayList<>();
        this.indexCategory = new HashMap<>();
        this.indexProduct = new HashMap<>();

        for (CategoriaEntity e : centities) {
            ComarCategory category = new ComarCategory(e);
            addCategory(category);

//            if (category.getEntity().getNombre().equals(CategoriaEntity.DEFAULT_CATEGORY)) {
//                foundDefaultCategory = true;
//            }
        }

//        if (!foundDefaultCategory) {
//            throw new RuntimeException("No se encontro categoria por defecto: " + CategoriaEntity.DEFAULT_CATEGORY);
//        }

        for (ProductoEntity e : pentities) {
            if (e.getCategoriaId() == null) {
                throw new RuntimeException("Producto sin categoria: " + e);
            }

            String idCategoria = UUIDUtils.toString(e.getCategoriaId());
            ComarCategory category = indexCategory.get(idCategoria);

            ComarProduct prod = new ComarProduct(e, category);
            addProduct(prod);
        }
    }

    private void loadBills(ComarService service) {
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

        bills = new ArrayList<>();
        billUnits = new ArrayList<>();
        indexBill = new HashMap<>();
        indexBillUnit = new HashMap<>();

        for (FacturaEntity e : ebills) {
            ComarBill bill = new ComarBill(e);
            addBill(bill);
        }

        for (FacturaUnidadEntity e : ebillUnits) {
            if (e.getIdFactura() == null) {
                throw new RuntimeException("Elemento de factura sin factura asignada: " + e);
            }

            ComarBill bill = indexBill.get(UUIDUtils.toString(e.getIdFactura()));
            ComarProduct product = indexProduct.get(UUIDUtils.toString(e.getIdProducto()));

            ComarBillUnit unit = new ComarBillUnit(e, bill, product);
            addBillUnit(unit);
        }

    }

    private void loadSells(ComarService service) {
        List<VentaEntity> eventas = new ArrayList<>();
        List<VentaUnidadEntity> eventaUnidades = new ArrayList<>();
        try (ComarTransaction tx = service.createTransaction()) {
            try {
                eventas = service.getAllVenta();
                eventaUnidades = service.getAllVentaUnidad();
            } catch (ComarServiceException e) {
                e.printStackTrace();
            } finally {
                tx.rollback();
            }
        } catch (ComarServiceException e) {
            e.printStackTrace();
        }

        sells = new ArrayList<>();
        sellUnits = new ArrayList<>();
        indexSell = new HashMap<>();
        indexSellUnit = new HashMap<>();

        for (VentaEntity e : eventas) {
            addSell(new ComarSell(e));
        }

        for (VentaUnidadEntity e : eventaUnidades) {
            if (e.getIdVenta() == null) {
                throw new RuntimeException("Elemento de factura sin factura asignada: " + e);
            }

            String idVenta = UUIDUtils.toString(e.getIdVenta());
            String idProducto = UUIDUtils.toString(e.getIdProducto());
            ComarSell sell = indexSell.get(idVenta);
            ComarProduct product = indexProduct.get(idProducto);

            ComarSellUnit unit = new ComarSellUnit(e, sell, product);
            addSellUnit(unit);
        }
    }

    public void addCategory(ComarCategory category) {
        categories.add(category);
        indexCategory.put(UUIDUtils.toString(category.getEntity().getId()), category);
    }

    public void addProduct(ComarProduct product) {
        products.add(product);
        indexProduct.put(UUIDUtils.toString(product.getEntity().getId()), product);
        product.getCategory().addProduct(product);
    }

    public void addBill(ComarBill bill) {
        bills.add(bill);
        indexBill.put(bill.getId(), bill);
    }

    public void addBillUnit(ComarBillUnit billUnit) {
        billUnits.add(billUnit);
        indexBillUnit.put(billUnit.getId(), billUnit);
        billUnit.getBill().addUnit(billUnit);
    }

    public void addSell(ComarSell sell) {
        sells.add(sell);
        indexSell.put(sell.getId(), sell);
    }

    public void addSellUnit(ComarSellUnit sellUnit) {
        sellUnits.add(sellUnit);
        indexSellUnit.put(sellUnit.getId(), sellUnit);

        sellUnit.getSell().addUnit(sellUnit);
    }

    public List<ComarCategory> getCategories() {
        return categories;
    }

    public List<ComarBill> getBills() {
        return bills;
    }

    public List<ComarSell> getSells() {
        return sells;
    }

    public void removeCategory(ComarCategory c) {
        this.categories.remove(c);
        this.indexCategory.remove(c.getId());
    }

    public void removeProduct(ComarProduct p, ComarCategory c) {
        this.products.remove(p);
        this.indexProduct.remove(p.getId());
        c.removeProduct(p);
    }

    public void removeBill(ComarBill bill) {
        List<ComarBillUnit> units = bill.getUnits();
        for (ComarBillUnit billUnit : units) {
            this.billUnits.remove(billUnit);
            this.indexBillUnit.remove(billUnit.getId());

            BigDecimal cantidad = billUnit.getEntity().getCantidad();
            BigDecimal stockComprado = billUnit.getProduct().getEntity().getStockComprado();
            BigDecimal stock = stockComprado.subtract(cantidad);
            billUnit.getProduct().getEntity().setStockComprado(stock);
        }

        bill.getUnits().clear();
        this.bills.remove(bill);
        this.indexBill.remove(bill.getId());
    }

    public void removeBills(List<ComarBill> bills) {
        for (ComarBill bill : bills) {
            removeBill(bill);
        }
    }

    public void removeBillUnit(ComarBillUnit billUnit, ComarBill bill) {
        this.billUnits.remove(billUnit);
        this.indexBillUnit.remove(billUnit.getId());
        bill.removeUnit(billUnit);
    }

    public void removeSell(ComarSell sell) {
        List<ComarSellUnit> units = sell.getUnits();
        for (ComarSellUnit sellUnit : units) {
            this.sellUnits.remove(sellUnit);
            this.indexSellUnit.remove(sellUnit.getId());
        }

        sell.getUnits().clear();
        this.sells.remove(sell);
        this.indexSell.remove(sell.getId());
    }

    public void removeSellUnit(ComarSellUnit sellUnit, ComarSell sell) {
        this.sellUnits.remove(sellUnit);
        this.indexSellUnit.remove(sellUnit.getId());
        sell.removeUnit(sellUnit);
    }

    public void removeProducts(List<ComarProduct> products) {
        for (ComarProduct product : products) {
            ComarCategory category = product.getCategory();
            removeProduct(product, category);
        }
    }

    public void moveProducts(List<ComarProduct> pmodels, ComarCategory cmodel) {
        for (ComarProduct pmodel : pmodels) {
            pmodel.getCategory().removeProduct(pmodel);
            cmodel.addProduct(pmodel);
        }
    }

    public ComarProduct getProductByCode(String code) {
        for (ComarCategory c : categories) {
            ComarProduct p = c.getProduct(code);
            if (p != null) {
                return p;
            }
        }
        return null;
    }

    public ComarProduct getProductById(byte[] idProducto) {
        for (ComarCategory c : categories) {
            ComarProduct p = c.getProductById(idProducto);
            if (p != null) {
                return p;
            }
        }
        return null;
    }

    public boolean checkReferences(List<ComarProduct> products) {
        for (ComarProduct product : products) {
            for (ComarBill bill : bills) {
                for (ComarBillUnit unit : bill.getUnits()) {
                    if (unit.getProduct().equals(product)) {
                        return true;
                    }
                }
            }

            for (ComarSell sell : sells) {
                for (ComarSellUnit unit : sell.getUnits()) {
                    if (unit.getProduct().equals(product)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public ComarCategory getCategoryByName(String catName) {
        for (ComarCategory c : categories) {
            if (c.getEntity().getNombre().equals(catName)) {
                return c;
            }
        }
        return null;
    }

}
