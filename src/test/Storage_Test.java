package test;

import java.time.LocalDate;
import controller.Controller;
import model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import storage.Storage;


public class Storage_Test {

    Controller controller = Controller.getController();
    Storage storage = Storage.getStorage();

    @Test
    @Order(1)
    void TC1_Solgt_KlippeKort() {
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
    void TC2_Solgt_KlippeKort_Ugyldig_OrderDato() {
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
    void TC3_Brugt_Klip() {
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
    void TC4_Brugt_Klip_Ugyldig_OrderDato() {
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
