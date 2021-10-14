package gui;


import controller.Controller;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    // Controller access
    private Controller controller;

//------------------------------------------------------------------------------------------------

    @Override
    public void init() {
//            controller = Controller.getController();
    }

    //------------------------------------------------------------------------------------------------
    @Override
    public void start(Stage stage) {

        stage.setTitle("Salespoint");
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setHeight(400);
        stage.setWidth(600);
        stage.show();
    }

    // Buttons
    private Button btnAdmin;
    private Button btnSalg;

    private void initContent(GridPane pane) {
        pane.setGridLinesVisible(false);
        pane.setAlignment(Pos.CENTER);

        //Buttons ----------------------

        //Admin button
        String  imageAdmin= "https://cdn4.iconfinder.com/data/icons/forgen-phone-settings/48/setting-512.png";
        Image imAdmin = new Image(imageAdmin, 100, 100, false, false);
        ImageView imageViewAdmin = new ImageView(imAdmin);

        btnAdmin = new Button("Admin", imageViewAdmin);

        pane.add(btnAdmin, 5, 4);
        btnAdmin.setContentDisplay(ContentDisplay.TOP);
        pane.setMargin(btnAdmin, new Insets(10, 10, 10, 10));
        pane.setHalignment(btnAdmin, HPos.CENTER);
        btnAdmin.setMaxSize(100, 100);

        btnAdmin.setOnAction(event -> openAdminWindow());

        //Salgs button
        String  imageSalg= "http://simpleicon.com/wp-content/uploads/shop-4.png";
        Image imSalg = new Image(imageSalg, 100, 100, false, false);
        ImageView imageViewSalg = new ImageView(imSalg);

        btnSalg = new Button("Salg", imageViewSalg);

        pane.add(btnSalg, 6, 4);
        btnSalg.setContentDisplay(ContentDisplay.TOP);
        pane.setMargin(btnSalg, new Insets(10, 10, 10, 10));
        pane.setHalignment(btnSalg, HPos.CENTER);
        btnSalg.setMaxSize(100, 100);

        btnSalg.setOnAction(event -> openSalgWindow());
    }

    private void openAdminWindow() {
        System.out.println("Test Admin");
        AdminWindow aw = new AdminWindow();
        aw.start(new Stage());
    }

    private void openSalgWindow() {
        System.out.println("Test Salg");
    }
}

