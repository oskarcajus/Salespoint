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
}
