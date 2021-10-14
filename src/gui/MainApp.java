package gui;


import controller.Controller;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

//import java.awt.*;

public class MainApp extends Application {

        // Controller access
        private Controller controller;

//------------------------------------------------------------------------------------------------

        @Override
        public void init() {
//            controller = Controller.getController();
        }

        //------------------------------------------------------------------------------------------------
        @Override
        public void start(Stage stage) {

            stage.setTitle("Salespoint");
            GridPane pane = new GridPane();
            this.initContent(pane);

            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setHeight(200);
            stage.setWidth(300);
            stage.show();
        }

        // Buttons
        private Button btnAdmin = new Button("Admin");
        private Button btnSalg = new Button("Salg System");

//------tabPane------------------------------------------------------------------------------------------

        private void initContent(GridPane pane) {
            pane.setGridLinesVisible(false);
            pane.setAlignment(Pos.CENTER);

            pane.add(btnAdmin, 5, 4);
            pane.setMargin(btnAdmin, new Insets(10, 10, 10, 10));
            pane.setHalignment(btnAdmin, HPos.CENTER);
            btnAdmin.setMinSize(100, 100);
//            btnAdmin.setOnAction(event -> rollAction());

            pane.add(btnSalg, 6, 4);
            pane.setMargin(btnSalg, new Insets(10, 10, 10, 10));
            pane.setHalignment(btnSalg, HPos.CENTER);
            btnSalg.setMinSize(100, 100);
//            btnAdmin.setOnAction(event -> rollAction());

        }
    }

