/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author aplik
 */
public class Tuple {

    private ObjectProperty<LocalDateTime> from = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> to = new SimpleObjectProperty<>();
    private StringProperty type = new SimpleStringProperty();
    private DoubleProperty cost = new SimpleDoubleProperty();

    public Tuple(LocalDateTime from, LocalDateTime to, String type) {
        this.from.set(from);
        this.to.set(to);
        this.type.set(type);
        this.cost.set(0);
    }
    public final void setFrom(LocalDateTime date) {
        from.set(date);
    }

    public final LocalDateTime getFrom() {
        return from.get();
    }
    
    public final void setTo(LocalDateTime date) {
        to.set(date);
    }

    public final LocalDateTime getTo() {
        return to.get();
    }

    public final void setType(String value) {
        type.set(value);
    }

    public final String getType() {
        return type.get();
    }

    public final StringProperty typeProperty() {
        return type;
    }

    public final void setCost(Double value) {
        cost.set(value);
    }

    public final Double getCost() {
        return cost.get();
    }

    public final DoubleProperty costProperty() {
        return cost;
    }

    public final ObjectProperty<LocalDateTime> fromProperty(){
        return from;
    }
    
    public final ObjectProperty<LocalDateTime> toProperty(){
        return to;
    }
}
