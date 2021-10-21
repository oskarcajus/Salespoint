package gui;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminOpretProduktTypeWindow extends Stage {
    private AdminProduktPane adminProduktPane;
    private Label lblNavn;
    private TextField navnInput;
    private Button btnOpret;
    private VBox vBox;
    private Alert errorAlert;

    public AdminOpretProduktTypeWindow(AdminProduktPane adminProduktPane) {
        this.adminProduktPane = adminProduktPane;

        this.setMinHeight(200);
        this.setMinWidth(300);


        this.setTitle("Opret produkttype");
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

        lblNavn = new Label("Navn på produkttype: ");
        navnInput = new TextField();
        btnOpret = new Button("Opret");
        btnOpret.setOnAction(event -> this.opretProduktType(navnInput.getText().trim()));

        vBox = new VBox();
        vBox.getChildren().add(btnOpret);

        pane.add(lblNavn, 0, 1);
        pane.add(navnInput, 1, 1);
        pane.add(vBox, 1, 2);

    }

    private void opretProduktType(String navn) {
        if (navn.equals("")) {
            errorAlert = new Alert(Alert.AlertType.ERROR, "Manglende navn.");
            errorAlert.show();
        }
        else {
            try {
                Controller.createProduktType(navn);
                this.adminProduktPane.updateLwProduktTyper();
            } catch (IllegalArgumentException e) {
                errorAlert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                errorAlert.show();
            }

        }
    }

}
