/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.properties;

/**
 *
 * @author aplik
 */
public interface ComarProperties {

    String REDONDEO = "redondeo";
    String IVA = "iva";

    public void load();

    public void save();

    public ComarRedondeo getRedondeo();

    public void setRedondeo(ComarRedondeo roundingMode);

    public double getIva();

    public void setIva(double iva);
}
