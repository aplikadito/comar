/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.model;

import java.time.LocalDateTime;

/**
 *
 * @author aplik
 */
public interface ComarProductHistorial {

    public String getCode();

    public void setCode(String code);

    public LocalDateTime getDateTime();

    public void setDateTime(LocalDateTime now);

    public String getAction();

    public void setAction(String action);

    String getProperty();

    void setProperty(String property);

    String getOldValue();

    void setOldValue(String oldValue);

    String getNewValue();

    void setNewValue(String newValue);
}
