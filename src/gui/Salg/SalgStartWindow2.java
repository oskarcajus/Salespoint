package gui.Salg;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.OrderLine;
import model.Pris;
import model.Produkt;
import model.SalgsSituation;

import java.util.List;

public class SalgStartWindow2 extends Stage {
    private final Controller controller = Controller.getController();

    private SalgsSituation salgsSituation;
    private ListView lvwProdukter;
    private ListView lvwOrdrelines;
    private Label lblProdukter, lblOrdrelinjer, lblSamletBeløb, lblAftaltBeløb, lblPris, lblPantPris, lblKlippris, lblAntal;
    private Button btnTilføjOL, btnRedigerOl, btnFjernOl, btnOpretBetaling;
    private HBox hBoxRedigerFjern;

    private TextField txfPris, txfPantpris, txfKlippris, txfSamletBeløb, txfAntal;


    public SalgStartWindow2(SalgsSituation salgsSituation) {
        this.salgsSituation = salgsSituation;


        this.setMinHeight(750);
        this.setMinWidth(750);


        this.setTitle("Salg for " + salgsSituation);
        GridPane pane = new GridPane();
        pane.setGridLinesVisible(false);
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        Scene scene = new Scene(pane);
        this.setScene(scene);

        this.initContent(pane);
    }

    private void initContent(GridPane pane) {
        // Produkt
        lblProdukter = new Label("Produkter: ");
        pane.add(lblProdukter, 0, 0);

        lvwProdukter = new ListView();
        Font font = Font.font("Calibri", FontWeight.BOLD, FontPosture.ITALIC, 15);
        String produktGruppe = "";

        for (Pris pr : controller.getPriserFromSalgsSituation(salgsSituation)) {

            if (!pr.getProdukt().getProduktgruppe().getNavn().equals(produktGruppe)) {
                produktGruppe = pr.getProdukt().getProduktgruppe().getNavn();
                Text txt = new Text(produktGruppe+":");
                txt.setFont(font);
                lvwProdukter.getItems().add(txt);
            }
            lvwProdukter.getItems().add(pr);
        }
        lvwProdukter.getSelectionModel().selectedItemProperty().addListener(obs -> {
            if (lvwProdukter.getSelectionModel().getSelectedItem() instanceof Text)
                lvwProdukter.getSelectionModel().selectNext();
        });
        lvwProdukter.setPrefWidth(300);
        lvwProdukter.setPrefHeight(500);
        pane.add(lvwProdukter, 0, 1, 1, 5);


        // Ordre line
        lblOrdrelinjer = new Label("Ordrelinjer:");
        pane.add(lblOrdrelinjer, 3, 0);

        lvwOrdrelines = new ListView();
        pane.add(lvwOrdrelines, 3, 1, 1, 5);
        lvwOrdrelines.setPrefWidth(300);
        lvwOrdrelines.setPrefHeight(500);

        //Textfields
        lblPris = new Label("Pris:");
        pane.add(lblPris, 1,1);
        txfPris = new TextField();
        pane.add(txfPris, 1, 2);

        lblPantPris = new Label("Pant:");
        pane.add(lblPantPris, 2, 1);
        txfPantpris = new TextField();
        pane.add(txfPantpris, 2, 2);

        lblKlippris = new Label("Klip:");
        pane.add(lblKlippris, 3, 1);
        txfKlippris = new TextField();
        pane.add(txfKlippris, 3, 2);

        lblAntal = new Label("Antal:");
        pane.add(lblAntal, 4, 1);
        txfAntal = new TextField();
        pane.add(txfAntal, 4, 2);


        //Buttons
        btnTilføjOL = new Button("Tilføj");
        pane.add(btnTilføjOL, 5, 1);

        hBoxRedigerFjern = new HBox();
        btnRedigerOl = new Button("Regiger");
        btnFjernOl = new Button("Slet");
        hBoxRedigerFjern.getChildren().addAll(btnRedigerOl, btnFjernOl);
        pane.add(hBoxRedigerFjern, 6, 3);


        // Samlet beløb
        lblSamletBeløb = new Label("Samlet beløb:");
        pane.add(lblSamletBeløb,7,0);
        txfSamletBeløb = new TextField("00.0");
        pane.add(txfSamletBeløb,7,1);
        txfSamletBeløb.setPrefWidth(100);
        txfSamletBeløb.setEditable(false);

        // Aftalt Beløb
//        lblAftaltBeløb = new Label("Aftalt beløb:");
//        pane.add(lblAftaltBeløb,5,0);

//        txfAftaltBeløb = new TextField("00.0");
//        pane.add(txfAftaltBeløb,5,1);
//        txfAftaltBeløb.setPrefWidth(200);

        // Buttons
//        HBox hbxButtonsOrdre = new HBox(40);
//        pane.add(hbxButtonsOrdre, 3, 6, 1, 1);

//        hbxButtonsOrdre.setPadding(new Insets(10, 0, 0, 0));
//        hbxButtonsOrdre.setAlignment(Pos.BASELINE_CENTER);

        btnOpretBetaling = new Button("Opret betaling");
        pane.add(btnOpretBetaling, 8, 1);
//        btnOpretBestilling.setOnAction(event -> this.createActionKonference());

//        Button btnFjernProdukt = new Button("Fjern produkt");
//        hbxButtonsOrdre.getChildren().add(btnFjernProdukt);
//        btnFjernProdukt.setOnAction(event -> this.updateActionKonference());

        //Add til pane



    }


}
