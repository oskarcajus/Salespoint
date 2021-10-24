package gui;

import controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.*;

import java.util.ArrayList;

public class AdminPrisPane extends GridPane {
    //Alerts
    private Alert errorAlert;

    //Listviews
    private ListView<SalgsSituation> lwSalgsSituationer;
    private ListView<Pris> lwPriser;

    //Labels
    private final Label lblSalgsSituationer = new Label("Salgssituationer:");
    private final Label lblPriser = new Label("Priser:");

    //Buttons
    private final HBox hBoxSalgsSituationer = new HBox();
    private final Button btnOpretSalgsSituation = new Button("Opret");
    private final Button btnRedigerSalgsSituation = new Button("Rediger");
    private final Button btnSletSalgsSituation = new Button("Slet");

    private final HBox hBoxPriser = new HBox();
    private final Button btnOpretPris = new Button("Opret");
    private final Button btnRedigerPris = new Button("Rediger");
    private final Button btnSletPris = new Button("Slet");


    public AdminPrisPane() {
        this.setGridLinesVisible(false);
        this.initContent();
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(20));
    }

    public void initContent() {

        //Listviews

        lwSalgsSituationer = new ListView<>();

        lwSalgsSituationer.getItems().setAll(Controller.getSalgsSituationer());
        lwSalgsSituationer.getSelectionModel().select(0);
        lwSalgsSituationer.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        ChangeListener<? super Produktgruppe> lwProduktgrupperListener = (ov, oldString, newString) -> this.selectionChangedLwProduktgrupper();
//        lwProduktgrupper.getSelectionModel().selectedItemProperty().addListener(lwProduktgrupperListener);
        this.add(lwSalgsSituationer, 0, 1);

        lwPriser = new ListView<>();

        lwPriser.getItems().setAll(Controller.getPriserFromSalgsSituation(lwSalgsSituationer.getItems().get(0)));
        lwPriser.getSelectionModel().select(0);
        lwPriser.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        ChangeListener<? super ProduktType> lwProduktTyperListener = (ov, oldString, newString) -> this.selectionChangedLwProduktTyper();
//        lwPriser.getSelectionModel().selectedItemProperty().addListener(lwProduktTyperListener);
        this.add(lwPriser, 1, 1);


//        ObservableList<SalgsSituation> selectedSalgSituationer = this.lwSalgsSituationer.getSelectionModel().getSelectedItems();
//        ObservableList<Pris> selectedPriser = this.lwPriser.getSelectionModel().getSelectedItems();

        //Labels
        this.add(lblSalgsSituationer, 0,0);
        this.add(lblPriser, 1,0);

        //Buttons
        hBoxSalgsSituationer.getChildren().addAll(btnOpretSalgsSituation, btnRedigerSalgsSituation, btnSletSalgsSituation);
        hBoxSalgsSituationer.setAlignment(Pos.CENTER);
        hBoxSalgsSituationer.setSpacing(20);
        this.add(hBoxSalgsSituationer, 0, 2);

        hBoxPriser.getChildren().addAll(btnOpretPris, btnRedigerPris, btnSletPris);
        hBoxPriser.setAlignment(Pos.CENTER);
        hBoxPriser.setSpacing(20);
        this.add(hBoxPriser, 1, 2);

        //Buttons on click
//        btnOpretProduktgruppe.setOnAction(event ->
//                this.onActionBtnOpretProduktgruppe(this));
//        btnRedigerProduktgruppe.setOnAction(event ->
//                this.onActionBtnRedigerProduktgruppe(this, lwProduktgrupper.getSelectionModel().getSelectedItem()));
//        btnSletProduktgruppe.setOnAction(event ->
//                this.onActionBtnSletProduktgruppe(lwProduktgrupper.getSelectionModel().getSelectedItem()));
//
//        btnOpretProduktType.setOnAction(event ->
//                this.onActionBtnOpretProduktType(this));
//        btnRedigerProduktType.setOnAction(event ->
//                this.onActionBtnRedigerProduktType(this, lwProduktTyper.getSelectionModel().getSelectedItem()));
//        btnSletProduktType.setOnAction(event ->
//                this.onActionBtnSletProduktType(lwProduktTyper.getSelectionModel().getSelectedItem()));
//
//        btnOpretProdukt.setOnAction(event ->
//                this.onActionBtnOpretProdukt(this, lwProduktgrupper.getSelectionModel().getSelectedItem(),
//                        lwProduktTyper.getSelectionModel().getSelectedItem()));
//        btnRedigerProdukt.setOnAction(event ->
//                this.onActionBtnRedigerProdukt(this, lwProduktTyper.getSelectionModel().getSelectedItem()));
//        btnSletProdukt.setOnAction(event ->
//                this.onActionBtnSletProdukt(lwProduktTyper.getSelectionModel().getSelectedItem()));




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

//    private void onActionBtnSletProduktgruppe(Produktgruppe produktgruppe) {
//        try {
//            Controller.removeProduktgruppe(produktgruppe);
//            this.updateLwProduktgrupper();
//        }
//        catch (RuntimeException e) {
//            errorAlert = new Alert(Alert.AlertType.ERROR, e.getMessage());
//        }
//    }

//    public void updateLwProduktgrupper() {
//        lwProduktgrupper.getItems().setAll(Controller.getProduktgrupper());
//    }

//--------------------------------------

    private void onActionBtnOpretProduktType(AdminProduktPane adminProduktPane) {
        AdminOpretProduktTypeWindow adminOpretProduktTypeWindow = new AdminOpretProduktTypeWindow(adminProduktPane);
        adminOpretProduktTypeWindow.showAndWait();
    }

    private void onActionBtnRedigerProduktType(AdminProduktPane adminProduktPane, ProduktType produktType) {
        AdminRedigerProduktTypeWindow adminRedigerProduktTypeWindow = new AdminRedigerProduktTypeWindow(adminProduktPane, produktType);
        adminRedigerProduktTypeWindow.showAndWait();
    }


//    private void onActionBtnSletProduktType(ProduktType produktType) {
//        try {
//            Controller.removeProduktType(produktType);
//            this.updateLwProduktTyper();
//        }
//        catch (RuntimeException e) {
//            errorAlert = new Alert(Alert.AlertType.ERROR, e.getMessage());
//        }
//    }

//    public void updateLwProduktTyper()  { lwProduktTyper.getItems().setAll(Controller.getProduktTyper()); }

//--------------------------------------

    private void onActionBtnOpretProdukt(AdminProduktPane adminProduktPane, Produktgruppe produktgruppe, ProduktType produktType) {
        AdminOpretProduktWindow adminOpretProduktWindow = new AdminOpretProduktWindow(adminProduktPane, produktgruppe, produktType);
        adminOpretProduktWindow.showAndWait();
    }

//    private void onActionBtnRedigerProdukt(AdminProduktPane adminProduktPane, Produkt produkt) {
//        AdminRedigerProduktWindow adminRedigerProduktWindow = new AdminRedigerProduktWindow(adminProduktPane, produkt);
//        adminRedigerProduktWindow.showAndWait();
//    }

//    private void onActionBtnSletProduktgruppe(Produktgruppe produktgruppe, Produkt produkt) {
//        try {
//            Controller.removeProdukt(produkt);
//            this.updateLwProdukter();
//        }
//        catch (RuntimeException e) {
//            errorAlert = new Alert(Alert.AlertType.ERROR, e.getMessage());
//        }
//    }

//    public void updateLwProdukter() {
//        ObservableList<Produktgruppe> selectedProduktgrupper = this.lwProduktgrupper.getSelectionModel().getSelectedItems();
//        ObservableList<ProduktType> selectedProduktTyper = this.lwProduktTyper.getSelectionModel().getSelectedItems();
//        lwProdukter.getItems().setAll(
//                Controller.getProdukterInProduktgruppeAndOrProduktType(selectedProduktgrupper, selectedProduktTyper));
//    }

    //----------------------------------------

//    private void selectionChangedLwProduktgrupper() {
//        this.updateLwProdukter();
//    }
//    private void selectionChangedLwProduktTyper() {
//        this.updateLwProdukter();
//    }

}