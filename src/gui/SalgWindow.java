package gui;

import controller.Controller;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.SalgsSituation;
import javafx.beans.value.ChangeListener;
import org.w3c.dom.html.HTMLButtonElement;

public class SalgWindow extends Application {

    // Controller access
    private Controller controller;
    private ListView<SalgsSituation> lvwSalgsSituation;
    private MainApp mainApp;
    private Button start;

//------------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------------
    public void start(Stage stage) {

        stage.setTitle("Salgs Situation");
        GridPane pane = new GridPane();
        this.initContent(pane);
        stage.initModality(Modality.WINDOW_MODAL);


        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setHeight(300);
        stage.setWidth(500);
        stage.show();
    }

//------tabPane------------------------------------------------------------------------------------------

    private void initContent(GridPane pane) {
        pane.setAlignment(Pos.BASELINE_CENTER);

        Label lblSalg = new Label("Liste af Salgs områder");
        pane.add(lblSalg,10,9);


        lvwSalgsSituation = new ListView<>();
        pane.add(lvwSalgsSituation, 10, 10, 1, 5);
        lvwSalgsSituation.setPrefWidth(200);
        lvwSalgsSituation.setPrefHeight(200);
        lvwSalgsSituation.getItems().setAll();

//        ChangeListener<SalgsSituation> listenerSalgsSituation = (ok, oldKonference, newKonference) -> ;
//        lvwSalgsSituation.getSelectionModel().selectedItemProperty().addListener(listenerSalgsSituation);



    }

    public void btnActionStart(){

    }

}

