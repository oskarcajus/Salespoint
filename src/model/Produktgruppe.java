package model;

import java.util.ArrayList;

public class Produktgruppe {
    private String navn;
    private ArrayList<Produkt> produkter = new ArrayList<Produkt>();

    public Produktgruppe(String navn) {
        this.navn = navn;
    }

    public Produkt createProdukt(String navn, ProduktType produktType) {
        Produkt produkt = null;
        for (Produkt p : produkter) {
            if (p.getName().equals(navn)) {
                throw new IllegalArgumentException("Der findes allerede et produkt med dette navn.");
            }
            produkt = new Produkt(navn, produktType, this);
        }
        return produkt;
    }

    public void addProdukt (Produkt produkt){
        if (!this.produkter.contains(produkt)) {
            produkter.add(produkt);
            produkt.setProduktgruppe(this);
        }
    }

    public void removeProdukt (Produkt produkt){
        if (this.produkter.contains(produkt)) {
            produkter.remove(produkt);
            produkt.setProduktgruppe(null);
        }
    }




}
