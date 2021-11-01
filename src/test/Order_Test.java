package test;

import model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Order_Test {

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

    Pris pris;
    Pris pris2;

    Order order;

    RabatStrategy amountRabat = new AmountRabat(200, 3000); // 200kr discount,hvis total orders beløb er mere end 3000kr
    RabatStrategy procentRabat = new ProcentRabat(7); // 7% discount

    @BeforeEach
    void setUp() throws Exception {
        produktgruppe = new Produktgruppe("Flaske");
        produktgruppe2 = new Produktgruppe("Fustage");

        produktType = new ProduktType("normal");
        produktType2 = new ProduktType("pant");
        produktType3 = new ProduktType("KlippeKort");

        produkt = new Produkt("Julebryg", produktType, produktgruppe);
        produkt2 = new Produkt("Blondie25L", produktType2, produktgruppe2);
        produkt3 = new Produkt("Klosterbryg ", produktType3, produktgruppe);

        butikPrisliste = new SalgsSituation("Butik");
        barPrisListe = new SalgsSituation("FredagsBar");
    }

//------------Test Start------------------------------------------------------------------------------------------

    @Test
    void TC1_Create_OrderLine_NormalPris() {
        // Arrange
        normalPris = 150;
        antalProdukt = 2;
        pris = butikPrisliste.createPris(produkt, normalPris, 0, 0);
        order = new Order(1, LocalDate.of(2021,10,30));

        // Act
        OrderLine actual = order.createOrderLine(antalProdukt, pris);

        // Assert
        Assertions.assertEquals(antalProdukt, actual.getAntalProdukt()); // 2, 2
        Assertions.assertEquals(pris.getPris(), actual.getPris().getPris()); // 150, 150
    }

    @Test
    void TC2_Create_OrderLine_pantPris() {
        // Arrange
        antalProdukt = 2;
        pantPris = 100;
        pris = butikPrisliste.createPris(produkt2, 150, 0, pantPris);
        order = new Order(2, LocalDate.of(2021,10,30));

        // Act
        OrderLine actual = order.createOrderLine(antalProdukt, pris);

        // Assert
        Assertions.assertEquals(antalProdukt, actual.getAntalProdukt()); // 2, 2
        Assertions.assertEquals(pris.getPantPris(), actual.getPris().getPantPris()); // 100, 100
       }

    @Test
    void TC3_Create_OrderLine_klipPris() {
        // Arrange
        antalProdukt = 2;
        klipPris = 2;
        pris = barPrisListe.createPris(produkt, 150, klipPris, 0);
        order = new Order(3, LocalDate.of(2021,10,30));

        // Act
        OrderLine actual = order.createOrderLine(antalProdukt, pris);

        // Assert
        Assertions.assertEquals(antalProdukt, actual.getAntalProdukt()); // 2,2
        Assertions.assertEquals(pris, actual.getPris()); // 4, 4
       }

    @Test
    void TC4_Remove_OrderLine() {
        // Arrange
        pris = butikPrisliste.createPris(produkt, 100, 0, 0);
        order = new Order(4, LocalDate.of(2021,10,30));
        OrderLine shouldRemove = order.createOrderLine(antalProdukt, pris);

        // Act
        order.removeOrderLine(shouldRemove);
        int actual = order.getOrderLines().size(); // expected size -- 0

        // Assert
        Assertions.assertEquals(0, actual); // expect, actual
    }

    @Test
    void TC5_KlipPris_Beløb() {
        // Arrange
        pris = barPrisListe.createPris(produkt, 150, 2, 0 );
        order = new Order(5, LocalDate.of(2021,10,30));
        OrderLine orderLine = order.createOrderLine(2, pris);

        // Act
        int actual = order.orderKlipPris(); // 2 * 2 = 4

        // Assert
        Assertions.assertEquals(4, actual);
    }

    @Test
    void TC6_OrderPris_Beløb() {
        // Arrange
        pris = butikPrisliste.createPris(produkt, 150, 0, 0 );
        pris2 = butikPrisliste.createPris(produkt2, 150, 0, 100);
        order = new Order(6, LocalDate.of(2021,10,30));
        OrderLine orderLine = order.createOrderLine(2, pris); // 2 * 150 = 300
        OrderLine orderLine2 = order.createOrderLine(2, pris2); // (produkt : 150*2) + (pant : 100*2) = 500

        // Act
        double actual = order.orderPris(); // 300+500 = 800

        // Assert
        Assertions.assertEquals(800, actual);
    }

    @Test
    void TC7_prisWithRabat_AmountRabat_TotalBeløb_3000kr() {
        // Arrange
        pris = butikPrisliste.createPris(produkt, 1000, 0, 0 );
        pris2 = butikPrisliste.createPris(produkt3, 400, 0, 100);
        order = new Order(7, LocalDate.of(2021,10,30));

        OrderLine orderLine = order.createOrderLine(2, pris); // 2*1000 = 2000
        OrderLine orderLine2 = order.createOrderLine(2, pris2); // (2 * 400) + (2 * 100) = 1000

        order.setRabatStrategy(amountRabat); // 200kr rabat, hvis en orders total beløb >= 3000kr
        order.getRabatStrategy().getRabat(order.orderPris()); // total beløb = 3000 kr

        // ACT
        double actual = order.prisWithRabat(); // 3000kr - 200kr = 2800kr

        // Assert
        Assertions.assertEquals(2800, actual);
    }


    @Test
    void TC8_prisWithRabat_AmountRabat_TotalBeløb_2998kr() {
        // Arrange
        pris = butikPrisliste.createPris(produkt, 1000, 0, 0 );
        pris2 = butikPrisliste.createPris(produkt2, 399, 0, 100);
        order = new Order(8, LocalDate.of(2021,10,30));

        OrderLine orderLine = order.createOrderLine(2, pris); // 2*1000 = 2000
        OrderLine orderLine2 = order.createOrderLine(2, pris2); // (2 * 399) + (2 * 100) = 998

        order.setRabatStrategy(amountRabat); // NO rabat, hvis en orders total beløb < 3000kr
        order.getRabatStrategy().getRabat(order.orderPris()); // total beløb = 2998 kr

        // ACT
        double actual = order.prisWithRabat();

        // Assert
        Assertions.assertEquals(2998, actual); // no discount
    }

    @Test
    void TC9_prisWithRabat_procentRabat_7procent() {
        // Arrange
        pris = butikPrisliste.createPris(produkt, 150, 0, 0 );
        pris2 = butikPrisliste.createPris(produkt2, 150, 0, 100);
        order = new Order(9, LocalDate.of(2021,10,30));

        OrderLine orderLine = order.createOrderLine(8, pris); // 8 * 150 = 1200
        OrderLine orderLine2 = order.createOrderLine(2, pris2); // (produkt : 150*2) + (pant : 100*2) = 500

        order.setRabatStrategy(procentRabat); // 7% rabat
        order.getRabatStrategy().getRabat(order.orderPris()); // total beløb = 1700 kr

        // ACT
        double actual = order.prisWithRabat(); // 1700 - (1700 * (7/100)) = 1581

        // Assert
        Assertions.assertEquals(1581, actual);
    }
}
