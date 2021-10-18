package gui;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Produktgruppe;

public class AdminRedigerProduktgruppeWindow extends Stage {
    private AdminProduktPane adminProduktPane;
    private Produktgruppe produktgruppe;
    private Label lblNavn;
    private TextField navnInput;
    private Button btnRediger;
    private VBox vBox;
    private Alert errorAlert;

    public AdminRedigerProduktgruppeWindow(AdminProduktPane adminProduktPane, Produktgruppe produktgruppe) {
        this.adminProduktPane = adminProduktPane;
        this.produktgruppe = produktgruppe;

        this.setMinHeight(200);
        this.setMinWidth(300);


        this.setTitle("Rediger produktgruppe");
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

        lblNavn = new Label("Nyt navn på produktgruppe: ");
        navnInput = new TextField();
        btnRediger = new Button("Rediger");
        btnRediger.setOnAction(event -> this.redigerProduktgruppe(
                this.produktgruppe,
                navnInput.getText().trim()));

        vBox = new VBox();
        vBox.getChildren().add(btnRediger);

        pane.add(lblNavn, 0, 1);
        pane.add(navnInput, 1, 1);
        pane.add(vBox, 1, 2);

    }

    private void redigerProduktgruppe(Produktgruppe produktgruppe, String navn) {
        if (navn.equals("")) {
            errorAlert = new Alert(Alert.AlertType.ERROR, "Manglende navn.");
            errorAlert.show();
        }
        else {
            try {
                Controller.renameProduktgruppe(produktgruppe, navn);
                this.adminProduktPane.updateLwProduktgrupper();
            } catch (IllegalArgumentException e) {
                errorAlert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                errorAlert.show();
            }

        }
    }

}
