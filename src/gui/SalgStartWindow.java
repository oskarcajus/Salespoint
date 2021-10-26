package gui;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Produkt;
import model.SalgsSituation;

public class SalgStartWindow extends Stage {
    private SalgsSituation salgsSituation;
    private ListView lvwProdukter, lvwOrdreList;
    private Label lblProdukter, lblOrdreList, lblSamletBeløb, lblAftaltBeløb;
    private Button btnOpretOrdre;
    private Controller controller;
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
        pane.add(lvwProdukter, 0, 1, 1, 5);
        lvwProdukter.setPrefWidth(200);
        lvwProdukter.setPrefHeight(500);
        lvwProdukter.getItems().setAll(salgsSituation.getPriser());


        // Ordre line
        lblOrdreList = new Label("Ordrelist:");
        pane.add(lblOrdreList, 3, 0);

        lvwOrdreList = new ListView();
        pane.add(lvwOrdreList, 3, 1, 1, 5);
        lvwOrdreList.setPrefWidth(200);
        lvwOrdreList.setPrefHeight(500);
        /* TODO Den her skal ændres ASAP; når de nye ordre, ordrelinje klasser er lavet */
        lvwOrdreList.getItems().setAll(salgsSituation.getNavn());

        // Samlet beløb
        lblSamletBeløb = new Label("Samlet beløb:");
        pane.add(lblSamletBeløb,5,0);

        txfSamletBeløb = new TextField("00.0");
        pane.add(txfSamletBeløb,5,1);
        txfSamletBeløb.setPrefWidth(100);
        txfSamletBeløb.setEditable(false);

        // Aftalt Beløb
        lblAftaltBeløb = new Label("Aftalt beløb:");
        pane.add(lblAftaltBeløb,5,6);

        txfAftaltBeløb = new TextField("00.0");
        pane.add(txfAftaltBeløb,5,11);
        txfAftaltBeløb.setPrefWidth(200);

    }


}
