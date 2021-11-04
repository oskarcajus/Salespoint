package test;

import java.time.LocalDate;
import java.util.ArrayList;

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

        //produkt = new Produkt("Julebryg", produktType, produktgruppe);
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
    @Order(2)
// Boolean test
    void TC2_Create_Produkt_Same_ProduktGruppeName() { //Produktgruppe produktgruppe, ProduktType produktType, String navn
        // Arrange
        Produkt produkt = controller.createProdukt(produktgruppe, produktType, "Øl");

        // Act
        boolean actual = controller.checkProduktNameIsInProduktgruppe(produktgruppe, "Øl");

        // Assert
        Assertions.assertTrue(actual); // true test
    }

    @Test
    @Order(3)
// Exception test
    void TC3_Create_Produkt_Same_ProduktGruppeName() { //Produktgruppe produktgruppe, ProduktType produktType, String navn
        // Arrange
        Produkt produkt = controller.createProdukt(produktgruppe, produktType, "Øl");

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            controller.createProdukt(produktgruppe, produktType, "Øl");
        });

        // Assert
        Assertions.assertEquals("Der er allerede et produkt med det navn tilknyttet produktgruppen.", exception.getMessage()); // exception test
    }

    @Test
    @Order(4)
        // boolean test
    void TC4_Create_Produkt_Same_ProduktType() {
        // Arrange
        Produkt produkt = controller.createProdukt(produktgruppe, produktType, "wiskey");

        // Act
        boolean actual = controller.checkProduktNameIsInProduktType(produktType, "wiskey");

        // Assert
        Assertions.assertTrue(actual); // true test
    }

    @Test
    @Order(5)// Exception test
    void TC5_Create_Produkt_Same_ProduktType() {
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
    @Order(6)
    void TC6_Remove_Produkt() {
        // Arrange
        Produkt p1 = controller.createProdukt(produktgruppe, produktType2, "Fustagee");
        Produkt p2 = controller.createProdukt(produktgruppe2, produktType3, "Julebrygg");
        Produkt p3 = controller.createProdukt(produktgruppe, produktType, "Blak Monsterr");

        // Act
        int actualSize = controller.getAllProdukter().size(); // expect -- 3
        Assertions.assertEquals(3, actualSize);

        controller.removeProdukt(p1); // 3-1 = 2
        actualSize = controller.getAllProdukter().size(); // expect -- 2

        // Assert
        Assertions.assertEquals(2, actualSize);
    }

    @Test
    @Order(7)
    void TC7_Create_ProduktGruppe() {
        // Arrange & Act
        Produktgruppe actual = controller.createProduktgruppe("Rundvisning");

        // Assert
        Assertions.assertEquals("Rundvisning", actual.getNavn());
    }

    @Test
    @Order(8)
// Exception test
    void TC8_Create_ProduktGruppe_SameName() {
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
    @Order(9)
     void TC9_Remove_ProduktGruppe() {
        // Arrange

        // Act
        int actual1 = controller.getProduktgrupper().size(); // 2
        controller.removeProduktgruppe(produktgruppe);
        int actual2 = controller.getProduktgrupper().size(); // 1

        // Assert
        Assertions.assertEquals(2, actual1);
        Assertions.assertEquals(1, actual2);
    }

    @Test
    @Order(11)
    void TC11_Create_Order() {
        // Arrange

        // Act
        model.Order actual = controller.createOrder(1, LocalDate.now());

        // Assert
        Assertions.assertEquals(1, actual.getOrderNr());
        Assertions.assertEquals(LocalDate.now(), actual.getOprettelsesDato());
    }

    @Test
    @Order(12)// Exception Test
    void TC12_Create_Order_Same_OrderNr() {
        // Arrange

        // Act
        model.Order actual_NewOrder = controller.createOrder(2, LocalDate.of(2021, 10, 30));

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            controller.createOrder(2, LocalDate.now());
        });

        // Assert
        Assertions.assertEquals("Der findes allerede en ordre med det ordrenr.", exception.getMessage());
    }

    @Test
    @Order(13)
    void TC13_Create_RundvisningOrder() {
        // Arrange
        // Act
        RundvisningOrder actual = controller.createRundvisningOrder(3, LocalDate.now(), LocalDate.of(2021, 11, 30));// Act

        // Assert
        Assertions.assertEquals(3, actual.getOrderNr());
        Assertions.assertEquals(LocalDate.now(), actual.getOprettelsesDato());
        Assertions.assertEquals(LocalDate.of(2021, 11, 30), actual.getExpectingBetalingsDato());
    }

    @Test
    @Order(14)// Ekception test
    void TC14_Create_RundvisningOrder_Same_OrderNr() {
        // Arrange
        RundvisningOrder actual = controller.createRundvisningOrder(250, LocalDate.now(), LocalDate.of(2021, 11, 30));

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            controller.createRundvisningOrder(250, LocalDate.now(), LocalDate.of(2022, 12, 30));
        });

        // Assert
        Assertions.assertEquals("Der findes allerede en ordre med det ordrenr " + actual.getOrderNr(), exception.getMessage());
    }

    @Test
    @Order(15)
    void TC15_Create_UdlejningOrder() {
        // Act
        UdlejningsOrder actual = controller.createUdlejningOrder(5, LocalDate.now(), LocalDate.of(2021, 11, 30));// Act

        // Assert
        Assertions.assertEquals(5, actual.getOrderNr());
        Assertions.assertEquals(LocalDate.now(), actual.getOprettelsesDato());
        Assertions.assertEquals(LocalDate.of(2021, 11, 30), actual.getForventetReturDato());
    }

    @Test
    @Order(16)
    void TC16_Create_Return_Order() {
        // Arrange
        SalgsSituation s1 = controller.createSalgsSituation("HyggeBar");
        produkt = controller.createProdukt(produktgruppe, produktType, "Kimchi");
        pris = controller.createPris(s1, produkt, 150, 0, 100 );
        model.Order order = controller.createOrder(100,LocalDate.now());
        OrderLine orderLine = controller.createOrderLine(order, 1, pris);

        // Act
        model.Order actual = controller.createReturnOrder(100);
//        order.setOrderNr(10000000);

        // Assert
        Assertions.assertEquals(10000000 + actual.getRefOrderNr(), actual.getOrderNr());
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
    @Order(18)// Exception Test
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
    @Order(19)
       void TC19_Create_Pris() {
        // Arrange
        SalgsSituation s1 = controller.createSalgsSituation("NewyearBar");
        produkt = controller.createProdukt(produktgruppe, produktType, "KoreanBeer");

        // Act
        Pris actual = controller.createPris(s1, produkt, 100, 2, 50);

        // Assert
        Assertions.assertEquals(produkt, actual.getProdukt());
        Assertions.assertEquals(100, actual.getPris());
        Assertions.assertEquals(2, actual.getKlipPris());
        Assertions.assertEquals(50, actual.getPantPris());
    }

    @Test
    @Order(20)
        // Exception Test
    void TC20_Create_Pris_Same_Produkt() {
        // Arrange
        SalgsSituation s1 = controller.createSalgsSituation("MondayBar");
        Produkt produkt2 = controller.createProdukt(produktgruppe, produktType, "KimchiTonic");

        // Act
        Pris actual = controller.createPris(s1, produkt2, 100, 0, 50);
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            controller.createPris(s1, produkt2, 300, 0, 0);
        });

        // Assert
        Assertions.assertEquals("Produktet har allerede en pris i denne salgssituation. \n" +
                "Redigér i stedet prisen for produktet.", exception.getMessage());
    }

    @Test
    @Order(21)
    void TC21_Solgt_KlippeKort() {
        // Arrange
        Produktgruppe produktgruppe = controller.createProduktgruppe("KlippeKort");
        Produkt produkt = controller.createProdukt(produktgruppe, controller.createProduktType("klip"), "klippekort");
        SalgsSituation s1 = controller.createSalgsSituation("MondayBar");
        Pris pris = controller.createPris(s1, produkt, 130, 0, 0);

        LocalDate startDate = LocalDate.of(2021, 10, 30);
        LocalDate endDate = LocalDate.of(2021, 11, 4);

        model.Order order = controller.createOrder(1, LocalDate.of(2021, 11, 4));
        OrderLine orderLine = controller.createOrderLine(order, 3, pris);

        // Act
        int actual = controller.solgtKlippeKort(startDate, endDate);

        // Assert
        Assertions.assertEquals(3, actual); // antal Produkt = 3
    }

    @Test
    @Order(22) // Exception test
    void TC22_Solgt_KlippeKort_Ugyldig_OrderDato_10_1() {
        // Arrange
        Produktgruppe produktgruppe = controller.createProduktgruppe("KlippeKort");
        Produkt produkt = controller.createProdukt(produktgruppe, controller.createProduktType("klip"), "klippekort");
        SalgsSituation s1 = controller.createSalgsSituation("MondayBar");
        Pris pris = controller.createPris(s1, produkt, 130, 0, 0);

        LocalDate startDate = LocalDate.of(2021, 10, 30);
        LocalDate endDate = LocalDate.of(2021, 11, 4);

        model.Order order = controller.createOrder(1, LocalDate.of(2021, 11, 5));
        OrderLine orderLine = controller.createOrderLine(order, 3, pris);

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            controller.solgtKlippeKort(startDate, endDate);
        });

        // Assert
        Assertions.assertEquals("Der er ingen solgt klippekort i den periode.", exception.getMessage());
    }

    @Test
    @Order(23)
    void TC23_Brugt_Klip() {
        // Arrange
        Produktgruppe produktgruppe = controller.createProduktgruppe("FlYske");
        Produkt produkt = controller.createProdukt(produktgruppe, controller.createProduktType("norrmal"), "Klasterbryg");
        SalgsSituation s1 = controller.createSalgsSituation("TuesdayyBar");
        Pris pris = controller.createPris(s1, produkt, 70, 2, 0);

        LocalDate startDate = LocalDate.of(2021, 10, 30);
        LocalDate endDate = LocalDate.of(2021, 11, 4);
        model.Order order = controller.createOrder(1000, LocalDate.of(2021, 11, 4));

        order.setBetalingsType(BetalingsType.KLIPPEKORT);
        OrderLine orderLine = controller.createOrderLine(order, 3, pris);

        // Act
        int actual = controller.brugtKlip(startDate, endDate);

        // Assert
        Assertions.assertEquals(6, actual); // klipPris 2 * antal Produkt 3 = 6
    }

    @Test
    @Order(24)
        // Exception Test
    void TC24_Brugt_Klip_Ugyldig_OrderDato() {
        // Arrange
        Produktgruppe produktgruppe = controller.createProduktgruppe("Floskee");
        Produkt produkt = controller.createProdukt(produktgruppe, controller.createProduktType("normall"), "Klosterbrygg");
        SalgsSituation s1 = controller.createSalgsSituation("TuesdayBar");
        Pris pris = controller.createPris(s1, produkt, 70, 2, 0);

        LocalDate startDate = LocalDate.of(2021, 10, 30);
        LocalDate endDate = LocalDate.of(2021, 11, 4);
        model.Order order = controller.createOrder(1, LocalDate.of(2021, 11, 5));

        order.setBetalingsType(BetalingsType.KLIPPEKORT);
        OrderLine orderLine = controller.createOrderLine(order, 3, pris);

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            controller.brugtKlip(startDate, endDate);
        });

        // Assert
        Assertions.assertEquals("Der er ingen brugte klip i den periode.", exception.getMessage());
    }

    @Test
    @Order(25)
    void TC25_ikke_Return_Produkt() {
        // Arrange
        produktgruppe = controller.createProduktgruppe("Floske");
        produkt = controller.createProdukt(produktgruppe, controller.createProduktType("norm"), "Klosterbryg");
        SalgsSituation s1 = controller.createSalgsSituation("Bar");
        Pris pris = controller.createPris(s1, produkt, 150,0 , 100);

        model.Order ikkeReturn1 = controller.createUdlejningOrder(100, LocalDate.of(2021,10,1), LocalDate.of(2021,10,30));
        ikkeReturn1.createOrderLine(1, pris);
        model.Order ikkeReturn2 = controller.createUdlejningOrder(101, LocalDate.of(2021,10,1), LocalDate.of(2021,10,20));
        ikkeReturn2.createOrderLine(1,pris);
        // venter på return
        model.Order udlejningsOrder = controller.createUdlejningOrder(102, LocalDate.of(2021,10,2), LocalDate.of(2021, 11,30));
        udlejningsOrder.createOrderLine(1,pris);

        // Act
        ArrayList<Produkt> actual = controller.ikkeReturnProdukt();

        // Act
        Assertions.assertEquals(2, actual.size());
    }
}



