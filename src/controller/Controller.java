package controller;

import model.*;
import storage.*;

import java.util.ArrayList;

public class Controller {

    //Produkt -------------------------
    public static Produkt createProdukt(Produktgruppe produktgruppe, ProduktType produktType, String navn) {
        Produkt p = produktgruppe.createProdukt(produktType, navn);
//        Storage.addProdukt(p);
        return p;
    }

    public static Produkt removeProdukt(Produktgruppe produktgruppe, Produkt produkt) {
        produktgruppe.removeProdukt(produkt);
        return produkt;
    }
    // --------------------------------

    //Produktgruppe -------------------
    public static Produktgruppe createProduktgruppe(String navn) {
        Produktgruppe produktgruppe;
        for (Produktgruppe pg : Storage.getProduktgrupper()) {
            if (pg.getNavn().equals(navn)) {
                throw new IllegalArgumentException("Der findes allerede en produktgruppe med dette navn.");
            }
        }
        produktgruppe = new Produktgruppe(navn);
        Storage.addProduktgruppe(produktgruppe);
        return produktgruppe;
    }

    public static void removeProduktgruppe(Produktgruppe produktgruppe) {
        if (Controller.getProdukterFromProduktgruppe(produktgruppe).size() == 0) {
            Storage.removeProduktgruppe(produktgruppe);
        }
        else {
            throw new RuntimeException("Der er stadig produkter tilknyttet den valgte produktgruppe.\nFjern alle produkter fra produktgruppen før du fortsætter.");
        }
    }

    public static ArrayList<Produkt> getProdukterFromProduktgruppe(Produktgruppe produktgruppe) {
        ArrayList<Produkt> produkter = new ArrayList<>();
        for (Produkt p : produktgruppe.getProdukter()) {
            produkter.add(p);
        }
        return produkter;
    }

    public static void renameProduktgruppe(Produktgruppe produktgruppe, String navn) {
        for (Produktgruppe pg : Storage.getProduktgrupper()) {
            if (pg.getNavn().equals(navn)) {
                throw new IllegalArgumentException("Der findes allerede en produktgruppe med det navn.");
            }
        }
        produktgruppe.setNavn(navn);
    }

    //ProduktType
    public static ProduktType createProduktType(String navn) {
        ProduktType produktType;
        for (ProduktType pt : Storage.getProduktTyper()) {
            if (pt.getNavn().equals(navn)) {
                throw new IllegalArgumentException("Der findes allerede en produkttype med dette navn.");
            }
        }
        produktType = new ProduktType(navn);
        Storage.addProduktType(produktType);
        return produktType;
    }

    public static void removeProduktType(ProduktType produktType) {
        if (Controller.getProdukterFromProduktType(produktType).size() == 0) {
            Storage.removeProduktType(produktType);
        }
        else {
            throw new RuntimeException("Der er stadig produkter tilknyttet den valgte produktgruppe.\nFjern alle produkter fra produktgruppen før du fortsætter.");
        }
    }

    private static ArrayList<Produkt> getProdukterFromProduktType(ProduktType produktType) {
        return produktType.getProdukter();
    }

    public static void renameProduktType(ProduktType produktType, String navn) {
        for (ProduktType pt : Storage.getProduktTyper()) {
            if (pt.getNavn().equals(navn)) {
                throw new IllegalArgumentException("Der findes allerede en produkttype med det navn.");
            }
        }
        produktType.setNavn(navn);
    }


    public static void initContent() {
        //Create produktgrupper
        Controller.createProduktgruppe("Øl");
        Controller.createProduktgruppe("Fustage");
    }

}
