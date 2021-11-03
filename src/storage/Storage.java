package storage;

import model.*;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

public class Storage {

    private static Storage storage;

    //Lav singleton
    public static Storage getStorage() {
        if (storage == null) {
            storage = new Storage();
        }
        return storage;
    }

    //-----------------------------------------------------------------------

    //Produktgruppe
    private final ArrayList<Produktgruppe> produktgrupper = new ArrayList<>();

    public void addProduktgruppe(Produktgruppe pg) {
        produktgrupper.add(pg);
    }

    public void removeProduktgruppe(Produktgruppe pg) {
        produktgrupper.remove(pg);
    }

    public ArrayList<Produktgruppe> getProduktgrupper() {
        return new ArrayList<>(this.produktgrupper);
    }

    //ProduktType
    private final ArrayList<ProduktType> produktTyper = new ArrayList<>();

    public void addProduktType(ProduktType pt) {
        produktTyper.add(pt);
    }

    public void removeProduktType(ProduktType pt) {
        produktTyper.remove(pt);
    }

    public ArrayList<ProduktType> getProduktTyper() {
        return new ArrayList<>(this.produktTyper);
    }

    //SalgsSituation
    private final ArrayList<SalgsSituation> salgsSituationer = new ArrayList<>();

    public void addSalgsSituation(SalgsSituation ss) {
        salgsSituationer.add(ss);
    }

    public void removeSalgsSituation(SalgsSituation ss) {
        salgsSituationer.remove(ss);
    }

    public ArrayList<SalgsSituation> getSalgsSituationer() {
        return new ArrayList<>(this.salgsSituationer);
    }

    //Order
    private final ArrayList<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    public ArrayList<Order> getOrders() {
        return new ArrayList<>(this.orders);
    }

    // Kunde
    private final ArrayList<Kunde> kunder = new ArrayList<>();

    public void addKunde(Kunde kunde) {
        kunder.add(kunde);
    }

    public void removeKunde(Kunde kunde) {
        kunder.remove(kunde);
    }

    public ArrayList<Kunde> getKunder() {
        return kunder;
    }

    public void clearStorage() {
        this.produktgrupper.clear();
        this.produktTyper.clear();
        this.salgsSituationer.clear();
        this.orders.clear();
        this.kunder.clear();
    }

//--------KlippeKort statistik---------------------------------------------------------------------------------------

    // solgtKlippeKort
    public int solgtKlippeKort(LocalDate startDate, LocalDate endDate) {
        int solgtKlippeKort = 0;

        for (Order order : this.getOrders()) {
            int compareTo1 = order.getOprettelsesDato().compareTo(startDate);
            int compareTo2 = order.getOprettelsesDato().compareTo(endDate);

            if (compareTo1 < 0 || compareTo2 > 0) {
                throw new IllegalArgumentException("Ugyldig Order Dato");
            } else {
                for (OrderLine ol : order.getOrderLines()) {
                    if (ol.getPris().getProdukt().getProduktgruppe().getNavn().equalsIgnoreCase("KlippeKort"))
                        solgtKlippeKort += ol.getAntalProdukt();
                }
            }
        }
        return solgtKlippeKort;
    }

    // brugtKlippeKort
    public int brugtKlip(LocalDate startDate, LocalDate endDate) {
        int sum = 0;

        for (Order order : this.getOrders()) {
            if(order.getOprettelsesDato().isAfter(startDate) && order.getOprettelsesDato().isBefore(endDate)){

               if (order.getBetalingsType().equals(BetalingsType.KLIPPEKORT)) {
                   for (OrderLine ol : order.getOrderLines()) {

                       if (ol.getOrderLineKlipBeløb() != 0)
                           sum += ol.getOrderLineKlipBeløb();
                        }
                    }
                }
            throw new IllegalArgumentException("Ugyldig Order Dato");
            }
        return sum;
    }
// Oversigt over ikke afleverede udlejede produkter.----------------------------------------------------------
public ArrayList<Produkt> ikkeReturnProdukt(){

        ArrayList<Produkt> oversigt = new ArrayList<>();
        Produkt ikkeAflejeveret = null;

        for(Order order : this.orders){
            if(order instanceof UdlejningsOrder){
                UdlejningsOrder udlejningsOrder = (UdlejningsOrder)order;

                if(udlejningsOrder.getForventetReturDato().isBefore(LocalDate.now())){
                    for(OrderLine ol : order.getOrderLines()){
                       ikkeAflejeveret = ol.getPris().getProdukt();
                    }
                }
                    oversigt.add(ikkeAflejeveret);
                }
            }
        return oversigt;
        }
}

