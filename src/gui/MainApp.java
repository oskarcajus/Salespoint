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
            stage.setHeight(500);
            stage.setWidth(800);
            stage.show();
        }

        // Buttons
        private Button btnAdmin = new Button("Admin");
        private Button btnSalg = new Button("Salg System");

//------tabPane------------------------------------------------------------------------------------------

        private void initContent(GridPane pane) {
            pane.setGridLinesVisible(false);
            pane.setAlignment(Pos.CENTER);
            pane.setPadding(new Insets(10));
            pane.setHgap(10);
            pane.setVgap(10);

            pane.add(btnAdmin, 5, 4);
//            pane.setMargin(btnAdmin, new Insets(1, 0, 1, 5));
            pane.setHalignment(btnAdmin, HPos.CENTER);
//            btnAdmin.setOnAction(event -> rollAction());

            pane.add(btnSalg, 6, 4);
            pane.setHalignment(btnSalg, HPos.CENTER);;
//            btnAdmin.setOnAction(event -> rollAction());

//            HBox hbox = new HBox();
//            Button button1 = new Button("Add");
//            Button button2 = new Button("Remove");
//            HBox.setHgrow(button1, Priority.ALWAYS);
//            HBox.setHgrow(button2, Priority.ALWAYS);
//            button1.setMaxWidth(Double.MAX_VALUE);
//            button2.setMaxWidth(Double.MAX_VALUE);
//            hbox.getChildren().addAll(button1, button2);
//            pane.add(button1,5,4);
//            pane.setHalignment(button1, HPos.CENTER);
//
//            pane.add(button2,3,5);
//            pane.setHalignment(button2, HPos.CENTER);

        }
    }

