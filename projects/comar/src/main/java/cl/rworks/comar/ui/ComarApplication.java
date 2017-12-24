/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui;

import cl.rworks.comar.core.model.ComarContext;
import cl.rworks.comar.core.impl.ComarContextImpl;
import cl.rworks.comar.ui.adm.ComarPaneAdmProducts;
import cl.rworks.comar.ui.adm.ComarSceneAdm;
import cl.rworks.comar.ui.pos.ComarScenePos;
import cl.rworks.comar.ui.test.ComarContextTest;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author rgonzalez
 */
public class ComarApplication extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {
//        startNormal(primaryStage);
        startTest(primaryStage);
    }
    
    private void startNormal(final Stage primaryStage){
        ComarContext context = ComarContextImpl.getInstance();

        ComarScenePos.init(context);
        ComarSceneAdm.init(context);

        primaryStage.setTitle("Comar");
        primaryStage.setScene(ComarScenePos.getInstance());
        primaryStage.setWidth(1024);
        primaryStage.setHeight(700);

//        primaryStage.
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                //                primaryStage.setScene(new ComarScene(new ComarPaneExit()));
                event.consume();

                // show close dialog
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Salir");
                alert.setHeaderText("Desea salir de la aplicación?");
                alert.initOwner(primaryStage);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Platform.exit();
                }
            }
        });

        primaryStage.show();
    }
    
    public void startTest(final Stage primaryStage) throws Exception {
//        Parent root = new TestPane();
        ComarPaneAdmProducts root = new ComarPaneAdmProducts();
        root.init(new ComarContextTest());
        root.getStyleClass().add("comar-content");
        Scene scene = new Scene(root);
        
        scene.getStylesheets().add("comar.css");
        primaryStage.setTitle("Comar");
        primaryStage.setScene(scene);
        primaryStage.setWidth(1024);
        primaryStage.setHeight(700);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
