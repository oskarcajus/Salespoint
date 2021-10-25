package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.SalgsSituation;

public class SalgStartWindow extends Stage {
private SalgsSituation salgsSituation;

public SalgStartWindow (SalgsSituation salgsSituation){
    this.salgsSituation = salgsSituation;

    this.setMinHeight(500);
    this.setMinWidth(500);


    this.setTitle("Salg for " + salgsSituation);
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


    }


}
