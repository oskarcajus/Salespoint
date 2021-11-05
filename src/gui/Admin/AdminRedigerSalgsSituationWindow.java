package gui.Admin;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.SalgsSituation;

public class AdminRedigerSalgsSituationWindow extends Stage {
    private final Controller controller = Controller.getController();

    private AdminPrisPane adminPrisPane;
    private SalgsSituation salgsSituation;

    private Label lblNavn;
    private TextField navnInput;

    private Button btnRediger;
    private VBox vBox;
    private Alert errorAlert;

    public AdminRedigerSalgsSituationWindow(AdminPrisPane adminPrisPane, SalgsSituation salgsSituation) {
        this.adminPrisPane = adminPrisPane;
        this.salgsSituation = salgsSituation;

        this.setMinHeight(200);
        this.setMinWidth(300);


        this.setTitle("Rediger salgssituation");
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

        lblNavn = new Label("Nyt navn på salgssituation: ");
        navnInput = new TextField();
        btnRediger = new Button("Rediger");
        btnRediger.setOnAction(event -> this.redigerSalgsSituation(salgsSituation,
                navnInput.getText().trim()));

        vBox = new VBox();
        vBox.getChildren().add(btnRediger);

        pane.add(lblNavn, 0, 0);
        pane.add(navnInput, 1, 0);
        pane.add(vBox, 2, 0);

    }

    private void redigerSalgsSituation(SalgsSituation salgsSituation, String navn) {
        if (navn.equals("")) {
            errorAlert = new Alert(Alert.AlertType.ERROR, "Manglende navn.");
            errorAlert.show();
        }
        else {
            try {
                controller.redigerSalgsSituation(salgsSituation, navn);
                this.adminPrisPane.updateLwSalgsSituationer();
                hide();
            } catch (IllegalArgumentException e) {
                errorAlert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                errorAlert.show();
            }

        }
    }
}
