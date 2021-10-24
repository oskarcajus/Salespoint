package storage;

import model.ProduktType;
import model.Produktgruppe;
import model.SalgsSituation;

import java.util.ArrayList;

public class Storage {

    //Kommentar fra Oskar: Skal vi have produkter i storage? Jeg tror at vi skal hente dem fra produktgrupper i stedet
    //Produkt -----------------
//    private static ArrayList<Produkt> produkter = new ArrayList<>();
//
//    public static void addProdukt(Produkt p) {
//        produkter.add(p);
//    }
//    public static void removeProdukt(Produkt p) {
//        produkter.remove(p);
//    }
//    public static ArrayList<Produkt> getProdukter() {
//        return new ArrayList<Produkt>(Storage.produkter);
//    }
    //-------------------------

    //Produktgruppe
    private static final ArrayList<Produktgruppe> produktgrupper = new ArrayList<>();

    public static void addProduktgruppe(Produktgruppe pg) {
        produktgrupper.add(pg);
    }
    public static void removeProduktgruppe(Produktgruppe pg) {
        produktgrupper.remove(pg);
    }
    public static ArrayList<Produktgruppe> getProduktgrupper() {
        return new ArrayList<Produktgruppe>(produktgrupper);
    }

    //ProduktType
    private static final ArrayList<ProduktType> produktTyper = new ArrayList<>();

    public static void addProduktType(ProduktType pt) {
        produktTyper.add(pt);
    }
    public static void removeProduktType(ProduktType pt) {
        produktTyper.remove(pt);
    }
    public static ArrayList<ProduktType> getProduktTyper() {
        return new ArrayList<ProduktType>(Storage.produktTyper);
    }

    //SalgsSituation
    private static final ArrayList<SalgsSituation> salgsSituationer = new ArrayList<>();

    public static void addSalgsSituation(SalgsSituation ss) {
        salgsSituationer.add(ss);
    }
    public static void removeSalgsSituation(SalgsSituation ss) {
        salgsSituationer.remove(ss);
    }
    public static ArrayList<SalgsSituation> getSalgsSituationer() {
        return new ArrayList<SalgsSituation>(Storage.salgsSituationer);
    }
}
