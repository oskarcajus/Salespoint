package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.*;
import storage.*;

public class AdminProduktPane extends GridPane {

    //Listviews
    private ListView<Produkt> lwProdukter;
    private ListView<Produktgruppe> lwProduktgrupper;

    //Labels
    private final Label lblProduktgrupper = new Label("Produktgrupper:");
    private final Label lblProdukter = new Label("Produkter:");

    //Buttons

    HBox hBoxProduktgrupper = new HBox();
    private final Button btnOpretProduktgruppe = new Button("Opret");
    private final Button btnRedigerProduktgruppe = new Button("Rediger");
    private final Button btnSletProduktgruppe = new Button("Slet");

    HBox hBoxProdukt = new HBox();
    private final Button btnOpretProdukt = new Button("Opret");
    private final Button btnRedigerProdukt = new Button("Rediger");
    private final Button btnSletProdukt = new Button("Slet");


    public AdminProduktPane() {
        this.setGridLinesVisible(false);
        this.initContent();
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(20));
    }

    public void initContent() {

        //Listviews
        lwProduktgrupper = new ListView<>();
        this.add(lwProduktgrupper, 0, 1);
        lwProdukter = new ListView<>();
        this.add(lwProdukter, 1, 1);

        //Labels
        this.add(lblProduktgrupper, 0,0);
        this.add(lblProdukter, 1,0);

        //Buttons
        hBoxProduktgrupper.getChildren().addAll(btnOpretProduktgruppe, btnRedigerProduktgruppe, btnSletProduktgruppe);
        hBoxProduktgrupper.setAlignment(Pos.CENTER);
        hBoxProduktgrupper.setSpacing(20);
        this.add(hBoxProduktgrupper, 0, 2);

        hBoxProdukt.getChildren().addAll(btnOpretProdukt, btnRedigerProdukt, btnSletProdukt);
        hBoxProdukt.setAlignment(Pos.CENTER);
        hBoxProdukt.setSpacing(20);
        this.add(hBoxProdukt, 1, 2);

    }
}
