package gui.Admin;

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

public class AdminOpretSalgsSituationWindow extends Stage {
    private final Controller controller = Controller.getController();

    private AdminPrisPane adminPrisPane;
    private Label lblNavn;
    private TextField navnInput;
    private Button btnOpret;
    private VBox vBox;
    private Alert errorAlert;

    public AdminOpretSalgsSituationWindow(AdminPrisPane adminPrisPane) {

        this.adminPrisPane = adminPrisPane;

        this.setMinHeight(200);
        this.setMinWidth(300);

        this.setTitle("Opret salgssituation");
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

        lblNavn = new Label("Navn på salgssituation: ");
        navnInput = new TextField();
        btnOpret = new Button("Opret");
        btnOpret.setOnAction(event -> this.opretSalgsSituation(navnInput.getText().trim()));

        vBox = new VBox();
        vBox.getChildren().add(btnOpret);

        pane.add(lblNavn, 0, 1);
        pane.add(navnInput, 1, 1);
        pane.add(vBox, 1, 2);

    }

    private void opretSalgsSituation(String navn) {
        if (navn.equals("")) {
            errorAlert = new Alert(Alert.AlertType.ERROR, "Manglende navn.");
            errorAlert.show();
        }
        else {
            try {
                controller.createSalgsSituation(navn);
                this.adminPrisPane.updateLwSalgsSituationer();
                hide();
            } catch (IllegalArgumentException e) {
                errorAlert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                errorAlert.show();
            }

        }
    }

}

