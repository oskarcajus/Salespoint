package storage;

import model.Produkt;
import model.Produktgruppe;

import java.lang.reflect.Array;
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
    private static ArrayList<Produktgruppe> produktgrupper = new ArrayList<>();

    public static void addProduktgruppe(Produktgruppe pg) {
        produktgrupper.add(pg);
    }
    public static void removeProduktgruppe(Produktgruppe pg) {
        produktgrupper.remove(pg);
    }
    public static ArrayList<Produktgruppe> getProduktgrupper() {
        return new ArrayList<Produktgruppe>(produktgrupper);
    }
}
