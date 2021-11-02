package gui.Salg;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.LightBase;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.*;

import java.time.LocalDate;

public class SalgNewStartPane extends GridPane {
    private final Controller controller = Controller.getController();
    private Order currentOrder;

    private SalgsSituation salgsSituation;
    private ListView lvwProduktPriser;
    private ListView<OrderLine> lvwOrderLines;

    private Label lblProdukter, lblOrdreList, lblSamletBeløb, lblAntal, lblPris, lblPantPris, lblKlipPris, lblOrdreNr, lblOrdrerTxt;
    private Button btnOpretBestilling, btnTilføjeProdukt, btnSletProdukt, btnRedigerProdukt;
    private TextField txfSamletBeløb, txfAntal, txfPris, txfKlipPris, txfPantPris;
    private Alert errorAlert;


    public SalgNewStartPane(SalgsSituation salgsSituation) {
        this.salgsSituation = salgsSituation;
        this.setGridLinesVisible(false);
        this.initContent();
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(20));
    }

    public void initContent() {
        // Left side
        // OrdrerNr
        HBox hBoxOrderNr = new HBox(10);
        this.add(hBoxOrderNr, 0, 0);

        lblOrdrerTxt = new Label("Ordrer nr: ");
        hBoxOrderNr.getChildren().add(lblOrdrerTxt);

        lblOrdreNr = new Label(Integer.toString(getCurrentOrder().getOrderNr()));
        hBoxOrderNr.getChildren().add(lblOrdreNr);

        // Produkt
        lblProdukter = new Label("Produkter: ");
        this.add(lblProdukter, 0, 1);

        lvwProduktPriser = new ListView();
        Font font = Font.font("Calibri", FontWeight.BOLD, FontPosture.ITALIC, 15);
        String produktGruppe = "";

        for (Pris pr : salgsSituation.getPriser()) {

            if (!pr.getProdukt().getProduktgruppe().getNavn().equals(produktGruppe)) {
                produktGruppe = pr.getProdukt().getProduktgruppe().getNavn();
                Text txt = new Text(produktGruppe + ":");
                txt.setFont(font);
                lvwProduktPriser.getItems().add(txt);
            }
            lvwProduktPriser.getItems().add(pr);
        }
        lvwProduktPriser.getSelectionModel().selectedItemProperty().addListener(obs -> {
            if (lvwProduktPriser.getSelectionModel().getSelectedItem() instanceof Text)
                lvwProduktPriser.getSelectionModel().selectNext();
        });
        this.add(lvwProduktPriser, 0, 2, 1, 6);
        lvwProduktPriser.setPrefWidth(300);
        lvwProduktPriser.setPrefHeight(500);

        // Middel
        // Priserne pris,pant,klip, antal...
        lblPris = new Label("Pris: ");
        this.add(lblPris, 1, 2);

        lblKlipPris = new Label("Klippris: ");
        this.add(lblKlipPris, 1, 3);

        lblPantPris = new Label("Pantpris: ");
        this.add(lblPantPris, 1, 4);

        lblAntal = new Label("Antal: ");
        this.add(lblAntal, 1, 5);

        // TextFiels Vbox
        txfPris = new TextField();
        this.add(txfPris, 2, 2);
        txfPris.setText("0");

        txfKlipPris = new TextField();
        this.add(txfKlipPris, 2, 3);
        txfKlipPris.setText("0");

        txfPantPris = new TextField();
        this.add(txfPantPris, 2, 4);
        txfPantPris.setText("0");

        txfAntal = new TextField();
        txfAntal.setText("1");
        this.add(txfAntal, 2, 5);

        // Buttons
        btnTilføjeProdukt = new Button("Tilføj");
        this.add(btnTilføjeProdukt, 1, 6);
        btnTilføjeProdukt.setOnAction(event -> this.tilføjeProduktAction());



        // samlet pris
        lblSamletBeløb = new Label("Samlet pris: ");
        this.add(lblSamletBeløb, 1, 7);

        txfSamletBeløb = new TextField();
        this.add(txfSamletBeløb, 2, 7);
        txfSamletBeløb.setEditable(false);

        // Opretbestilling
        btnOpretBestilling = new Button("Gå til betaling");
        this.add(btnOpretBestilling, 1, 8);
        btnOpretBestilling.setOnAction(event -> this.opretBestilling());

        // Right side
        //OrdrerLine
        lblOrdreList = new Label("OrderLine:");
        this.add(lblOrdreList, 4, 1);

        lvwOrderLines = new ListView();
        this.add(lvwOrderLines, 4, 2, 1, 6);
        lvwOrderLines.setPrefWidth(300);
        lvwOrderLines.setPrefHeight(500);

        // Rediger og slet knapper
        HBox hBoxRS = new HBox(10);
        hBoxRS.setAlignment(Pos.CENTER);
        this.add(hBoxRS, 4, 8);

        btnRedigerProdukt = new Button("Rediger");
        btnSletProdukt = new Button("Fjern");
        btnSletProdukt.setOnAction(event -> this.fjernOrdreLineAction(lvwOrderLines.getSelectionModel().getSelectedItem()));
        hBoxRS.getChildren().addAll(btnRedigerProdukt, btnSletProdukt);

    }

    //------------------------------[Metoder]--------------------------------------------------
    //Add antal
    public void tilføjeProduktAction() {
        Order order = getCurrentOrder();
        Object o = lvwProduktPriser.getSelectionModel().getSelectedItem();

        try {
            int antal = Integer.parseInt(txfAntal.getText().trim());
            double pris = Double.parseDouble(txfPris.getText().trim());
            double pantPris = Double.parseDouble(txfPantPris.getText().trim());
            int klipPris = Integer.parseInt(txfKlipPris.getText().trim());
            if (o != null) {
                if (o instanceof Pris) {
                    Pris produkt = (Pris) o;
                    if (produkt != null) {
                        //Ændring i priser
                        if (pris >0){
                          produkt.setPris(pris);  
                        } 
                        if (pantPris >0){
                            produkt.setPantPris(pantPris);
                        }
                        if (klipPris >0) {
                            produkt.setKlipPris(klipPris);
                        }
                        // Opret order
                        if (antal >0){
                            order.createOrderLine(antal,produkt);
                        } else {
                            errorAlert = new Alert(Alert.AlertType.ERROR, "Antallet skal være mere end 0");
                            errorAlert.show();
                        }
                    }
                }
            } else {
                errorAlert = new Alert(Alert.AlertType.ERROR, "Du skal vælge en produkt!");
                errorAlert.show();
            }
            updateOrderLines();
            updateSamletBeløb();
        } catch (NumberFormatException e) {
            errorAlert = new Alert(Alert.AlertType.ERROR, "Det skal være et tal");
            errorAlert.show();
        }
    }


    //---------------------------------------------------------------------------------------------------------
    // Fjernes produkter fra Ordrer Listview
    public void fjernOrdreLineAction(OrderLine ol) {
        if (ol != null) {
            controller.removeOrderLine(currentOrder, ol);
        } else {
            errorAlert = new Alert(Alert.AlertType.ERROR, "Du skal vælge en produkt i ordrelisten!");
            errorAlert.show();
        }
        updateSamletBeløb();
        updateOrderLines();

    }

    //---------------------------------------------------------------------------------------------------------
    // Updates
    public void updateSamletBeløb() {
        double pris = getCurrentOrder().orderPris();
        String s = String.valueOf(pris);
        txfSamletBeløb.setText(s);
    }

    public void updateOrderLines() {
        lvwOrderLines.getItems().setAll(getCurrentOrder().getOrderLines());
        lblOrdreNr = new Label(Integer.toString(getCurrentOrder().getOrderNr()));

    }

    // TODO rediger metoden!!


    //---------------------------------------------------------------------------------------------------------
    // Lav ny eller get order funktion
    public Order getCurrentOrder() {
        if (currentOrder == null || currentOrder.getOrderStatus().equals(OrderStatus.AFSLUTTET)) {
            currentOrder = controller.createOrder(controller.getOrders().size() + 1, LocalDate.now());
            lblOrdreNr = new Label(Integer.toString(getCurrentOrder().getOrderNr()));
        }
        return currentOrder;
    }

    //------------------------------------------------------------------------------------------------------------------------------------
    // Opret bestilling
    public void opretBestilling() {
        if (getCurrentOrder() != null) {
            if (getCurrentOrder().getOrderLines().size() > 0) {
                SalgOpretBestillingWindow salgOpretBestillingWindow = new SalgOpretBestillingWindow(getCurrentOrder());
                salgOpretBestillingWindow.showAndWait();

                updateOrderLines();

            } else {
                errorAlert = new Alert(Alert.AlertType.ERROR, "Du kan ikke opret en bestilling uden at vælge et produkt");
                errorAlert.show();
            }
        } else {
            errorAlert = new Alert(Alert.AlertType.ERROR, "Du skal først oprette en ordre, ved at tilføje et produkt!!");
            errorAlert.show();
        }
    }
}
