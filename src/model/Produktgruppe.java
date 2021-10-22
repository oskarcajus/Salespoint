package model;

import java.util.ArrayList;

public class Produktgruppe {
    private String navn;
    private ArrayList<Produkt> produkter = new ArrayList<Produkt>();

    public Produktgruppe(String navn) {
        this.navn = navn;
    }

    public Produkt createProdukt(ProduktType produktType, String navn) {
        Produkt produkt;
        for (Produkt p : produkter) {
            if (p.getName().equals(navn)) {
                throw new IllegalArgumentException("Der findes allerede et produkt med dette navn.");
            }
        }
        produkt = new Produkt(navn, produktType, this);
        // De her to gør at produktet bliver added x2 til Produktgruppe og ProduktType
        // Vi skal finde ud af hvor de skal være placeret
//        this.produkter.add(produkt);
//        produktType.addProdukt(produkt);
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

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    @Override
    public String toString() {
        return this.getNavn();
    }

    public ArrayList<Produkt> getProdukter() {
        return new ArrayList<Produkt>(this.produkter);
    }
}
