package gui.Salg;

import controller.Controller;
import gui.Admin.AdminProduktPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Kunde;

public class SalgOpretKundeWindow extends Stage {
    private final Controller controller = Controller.getController();

    private SalgKundePane salgKundePane;
    private Label lblNavn, lblTlf;
    private TextField txfNavn, txfTlf;
    private Button btnOpret;
    private CheckBox chbFirma;
    private Alert errorAlert;

    public SalgOpretKundeWindow(SalgKundePane salgKundePane) {

        this.salgKundePane = salgKundePane;

        this.setMinHeight(200);
        this.setMinWidth(300);


        this.setTitle("Opret produktgruppe");
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
        // Navn
        lblNavn = new Label("Navn: ");
        txfNavn = new TextField();
        pane.add(lblNavn, 0, 1);
        pane.add(txfNavn, 1, 1);

        // Tlf nummer
        lblTlf = new Label("Telefon nr: ");
        txfTlf = new TextField();
        pane.add(lblTlf, 0, 2);
        pane.add(txfTlf, 1, 2);


        //isFirma
        chbFirma = new CheckBox("Firma");
        pane.add(chbFirma, 0, 3);

        //Button
        btnOpret = new Button("Opret");
        pane.add(btnOpret, 1, 4);
        btnOpret.setOnAction(event -> this.opretKunde());

    }

    private void opretKunde() {
        String navn = txfNavn.getText().trim();
        String tlf = txfTlf.getText().trim();

        if (navn.isEmpty() || tlf.isEmpty()) {
            errorAlert = new Alert(Alert.AlertType.ERROR, "Manglende navn eller telefon.");
            errorAlert.show();
        } else if (chbFirma.isSelected()) {
            Kunde kunde = controller.createKunde(navn, tlf);
            kunde.setFirma(true);
            hide();
        } else {
            controller.createKunde(navn, tlf);
            hide();
        }



    }
}