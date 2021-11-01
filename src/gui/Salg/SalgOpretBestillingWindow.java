package gui.Salg;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.*;

import java.time.LocalDate;
import java.util.Optional;

public class SalgOpretBestillingWindow extends Stage {
    private final Controller controller = Controller.getController();
    private Order order;


    private Label lblRabat, lblAftaltPris, lblOrderNummer, lblBetalingMetode, lblKunde, lblTotalPris;
    private ComboBox<Kunde> cBoxKunde;
    private TextField txfAftaltPris, txfRabat, txfTotaltPris;
    private Button btnFærdigBestilling, btnOpretKunde;
    private RadioButton rbBetalingsMetode;
    private ToggleGroup betalingsGroup = new ToggleGroup();
    private Alert errorAlert;
    GridPane pane = new GridPane();

    public SalgOpretBestillingWindow(Order order) {
        this.order = order;
        ;

        this.setMinHeight(200);
        this.setMinWidth(300);


        this.setTitle("Opret Bestilling for Ordre Nr; " + order.getOrderNr());

        pane.setGridLinesVisible(false);
        pane.setPadding(new Insets(20));
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);

        Scene scene = new Scene(pane);
        this.setScene(scene);

        this.initContent(pane);
    }

    private void initContent(GridPane pane) {
        // Kunde
        HBox hBoxKunde = new HBox(10);
        lblKunde = new Label("Vælge Kunde: ");
        pane.add(lblKunde, 0, 0);

        cBoxKunde = new ComboBox<Kunde>();
        cBoxKunde.getItems().add(null);
        cBoxKunde.getItems().addAll(FXCollections.observableArrayList(controller.getKunder()));
        cBoxKunde.setMinWidth(200);
////      TODO  cBoxKunde.getSelectionModel().selectedItemProperty().addListener(obs -> updateControls());
        hBoxKunde.getChildren().add(cBoxKunde);

        btnOpretKunde = new Button("Opret Kunde");
        btnOpretKunde.setOnAction(event -> opretKundeAction());
        hBoxKunde.getChildren().add(btnOpretKunde);

        pane.add(hBoxKunde, 1, 0);

        // Aftalt Pris;
        lblAftaltPris = new Label("Aftalt Pris:");
        pane.add(lblAftaltPris, 0, 3);

        txfAftaltPris = new TextField();
        pane.add(txfAftaltPris, 1, 3);
        txfAftaltPris.setMaxWidth(100);
        txfAftaltPris.setOnKeyTyped(event -> aftaltPrisAction());


        //Rabat Pris
        lblRabat = new Label("Rabat %");
        pane.add(lblRabat, 0, 4);

        txfRabat = new TextField();
        pane.add(txfRabat, 1, 4);
        txfRabat.setMaxWidth(100);
        txfRabat.setOnKeyTyped(event -> this.rabatAction());

        // Betalings metode
        lblBetalingMetode = new Label("Betalings Type:");
        pane.add(lblBetalingMetode, 0, 5);

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
        pane.add(hbxBetaling, 1, 5, 2, 1);

        // TotalPris
        lblTotalPris = new Label("Total pris: ");
        pane.add(lblTotalPris, 0, 7);

        txfTotaltPris = new TextField(String.valueOf(order.orderPris()));
        pane.add(txfTotaltPris, 1, 7);
        txfTotaltPris.setEditable(false);
        txfTotaltPris.setMaxWidth(100);

        btnFærdigBestilling = new Button("Afslut salg");
        pane.add(btnFærdigBestilling, 0, 8);
        btnFærdigBestilling.setOnAction(event -> afslutBestllingAction());


    }

    // -------------------------------------------------------------------------------------------------------
    private void actionBetalingsTypeChanged() {

        BetalingsType betalingsTypee = (BetalingsType) betalingsGroup.getSelectedToggle().getUserData();

        if (betalingsTypee != null) {
            if (BetalingsType.KLIPPEKORT.equals(betalingsTypee)) {
                txfRabat.setDisable(true);
                txfTotaltPris.setText(String.valueOf(order.orderKlipPris()));
                txfAftaltPris.setDisable(true);
            } else {
                txfRabat.setDisable(false);
                txfAftaltPris.setDisable(false);
                txfTotaltPris.setText(String.valueOf(order.prisWithRabat()));
            }

            order.setBetalingsType(betalingsTypee);
        }
    }

    //---------------------------------------------------------------------------------------
    public void opretKundeAction() {
        SalgOpretKundeWindow salgOpretKundeWindow = new SalgOpretKundeWindow();
        salgOpretKundeWindow.showAndWait();

        cBoxKunde.getItems().clear();
        cBoxKunde.getItems().add(null);
        cBoxKunde.getItems().addAll(controller.getKunder());
    }

    public void rabatAction() {
        try {
            if (!txfRabat.isDisable() && !txfRabat.getText().isEmpty()) {
                int rabat = Integer.parseInt(txfRabat.getText().trim());
                order.setRabatStrategy(new ProcentRabat(rabat) {
                });
                updateControl();

            } else if (txfRabat.getText().isEmpty()) {
                txfTotaltPris.setText(String.valueOf(order.prisWithRabat()));

            }

        } catch (NumberFormatException e) {
            errorAlert = new Alert(Alert.AlertType.ERROR, "Det skal være et tal");
            errorAlert.show();
        }

    }

    public void aftaltPrisAction() {
//        samletpris - Aftaltpris = discount fixed pris
        try {
            if (!txfAftaltPris.getText().isEmpty()) {
                double rabat1 = order.orderPris() - Integer.parseInt(txfAftaltPris.getText().trim());
                order.setRabatStrategy(new AmountRabat(rabat1, order.orderPris()) {
                });
                updateControl();
            } else if (txfAftaltPris.getText().isEmpty()) {
                txfTotaltPris.setText(String.valueOf(order.prisWithRabat()));
            }

        } catch (NumberFormatException e) {
            errorAlert = new Alert(Alert.AlertType.ERROR, "Det skal være et tal");
            errorAlert.show();
        }
    }

    public void updateControl() {
        txfTotaltPris.setText(String.valueOf(order.prisWithRabat()));
    }

    //------------------------------------------------------------------------------------------------------------
    // Opret bestilling action
    public void afslutBestllingAction() {
        Kunde kunde = cBoxKunde.getValue();
        BetalingsType betalingsType = (BetalingsType) betalingsGroup.getSelectedToggle().getUserData();


        if (betalingsType != null) {
            order.setBetalingsType(betalingsType);

        } else if (betalingsType == null) {
            errorAlert = new Alert(Alert.AlertType.ERROR, "Vælge betalingsmåde!");
            errorAlert.show();
        }
        order.setOrderStatus(OrderStatus.AFSLUTTET);
        order.setBetalingsDato(LocalDate.now());
        if (kunde != null) {
            order.setKunde(kunde);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Afsluttet salg");
            alert.setHeaderText("Kvittering");
            alert.setContentText("Samlet beløb: " + String.valueOf(order.prisWithRabat()) + "\nBetalings type: " + betalingsType + "\nStatus: " + order.getOrderStatus() + "\nAfsluttet Dato: " + order.getBetalingsDato() + "\nKunde: " + kunde.toString());
            Optional<ButtonType> result = alert.showAndWait();
            hide();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Afsluttet salg");
            alert.setHeaderText("Kvittering");
            alert.setContentText("Samlet beløb: " + String.valueOf(order.prisWithRabat()) + "\nBetalings type: " + betalingsType + "\nStatus: " + order.getOrderStatus() + "\nAfsluttet Dato: " + order.getBetalingsDato());
            Optional<ButtonType> result = alert.showAndWait();
            hide();
        }
    }


}
