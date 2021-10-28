package gui.Salg;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.SalgsSituation;

public class SalgButikStartWindow extends Stage {
    private SalgsSituation salgsSituation;
    private ListView lvwProdukter, lvwOrdreList;
    private Label lblProdukter, lblOrdreList, lblSamletBeløb, lblAftaltBeløb;
    private Button btnOpretOrdre;
    private Controller controller;
    private TextField txfSamletBeløb, txfBetalingStatus, txfAftaltBeløb;

    public SalgButikStartWindow(SalgsSituation salgsSituation) {
        this.salgsSituation = salgsSituation;

        this.setMinHeight(750);
        this.setMinWidth(750);

        this.setTitle("Salg for " + salgsSituation);
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(20));

        Scene scene = new Scene(pane);
        this.setScene(scene);

        this.initContent(pane);
    }

    private void initContent(BorderPane pane) {
        TabPane tabPane = new TabPane();
        this.initTabPane(tabPane);
        pane.setCenter(tabPane);
    }

    private void initTabPane(TabPane tabPane) {

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tabSalg = new Tab("Normal Salg");
        Tab tabUdlejning = new Tab("Udlejning");
        Tab tabKunde = new Tab("Kunder");
        Tab tabRundvisning = new Tab("Rundvisning");


        SalgStartPane salgStartPane = new SalgStartPane(salgsSituation);
        tabSalg.setContent(salgStartPane);

        SalgKundePane salgKundePane = new SalgKundePane();
        tabKunde.setContent(salgKundePane);

        // Add Ordre, produkter, pristLister til TabPane
        tabPane.getTabs().addAll(tabSalg,tabKunde);


    }


    }

