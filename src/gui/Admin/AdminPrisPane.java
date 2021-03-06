package gui.Admin;

import controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.*;

public class AdminPrisPane extends GridPane {
    private final Controller controller = Controller.getController();

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

    SalgsSituation selectedSalgsSituation;
    Pris selectedPris;

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

        lwSalgsSituationer.getItems().setAll(controller.getSalgsSituationer());
        lwSalgsSituationer.getSelectionModel().select(0);
        lwSalgsSituationer.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ChangeListener<? super SalgsSituation> lwSalgsSituationListener = (ov, oldString, newString) -> this.selectionChangedLwSalgsSituationer();
        lwSalgsSituationer.getSelectionModel().selectedItemProperty().addListener(lwSalgsSituationListener);
        this.add(lwSalgsSituationer, 0, 1);
        selectedSalgsSituation = this.lwSalgsSituationer.getSelectionModel().getSelectedItem();


        lwPriser = new ListView<>();

        lwPriser.getItems().setAll(controller.getPriserFromSalgsSituation(lwSalgsSituationer.getItems().get(0)));
        lwPriser.getSelectionModel().select(0);
        this.add(lwPriser, 1, 1);
        selectedPris = this.lwPriser.getSelectionModel().getSelectedItem();

        //Labels
        this.add(lblSalgsSituationer, 0, 0);
        this.add(lblPriser, 1, 0);

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
        btnOpretSalgsSituation.setOnAction(event ->
                this.onActionBtnOpretSalgsSituation(this));
        btnRedigerSalgsSituation.setOnAction(event ->
                this.onActionBtnRedigerSalgsSituation(this, selectedSalgsSituation));
        btnSletSalgsSituation.setOnAction(event ->
                this.onActionBtnSletSalgsSituation(selectedSalgsSituation));

        btnOpretPris.setOnAction(event ->
                this.onActionBtnOpretPris(this, selectedSalgsSituation));
        btnRedigerPris.setOnAction(event ->
                this.onActionBtnRedigerPris(this, selectedSalgsSituation, selectedPris));
        btnSletPris.setOnAction(event ->
                this.onActionBtnSletPris(selectedSalgsSituation, selectedPris));

    }

    private void selectionChangedLwSalgsSituationer() {
        if (selectedSalgsSituation == null) {
            selectedSalgsSituation = lwSalgsSituationer.getItems().get(0);
        }
        else {
            selectedSalgsSituation = lwSalgsSituationer.getSelectionModel().getSelectedItem();
        }

        this.updateLwPriser(selectedSalgsSituation);
    }


    private void onActionBtnOpretSalgsSituation(AdminPrisPane adminPrisPane) {
        AdminOpretSalgsSituationWindow adminOpretSalgsSituationWindow = new AdminOpretSalgsSituationWindow(adminPrisPane);
        adminOpretSalgsSituationWindow.showAndWait();
    }

    private void onActionBtnRedigerSalgsSituation(AdminPrisPane adminPrisPane, SalgsSituation salgsSituation) {
        AdminRedigerSalgsSituationWindow adminRedigerSalgsSituationWindow = new AdminRedigerSalgsSituationWindow(adminPrisPane, salgsSituation);
        adminRedigerSalgsSituationWindow.showAndWait();
    }

    private void onActionBtnSletSalgsSituation(SalgsSituation salgsSituation) {
        try {
            controller.removeSalgsSituation(salgsSituation);
            this.updateLwSalgsSituationer();
        }
        catch (RuntimeException e) {
            errorAlert = new Alert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    public void updateLwSalgsSituationer() {
        lwSalgsSituationer.getItems().setAll(controller.getSalgsSituationer());
    }

//--------------------------------------

    private void onActionBtnOpretPris(AdminPrisPane adminPrisPane, SalgsSituation salgsSituation) {
        AdminOpretPrisWindow adminOpretPrisWindow = new AdminOpretPrisWindow(adminPrisPane, salgsSituation);
        adminOpretPrisWindow.showAndWait();
    }

    private void onActionBtnRedigerPris(AdminPrisPane adminPrisPane, SalgsSituation salgsSituation, Pris pris) {
        AdminRedigerPrisWindow adminRedigerPrisWindow = new AdminRedigerPrisWindow(adminPrisPane, salgsSituation, lwPriser.getSelectionModel().getSelectedItem());
        adminRedigerPrisWindow.showAndWait();
    }


    private void onActionBtnSletPris(SalgsSituation salgsSituation, Pris pris) {
        try {
            controller.removePris(salgsSituation, pris);
            this.updateLwPriser(salgsSituation);
        }
        catch (RuntimeException e) {
            errorAlert = new Alert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    public void updateLwPriser(SalgsSituation salgsSituation)  {
        lwPriser.getItems().setAll(controller.getPriserFromSalgsSituation(salgsSituation));
    }

//--------------------------------------

}
