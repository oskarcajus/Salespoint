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
import model.SalgsSituation;

public class AdminOpretPrisWindow extends Stage {
    private final Controller controller = Controller.getController();

    private AdminPrisPane adminPrisPane;
    private SalgsSituation salgsSituation;
    private Label lblProdukter;
    private ComboBox<Produkt> cbProdukter;
    private Label lblPris;
    private TextField prisInput;
    private Label lblKlikPris;
    private TextField klikPrisInput;
    private Label lblPantPris;
    private TextField pantPrisInput;
    private Button btnOpret;
    private VBox vBox;
    private Alert errorAlert;



    public AdminOpretPrisWindow(AdminPrisPane adminPrisPane, SalgsSituation salgsSituation) {
        this.adminPrisPane = adminPrisPane;
        this.salgsSituation = salgsSituation;

        this.setMinHeight(200);
        this.setMinWidth(300);


        this.setTitle("Opret pris");
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

        lblProdukter = new Label("Vælg et produkt:");
        cbProdukter = new ComboBox<>();
        ObservableList<Produkt> alleProdukter = FXCollections.observableArrayList(controller.getAllProdukter());
        for (Produkt p : controller.getAllProdukter()) {
            cbProdukter.getItems().add(p);
        }
//        cbProdukter.getItems().addAll(alleProdukter);

        lblPris = new Label("Pris: ");
        prisInput = new TextField();

        lblKlikPris = new Label("Klikpris: ");
        klikPrisInput = new TextField();

        lblPantPris = new Label("Pantpris: ");
        pantPrisInput = new TextField();

        btnOpret = new Button("Opret");
        btnOpret.setOnAction(event -> this.opretPris(
                cbProdukter.getSelectionModel().getSelectedItem(),
                prisInput.getText(),
                klikPrisInput.getText(),
                pantPrisInput.getText()));


        vBox = new VBox();
        vBox.getChildren().add(btnOpret);

        pane.add(lblProdukter, 0, 0);
        pane.add(cbProdukter, 1, 0);

        pane.add(lblPris, 0, 2);
        pane.add(prisInput, 1, 2);

        pane.add(lblKlikPris, 0, 3);
        pane.add(klikPrisInput, 1, 3);

        pane.add(lblPantPris, 0, 4);
        pane.add(pantPrisInput, 1, 4);

        pane.add(vBox, 1, 5);

    }

    private void opretPris(Produkt produkt, String inputPris, String inputKlikPris, String inputPantPris) {
        double pris;
        int klikPris;
        double pantPris;

        if (inputPris.equals("")) {
            pris = 0;
        }
        else {
            pris = Double.parseDouble(inputPris);
        }

        if (inputKlikPris.equals("")) {
            klikPris = 0;
        }
        else {
            klikPris = Integer.parseInt(inputKlikPris);
        }

        if (inputPantPris.equals("")) {
            pantPris = 0;
        }
        else {
            pantPris = Double.parseDouble(inputPantPris);
        }

        try {
            controller.createPris(this.salgsSituation, produkt, pris, klikPris, pantPris);
            adminPrisPane.updateLwPriser(this.salgsSituation);

        }
        catch (IllegalArgumentException e) {
            errorAlert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            errorAlert.show();
        }
    }

}
