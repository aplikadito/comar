/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui.pos;

import cl.rworks.comar.core.ComarContext;
import javafx.scene.Scene;

/**
 *
 * @author rgonzalez
 */
public class ComarScenePos extends Scene {

    private static ComarScenePos instance;

    public static void init(ComarContext context) {
        instance = new ComarScenePos(context);
    }

    public static ComarScenePos getInstance() {
        return instance;
    }

    private ComarScenePos(ComarContext context) {
        super(new ComarPanePos(context));
        getStylesheets().add("comar.css");

//        setOnKeyReleased(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                if (event.getCode() == KeyCode.ESCAPE) {
//                    Scene scene = (Scene) event.getSource();
//                    Stage stage = (Stage) scene.getWindow();
//                }
//                
//                System.out.println(event.getCode());
//            }
//        });
    }

}
