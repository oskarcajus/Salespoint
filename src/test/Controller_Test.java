package test;

import java.time.LocalDate;
import controller.Controller;
import model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import storage.Storage;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Controller_Test {
    // Basis data setup
    Produktgruppe produktgruppe;
    Produktgruppe produktgruppe2;

    ProduktType produktType;
    ProduktType produktType2;
    ProduktType produktType3;

    Produkt produkt;
    Produkt produkt2;
    Produkt produkt3;

    SalgsSituation butikPrisliste;
    SalgsSituation barPrisListe;

    int antalProdukt;

    double normalPris;
    double pantPris;
    int klipPris;

    Pris pris = null;

    Controller controller;
    private Storage storage = Storage.getStorage();

    @BeforeEach
    void setUp() throws Exception {
        System.out.println("Setting up");
        controller = Controller.getController();
        produktgruppe = controller.createProduktgruppe("Flaske");
        produktgruppe2 = controller.createProduktgruppe("Fustage");
        //produktgruppe = new Produktgruppe("Flaske");
        //produktgruppe2 = new Produktgruppe("Fustage");

        produktType = controller.createProduktType("normal");
        produktType2 = controller.createProduktType("pant");
        produktType3 = controller.createProduktType("KlippeKort");
        //produktType = new ProduktType("normal");
        //produktType2 = new ProduktType("pant");
        //produktType3 = new ProduktType("KlippeKort");

      produkt = new Produkt("Julebryg", produktType, produktgruppe);
      //produkt2 = new Produkt("Blondie25L", produktType2, produktgruppe2);
      //produkt3 = new Produkt("Klosterbryg ", produktType3, produktgruppe);

      butikPrisliste = new SalgsSituation("Butik");
      barPrisListe = new SalgsSituation("FredagsBar");
    }

    @AfterEach
    void cleanup() throws Exception {
        System.out.println("Cleaning up");
        storage.clearStorage();
    }

//------------test start-------------------------------------------------------------------------------------
    @Test
    @Order(1)
    void TC1_Create_Produkt() { //Produktgruppe produktgruppe, ProduktType produktType, String navn
        // Arrange

        // Act
        Produkt actual = controller.createProdukt(produktgruppe, produktType, "Øl");

        // Assert
        Assertions.assertEquals(produktgruppe, actual.getProduktgruppe());
        Assertions.assertEquals(produktType, actual.getProduktType());
        Assertions.assertEquals("Øl", actual.getName());
    }

    @Test
    @Order(2)// Boolean test
    void TC2_Create_Produkt_Same_ProduktGruppeName() { //Produktgruppe produktgruppe, ProduktType produktType, String navn
        // Arrange
        Produkt produkt = controller.createProdukt(produktgruppe, produktType, "Øl");

        // Act
        boolean actual = controller.checkProduktNameIsInProduktgruppe(produktgruppe, "Øl");

        // Assert
        Assertions.assertTrue(actual); // true test
    }

    @Test
    @Order(13)// Exception test
    void TC13_Create_Produkt_Same_ProduktGruppeName() { //Produktgruppe produktgruppe, ProduktType produktType, String navn
        // Arrange
        Produkt produkt = controller.createProdukt(produktgruppe,produktType, "Øl");

        // Act
          IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            controller.createProdukt(produktgruppe, produktType, "Øl");
        });

        // Assert
        Assertions.assertEquals("Der er allerede et produkt med det navn tilknyttet produktgruppen.", exception.getMessage()); // exception test
    }

    @Test
    @Order(3) // boolean test
    void TC3_Create_Produkt_Same_ProduktType() {
        // Arrange
        Produkt produkt = controller.createProdukt(produktgruppe, produktType, "wiskey");

        // Act
        boolean actual = controller.checkProduktNameIsInProduktType(produktType, "wiskey");

        // Assert
        Assertions.assertTrue(actual); // true test
    }

    @Test
    @Order(14)// Exception test
    void TC14_Create_Produkt_Same_ProduktType() {
        // Arrange
        Produkt produkt = controller.createProdukt(produktgruppe, produktType, "wiskey");

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            controller.createProdukt(produktgruppe2, produktType, "Wiskey");
        });

        // Assert
        Assertions.assertEquals("Der er allerede et produkt med det navn tilknyttet produkttypen.", exception.getMessage()); // exception test
    }

    @Test
    @Order(4)
    void TC4_Remove_Produkt() {
        // Arrange
        Produkt p1 = controller.createProdukt(produktgruppe, produktType2, "Fustage");
        Produkt p2 = controller.createProdukt(produktgruppe2, produktType3, "Julebryg");
        Produkt p3 = controller.createProdukt(produktgruppe, produktType, "Blak Monster");

        // Act
        int actualSize = controller.getAllProdukter().size(); // expect -- 3
        Assertions.assertEquals(3, actualSize);

        controller.removeProdukt(p1); // 3-1 = 2
        actualSize = controller.getAllProdukter().size(); // expect -- 2

        // Assert
        Assertions.assertEquals(2, actualSize);
    }

    @Test
    @Order(5)
      void TC5_Create_ProduktGruppe() {
        // Arrange & Act
        Produktgruppe actual = controller.createProduktgruppe("Rundvisning");

        // Assert
        Assertions.assertEquals("Rundvisning", actual.getNavn());
      }

    @Test
    @Order(6)// Exception test
    void TC6_Create_ProduktGruppe_SameName() {
            // Arrange
            Produktgruppe pg1 = controller.createProduktgruppe("Glas");

            // Act
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
                controller.createProduktgruppe("glas");
            });

            // Assert
        Assertions.assertEquals("Der findes allerede en produktgruppe med dette navn.", exception.getMessage());
    }

    @Test
    @Order(7) //????????????????????????????????????????????? --- add double times
    void TC7_Remove_ProduktGruppe() {
        // Arrange
        Produktgruppe p1 = controller.createProduktgruppe("Tshirts");
        Produktgruppe p2 = controller.createProduktgruppe("Cola");

        // Act
        int actual1 = controller.getProduktgrupper().size(); // 2
        controller.removeProduktgruppe(p1);
        int actual2 = controller.getProduktgrupper().size(); // 1

        // Assert
        Assertions.assertEquals(2, actual1);
        Assertions.assertEquals(1, actual2);
    }


    @Test
    @Order(8) // Ekception test
    void TC8_Remove_ProduktGruppe() {

    }

    @Test
    @Order(15)
    void TC15_Create_Order() {
    // Arrange

    // Act
    model.Order actual = controller.createOrder(1, LocalDate.now());

    // Assert
        Assertions.assertEquals(1, actual.getOrderNr());
        Assertions.assertEquals(LocalDate.now(), actual.getOprettelsesDato());
    }

    @Test
    @Order(16) // Exception Test
    void TC16_Create_Order_Same_OrderNr() {
        // Arrange

        // Act
        model.Order actual_NewOrder = controller.createOrder(2, LocalDate.of(2021,10,30));

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            controller.createOrder(2, LocalDate.now());
        });

        // Assert
        Assertions.assertEquals("Der findes allerede en ordre med det ordrenr.", exception.getMessage());
    }

    @Test
    @Order(9)
    void TC9_Create_RundvisningOrder() {
        // Arrange
        // Act
        RundvisningOrder actual = controller.createRundvisningOrder(3, LocalDate.now(), LocalDate.of(2021,11,30));// Act

        // Assert
        Assertions.assertEquals(3, actual.getOrderNr());
        Assertions.assertEquals(LocalDate.now(), actual.getOprettelsesDato());
        Assertions.assertEquals(LocalDate.of(2021,11,30), actual.getExpectingBetalingsDato());
    }

    @Test
    @Order(10) // Ekception test
    void TC10_Create_RundvisningOrder_Same_OrderNr() {
        // Arrange
        RundvisningOrder actual = controller.createRundvisningOrder(4, LocalDate.now(), LocalDate.of(2021,11,30));

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            controller.createRundvisningOrder(4, LocalDate.now(), LocalDate.of(2022,12,30));
        });

        // Assert
        Assertions.assertEquals("Der findes allerede en ordre med det ordrenr.", exception.getMessage());
    }

    @Test
    @Order(11)
    void TC11_Create_UdlejningOrder() {
        // Act
        UdlejningsOrder actual = controller.createUdlejningOrder(5, LocalDate.now(), LocalDate.of(2021,11,30));// Act

        // Assert
        Assertions.assertEquals(5, actual.getOrderNr());
        Assertions.assertEquals(LocalDate.now(), actual.getOprettelsesDato());
        Assertions.assertEquals(LocalDate.of(2021,11,30), actual.getForventetReturDato());
    }

    @Test
    @Order(12) //????????????????????????????????????????????????????????????????????????
    void TC12_Create_Return_Order() {
        // Arrange

        // Act
        model.Order actual = controller.createReturnOrder(10000000);

        // Assert
        Assertions.assertEquals(6, actual.getOrderNr());
    }

    @Test
    @Order(17)
    void TC17_Create_SalgsSituation() {
        // Arrange

        // Act
        SalgsSituation actual = controller.createSalgsSituation("ChristmasBar");

        // Assert
        Assertions.assertEquals("ChristmasBar", actual.getNavn());
    }

    @Test
    @Order(18) // Exception Test
    void TC18_Create_SalgsSituation_Same_Name() {
        // Arrange

        // Act
        SalgsSituation actual = controller.createSalgsSituation("NewYearBar");

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            controller.createSalgsSituation("newyearbar");
        });

        // Assert
        Assertions.assertEquals("Der findes allerede en salgssituation med det navn.", exception.getMessage());
    }

    @Test
    @Order(19) //????????????????????????????????????????????????????????
    void TC19_Create_Pris() {
        // Arrange
        SalgsSituation s1 = controller.createSalgsSituation("NewyearBar");

        // Act
        Pris actual = controller.createPris(s1, produkt, 100, 2, 50);

        // Assert
        Assertions.assertEquals(produkt, actual.getProdukt());
        Assertions.assertEquals(100, actual.getPris());
        Assertions.assertEquals(2, actual.getKlipPris());
        Assertions.assertEquals(50, actual.getPantPris());
    }

    @Test
    @Order(20) // Exception Test
    void TC20_Create_Pris_Same_Produkt() {
        // Arrange
        SalgsSituation s1 = controller.createSalgsSituation("MondayBar");
        Produkt produkt2 = controller.createProdukt(produktgruppe, produktType, "KimchiTonic");

        // Act
        Pris actual = controller.createPris(s1, produkt2, 100, 0, 50);
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            controller.createPris(s1, produkt2, 300, 0,0);
        });

        // Assert
        Assertions.assertEquals("Produktet har allerede en pris i denne salgssituation. \n" +
                "Redigér i stedet prisen for produktet.", exception.getMessage());
    }

    @Test
    @Order(1)
    void TC21_Solgt_KlippeKort() {
        // Arrange
        Produktgruppe produktgruppe = controller.createProduktgruppe("KlippeKort");
        Produkt produkt = controller.createProdukt(produktgruppe, controller.createProduktType("klip"), "klippekort" );
        SalgsSituation s1 = controller.createSalgsSituation("MondayBar");
        Pris pris = controller.createPris(s1, produkt,130,0,0 );

        LocalDate startDate = LocalDate.of(2021,10,30);
        LocalDate endDate = LocalDate.of(2021,11,4);
        model.Order order = controller.createOrder(1, LocalDate.of(2021,11,4));

        OrderLine orderLine = controller.createOrderLine(order,3, pris);

        // Act
        int actual = storage.solgtKlippeKort(startDate, endDate);

        // Assert
        Assertions.assertEquals(3, actual); // antal Produkt = 3
    }
    @Test
    @Order(2)
    void TC22_Solgt_KlippeKort_Ugyldig_OrderDato() {
        // Arrange
        Produktgruppe produktgruppe = controller.createProduktgruppe("KlippeKort");
        Produkt produkt = controller.createProdukt(produktgruppe, controller.createProduktType("klip"), "klippekort" );
        SalgsSituation s1 = controller.createSalgsSituation("MondayBar");
        Pris pris = controller.createPris(s1, produkt,130,0,0 );

        LocalDate startDate = LocalDate.of(2021,10,30);
        LocalDate endDate = LocalDate.of(2021,11,4);
        model.Order order = controller.createOrder(1, LocalDate.of(2021,11,5));

        OrderLine orderLine = controller.createOrderLine(order,3, pris);

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            storage.solgtKlippeKort(startDate,endDate);
        });

        // Assert
        Assertions.assertEquals("Ugyldig Order Dato", exception.getMessage());
    }

    @Test
    @Order(3)
    void TC23_Brugt_Klip() {
        // Arrange
        Produktgruppe produktgruppe = controller.createProduktgruppe("Flaske");
        Produkt produkt = controller.createProdukt(produktgruppe, controller.createProduktType("normal"), "Klosterbryg" );
        SalgsSituation s1 = controller.createSalgsSituation("TuesdayBar");
        Pris pris = controller.createPris(s1, produkt,70,2,0 );

        LocalDate startDate = LocalDate.of(2021,10,30);
        LocalDate endDate = LocalDate.of(2021,11,4);
        model.Order order = controller.createOrder(1,LocalDate.of(2021,11,4));

        order.setBetalingsType(BetalingsType.KLIPPEKORT);
        OrderLine orderLine = controller.createOrderLine(order,3, pris);

        // Act
        int actual = storage.brugtKlip(startDate, endDate);

        // Assert
        Assertions.assertEquals(6, actual); // klipPris 2 * antal Produkt 3 = 6
    }

    @Test
    @Order(4) // Exception Test
    void TC24_Brugt_Klip_Ugyldig_OrderDato() {
        // Arrange
        Produktgruppe produktgruppe = controller.createProduktgruppe("Flaske");
        Produkt produkt = controller.createProdukt(produktgruppe, controller.createProduktType("normal"), "Klosterbryg" );
        SalgsSituation s1 = controller.createSalgsSituation("TuesdayBar");
        Pris pris = controller.createPris(s1, produkt,70,2,0 );

        LocalDate startDate = LocalDate.of(2021,10,30);
        LocalDate endDate = LocalDate.of(2021,11,4);
        model.Order order = controller.createOrder(1,LocalDate.of(2021,11,5));

        order.setBetalingsType(BetalingsType.KLIPPEKORT);
        OrderLine orderLine = controller.createOrderLine(order,3, pris);

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            storage.brugtKlip(startDate,endDate);
        });

        // Assert
        Assertions.assertEquals("Ugyldig Order Dato", exception.getMessage());
    }


}



