/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui.test;

import cl.rworks.comar.ui.adm.ComarPaneAdmProducts;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author aplik
 */
public class ApplicationTest extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {
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

    private class TestPane extends BorderPane {

        public TestPane() {

            GridPane grid = new GridPane();
//            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));
//            grid.setMinWidth(400);

//            Text scenetitle = new Text("Welcome");
//            scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
//            grid.add(scenetitle, 0, 0, 2, 1);

            Label userName = new Label("User Name:");
            grid.add(userName, 0, 1);

            TextField userTextField = new TextField();
            userTextField.setMinWidth(400);
            grid.add(userTextField, 1, 1);

            Label pw = new Label("Password:");
            grid.add(pw, 0, 2);

            PasswordField pwBox = new PasswordField();
            grid.add(pwBox, 1, 2);

            setTop(new Label("label"));
            setCenter(grid);
        }

    }
}
