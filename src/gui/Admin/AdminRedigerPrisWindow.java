package gui.Admin;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Pris;
import model.Produkt;
import model.SalgsSituation;

public class AdminRedigerPrisWindow extends Stage {
    private final Controller controller = Controller.getController();

    private AdminPrisPane adminPrisPane;
    private SalgsSituation salgsSituation;
    private Pris pris;

    private Label lblProdukter;
    private TextField txfProdukt;

    private Label lblPris;
    private TextField prisInput;

    private Label lblKlikPris;
    private TextField klikPrisInput;

    private Label lblPantPris;
    private TextField pantPrisInput;

    private Button btnOpret;
    private VBox vBox;
    private Alert errorAlert;



    public AdminRedigerPrisWindow(AdminPrisPane adminPrisPane, SalgsSituation salgsSituation, Pris pris) {
        this.adminPrisPane = adminPrisPane;
        this.salgsSituation = salgsSituation;
        this.pris = pris;

        this.setMinHeight(200);
        this.setMinWidth(300);


        this.setTitle("Rediger pris");
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

        lblProdukter = new Label("Produkt: ");
        txfProdukt = new TextField();
        txfProdukt.setText(pris.getProdukt().getName());
        txfProdukt.setEditable(false);
        txfProdukt.setFocusTraversable(false);
        txfProdukt.setStyle("-fx-font-weight: bold");
        txfProdukt.setBackground(Background.EMPTY);

        lblPris = new Label("Pris: ");
        prisInput = new TextField();
        prisInput.setText(pris.getPris() + "");

        lblKlikPris = new Label("Klikpris: ");
        klikPrisInput = new TextField();
        klikPrisInput.setText(pris.getKlipPris() + "");

        lblPantPris = new Label("Pantpris: ");
        pantPrisInput = new TextField();
        pantPrisInput.setText(pris.getPantPris() + "");

        btnOpret = new Button("Rediger");
        btnOpret.setOnAction(event -> this.redigerPris(
                pris,
                prisInput.getText(),
                klikPrisInput.getText(),
                pantPrisInput.getText()));


        vBox = new VBox();
        vBox.getChildren().add(btnOpret);

        pane.add(lblProdukter, 0, 0);
        pane.add(txfProdukt, 1, 0);

        pane.add(lblPris, 0, 2);
        pane.add(prisInput, 1, 2);

        pane.add(lblKlikPris, 0, 3);
        pane.add(klikPrisInput, 1, 3);

        pane.add(lblPantPris, 0, 4);
        pane.add(pantPrisInput, 1, 4);

        pane.add(vBox, 1, 5);

    }

    private void redigerPris(Pris pris, String inputPris, String inputKlikPris, String inputPantPris) {
        double newPris;
        int newKlikPris;
        double newPantPris;

        if (inputPris.equals("")) {
            newPris = 0;
        }
        else {
            newPris = Double.parseDouble(inputPris);
        }

        if (inputKlikPris.equals("")) {
            newKlikPris = 0;
        }
        else {
            newKlikPris = Integer.parseInt(inputKlikPris);
        }

        if (inputPantPris.equals("")) {
            newPantPris = 0;
        }
        else {
            newPantPris = Double.parseDouble(inputPantPris);
        }

        try {
            controller.redigerPris(pris, newPris, newKlikPris, newPantPris);
            adminPrisPane.updateLwPriser(this.salgsSituation);

        }
        catch (IllegalArgumentException e) {
            errorAlert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            errorAlert.show();
        }
    }

}
