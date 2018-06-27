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
public enum ComarMetric {

    UNIDADES(0, "Unidades", "[u]"),
    KILOS(1, "Kilos", "[Kg]"),
    GRAMOS(2, "Gramos", "[gr]"),
    LITROS(3, "Litros", "[lt]"),
    CENTIMETROS_CUBICOS(4, "Centimetros Cubicos", "[cc]");

    private final int id;
    private final String name;
    private final String symbol;

    ComarMetric(int id, String name, String symbol) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return getName();
    }

    public static ComarMetric get(int id) {
        switch (id) {
            case 0:
                return UNIDADES;
            case 1:
                return KILOS;
            case 2:
                return GRAMOS;
            case 3:
                return LITROS;
            case 4:
                return CENTIMETROS_CUBICOS;
            default:
                return null;
        }
    }
}
