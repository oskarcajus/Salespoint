package gui.Admin;

import controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.*;

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
    private final HBox hBoxProduktgrupper = new HBox();
    private final Button btnOpretProduktgruppe = new Button("Opret");
    private final Button btnRedigerProduktgruppe = new Button("Rediger");
    private final Button btnSletProduktgruppe = new Button("Slet");

    private final HBox hBoxProduktTyper = new HBox();
    private final Button btnOpretProduktType = new Button("Opret");
    private final Button btnRedigerProduktType = new Button("Rediger");
    private final Button btnSletProduktType = new Button("Slet");

    private final HBox hBoxProdukt = new HBox();
    private final Button btnOpretProdukt = new Button("Opret");
    private final Button btnRedigerProdukt = new Button("Rediger");
    private final Button btnSletProdukt = new Button("Slet");

    private ObservableList<Produktgruppe> selectedProduktgrupper;
    private ObservableList<ProduktType> selectedProduktTyper;

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
        selectedProduktgrupper = this.lwProduktgrupper.getSelectionModel().getSelectedItems();

        lwProduktgrupper.getItems().setAll(Controller.getProduktgrupper());
        lwProduktgrupper.getSelectionModel().select(0);
        lwProduktgrupper.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ChangeListener<? super Produktgruppe> lwProduktgrupperListener = (ov, oldString, newString) -> this.selectionChangedLwProduktgrupper();
        lwProduktgrupper.getSelectionModel().selectedItemProperty().addListener(lwProduktgrupperListener);
        this.add(lwProduktgrupper, 0, 1);

        lwProduktTyper = new ListView<>();
        selectedProduktTyper = this.lwProduktTyper.getSelectionModel().getSelectedItems();

        lwProduktTyper.getItems().setAll(Controller.getProduktTyper());
        lwProduktTyper.getSelectionModel().select(0);
        lwProduktTyper.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ChangeListener<? super ProduktType> lwProduktTyperListener = (ov, oldString, newString) -> this.selectionChangedLwProduktTyper();
        lwProduktTyper.getSelectionModel().selectedItemProperty().addListener(lwProduktTyperListener);
        this.add(lwProduktTyper, 0, 4);




        lwProdukter = new ListView<>();
        lwProdukter.getItems().setAll(Controller.getProdukterInProduktgruppeAndOrProduktType(selectedProduktgrupper, selectedProduktTyper));
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

        btnOpretProdukt.setOnAction(event ->
                this.onActionBtnOpretProdukt(this, lwProduktgrupper.getSelectionModel().getSelectedItem(),
                        lwProduktTyper.getSelectionModel().getSelectedItem()));
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
        lwProduktgrupper.getItems().setAll(Controller.getProduktgrupper());
    }

//--------------------------------------

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

    public void updateLwProduktTyper()  { lwProduktTyper.getItems().setAll(Controller.getProduktTyper()); }

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

    public void updateLwProdukter() {
        ObservableList<Produktgruppe> selectedProduktgrupper = this.lwProduktgrupper.getSelectionModel().getSelectedItems();
        ObservableList<ProduktType> selectedProduktTyper = this.lwProduktTyper.getSelectionModel().getSelectedItems();
        lwProdukter.getItems().setAll(
                Controller.getProdukterInProduktgruppeAndOrProduktType(selectedProduktgrupper, selectedProduktTyper));
    }

    //----------------------------------------

    private void selectionChangedLwProduktgrupper() {
        this.updateLwProdukter();
    }
    private void selectionChangedLwProduktTyper() {
        this.updateLwProdukter();
    }

}