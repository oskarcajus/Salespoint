package gui.Salg;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.SalgsSituation;
import java.time.LocalDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SalgRundvisningPane extends GridPane {

    SalgsSituation salgsSituation;
    Label lblProdukter;
    ListView lvwProdukter;

    private DatePicker dpDato = new DatePicker(LocalDate.now());
    private TextField txfTimeHour = new TextField();
    private TextField txfTimeMinute = new TextField();
    private TextField txfDeltagere = new TextField();
    private Label lblError;

    // constructor
    public SalgRundvisningPane(SalgsSituation salgsSituation) {

        this.salgsSituation = salgsSituation;

        this.setGridLinesVisible(false);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(20));

        GridPane pane = new GridPane();
        Scene scene = new Scene(pane);
        this.initContent(pane);
        //Sthis.setScene(scene);

        pane.setPadding(new Insets(20));
        pane.setGridLinesVisible(false);
    }

    // Init content
    public void initContent(GridPane pane) {

        Label lblRundvisningsDato = new Label("Rundvisnings Dato");
        pane.add(lblRundvisningsDato, 0, 0);
        HBox hbxDateTime = new HBox();
        hbxDateTime.getChildren().add(dpDato);
        txfTimeHour.setMaxWidth(50);
        hbxDateTime.getChildren().add(txfTimeHour);
        Label lblRundvisningTimeSeperator = new Label(":");
        hbxDateTime.getChildren().add(lblRundvisningTimeSeperator);
        txfTimeMinute.setMaxWidth(50);
        hbxDateTime.getChildren().add(txfTimeMinute);
        pane.add(hbxDateTime, 0, 1);

        Label lblDeltagere = new Label("Deltagere");
        pane.add(lblDeltagere, 0, 2);
        pane.add(txfDeltagere, 0, 3);

        HBox hbxDialogControlls = new HBox();
        Button btnOpret = new Button("Opret");
        btnOpret.setOnAction(event -> actionOpret());
        hbxDialogControlls.getChildren().add(btnOpret);
        Button btnCancel = new Button("Cancel");
        //btnCancel.setOnAction(event -> this.close());
        hbxDialogControlls.getChildren().add(btnCancel);

        pane.add(hbxDialogControlls, 0, 4);
    }


    //  Button action
    private void actionOpret() {


    }
}


