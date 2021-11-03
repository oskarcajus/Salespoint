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

    // Produkt ---------------------------------------------------------------------------------------------------------
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
        if (controller.checkProduktNameIsInProduktgruppe(produktgruppe, navn)){
            throw new IllegalArgumentException("Der er allerede et produkt med det navn tilknyttet produktgruppen.");
        }
        if (controller.checkProduktNameIsInProduktType(produktType, navn)) {
            throw new IllegalArgumentException("Der er allerede et produkt med det navn tilknyttet produkttypen.");
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

    //Produktgruppe ----------------------------------------------------------------------------------------------------
    public Produktgruppe createProduktgruppe(String navn) {
        Produktgruppe produktgruppe;
        for (Produktgruppe pg : storage.getProduktgrupper()) {
            if (pg.getNavn().equalsIgnoreCase(navn)) {
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
            if (pg.getNavn().equalsIgnoreCase(navn)) {
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

    //ProduktType ------------------------------------------------------------------------------------------------------
    public ProduktType createProduktType(String navn) {
        ProduktType produktType;
        for (ProduktType pt : storage.getProduktTyper()) {
            if (pt.getNavn().equalsIgnoreCase(navn)) {
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
            if (pt.getNavn().equalsIgnoreCase(navn)) {
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

    //-------------Help method------------------------------------------------------------------------------------------

    public boolean checkProduktNameIsInProduktgruppe(Produktgruppe produktgruppe, String navn) {
        boolean check = false;
        for (Produkt p : produktgruppe.getProdukter()) {
            if (p.getName().equalsIgnoreCase(navn)) { // big letter(ØL) small letter(øl) should be same obj
                check = true;
                break;
            }
        }
        return check;
    }

    public boolean checkProduktNameIsInProduktType(ProduktType produktType, String navn) {
        boolean check = false;
        for (Produkt p : produktType.getProdukter()) {
            if (p.getName().equalsIgnoreCase(navn)) {
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
        //produkter.sort((Produkt p1, Produkt p2) -> p1.getName().compareTo(p2.getName()));
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

    //SalgSituation ----------------------------------------------------------------------------------------------------

    public ArrayList<SalgsSituation> getSalgsSituationer() {
        return new ArrayList<>(storage.getSalgsSituationer());
    }

    public void removeSalgsSituation(SalgsSituation salgsSituation) {
        storage.removeSalgsSituation(salgsSituation);
    }

    public SalgsSituation createSalgsSituation(String navn) {
        SalgsSituation salgsSituation = new SalgsSituation(navn);
        for (SalgsSituation ss :  storage.getSalgsSituationer()) {
            if (ss.getNavn().equalsIgnoreCase(navn)) {
                throw new IllegalArgumentException("Der findes allerede en salgssituation med det navn.");
            }
        }
        storage.addSalgsSituation(salgsSituation);
        return salgsSituation;
    }

    //Pris -------------------------------------------------------------------------------------------------------------s

    public ArrayList<Pris> getPriserFromSalgsSituation(SalgsSituation salgsSituation) {
        return new ArrayList<>(salgsSituation.getPriser());
    }

    public Pris createPris(SalgsSituation salgsSituation, Produkt produkt, double pris, int klipPris, double pantPris) {
        if (produkt == null) {
            throw new IllegalArgumentException("Du har ikke valgt et produkt.");
        }
        if (pris < 0 || klipPris < 0 || pantPris < 0) {
            throw new IllegalArgumentException("En pris må ikke være mindre end 0.");
        }
        if (pris == 0 && klipPris == 0 && pantPris == 0) {
            throw new IllegalArgumentException("Vælg mindst én pris.");
        }

        for (Pris p : salgsSituation.getPriser()) {
            if (p.getProdukt().equals(produkt)) {
                throw new IllegalArgumentException("Produktet har allerede en pris i denne salgssituation. \n" +
                        "Redigér i stedet prisen for produktet.");
            }
        }
        return salgsSituation.createPris(produkt, pris, klipPris, pantPris);
    }

    public void redigerPris(Pris pris, double newPris, int newKlipPris, double newPantPris) {
        if (newPris < 0 || newKlipPris < 0 || newPantPris < 0) {
            throw new IllegalArgumentException("En pris må ikke være mindre end 0.");
        }
        if (newPris == 0 && newKlipPris == 0 && newPantPris == 0) {
            throw new IllegalArgumentException("Vælg mindst én pris.");
        }

        pris.setPris(newPris);
        pris.setKlipPris(newKlipPris);
        pris.setPantPris(newPantPris);
    }

    public void removePris(SalgsSituation salgsSituation, Pris pris) {
        salgsSituation.removePris(pris);
    }

    //Order ------------------------------------------------------------------------------------------------------------

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

    public RundvisningOrder createRundvisningOrder(int orderNr, LocalDate oprettelsesDato, LocalDate expectingBetalingsDato) {
        for (Order o : controller.getOrders()) {
            if (o.getOrderNr() == orderNr) {
                throw new IllegalArgumentException("Der findes allerede en ordre med det ordrenr " + orderNr);
            }
        }
        RundvisningOrder rundvisningsOrder = new RundvisningOrder(orderNr, oprettelsesDato, expectingBetalingsDato);
        storage.addOrder(rundvisningsOrder);
        return rundvisningsOrder;
    }

    public UdlejningsOrder createUdlejningOrder(int orderNr, LocalDate oprettelsesDato, LocalDate forventetReturDato) {
        for (Order o : controller.getOrders()) {
            if (o.getOrderNr() == orderNr) {
                throw new IllegalArgumentException("Der findes allerede en ordre med det ordrenr.");
            }
        }
        UdlejningsOrder udlejningsOrder = new UdlejningsOrder(orderNr, oprettelsesDato, forventetReturDato);
        storage.addOrder(udlejningsOrder);
        return udlejningsOrder;
    }

    public Order createReturnOrder(int orderNr) {
        Order returOrder = null;

        boolean found = false;
        for (Order order : controller.getOrders()) {
            if (order.getOrderNr() == orderNr && !found) {
                found = true;
                //Laver en ny ordre
                returOrder = controller.createOrder(order.getOrderNr() + 10000000, LocalDate.now());
                returOrder.setRefOrderNr(orderNr);
                //Kopierer ordrelinjer fra order til returorder
                //Undgår shallow-copy ved at lave et nyt Pris-objekt
                for (OrderLine ol : order.getOrderLines()) {
                    returOrder.createOrderLine(ol.getAntalProdukt(), new Pris(ol.getPris().getProdukt(),
                            ol.getPris().getPris(), ol.getPris().getKlipPris(), ol.getPris().getPantPris()));

                }
                //Sætter beløbet på alle orderlinjer til modsat fortegn
                for (OrderLine olRetur : returOrder.getOrderLines()) {
                    olRetur.setOrderLinePrisBeløb(olRetur.getOrderLinePrisBeløb() * (- 1));
                    olRetur.setOrderLinePantBeløb(olRetur.getOrderLinePantBeløb() * (- 1));
                }
            }
        }
        return returOrder;
    }

    public void removeOrder(Order order) {
        storage.removeOrder(order);
    }

    // We should not add these all calculation redundant methods inside of Controller,
   // because we already have it inside of model classes. --> (redundant)
    // we have to just use those methods throuth controller, whenever we need.

   // public int beregnKlikPris(Order order) {
    //   return order.orderKlipPris();
    // }

    //Må gerne være negativ, da den også bruges til returordre
    //  public double beregnPris(Order order) {
    //      return order.prisWithRabat();
    // }

    //  public int beregnKlipPris(Order order) {
    //      int sum = 0;
    //    for (OrderLine ol : order.getOrderLines()) {
    //        sum += ol.getOrderLineKlipBeløb();
    //     }
    //     return sum;
    //  }
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

    public void redigerOrderLine(OrderLine orderLine, int antalProdukt, double pris, int klipPris, double pantPris) {
        if (orderLine == null || antalProdukt == 0) {
            throw new IllegalArgumentException("Du mangler at udfylde en eller argumenter.");
        }
        if (pris >0){
            orderLine.setOrderLinePrisBeløb(pris);
        }
        if (pantPris >0){
            orderLine.setOrderLinePantBeløb(pantPris);
        }
        if (klipPris >0) {
            orderLine.setOrderLineKlipBeløb(klipPris);
        }
        orderLine.setAntalProdukt(antalProdukt);
    }

    public void removeOrderLine(Order order, OrderLine orderLine) {
        order.removeOrderLine(orderLine);
    }

    //---------------------------------------------------------------------------

    //Kunde -----------------------------------------------------------------

    public Kunde createKunde(String navn, String nummer) {
        if (navn.isEmpty() || nummer.isEmpty()) {
            throw new IllegalArgumentException("Du mangler at udfylde en eller argumenter.");
        }
        Kunde kunde = new Kunde(navn, nummer);
        storage.addKunde(kunde);
        return kunde;
    }

    public void removeKunde(Kunde kunde){
        storage.removeKunde(kunde);
    }

    public ArrayList<Kunde> getKunder() {
        return new ArrayList<>(storage.getKunder());
    }

    public ArrayList<Order> getKundeOrders(Kunde kunde) {
        return kunde.getOrders();
    }

    // Statistik for GUI
    //-------------------statistik af solgtKlippeKort og brugtKlipper-----------------------------------------------------

    // solgtKlippeKort
    public int solgtKlippeKort(LocalDate startDate, LocalDate endDate) {
        int solgtKlippeKort = 0;

        for (Order order : this.getOrders()) {
            int compareTo1 = order.getOprettelsesDato().compareTo(startDate);
            int compareTo2 = order.getOprettelsesDato().compareTo(endDate);

            if (compareTo1 < 0 || compareTo2 > 0) {
                throw new IllegalArgumentException("Der er ingen klippekort solgt i det angivne datointerval");
            } else {
                for (OrderLine ol : order.getOrderLines()) {
                    if (ol.getPris().getProdukt().getProduktgruppe().getNavn().equalsIgnoreCase("KlippeKort"))
                        solgtKlippeKort += ol.getAntalProdukt();
                }
            }
        }
        return solgtKlippeKort;
    }

    // brugtKlips
    public int brugtKlip(LocalDate startDate, LocalDate endDate) {
        int sum = 0;

        for (Order order : this.getOrders()) {
            int compareTo1 = order.getOprettelsesDato().compareTo(startDate);
            int compareTo2 = order.getOprettelsesDato().compareTo(endDate);

            if (compareTo1 < 0 || compareTo2 > 0) {
                throw new IllegalArgumentException("Der er ingen klip brugt i det angivne datointerval");

            } else {
                if (order.getBetalingsType().equals(BetalingsType.KLIPPEKORT)) {
                    for (OrderLine ol : order.getOrderLines()) {

                        if (ol.getOrderLineKlipBeløb() != 0) {
                            sum += ol.getOrderLineKlipBeløb();
                        }
                    }
                }
            }
        }
        return sum;
    }

    // En Oversigt over ikke afleverede udlejede produkter.----------------------------------------------------------
    public ArrayList<Produkt> ikkeReturnProdukt() {

        ArrayList<Produkt> oversigt = new ArrayList<>();

        for (Order order : this.getOrders()) {
            if (order instanceof UdlejningsOrder) {
                UdlejningsOrder udlejningsOrder = (UdlejningsOrder) order;

                if (udlejningsOrder.getForventetReturDato().isBefore(LocalDate.now())) {
                    for (OrderLine ol : order.getOrderLines()) {
                        oversigt.add(ol.getPris().getProdukt());
                    }
                }
            }
        }
        return oversigt;
    }
    //-------------------------------------------------------------------------------------------------------------
    

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

        //Create kunder
        // Kunde
        Kunde k1 = controller.createKunde("Arosan","12345678");
        Kunde k2 = controller.createKunde("Oskar", "09876543");
        Kunde k3 = controller.createKunde("Kim", "23456789");

        //Create order & orderline
        Order o1 = controller.createOrder(1, LocalDate.of(2021, 9, 1));
        OrderLine ol1 = controller.createOrderLine(o1, 3, pris1);
        OrderLine ol2 = controller.createOrderLine(o1, 1, pris3);

        Order o2 = controller.createOrder(2, LocalDate.of(2021, 9, 1));

        Order o5 = controller.createOrder(5, LocalDate.of(2001,2,2));

        //Udlejning
        Order uo1 = controller.createUdlejningOrder(3, LocalDate.of(2021, 5, 1),
                LocalDate.of(2021, 6, 1));
        OrderLine ol3 = controller.createOrderLine(uo1, 2, pris3);

        Order uo2 = controller.createUdlejningOrder(4, LocalDate.of(2021, 6, 5),
                LocalDate.of(2021, 6, 12));

        //Retur
        Order uor1 = controller.createReturnOrder(3);

        System.out.println(uo1);
        System.out.println(uo1.orderPris()); // you have to use this instead
       // System.out.println(controller.beregnPris(uo1));

        System.out.println(uor1);
        System.out.println(uor1.orderPris()); // you have to use this instead
       // System.out.println(controller.beregnPris(uor1));

        // Ordrer på kunden
        o5.setKunde(k1);
        o2.setKunde(k2);
        o1.setKunde(k3);
    }
}

