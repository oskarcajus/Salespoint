package gui.Salg;

import controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class SalgRundvisningPane extends GridPane {
    Controller controller = new Controller();
    SalgsSituation salgsSituation;

    Label lblOrdreNr, lblOrdrerTxt, lblExpectingDato, lblKundeNavn, lblBetalingMetode;
    TextField txfExpectingDato ;
    Button btnOpretRV, btnOpretKunde;
    ComboBox<Kunde> cBoxKunde;
    Alert errorAlert;
    ToggleGroup betalingsGroup = new ToggleGroup();


    public SalgRundvisningPane(SalgsSituation salgsSituation) {
        this.salgsSituation = salgsSituation;
        this.setGridLinesVisible(false);
        this.initContent();
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(20));
    }

    public void initContent() {
//Leftside
//OrdrerNr
        HBox hBoxOrderNr = new HBox(10);
        this.add(hBoxOrderNr, 0, 0);

        lblOrdrerTxt = new Label("Ordrernr:");
        hBoxOrderNr.getChildren().add(lblOrdrerTxt);

        lblOrdreNr = new Label(Integer.toString(getCurrentOrderNr()));
        hBoxOrderNr.getChildren().add(lblOrdreNr);

//exceptingdato
        lblExpectingDato = new Label("Rundvisingdato: (yyyy-mm-dd)");
        this.add(lblExpectingDato, 0, 1);

        txfExpectingDato = new TextField();
        txfExpectingDato.setMaxWidth(100);
        this.add(txfExpectingDato, 1, 1);

//Kunde
        HBox hBoxKunde = new HBox(10);
        lblKundeNavn = new Label("VælgeKunde:");
        this.add(lblKundeNavn, 0, 2);

        cBoxKunde = new ComboBox<Kunde>();
        cBoxKunde.getItems().add(null);
        cBoxKunde.getItems().addAll(FXCollections.observableArrayList(controller.getKunder()));
        cBoxKunde.setMinWidth(200);
        hBoxKunde.getChildren().add(cBoxKunde);

        btnOpretKunde = new Button("OpretKunde");
        btnOpretKunde.setOnAction(event -> opretKundeAction());
        hBoxKunde.getChildren().add(btnOpretKunde);
        this.add(hBoxKunde, 1, 2);

//button
        btnOpretRV = new Button("OpretRundvisning");
        this.add(btnOpretRV, 0, 5);
        btnOpretRV.setOnAction(event -> this.opretRundvisingAction());

        // Betalings metode
        lblBetalingMetode = new Label("Betalings Type:");
        this.add(lblBetalingMetode, 0, 3);

        HBox hbxBetaling = new HBox();
        for (BetalingsType bt : BetalingsType.values()) {
            RadioButton rb = new RadioButton();
            rb.setText(bt.name());
            rb.setPrefWidth(100);
            rb.setUserData(bt);
            rb.setToggleGroup(betalingsGroup);
            rb.setOnAction(event -> actionBetalingsTypeChanged());
            hbxBetaling.getChildren().add(rb);
        }
        this.add(hbxBetaling, 0, 4, 2, 1);

    }
    //---------------------------------------------------------------------------------------
    public int getCurrentOrderNr() {
        return controller.getOrders().size() + 1;
    }

    //---------------------------------------------------------------------------------------
    public void opretRundvisingAction() {
        LocalDate expecting = LocalDate.parse(txfExpectingDato.getText().trim());
        LocalDate now = LocalDate.now();
        Kunde kunde = cBoxKunde.getValue();
        int ordreNr = getCurrentOrderNr();
        BetalingsType betalingsType = (BetalingsType) betalingsGroup.getSelectedToggle().getUserData();

        if (!now.isAfter(expecting)) {
            if (kunde != null) {
                if (betalingsType != null) {
                    Order order = controller.createRundvisningOrder(ordreNr, now, expecting);
                    order.setKunde(kunde);

                    order.setBetalingsType(betalingsType);
                    order.setBetalingsDato(LocalDate.now());
                    order.setOrderStatus(OrderStatus.AFSLUTTET);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Afsluttet salg");
                    alert.setHeaderText("Kvittering");
                    alert.setContentText("Samlet beløb: " + String.valueOf(order.prisWithRabat()) + "\nBetalings type: " + betalingsType + "\nStatus: " + order.getOrderStatus() + "\nAfsluttet Dato: " + order.getBetalingsDato() + "\nKunde: " + kunde.toString());
                    Optional<ButtonType> result = alert.showAndWait();

                } else {
                    errorAlert = new Alert(Alert.AlertType.ERROR, "Vælge betalingsmåde!");
                    errorAlert.show();
                }

            } else {
                errorAlert = new Alert(Alert.AlertType.ERROR, "Opret en kunde eller vælge en kunde");
                errorAlert.show();
            }
        } else {
            errorAlert = new Alert(Alert.AlertType.ERROR, "Vælge en dato,som ikke er datid");
            errorAlert.show();
        }


    }

    //---------------------------------------------------------------------------------------
    public void opretKundeAction() {
        SalgOpretKundeWindow salgOpretKundeWindow = new SalgOpretKundeWindow();
        salgOpretKundeWindow.showAndWait();

        cBoxKunde.getItems().clear();
        cBoxKunde.getItems().add(null);
        cBoxKunde.getItems().setAll(controller.getKunder());
    }

    //---------------------------------------------------------------------------------------
    private void actionBetalingsTypeChanged() {

        BetalingsType betalingsTypee = (BetalingsType) betalingsGroup.getSelectedToggle().getUserData();

        if (betalingsTypee != null) {
            if (BetalingsType.KLIPPEKORT.equals(betalingsTypee)) {
                errorAlert = new Alert(Alert.AlertType.ERROR, "Man kan ikke betale med klippekort");
                errorAlert.show();
            }
        }
    }

}
