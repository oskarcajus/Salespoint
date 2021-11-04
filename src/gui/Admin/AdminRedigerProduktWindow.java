package gui.Admin;

import controller.Controller;
import javafx.collections.FXCollections;
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

public class AdminRedigerProduktWindow extends Stage {
    private final Controller controller = Controller.getController();

    private AdminProduktPane adminProduktPane;
    private Produkt produkt;

    private Label lblProduktgruppe;
    private ComboBox<Produktgruppe> cbProduktgruppe;

    private Label lblProduktType;
    private ComboBox<ProduktType> cbProduktType;

    private Label lblNavn;
    private TextField navnInput;

    private Button btnRediger;
    private VBox vBox;
    private Alert errorAlert;

    public AdminRedigerProduktWindow(AdminProduktPane adminProduktPane, Produkt produkt) {
        this.adminProduktPane = adminProduktPane;
        this.produkt = produkt;

        this.setMinHeight(200);
        this.setMinWidth(300);


        this.setTitle("Rediger produkt");
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

        lblProduktgruppe = new Label("Produktgruppe:");
        cbProduktgruppe = new ComboBox<>();
        cbProduktgruppe.setItems(FXCollections.observableArrayList(controller.getProduktgrupper()));
        cbProduktgruppe.getSelectionModel().select(controller.getProduktgruppeFromProdukt(this.produkt));

        lblProduktType = new Label("Produkttype:");
        cbProduktType = new ComboBox<>();
        cbProduktType.setItems(FXCollections.observableArrayList(controller.getProduktTyper()));
        cbProduktType.getSelectionModel().select(controller.getProduktTypeFromProdukt(this.produkt));

        lblNavn = new Label("Nyt navn på produkt: ");
        navnInput = new TextField();
        btnRediger = new Button("Rediger");
        btnRediger.setOnAction(event -> this.redigerProdukt(produkt,
                navnInput.getText().trim(),
                cbProduktgruppe.getSelectionModel().getSelectedItem(),
                cbProduktType.getSelectionModel().getSelectedItem()));

        vBox = new VBox();
        vBox.getChildren().add(btnRediger);

        pane.add(lblProduktgruppe, 0, 0);
        pane.add(cbProduktgruppe, 1, 0);

        pane.add(lblProduktType, 0, 1);
        pane.add(cbProduktType, 1, 1);

        pane.add(lblNavn, 0, 2);
        pane.add(navnInput, 1, 2);
        pane.add(vBox, 1, 3);

    }

    private void redigerProdukt(Produkt produkt, String navn, Produktgruppe produktgruppe, ProduktType produktType) {
        if (navn.equals("")) {
            errorAlert = new Alert(Alert.AlertType.ERROR, "Manglende navn.");
            errorAlert.show();
        }
        else {
            try {
                controller.redigerProdukt(produkt, navn, produktgruppe, produktType);
                this.adminProduktPane.updateLwProdukter();
                hide();
            } catch (IllegalArgumentException e) {
                errorAlert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                errorAlert.show();
            }

        }
    }

}
