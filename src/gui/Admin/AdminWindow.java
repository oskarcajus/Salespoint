package gui.Admin;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AdminWindow extends Application {

    // Controller access
    private Controller controller;
    //------------------------------------------------------------------------------------------------
    public void start(Stage stage) {

        stage.setTitle("Admin");
        BorderPane pane = new BorderPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setHeight(500);
        stage.setWidth(500);
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

        Tab tabProdukter = new Tab("Produkter");
        Tab tabPriser = new Tab("Priser");

        AdminProduktPane adminProduktPane = new AdminProduktPane();
        tabProdukter.setContent(adminProduktPane);

        AdminPrisPane adminPrisPane = new AdminPrisPane();
        tabPriser.setContent(adminPrisPane);

        // Add Ordre, produkter, pristLister til TabPane
        tabPane.getTabs().addAll(tabProdukter, tabPriser);


    }
}

