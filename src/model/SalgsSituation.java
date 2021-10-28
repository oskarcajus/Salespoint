package model;

import java.util.ArrayList;

public class SalgsSituation {
    private String navn;
    private ArrayList<Pris> priser = new ArrayList<Pris>();

    public SalgsSituation(String navn) {
        this.navn = navn;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    /* ------------------------------------------------------------------------------------------------------------*/
    // Create pris
    public Pris createPris(Produkt produkt, double pris, int klipPris, double pantPris) {
        for (Pris p : this.priser) {
            if (p.getProdukt().equals(produkt)) {
                throw new IllegalArgumentException("Produktet har allerede en pris i denne salgssituation.");
            }
        }
        Pris newPris = new Pris(produkt, pris, klipPris, pantPris);
        this.priser.add(newPris);
        return newPris;
    }

    public void removePris(Pris pris) {
        priser.remove(pris);
    }

    public ArrayList<Pris> getPriser() {
        return  new ArrayList<>(this.priser);
    }

    @Override
    public String toString() {
        return navn;
    }
}

