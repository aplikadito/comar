/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.FacturaEntity;
import cl.rworks.comar.core.model.FacturaUnidadEntity;
import cl.rworks.comar.core.model.ProductoEntity;
import cl.rworks.comar.core.model.impl.FacturaUnidadEntityImpl;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.util.BigDecimalUtils;
import cl.rworks.comar.core.util.UUIDUtils;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author aplik
 */
public class InsertFacturaUnidad {

    private Connection connection;

    public InsertFacturaUnidad(Connection connection) {
        this.connection = connection;
    }

    public void execute(FacturaUnidadEntity unidad, FacturaEntity factura, ProductoEntity producto) throws ComarServiceException {
        byte[] id = UUIDUtils.createId();
        String sql = "INSERT INTO FACTURAUNIDAD VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int i = 1;
            ps.setBytes(i++, id);
            ps.setBytes(i++, producto.getId());
            ps.setLong(i++, BigDecimalUtils.toLong(unidad.getPrecioNetoCompra()));
            ps.setLong(i++, BigDecimalUtils.toLong(unidad.getCantidad()));
            ps.setBytes(i++, factura.getId());
            ps.executeUpdate();

            unidad.setId(id);
            unidad.setIdFactura(factura.getId());
            unidad.setIdProducto(producto.getId());
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static void serve(Connection connection, FacturaUnidadEntity unidad, FacturaEntity factura, ProductoEntity producto) throws ComarServiceException {
        new InsertFacturaUnidad(connection).execute(unidad, factura, producto);
    }

    public static FacturaUnidadEntity serve(Connection connection, BigDecimal precio, BigDecimal cantidad, FacturaEntity factura, ProductoEntity producto) throws ComarServiceException, SQLException {
        FacturaUnidadEntity unit = FacturaUnidadEntityImpl.create(precio, cantidad);
        new InsertFacturaUnidad(connection).execute(unit, factura, producto);
        return unit;
    }

}
