/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import java.util.List;

/**
 *
 * @author aplik
 */
public interface ComarMenuButtonManager {

    public void putMenuButton(String id, ComarMenuButton button);

    public ComarMenuButton getMenuButton(String id);

}
