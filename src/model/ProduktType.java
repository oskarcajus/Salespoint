package model;

import java.util.ArrayList;

public class ProduktType {
    private String navn;
    private ArrayList<Produkt> produkter = new ArrayList<Produkt>();

    public ProduktType(String navn) {
        this.navn = navn;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public ArrayList<Produkt> getProdukter() {
        return new ArrayList<>(this.produkter);
    }

    public void addProdukt(Produkt produkt) {
        if (!produkter.contains(produkt)) {
            produkter.add(produkt);
            produkt.setProduktType(this);
        }

}

    @Override
    public String toString() {
        return navn;
    }
}
