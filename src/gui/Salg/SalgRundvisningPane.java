package gui.Salg;

        import controller.Controller;
        import javafx.beans.value.ChangeListener;
        import javafx.collections.FXCollections;
        import javafx.geometry.Insets;
        import javafx.scene.control.*;
        import javafx.scene.layout.GridPane;
        import javafx.scene.layout.HBox;
        import model.Kunde;
        import model.Order;
        import model.OrderStatus;
        import model.SalgsSituation;

        import java.time.LocalDate;
        import java.time.temporal.ChronoUnit;
        import java.util.Optional;

        public class SalgRundvisningPane extends GridPane{
        Controller controller=new Controller();
        SalgsSituation salgsSituation;
        Order currentOrder;

        Label lblOrdreNr,lblOrdrerTxt,lblExpectingDato,lblKundeNavn,lblKundeTlf;
        TextField txfExpectingDato,txfKundeNavn,txfKundeTlf;
        Button btnOpretRV,btnOpretKunde;
        CheckBox checkKunde;
        ComboBox<Kunde>cBoxKunde;
        ListView lvwOrdreline;
        Alert errorAlert;


        public SalgRundvisningPane(SalgsSituation salgsSituation){
        this.salgsSituation=salgsSituation;
        this.setGridLinesVisible(false);
        this.initContent();
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(20));
        }

        public void initContent(){
//Leftside
//OrdrerNr
        HBox hBoxOrderNr=new HBox(10);
        this.add(hBoxOrderNr,0,0);

        lblOrdrerTxt=new Label("Ordrernr:");
        hBoxOrderNr.getChildren().add(lblOrdrerTxt);

        lblOrdreNr=new Label(Integer.toString(getCurrentOrderNr()));
        hBoxOrderNr.getChildren().add(lblOrdreNr);

//exceptingdato
        lblExpectingDato=new Label("Rundvisingdato:");
        this.add(lblExpectingDato,0,1);

        txfExpectingDato=new TextField();
        txfExpectingDato.setMaxWidth(100);
        this.add(txfExpectingDato,1,1);

//Kunde
        HBox hBoxKunde=new HBox(10);
        lblKundeNavn=new Label("VælgeKunde:");
        this.add(lblKundeNavn,0,2);

        cBoxKunde=new ComboBox<Kunde>();
        cBoxKunde.getItems().add(null);
        cBoxKunde.getItems().addAll(FXCollections.observableArrayList(controller.getKunder()));
        cBoxKunde.setMinWidth(200);
////TODOcBoxKunde.getSelectionModel().selectedItemProperty().addListener(obs->updateControls());
        hBoxKunde.getChildren().add(cBoxKunde);

        btnOpretKunde=new Button("OpretKunde");
        btnOpretKunde.setOnAction(event->opretKundeAction());
        hBoxKunde.getChildren().add(btnOpretKunde);
        this.add(hBoxKunde,1,2);

//button
        btnOpretRV=new Button("OpretRundvisning");
        this.add(btnOpretRV,0,6);
        btnOpretRV.setOnAction(event->this.opretRundvisingAction());

        }

        public int getCurrentOrderNr(){
        return controller.getOrders().size()+1;
        }


        public void opretRundvisingAction(){
        LocalDate expecting=LocalDate.parse(txfExpectingDato.getText().trim());
        LocalDate now=LocalDate.now();
        Kunde kunde=cBoxKunde.getValue();
        int ordreNr=getCurrentOrderNr();
        if(!now.isAfter(expecting)){
        if(kunde!=null){
        Order order=controller.createRundvisningOrder(ordreNr,now,expecting);
        order.setKunde(kunde);
        }else{
        errorAlert=new Alert(Alert.AlertType.ERROR,"Opretenkundeellervælgeenkunde");
        errorAlert.show();
        }
        }else{
        errorAlert=new Alert(Alert.AlertType.ERROR,"Vælgeendato,somikkeerdatid");
        errorAlert.show();
        }

        }

public void opretKundeAction(){
        SalgOpretKundeWindow salgOpretKundeWindow=new SalgOpretKundeWindow();
        salgOpretKundeWindow.showAndWait();

        cBoxKunde.getItems().clear();
        cBoxKunde.getItems().add(null);
        cBoxKunde.getItems().setAll(controller.getKunder());
        }


        }
