package gui.Salg;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Pris;
import model.SalgsSituation;

public class SalgUdlejningPane extends GridPane {
    private final Controller controller = Controller.getController();

    SalgsSituation salgsSituation;
    Label lblProdukter;
    ListView lvwProdukter;

    Button btnUdlejningRetur = new Button("Udlejning Retur");


    public SalgUdlejningPane(SalgsSituation salgsSituation) {
        this.salgsSituation = salgsSituation;
        this.setGridLinesVisible(false);
        this.initContent();
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(20));
    }

    public void initContent() {

        // Produkter
        lblProdukter = new Label("Udlejningsprodukter: ");
        this.add(lblProdukter, 0, 0);

        this.add(btnUdlejningRetur, 5, 5);
        btnUdlejningRetur.setOnAction(actionEvent -> onActionBtnUdlejningRetur(3));

        lvwProdukter = new ListView();
        Font font = Font.font("Calibri", FontWeight.BOLD, FontPosture.ITALIC, 15);
        String produktGruppe = "";

        for (Pris pr : salgsSituation.getPriser()) {

            if (!pr.getProdukt().getProduktgruppe().getNavn().equals(produktGruppe)) {
                produktGruppe = pr.getProdukt().getProduktgruppe().getNavn();
                Text txt = new Text(produktGruppe + ":");
                txt.setFont(font);
                lvwProdukter.getItems().add(txt);
            }
            if (pr.getPantPris() >0 ){
                lvwProdukter.getItems().add(pr);
            }

        }
        lvwProdukter.getSelectionModel().selectedItemProperty().addListener(obs -> {
            if (lvwProdukter.getSelectionModel().getSelectedItem() instanceof Text)
                lvwProdukter.getSelectionModel().selectNext();
        });
        this.add(lvwProdukter, 0, 1, 1, 5);
        lvwProdukter.setPrefWidth(300);
        lvwProdukter.setPrefHeight(500);

    }

    private void onActionBtnUdlejningRetur(int ordreNr) {

    }

}
