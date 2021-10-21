package gui;

import controller.Controller;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    private ComboBox<Produktgruppe> cbProduktgruppe;
    private ComboBox<ProduktType> cbProduktType;
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
        cbProduktgruppe = new ComboBox<Produktgruppe>();
        cbProduktgruppe.setItems((ObservableList<Produktgruppe>) Storage.getProduktgrupper());
        if (this.produktgruppe != null) {
            cbProduktgruppe.setValue(this.produktgruppe);
        }

        lblProduktType = new Label("Produkttype: ");
        cbProduktType = new ComboBox<ProduktType>();
        cbProduktType.setItems((ObservableList<ProduktType>) Storage.getProduktTyper());
        if (this.produktType != null) {
            cbProduktType.setValue(this.produktType);
        }


        lblNavn = new Label("Navn på produktet");
        navnInput = new TextField();
        btnOpret = new Button("Opret");
        btnOpret.setOnAction(event -> Controller.createProdukt(cbProduktgruppe.getValue(), cbProduktType.getValue(), navnInput.getText().trim()));

        vBox = new VBox();
        vBox.getChildren().add(btnOpret);

        pane.add(lblNavn, 0, 1);
        pane.add(navnInput, 1, 1);
        pane.add(vBox, 1, 2);

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
