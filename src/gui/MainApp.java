package gui;


import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
            BorderPane pane = new BorderPane();
            this.initContent(pane);

            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setHeight(500);
            stage.setWidth(800);
            stage.show();
        }

        // Buttons
        private Button btnAdmin = new Button("Admin");
        private Button btnSalg = new Button("Salg System");

//------tabPane------------------------------------------------------------------------------------------

        private void initContent(BorderPane pane) {
            TabPane tabPane = new TabPane();
            pane.setCenter(tabPane);

            pane.add(btnAdmin, 3, 4);
            pane.setMargin(btnAdmin, new Insets(1, 0, 1, 1));
//            btnAdmin.setOnAction(event -> rollAction());
        }
    }

