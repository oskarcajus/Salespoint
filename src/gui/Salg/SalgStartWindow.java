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
import model.Pris;
import model.Produkt;
import model.SalgsSituation;

public class SalgStartWindow extends Stage {
    private final Controller controller = Controller.getController();

    private SalgsSituation salgsSituation;
    private ListView lvwProdukter, lvwOrdreList;
    private Label lblProdukter, lblOrdreList, lblSamletBeløb, lblAftaltBeløb;
    private Button btnOpretOrdre;
    private TextField txfSamletBeløb, txfBetalingStatus, txfAftaltBeløb;


    public SalgStartWindow(SalgsSituation salgsSituation) {
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

        for (Pris pr : salgsSituation.getPriser()) {

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
        pane.add(lvwProdukter, 0, 1, 1, 5);
        lvwProdukter.setPrefWidth(300);
        lvwProdukter.setPrefHeight(500);


        // Ordre line
        lblOrdreList = new Label("Ordrelist:");
        pane.add(lblOrdreList, 3, 0);

        lvwOrdreList = new ListView();
        pane.add(lvwOrdreList, 3, 1, 1, 5);
        lvwOrdreList.setPrefWidth(300);
        lvwOrdreList.setPrefHeight(500);
        /* TODO Den her skal ændres ASAP; når de nye ordre, ordrelinje klasser er lavet */
        lvwOrdreList.getItems().setAll(salgsSituation.getNavn());

        // Samlet beløb
        lblSamletBeløb = new Label("Samlet beløb:");
        pane.add(lblSamletBeløb,4,6);

        txfSamletBeløb = new TextField("00.0");
        pane.add(txfSamletBeløb,5,6);
        txfSamletBeløb.setPrefWidth(100);
        txfSamletBeløb.setEditable(false);

        // Aftalt Beløb
        lblAftaltBeløb = new Label("Aftalt beløb:");
        pane.add(lblAftaltBeløb,5,0);

        txfAftaltBeløb = new TextField("00.0");
        pane.add(txfAftaltBeløb,5,1);
        txfAftaltBeløb.setPrefWidth(200);

        // Buttons
        HBox hbxButtonsOrdre = new HBox(40);
        pane.add(hbxButtonsOrdre, 3, 6, 1, 1);

        hbxButtonsOrdre.setPadding(new Insets(10, 0, 0, 0));
        hbxButtonsOrdre.setAlignment(Pos.BASELINE_CENTER);

        Button btnOpretBestilling = new Button("Opret Bestilling");
        hbxButtonsOrdre.getChildren().add(btnOpretBestilling);
//        btnOpretBestilling.setOnAction(event -> this.createActionKonference());

        Button btnFjernProdukt = new Button("Fjern produkt");
        hbxButtonsOrdre.getChildren().add(btnFjernProdukt);
//        btnFjernProdukt.setOnAction(event -> this.updateActionKonference());


    }


}
