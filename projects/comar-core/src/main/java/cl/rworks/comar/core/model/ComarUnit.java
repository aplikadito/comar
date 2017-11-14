/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.model;

/**
 *
 * @author rgonzalez
 */
public enum ComarUnit {
    
    UNIDAD("Unidad","[u]"),
    KILO("Kilo","[Kg]"),
    GRAMOS("Gramos","[gr]"),
    LITROS("Litros","[lt]");
    
    private final String name;
    private final String symbol;

    ComarUnit(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }
    
    
    
}
