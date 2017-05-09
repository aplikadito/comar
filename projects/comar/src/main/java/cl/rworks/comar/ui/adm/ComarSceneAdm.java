/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui.adm;

import cl.rworks.comar.core.ComarContext;
import javafx.scene.Scene;

/**
 *
 * @author rgonzalez
 */
public class ComarSceneAdm extends Scene {

    private static ComarSceneAdm instance;

    public static void init(ComarContext context) {
        instance = new ComarSceneAdm(context);
    }

    public static ComarSceneAdm getInstance() {
        return instance;
    }

    private ComarSceneAdm(ComarContext context) {
        super(new ComarPaneAdm(context));
        getStylesheets().add("comar.css");
    }

}
