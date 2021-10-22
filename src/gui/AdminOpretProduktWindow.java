package gui;

import controller.Controller;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Produkt;
import model.ProduktType;
import model.Produktgruppe;
import storage.Storage;

public class AdminOpretProduktWindow extends Stage {
    private AdminProduktPane adminProduktPane;
    private Produktgruppe produktgruppe;
    private ProduktType produktType;

    private Label lblProduktgruppe;
    private Label lblProduktType;
    private Label lblNavn;

    private TextField txfProduktgruppe;
    private TextField txfProduktType;
    private TextField navnInput;

    private Button btnOpret;
    private VBox vBox;
    private Alert errorAlert;

    public AdminOpretProduktWindow(AdminProduktPane adminProduktPane, Produktgruppe produktgruppe, ProduktType produktType) {
        this.adminProduktPane = adminProduktPane;
        this.produktgruppe = produktgruppe;
        this.produktType = produktType;

        this.setMinHeight(200);
        this.setMinWidth(300);

        this.setTitle("Opret produkt");
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

        lblProduktgruppe = new Label("Produktgruppe: ");
        txfProduktgruppe = new TextField(produktgruppe.getNavn());
        txfProduktgruppe.setBackground(Background.EMPTY);
        txfProduktgruppe.setEditable(false);

        lblProduktType = new Label("Produkttype: ");
        txfProduktType = new TextField(produktType.getNavn());
        txfProduktType.setBackground(Background.EMPTY);
        txfProduktType.setEditable(false);


        lblNavn = new Label("Navn på produktet: ");
        navnInput = new TextField();
        btnOpret = new Button("Opret");
        btnOpret.setOnAction(event -> this.opretProdukt(produktgruppe, produktType, navnInput.getText().trim()));

        vBox = new VBox();
        vBox.getChildren().add(btnOpret);

        pane.add(lblProduktgruppe, 0, 0);
        pane.add(txfProduktgruppe, 1, 0);

        pane.add(lblProduktType, 0, 1);
        pane.add(txfProduktType, 1, 1);

        pane.add(lblNavn, 0, 2);
        pane.add(navnInput, 1, 2);
        pane.add(vBox, 1, 3);



    }

    private void opretProdukt(Produktgruppe produktgruppe, ProduktType produktType, String navn) {
        if (navn.equals("")) {
            errorAlert = new Alert(Alert.AlertType.ERROR, "Manglende navn.");
            errorAlert.show();
        }
        else {
            //Errorhandling fra Produktgruppe i modellen
            try {
                Controller.createProdukt(produktgruppe, produktType, navn);
                this.adminProduktPane.updateLwProduktgrupper();
            } catch (IllegalArgumentException e) {
                errorAlert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                errorAlert.show();
            }

        }
    }

}
