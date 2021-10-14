package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class AdminProduktPane extends GridPane {
    private TextField txfTest = new TextField("Test");

    public AdminProduktPane() {
        this.add(txfTest, 10, 10);
    }

}
