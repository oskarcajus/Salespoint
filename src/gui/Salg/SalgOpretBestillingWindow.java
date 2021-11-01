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
import model.BetalingsType;
import model.Kunde;
import model.Order;

public class SalgOpretBestillingWindow extends Stage {
    private final Controller controller = Controller.getController();
    private Order order;


    private Label lblRabat, lblAftaltPris, lblOrderNummer,lblBetalingMetode, lblKunde, lblTotalPris;
    private ComboBox<Kunde> cBoxKunde;
    private TextField txfAftaltPris,txfRabat, txfTotaltPris;
    private Button btnFærdigBestilling, btnOpretKunde;
    private RadioButton rbBetalingsMetode;
    private ToggleGroup betalingsGroup;
    private Alert errorAlert;

    public SalgOpretBestillingWindow(Order order) {
            this.order = order;
        ;

        this.setMinHeight(200);
        this.setMinWidth(300);


        this.setTitle("Opret Bestilling for Ordre Nr; " + order.getOrderNr());
        GridPane pane = new GridPane();
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
        HBox hBoxKunde = new HBox();
        lblKunde = new Label("Vælge Kunde: ");
        pane.add(lblKunde, 0, 0);

        cBoxKunde = new ComboBox<Kunde>();
        cBoxKunde.getItems().add(null);
        cBoxKunde.getItems().addAll(FXCollections.observableArrayList(controller.getKunder()));
        cBoxKunde.setMinWidth(200);
////      TODO  cBoxKunde.getSelectionModel().selectedItemProperty().addListener(obs -> updateControls());
        hBoxKunde.getChildren().add(cBoxKunde);

        Button btnOpretKunde = new Button("Opret Kunde");
//       TODO btnOpretKunde.setOnAction(event -> actionOpretKunde());
        hBoxKunde.getChildren().add(btnOpretKunde);

        pane.add(hBoxKunde, 1, 0);

        // Aftalt Pris;
        lblAftaltPris = new Label("Aftalt Pris:");
        pane.add(lblAftaltPris,0,3);

        txfAftaltPris = new TextField();
        pane.add(txfAftaltPris,1,3);
        txfAftaltPris.setMaxWidth(100);
        //      TODO  txfAftaltPris.setOnKeyTyped(event -> actionRabatTyped());


        //Rabat Pris
        lblRabat = new Label("Rabat %");
        pane.add(lblRabat,0,4);

        txfRabat = new TextField();
        pane.add(txfRabat,1,4);
        txfRabat.setMaxWidth(100);
//      TODO  txfRabat.setOnKeyTyped(event -> actionRabatTyped());

        // Betalings metode
        lblBetalingMetode = new Label("Betalings Type:");
        pane.add(lblBetalingMetode, 0,5);

        HBox hbxBetaling = new HBox();
        for (BetalingsType bt : BetalingsType.values()) {
            RadioButton rb = new RadioButton();
            rb.setText(bt.name());
            rb.setPrefWidth(100);
            rb.setUserData(bt);
            rb.setOnAction(event -> actionBetalingsTypeChanged());
            rb.setToggleGroup(betalingsGroup);
            hbxBetaling.getChildren().add(rb);
        }
        pane.add(hbxBetaling,1,5);

        // TotalPris
        lblTotalPris = new Label("Total pris: ");
        pane.add(lblTotalPris,0,7);

        txfTotaltPris = new TextField(String.valueOf(order.orderPris()));
        pane.add(txfTotaltPris,1,7);
        txfTotaltPris.setEditable(false);
        txfTotaltPris.setMaxWidth(100);



    }
    private void actionBetalingsTypeChanged() {

        BetalingsType betalingsTypee = (BetalingsType) betalingsGroup.getSelectedToggle().getUserData();

        if (betalingsTypee != null) {
            if (BetalingsType.KLIPPEKORT.equals(betalingsTypee)) {
                txfRabat.setDisable(true);
            } else {
                txfRabat.setDisable(false);
            }

            order.setBetalingsType(betalingsTypee);
//            updateControls();
        }
    }

    public void updateControls(){

    }

}
