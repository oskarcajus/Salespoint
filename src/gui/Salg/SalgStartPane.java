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
import model.Pris;
import model.Produkt;
import model.SalgsSituation;

public class SalgStartPane extends GridPane {
    private final Controller controller = Controller.getController();

    private SalgsSituation salgsSituation;
    private ListView lvwProdukter, lvwOrdreList;
    private Label lblProdukter, lblOrdreList, lblSamletBeløb, lblAddProdukt,lblSpace;
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

        lvwProdukter = new ListView();
        Font font = Font.font("Calibri", FontWeight.BOLD, FontPosture.ITALIC, 15);
        String produktGruppe = "";

        for (Pris pr : salgsSituation.getPriser()) {

            if (!pr.getProdukt().getProduktgruppe().getNavn().equals(produktGruppe)) {
                produktGruppe = pr.getProdukt().getProduktgruppe().getNavn();
                Text txt = new Text(produktGruppe + ":");
                txt.setFont(font);
                lvwProdukter.getItems().add(txt);
            }
            lvwProdukter.getItems().add(pr);
        }
        lvwProdukter.getSelectionModel().selectedItemProperty().addListener(obs -> {
            if (lvwProdukter.getSelectionModel().getSelectedItem() instanceof Text)
                lvwProdukter.getSelectionModel().selectNext();
        });
        this.add(lvwProdukter, 0, 1, 1, 5);
        lvwProdukter.setPrefWidth(300);
        lvwProdukter.setPrefHeight(500);


        // Ordre line
        lblOrdreList = new Label("Ordrelist:");
        this.add(lblOrdreList, 3, 0);

        lvwOrdreList = new ListView();
        this.add(lvwOrdreList, 3, 1, 1, 5);
        lvwOrdreList.setPrefWidth(300);
        lvwOrdreList.setPrefHeight(500);


        // Samlet beløb
        VBox vboxSB = new VBox(20);
        vboxSB.setAlignment(Pos.CENTER_LEFT);
        this.add(vboxSB,1,4,1,1);

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
//        btnFjernProdukt.setOnAction(event -> this.updateActionKonference());


        // Tilføje antal produkter
        VBox xboxAddProdukt = new VBox(20);
        xboxAddProdukt.setAlignment(Pos.CENTER_LEFT);
        this.add(xboxAddProdukt,1,2,1,1);

        lblAddProdukt = new Label("Antal: ");
        xboxAddProdukt.getChildren().add(lblAddProdukt);

        txfAddProdukter = new TextField();
        xboxAddProdukt.getChildren().add(txfAddProdukter);

        btnAddProdukter = new Button("Add");
        btnFjernProdukt.setOnAction(event -> this.testAction());
        xboxAddProdukt.getChildren().add(btnAddProdukter);

        VBox vosa = new VBox(60);
        this.add(vosa,1,3,1,1);
        lblSpace = new Label("\n\n\n");
        vosa.getChildren().add(lblSpace);

    }

    public void testAction (){



            int antal = Integer.parseInt(txfAddProdukter.getText().trim());
            if (antal >0) {
                for (int i = 0; i < antal; i++){
                    lvwOrdreList.getItems().add(lvwProdukter.getSelectionModel().getSelectedItem());
                }

            } else {
                errorAlert = new Alert(Alert.AlertType.ERROR, "Du skal skrive antallet!");
                errorAlert.show();
            }

            ;
    }

}
