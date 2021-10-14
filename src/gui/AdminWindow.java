package gui;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

    public class AdminWindow extends Application {

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

            stage.setTitle("Admin");
            BorderPane pane = new BorderPane();
            this.initContent(pane);

            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setHeight(500);
            stage.setWidth(800);
            stage.show();
        }

//------tabPane------------------------------------------------------------------------------------------

        private void initContent(BorderPane pane) {
            TabPane tabPane = new TabPane();
//            this.initTabPane(tabPane);
            pane.setCenter(tabPane);
        }
}
