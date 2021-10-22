package controller;

import javafx.collections.ObservableList;
import model.*;
import storage.*;

import java.util.ArrayList;
import java.util.Collections;

public class Controller {

    //Produkt -------------------------
    public static Produkt createProdukt(Produktgruppe produktgruppe, ProduktType produktType, String navn) {
        Produkt produkt;
        if (Controller.checkProduktNameIsInProduktgruppe(produktgruppe, navn)){
            throw new IllegalArgumentException("Der er allerede et produkt med det navn tilknyttet produktgruppen.");
        }
        if (Controller.checkProduktNameIsInProduktType(produktType, navn)) {
            throw new IllegalArgumentException("Der er allerede et produkt med det navn tilknyttet produkttypen.");
        }
        produkt = produktgruppe.createProdukt(produktType, navn);
        return produkt;
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
        } else {
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

    public static ArrayList<Produktgruppe> getProduktgrupper() {
        return new ArrayList<>(Storage.getProduktgrupper());
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
        } else {
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

    public static ArrayList<ProduktType> getProduktTyper() {
        return new ArrayList<>(Storage.getProduktTyper());
    }

    //-------------------------------------

    public static boolean checkProduktNameIsInProduktgruppe(Produktgruppe produktgruppe, String navn) {
        boolean check = false;
        for (Produkt p : produktgruppe.getProdukter()) {
            if (p.getName().equals(navn)) {
                check = true;
                break;
            }
        }
        return check;
    }

    public static boolean checkProduktNameIsInProduktType(ProduktType produktType, String navn) {
        boolean check = false;
        for (Produkt p : produktType.getProdukter()) {
            if (p.getName().equals(navn)) {
                check = true;
                break;
            }
        }
        return check;
    }


    public static ArrayList<Produkt> getProdukterInProduktgruppeAndOrProduktType(ObservableList<Produktgruppe> produktgrupper,
                                                                                 ObservableList<ProduktType> produktTyper) {
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
        Collections.sort(produkter);
        return produkter;
    }

    public static void initContent() {
        //Create produktgrupper
        Produktgruppe pg1 = Controller.createProduktgruppe("Flaske");
        Produktgruppe pg2 = Controller.createProduktgruppe("Fustage");

        //Create produktTyper
        ProduktType pt1 = Controller.createProduktType("Produkt");
        ProduktType pt2 = Controller.createProduktType("Udlejning");

        //Create produkter
        Produkt p1 = Controller.createProdukt(pg1, pt1, "Klosterbryg");
        Produkt p2 = Controller.createProdukt(pg1, pt1, "Sweet Georgia Brown");
        Produkt p3 = Controller.createProdukt(pg2, pt2, "Jazz Classic, 25 liter");
        Produkt p4 = Controller.createProdukt(pg2, pt2, "Extra Pilsner, 25 liter");


    }

}

