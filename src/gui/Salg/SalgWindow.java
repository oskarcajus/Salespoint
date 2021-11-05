package gui.Salg;

import controller.Controller;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.SalgsSituation;

public class SalgWindow extends Application {
    private final Controller controller = Controller.getController();

    private ListView<SalgsSituation> lvwSalgsSituation;
    private Button btnStart;
    private Alert errorAlert;

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
        // Listviews
        Label lblSalg = new Label("Liste af Salgs områder");
        pane.add(lblSalg,10,9);


        lvwSalgsSituation = new ListView<>();
        pane.add(lvwSalgsSituation, 10, 10, 1, 5);
        lvwSalgsSituation.setPrefWidth(200);
        lvwSalgsSituation.setPrefHeight(200);
        lvwSalgsSituation.getItems().setAll(controller.getSalgsSituationer());


        // button
        Button btnStart = new Button("Start salg");
        pane.add(btnStart, 10, 20);
        btnStart.setOnAction(event ->
                this.onActionBtnStart(lvwSalgsSituation.getSelectionModel().getSelectedItem()));

    }

    //---------------------------------------------------------------------------------------------------------
    private void onActionBtnStart(SalgsSituation selectedItem) {
        if (selectedItem != null){
            SalgButikStartWindow salgButikStartWindow = new SalgButikStartWindow(selectedItem);
            salgButikStartWindow.showAndWait();
        } else {
            errorAlert = new Alert(Alert.AlertType.ERROR, "Mangler at vælge en salgs område!");
            errorAlert.show();
        }

    }




}

