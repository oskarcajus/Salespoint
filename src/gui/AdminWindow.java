package gui;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AdminWindow extends Application {

    // Controller access
    private Controller controller;
    private Button btnTest = new Button("Hej");

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
        Tab tabProdukter = new Tab("Produkter");
        Tab tabPrisLister = new Tab("Prislister");

        AdminProduktPane adminProduktPane = new AdminProduktPane();
        tabProdukter.setContent(adminProduktPane);
//            // ProduktPane til tab
//            ProduktPane produktPane = new ProduktPane();
//            tabProdukter.setContent(produktPane);
//
//            // PrisListePane til tab
//            PrisListePane prisListePane = new PrisListePane();
//            tabPrisLister.setContent(prisListePane);

        // Add Ordre, produkter, pristLister til TabPane
        tabPane.getTabs().addAll(tabProdukter, tabPrisLister);


    }
}

