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
import model.ProduktType;

public class AdminRedigerProduktTypeWindow extends Stage {
    private final Controller controller = Controller.getController();

    private AdminProduktPane adminProduktPane;
    private ProduktType produktType;
    private Label lblNavn;
    private TextField navnInput;
    private Button btnRediger;
    private VBox vBox;
    private Alert errorAlert;

    public AdminRedigerProduktTypeWindow(AdminProduktPane adminProduktPane, ProduktType produktType) {
        this.adminProduktPane = adminProduktPane;
        this.produktType = produktType;

        this.setMinHeight(200);
        this.setMinWidth(300);


        this.setTitle("Rediger produkttype");
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

        lblNavn = new Label("Nyt navn på produkttype: ");
        navnInput = new TextField();
        btnRediger = new Button("Rediger");
        btnRediger.setOnAction(event -> this.redigerProduktType(
                this.produktType,
                navnInput.getText().trim()));

        vBox = new VBox();
        vBox.getChildren().add(btnRediger);

        pane.add(lblNavn, 0, 1);
        pane.add(navnInput, 1, 1);
        pane.add(vBox, 1, 2);

    }

    private void redigerProduktType(ProduktType produktType, String navn) {
        if (navn.equals("")) {
            errorAlert = new Alert(Alert.AlertType.ERROR, "Manglende navn.");
            errorAlert.show();
        }
        else {
            try {
                controller.renameProduktType(produktType, navn);
                this.adminProduktPane.updateLwProduktTyper();
            } catch (IllegalArgumentException e) {
                errorAlert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                errorAlert.show();
            }
        }
    }
}
