package gui;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

    public class AdminWindow extends Stage {

        // Controller access
        private Controller controller;

//------------------------------------------------------------------------------------------------

        //------------------------------------------------------------------------------------------------
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
            this.initTabPane(tabPane);
            pane.setCenter(tabPane);
        }

        private void initTabPane(TabPane tabPane) {

            tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

            // forskellige tab
            Tab tabOrdre = new Tab("Ordre Admin");
            Tab tabProdukter = new Tab("Produkter");
            Tab tabPrisLister = new Tab("PrisLister");

            // Ordre admin pane til tab
//            OrdreAdminPane ordrePane = new OrdreAdminPane();
//            tabOrdre.setContent(ordrePane);
//
//            // ProduktPane til tab
//            ProduktPane produktPane = new ProduktPane();
//            tabProdukter.setContent(produktPane);
//
//            // PrisListePane til tab
//            PrisListePane prisListePane = new PrisListePane();
//            tabPrisLister.setContent(prisListePane);

            // Add Ordre, produkter, pristLister til TabPane
            tabPane.getTabs().add(tabOrdre);
            tabPane.getTabs().add(tabProdukter);
            tabPane.getTabs().add(tabPrisLister);
        }
}
