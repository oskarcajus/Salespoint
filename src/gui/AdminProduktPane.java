package gui;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.*;
import storage.*;

public class AdminProduktPane extends GridPane {
    //Alerts
    private Alert errorAlert;

    //Listviews
    private ListView<Produkt> lwProdukter;
    private ListView<Produktgruppe> lwProduktgrupper;

    //Labels
    private final Label lblProduktgrupper = new Label("Produktgrupper:");
    private final Label lblProdukter = new Label("Produkter:");

    //Buttons
    HBox hBoxProduktgrupper = new HBox();
    private final Button btnOpretProduktgruppe = new Button("Opret");
    private final Button btnRedigerProduktgruppe = new Button("Rediger");
    private final Button btnSletProduktgruppe = new Button("Slet");

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
        this.add(lwProduktgrupper, 0, 1);
        lwProdukter = new ListView<>();
        this.add(lwProdukter, 1, 1);

        //Labels
        this.add(lblProduktgrupper, 0,0);
        this.add(lblProdukter, 1,0);

        //Buttons
        hBoxProduktgrupper.getChildren().addAll(btnOpretProduktgruppe, btnRedigerProduktgruppe, btnSletProduktgruppe);
        btnOpretProduktgruppe.setOnAction(event -> this.onActionBtnOpretProduktgruppe(this));
        btnSletProduktgruppe.setOnAction(event -> this.onActionBtnSletProduktgruppe(lwProduktgrupper.getSelectionModel().getSelectedItem()));
        hBoxProduktgrupper.setAlignment(Pos.CENTER);
        hBoxProduktgrupper.setSpacing(20);
        this.add(hBoxProduktgrupper, 0, 2);

        hBoxProdukt.getChildren().addAll(btnOpretProdukt, btnRedigerProdukt, btnSletProdukt);
        hBoxProdukt.setAlignment(Pos.CENTER);
        hBoxProdukt.setSpacing(20);
        this.add(hBoxProdukt, 1, 2);

    }

    public void onActionBtnOpretProduktgruppe(AdminProduktPane adminProduktPane) {
        AdminOpretProduktgruppeWindow adminOpretProduktgruppeWindow = new AdminOpretProduktgruppeWindow(adminProduktPane);
        adminOpretProduktgruppeWindow.showAndWait();
    }

    public void updateLwProduktgrupper() {
        lwProduktgrupper.getItems().setAll(Storage.getProduktgrupper());
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
}
