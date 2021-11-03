package gui.Salg;

import controller.Controller;
import gui.Admin.AdminOpretProduktWindow;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Kunde;
import model.Order;
import model.Pris;
import model.SalgsSituation;

public class SalgKundePane extends GridPane {
    private final Controller controller = Controller.getController();

    private ListView lvwKunder, lvwKundeOrdreNr, lvwOrdreline;
    private Label lblKunde, lblOrdre, lblOrdreline;


    public SalgKundePane() {
        this.setGridLinesVisible(false);
        this.initContent();
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(20));
    }

    public void initContent() {
        // Kunde
        lblKunde = new Label("Kunder: ");
        this.add(lblKunde, 0, 0);

        lvwKunder = new ListView();
        this.add(lvwKunder, 0, 1, 1, 5);
        lvwKunder.setPrefWidth(300);
        lvwKunder.setPrefHeight(500);
        this.updateKunder();
        ChangeListener<Kunde> listenerKunde = (ok, oldKunde, newKunde) -> this.selectedKunde();
        lvwKunder.getSelectionModel().selectedItemProperty().addListener(listenerKunde);

        // Button kunde
        HBox hbxButtonsKunde = new HBox(40);
        this.add(hbxButtonsKunde, 0, 6, 1, 1);

        hbxButtonsKunde.setPadding(new Insets(10, 0, 0, 0));
        hbxButtonsKunde.setAlignment(Pos.BASELINE_CENTER);

        Button btnOpretKunde = new Button("Opret Kunde");
        hbxButtonsKunde.getChildren().add(btnOpretKunde);
        btnOpretKunde.setOnAction(event -> this.opretKunde());

        Button btnSletKunde = new Button("Slet Kunde");
        hbxButtonsKunde.getChildren().add(btnSletKunde);
        btnSletKunde.setOnAction(event -> this.sletKunde());


        //Kunde ordre
        lblOrdre = new Label("Kundes ordre: ");
        this.add(lblOrdre, 3, 0);

        lvwKundeOrdreNr = new ListView();
        this.add(lvwKundeOrdreNr, 3, 1, 1, 5);
        lvwKundeOrdreNr.setPrefWidth(300);
        lvwKundeOrdreNr.setPrefHeight(500);
        ChangeListener<Order> listenerKundeOrdre = (ok, oldKunde, newKunde) -> this.selectedKundeOrdrerline();
        lvwKundeOrdreNr.getSelectionModel().selectedItemProperty().addListener(listenerKundeOrdre);


        // Kunde
        lblOrdreline = new Label("Orderline: ");
        this.add(lblOrdreline, 4, 0);

        lvwOrdreline = new ListView();
        this.add(lvwOrdreline, 4, 1, 1, 5);
        lvwOrdreline.setPrefWidth(300);
        lvwOrdreline.setPrefHeight(500);

    }

    public void selectedKunde() {
        Kunde kunde = (Kunde) lvwKunder.getSelectionModel().getSelectedItem();
        lvwKundeOrdreNr.getItems().setAll(controller.getKundeOrders(kunde));
        lvwOrdreline.getItems().clear();
    }

    public void selectedKundeOrdrerline() {
        Order order = (Order) lvwKundeOrdreNr.getSelectionModel().getSelectedItem();
        if (order != null){
            lvwOrdreline.getItems().setAll(order.getOrderLines());
        }

    }


    public void opretKunde() {
        SalgOpretKundeWindow salgOpretKundeWindow = new SalgOpretKundeWindow();
        salgOpretKundeWindow.showAndWait();

        lvwKunder.getItems().setAll(controller.getKunder());
    }

    public void sletKunde() {
        Kunde kunde = (Kunde) lvwKunder.getSelectionModel().getSelectedItem();
        controller.removeKunde(kunde);
        lvwKunder.getItems().setAll(controller.getKunder());
    }

    public void updateKunder(){
        lvwKunder.getItems().setAll(controller.getKunder());
    }

}

