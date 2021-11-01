package gui.Salg;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

public class SalgStartPane extends GridPane {
    private final Controller controller = Controller.getController();
    private Order currentOrder;

    private SalgsSituation salgsSituation;
    private ListView lvwProduktPriser;
    private ListView<OrderLine> lvwOrderLines;
    
    private Label lblProdukter, lblOrdreList, lblSamletBeløb, lblAddProdukt, lblSpace;
    private Button btnOpretOrdre, btnAddProdukter;
    private TextField txfSamletBeløb, txfBetalingStatus, txfAddProdukter;
    private Alert errorAlert;


    public SalgStartPane(SalgsSituation salgsSituation) {
        this.salgsSituation = salgsSituation;
        this.setGridLinesVisible(false);
        this.initContent();
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(20));
    }

    public void initContent() {
        // Produkt
        lblProdukter = new Label("Produkter: ");
        this.add(lblProdukter, 0, 0);

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
        this.add(lvwProduktPriser, 0, 1, 1, 5);
        lvwProduktPriser.setPrefWidth(300);
        lvwProduktPriser.setPrefHeight(500);


        // Ordre line
        lblOrdreList = new Label("Ordrelist:");
        this.add(lblOrdreList, 3, 0);

        lvwOrderLines = new ListView();
        this.add(lvwOrderLines, 3, 1, 1, 5);
        lvwOrderLines.setPrefWidth(300);
        lvwOrderLines.setPrefHeight(500);


        // Samlet beløb
        VBox vboxSB = new VBox(20);
        vboxSB.setAlignment(Pos.CENTER_LEFT);
        this.add(vboxSB, 1, 4, 1, 1);

        lblSamletBeløb = new Label("Samlet beløb:");
        vboxSB.getChildren().add(lblSamletBeløb);

        txfSamletBeløb = new TextField("00.0");
        vboxSB.getChildren().add(txfSamletBeløb);
        txfSamletBeløb.setPrefWidth(100);
        txfSamletBeløb.setEditable(false);


        // Buttons
        HBox hbxButtonsOrdre = new HBox(40);
        this.add(hbxButtonsOrdre, 3, 6, 1, 1);

        hbxButtonsOrdre.setPadding(new Insets(10, 0, 0, 0));
        hbxButtonsOrdre.setAlignment(Pos.BASELINE_CENTER);

        Button btnOpretBestilling = new Button("Opret Bestilling");
        hbxButtonsOrdre.getChildren().add(btnOpretBestilling);
//        btnOpretBestilling.setOnAction(event -> this.createActionKonference());

        Button btnFjernProdukt = new Button("Fjern produkt");
        hbxButtonsOrdre.getChildren().add(btnFjernProdukt);
        btnFjernProdukt.setOnAction(event -> this.fjernProduktAction(lvwOrderLines.getSelectionModel().getSelectedItem()));


        // Tilføje antal produkter
        VBox xboxAddProdukt = new VBox(20);
        xboxAddProdukt.setAlignment(Pos.CENTER_LEFT);
        this.add(xboxAddProdukt, 1, 2, 1, 1);

        lblAddProdukt = new Label("Antal: ");
        xboxAddProdukt.getChildren().add(lblAddProdukt);

        txfAddProdukter = new TextField();
        txfAddProdukter.setText("1");
        xboxAddProdukt.getChildren().add(txfAddProdukter);

        btnAddProdukter = new Button("Add");
        btnAddProdukter.setOnAction(event -> this.addProduktAction());
        xboxAddProdukt.getChildren().add(btnAddProdukter);

        VBox vosa = new VBox(60);
        this.add(vosa, 1, 3, 1, 1);
        lblSpace = new Label("\n\n\n");
        vosa.getChildren().add(lblSpace);

    }

    //-----------------------------------------------------------------------------------------------------------------------------------------
    // Tilføjes produkter til den anden listview -------------------------------------------
    public void addProduktAction() {
        Order order = getCurrentOrder();
        Object o = lvwProduktPriser.getSelectionModel().getSelectedItem();
        int antal;
        if (txfAddProdukter.getText().trim().equals("")) {
            antal = 0;
        } else {
            antal = Integer.parseInt(txfAddProdukter.getText().trim());

            if (o != null) {
                if (o instanceof Pris) {
                    Pris pris = (Pris) o;
                    if (pris != null) {
                        if (antal == 0) {
                            order.createOrderLine(1, pris);
                        } else if (antal > 0) {
                            order.createOrderLine(antal, pris);
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
        }
        lvwOrderLines.getItems().setAll(order.getOrderLines());
        updateSamletBeløb();
    }
    //---------------------------------------------------------------------------------------------------------
    // Fjernes produkter fra Ordrer Listview

    public void fjernProduktAction(OrderLine ol) {
        if (ol != null) {
            controller.removeOrderLine(currentOrder, ol);
        } else {
            errorAlert = new Alert(Alert.AlertType.ERROR, "Du skal vælge en produkt i ordrelisten!");
            errorAlert.show();
        }
        updateSamletBeløb();
        updateLvwOrderLines();

    }

    public void updateSamletBeløb() {
        double pris = getCurrentOrder().orderPris();
        String s = String.valueOf(pris);
        txfSamletBeløb.setText(s);
    }

    public void updateLvwOrderLines() {
        lvwOrderLines.getItems().setAll(currentOrder.getOrderLines());
    }

    public Order getCurrentOrder() {
        if (currentOrder == null)
            currentOrder = controller.createOrder(controller.getOrders().size() + 1, LocalDate.now());
        return currentOrder;
    }
}
