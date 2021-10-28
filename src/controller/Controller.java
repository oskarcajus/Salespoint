package controller;

import javafx.collections.ObservableList;
import model.*;
import storage.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Controller {
    private static Controller controller;
    private final Storage storage = Storage.getStorage();

    // Singleton metode
    public static Controller getController() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    //Produkt -------------------------
    public Produkt createProdukt(Produktgruppe produktgruppe, ProduktType produktType, String navn) {
        Produkt produkt;
        if (controller.checkProduktNameIsInProduktgruppe(produktgruppe, navn)){
            throw new IllegalArgumentException("Der er allerede et produkt med det navn tilknyttet produktgruppen.");
        }
        if (controller.checkProduktNameIsInProduktType(produktType, navn)) {
            throw new IllegalArgumentException("Der er allerede et produkt med det navn tilknyttet produkttypen.");
        }
        produkt = produktgruppe.createProdukt(produktType, navn);
        return produkt;
    }

    public Produkt redigerProdukt(Produkt produkt, String navn, Produktgruppe produktgruppe, ProduktType produktType) {
        for (Produkt p : produktgruppe.getProdukter()) {
            if (p.getName().equals(navn)) {
                throw new IllegalArgumentException("Der findes allerede et produkt med det navn i " +
                        produktgruppe.getNavn() + ".");
            }
        }
        for (Produkt p : produktType.getProdukter()) {
            if (p.getName().equals(navn)) {
                throw new IllegalArgumentException("Der findes allerede et produkt med det navn i " +
                        produktType.getNavn() + ".");
            }
        }

        produkt.setName(navn);
        produkt.setProduktgruppe(produktgruppe);
        produkt.setProduktType(produktType);

        return produkt;
    }


    public void removeProdukt(Produkt produkt) {
        produkt.getProduktgruppe().removeProdukt(produkt);
        produkt.getProduktType().removeProdukt(produkt);
    }

    public ArrayList<Produkt> getAllProdukter() {
       return getProdukterInProduktgruppeAndOrProduktType(controller.getProduktgrupper(), controller.getProduktTyper());
    }

    public Produktgruppe getProduktgruppeFromProdukt(Produkt produkt) {
        return produkt.getProduktgruppe();
    }

    public ProduktType getProduktTypeFromProdukt(Produkt produkt) {
        return produkt.getProduktType();
    }
    // --------------------------------

    //Produktgruppe -------------------
    public Produktgruppe createProduktgruppe(String navn) {
        Produktgruppe produktgruppe;
        for (Produktgruppe pg : storage.getProduktgrupper()) {
            if (pg.getNavn().equals(navn)) {
                throw new IllegalArgumentException("Der findes allerede en produktgruppe med dette navn.");
            }
        }
        produktgruppe = new Produktgruppe(navn);
        storage.addProduktgruppe(produktgruppe);
        return produktgruppe;
    }

    public void removeProduktgruppe(Produktgruppe produktgruppe) {
        if (controller.getProdukterFromProduktgruppe(produktgruppe).size() == 0) {
            storage.removeProduktgruppe(produktgruppe);
        } else {
            throw new RuntimeException("Der er stadig produkter tilknyttet den valgte produktgruppe.\nFjern alle produkter fra produktgruppen før du fortsætter.");
        }
    }

    public ArrayList<Produkt> getProdukterFromProduktgruppe(Produktgruppe produktgruppe) {
        ArrayList<Produkt> produkter = new ArrayList<>();
        for (Produkt p : produktgruppe.getProdukter()) {
            produkter.add(p);
        }
        return produkter;
    }

    public void renameProduktgruppe(Produktgruppe produktgruppe, String navn) {
        for (Produktgruppe pg : storage.getProduktgrupper()) {
            if (pg.getNavn().equals(navn)) {
                throw new IllegalArgumentException("Der findes allerede en produktgruppe med det navn.");
            }
        }
        produktgruppe.setNavn(navn);
    }

    public ArrayList<Produktgruppe> getProduktgrupper() {
        ArrayList<Produktgruppe> produktgrupper = new ArrayList<>(storage.getProduktgrupper());
//        produktgrupper.sort((Produktgruppe pg1, Produktgruppe pg2) -> pg1.getNavn().compareTo(pg2.getNavn()));
        produktgrupper.sort(Comparator.comparing(Produktgruppe::getNavn));
        return produktgrupper;
    }

    //------------------------------------

    //ProduktType -----------------------------
    public ProduktType createProduktType(String navn) {
        ProduktType produktType;
        for (ProduktType pt : storage.getProduktTyper()) {
            if (pt.getNavn().equals(navn)) {
                throw new IllegalArgumentException("Der findes allerede en produkttype med dette navn.");
            }
        }
        produktType = new ProduktType(navn);
        storage.addProduktType(produktType);
        return produktType;
    }

    public void removeProduktType(ProduktType produktType) {
        if (controller.getProdukterFromProduktType(produktType).size() == 0) {
            storage.removeProduktType(produktType);
        } else {
            throw new RuntimeException("Der er stadig produkter tilknyttet den valgte produktgruppe." +
                    "\nFjern alle produkter fra produktgruppen før du fortsætter.");
        }
    }

    private ArrayList<Produkt> getProdukterFromProduktType(ProduktType produktType) {
        return produktType.getProdukter();
    }

    public void renameProduktType(ProduktType produktType, String navn) {
        for (ProduktType pt : storage.getProduktTyper()) {
            if (pt.getNavn().equals(navn)) {
                throw new IllegalArgumentException("Der findes allerede en produkttype med det navn.");
            }
        }
        produktType.setNavn(navn);
    }

    public ArrayList<ProduktType> getProduktTyper() {
        ArrayList<ProduktType> produktTyper = new ArrayList<>(storage.getProduktTyper());
//        produktTyper.sort((ProduktType pt1, ProduktType pt2) -> pt1.getNavn().compareTo(pt2.getNavn()));
        produktTyper.sort(Comparator.comparing(ProduktType::getNavn));
        return produktTyper;
    }

    //-------------------------------------

    public boolean checkProduktNameIsInProduktgruppe(Produktgruppe produktgruppe, String navn) {
        boolean check = false;
        for (Produkt p : produktgruppe.getProdukter()) {
            if (p.getName().equals(navn)) {
                check = true;
                break;
            }
        }
        return check;
    }

    public boolean checkProduktNameIsInProduktType(ProduktType produktType, String navn) {
        boolean check = false;
        for (Produkt p : produktType.getProdukter()) {
            if (p.getName().equals(navn)) {
                check = true;
                break;
            }
        }
        return check;
    }


    public ArrayList<Produkt> getProdukterInProduktgruppeAndOrProduktType(ObservableList<Produktgruppe> produktgrupper,
                                                                                 ObservableList<ProduktType> produktTyper) {
        ArrayList<Produkt> produkter = new ArrayList<>();
        //Skal nok være en mergesort!!

        for (Produktgruppe pg : produktgrupper) {
            produkter.addAll(pg.getProdukter());
        }
        for (ProduktType pt : produktTyper) {
            for (Produkt p : pt.getProdukter()) {
                if (!produkter.contains(p)) {
                    produkter.add(p);
                }
            }
        }
        //        produkter.sort((Produkt p1, Produkt p2) -> p1.getName().compareTo(p2.getName()));
        produkter.sort(Comparator.comparing(Produkt::getName));
        return produkter;
    }

    public ArrayList<Produkt> getProdukterInProduktgruppeAndOrProduktType(ArrayList<Produktgruppe> produktgrupper,
                                                                                 ArrayList<ProduktType> produktTyper) {
        //Skal nok være en mergesort!!
        ArrayList<Produkt> produkter = new ArrayList<>();
        for (Produktgruppe pg : produktgrupper) {
            produkter.addAll(pg.getProdukter());
        }
        for (ProduktType pt : produktTyper) {
            for (Produkt p : pt.getProdukter()) {
                if (!produkter.contains(p)) {
                    produkter.add(p);
                }
            }
        }
        //        produkter.sort((Produkt p1, Produkt p2) -> p1.getName().compareTo(p2.getName()));
        produkter.sort(Comparator.comparing(Produkt::getName));
        return produkter;
    }

    //----------------------------------------------

    //SalgSituation --------------------------------

    public ArrayList<SalgsSituation> getSalgsSituationer() {
        return new ArrayList<>(storage.getSalgsSituationer());
    }

    public void removeSalgsSituation(SalgsSituation salgsSituation) {
        storage.removeSalgsSituation(salgsSituation);
    }

    public SalgsSituation createSalgsSituation(String navn) {
        SalgsSituation salgsSituation = new SalgsSituation(navn);
        for (SalgsSituation ss :  storage.getSalgsSituationer()) {
            if (ss.getNavn().equals(navn)) {
                throw new IllegalArgumentException("Der findes allerede en salgssituation med det navn.");
            }
        }
        storage.addSalgsSituation(salgsSituation);
        return salgsSituation;
    }

    //----------------------------------------------

    //Pris ------------------------------------------

    public ArrayList<Pris> getPriserFromSalgsSituation(SalgsSituation salgsSituation) {
        return new ArrayList<>(salgsSituation.getPriser());
    }

    public Pris createPris(SalgsSituation salgsSituation, Produkt produkt, double pris, int klipPris, double pantPris) {
        if (produkt == null) {
            throw new IllegalArgumentException("Du har ikke valgt et produkt.");
        }

        for (Pris p : salgsSituation.getPriser()) {
            if (p.getProdukt().equals(produkt)) {
                throw new IllegalArgumentException("Produktet har allerede en pris i denne salgssituation.");
            }
        }
        return salgsSituation.createPris(produkt, pris, klipPris, pantPris);
    }

    public void removePris(SalgsSituation salgsSituation, Pris pris) {
        salgsSituation.removePris(pris);
    }

    //---------------------------------------------

    //Order ---------------------------------------------------------------------

    public ArrayList<Order> getOrders() {
        return new ArrayList<>(storage.getOrders());
    }

    public Order createOrder(int orderNr, LocalDate oprettelsesDato) {
        for (Order o : controller.getOrders()) {
            if (o.getOrderNr() == orderNr) {
                throw new IllegalArgumentException("Der findes allerede en ordre med det ordrenr.");
            }
        }
        Order order = new Order(orderNr, oprettelsesDato);
        storage.addOrder(order);

        return order;
    }

    public Order createRundvisningOrder(int orderNr, LocalDate oprettelsesDato, LocalDate expectingBetalingsDato) {
        for (Order o : controller.getOrders()) {
            if (o.getOrderNr() == orderNr) {
                throw new IllegalArgumentException("Der findes allerede en ordre med det ordrenr.");
            }
        }

        Order order = new RundvisningOrder(orderNr, oprettelsesDato, expectingBetalingsDato);
        storage.addOrder(order);

        return order;
    }

    public Order createUdlejningOrder(int orderNr, LocalDate oprettelsesDato, LocalDate expectingBetalingsDato, LocalDate forventetReturDato) {
        for (Order o : controller.getOrders()) {
            if (o.getOrderNr() == orderNr) {
                throw new IllegalArgumentException("Der findes allerede en ordre med det ordrenr.");
            }
        }

        Order order = new UdlejningsOrder(orderNr, oprettelsesDato, expectingBetalingsDato, forventetReturDato);
        storage.addOrder(order);

        return order;
    }

    public Order createReturnOrder(int orderNr) {
        Order returOrder = null;

        for (Order order : controller.getOrders()) {
            if (order.getOrderNr() == orderNr) {
                //Laver en ny ordre
                returOrder = controller.createOrder(order.getOrderNr() + 10000000, LocalDate.now());
                //Kopierer ordrelinjer fra order til returorder
                for (OrderLine ol : order.getOrderLines()) {
                    returOrder.createOrderLine(ol.getAntalProdukt(), ol.getPris());
                }
                //Sætter beløbet på alle orderlinjer til modsat fortegn
                for (OrderLine ol : returOrder.getOrderLines()) {
                    ol.setOrderLineBeløb(ol.getOrderLineBeløb() * (- 1));
                }
                break;
            }
        }


        return returOrder;
    }

    public void removeOrder(Order order) {
        storage.removeOrder(order);
    }
    //---------------------------------------------------------------------------

    //Orderline -----------------------------------------------------------------

    public OrderLine createOrderLine(Order order, int antalProdukt, Pris pris) {
        if (order == null || antalProdukt == 0 || pris == null) {
            throw new IllegalArgumentException("Du mangler at udfylde en eller argumenter.");
        }
        return order.createOrderLine(antalProdukt, pris);
    }

    public void redigerOrderLine(OrderLine orderLine, int antalProdukt, Pris pris) {
        if (orderLine == null || antalProdukt == 0 || pris == null) {
            throw new IllegalArgumentException("Du mangler at udfylde en eller argumenter.");
        }
        orderLine.setAntalProdukt(antalProdukt);
        orderLine.setPris(pris);
    }

    public void removeOrderLine(Order order, OrderLine orderLine) {
        order.removeOrderLine(orderLine);
    }

    //---------------------------------------------------------------------------

    public void initContent() {
        //Create produktgrupper
        Produktgruppe pg1 = controller.createProduktgruppe("Flaske");
        Produktgruppe pg2 = controller.createProduktgruppe("Fustage");

        //Create produktTyper
        ProduktType pt1 = controller.createProduktType("Produkt");
        ProduktType pt2 = controller.createProduktType("Udlejning");

        //Create produkter
        Produkt p1 = controller.createProdukt(pg1, pt1, "Klosterbryg");
        Produkt p2 = controller.createProdukt(pg1, pt1, "Sweet Georgia Brown");
        Produkt p3 = controller.createProdukt(pg2, pt2, "Jazz Classic, 25 liter");
        Produkt p4 = controller.createProdukt(pg2, pt2, "Extra Pilsner, 25 liter");

        //Create salgssituation
        SalgsSituation s1 = controller.createSalgsSituation("Butik");
        SalgsSituation s2 = controller.createSalgsSituation("Fredagsbar");


        //Create priser
        Pris pris1 = controller.createPris(s1, p1, 35, 0, 0);
        Pris pris2 = controller.createPris(s2, p1, 35, 2, 0);
        Pris pris3 = controller.createPris(s1, p3, 500, 0, 200);

    }



}

