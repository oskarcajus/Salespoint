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
        return produkter;
    }

    public void addProdukter(ArrayList<Produkt> produkt) {
        if (!produkter.contains(produkt)) {
            produkter.add(produkt);
            produkter.setProduktType(this);
        }

}

    @Override
    public String toString() {
        return navn;
    }
}
