package gui;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.*;
import storage.*;

public class AdminProduktPane extends GridPane {
    //Alerts
    private Alert errorAlert;

    //Listviews
    private ListView<Produktgruppe> lwProduktgrupper;
    private ListView<ProduktType> lwProduktTyper;
    private ListView<Produkt> lwProdukter;

    //Labels
    private final Label lblProduktgrupper = new Label("Produktgrupper:");
    private final Label lblProduktTyper = new Label("Produkttyper:");
    private final Label lblProdukter = new Label("Produkter:");

    //Buttons
    HBox hBoxProduktgrupper = new HBox();
    private final Button btnOpretProduktgruppe = new Button("Opret");
    private final Button btnRedigerProduktgruppe = new Button("Rediger");
    private final Button btnSletProduktgruppe = new Button("Slet");

    HBox hBoxProduktTyper = new HBox();
    private final Button btnOpretProduktType = new Button("Opret");
    private final Button btnRedigerProduktType = new Button("Rediger");
    private final Button btnSletProduktType = new Button("Slet");

    HBox hBoxProdukt = new HBox();
    private final Button btnOpretProdukt = new Button("Opret");
    private final Button btnRedigerProdukt = new Button("Rediger");
    private final Button btnSletProdukt = new Button("Slet");


    public AdminProduktPane() {
        this.setGridLinesVisible(false);
        this.initContent();
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(20));
    }

    public void initContent() {

        //Listviews
        lwProduktgrupper = new ListView<>();
        lwProduktgrupper.getItems().setAll(Storage.getProduktgrupper());
        lwProduktgrupper.getSelectionModel().select(0);
        this.add(lwProduktgrupper, 0, 1);

        lwProduktTyper = new ListView<>();
//        lwProduktTyper.getItems().setAll(Storage.getProduktgrupper());
        lwProduktTyper.getSelectionModel().select(0);
        this.add(lwProduktTyper, 0, 4);

        lwProdukter = new ListView<>();
        this.add(lwProdukter, 1, 1, 1, 4);

        //Labels
        this.add(lblProduktgrupper, 0,0);
        this.add(lblProduktTyper, 0,3);
        this.add(lblProdukter, 1,0);

        //Buttons
        hBoxProduktgrupper.getChildren().addAll(btnOpretProduktgruppe, btnRedigerProduktgruppe, btnSletProduktgruppe);
        hBoxProduktgrupper.setAlignment(Pos.CENTER);
        hBoxProduktgrupper.setSpacing(20);
        this.add(hBoxProduktgrupper, 0, 2);

        hBoxProduktTyper.getChildren().addAll(btnOpretProduktType, btnRedigerProduktType, btnSletProduktType);
        hBoxProduktTyper.setAlignment(Pos.CENTER);
        hBoxProduktTyper.setSpacing(20);
        this.add(hBoxProduktTyper, 0, 5);

        hBoxProdukt.getChildren().addAll(btnOpretProdukt, btnRedigerProdukt, btnSletProdukt);
        hBoxProdukt.setAlignment(Pos.CENTER);
        hBoxProdukt.setSpacing(20);
        this.add(hBoxProdukt, 1, 5);

        //Buttons on click
        btnOpretProduktgruppe.setOnAction(event ->
                this.onActionBtnOpretProduktgruppe(this));
        btnRedigerProduktgruppe.setOnAction(event ->
                this.onActionBtnRedigerProduktgruppe(this, lwProduktgrupper.getSelectionModel().getSelectedItem()));
        btnSletProduktgruppe.setOnAction(event ->
                this.onActionBtnSletProduktgruppe(lwProduktgrupper.getSelectionModel().getSelectedItem()));

        btnOpretProduktType.setOnAction(event ->
                this.onActionBtnOpretProduktType(this));
        btnRedigerProduktType.setOnAction(event ->
                this.onActionBtnRedigerProduktType(this, lwProduktTyper.getSelectionModel().getSelectedItem()));
        btnSletProduktType.setOnAction(event ->
                this.onActionBtnSletProduktType(lwProduktTyper.getSelectionModel().getSelectedItem()));




//        btnOpretProdukt.setOnAction(event -> this.onActionBtnOpretProdukt(this, lwProduktgrupper.getSelectionModel().getSelectedItem()));
//        btnOpretProdukt.setOnAction(event -> this.onActionBtnRedigerProdukt());


    }

    private void onActionBtnOpretProduktgruppe(AdminProduktPane adminProduktPane) {
        AdminOpretProduktgruppeWindow adminOpretProduktgruppeWindow = new AdminOpretProduktgruppeWindow(adminProduktPane);
        adminOpretProduktgruppeWindow.showAndWait();
    }

    private void onActionBtnRedigerProduktgruppe(AdminProduktPane adminProduktPane, Produktgruppe produktgruppe) {
        AdminRedigerProduktgruppeWindow adminRedigerProduktgruppeWindow = new AdminRedigerProduktgruppeWindow(adminProduktPane, produktgruppe);
        adminRedigerProduktgruppeWindow.showAndWait();
    }


    private void onActionBtnSletProduktgruppe(Produktgruppe produktgruppe) {
        try {
            Controller.removeProduktgruppe(produktgruppe);
            this.updateLwProduktgrupper();
        }
        catch (RuntimeException e) {
            errorAlert = new Alert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    public void updateLwProduktgrupper() {
        lwProduktgrupper.getItems().setAll(Storage.getProduktgrupper());
    }


    private void onActionBtnOpretProduktType(AdminProduktPane adminProduktPane) {
        AdminOpretProduktTypeWindow adminOpretProduktTypeWindow = new AdminOpretProduktTypeWindow(adminProduktPane);
        adminOpretProduktTypeWindow.showAndWait();
    }

    private void onActionBtnRedigerProduktType(AdminProduktPane adminProduktPane, ProduktType produktType) {
        AdminRedigerProduktTypeWindow adminRedigerProduktTypeWindow = new AdminRedigerProduktTypeWindow(adminProduktPane, produktType);
        adminRedigerProduktTypeWindow.showAndWait();
    }


    private void onActionBtnSletProduktType(ProduktType produktType) {
        try {
            Controller.removeProduktType(produktType);
            this.updateLwProduktTyper();
        }
        catch (RuntimeException e) {
            errorAlert = new Alert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    public void updateLwProduktTyper()  { lwProduktTyper.getItems().setAll(Storage.getProduktTyper()); }
}
