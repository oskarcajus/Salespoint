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
    public Pris createPris(Produkt produkt, int pris, int klip, int pant) {
        for (Pris price : priser) {
            if (price.getProdukt().equals(produkt)) {
                throw new IllegalArgumentException("Produkt har allerede en pris i denne SalgSituation");
            }
        }
        if (produkt == null) {
            throw new IllegalArgumentException("Du har ikke valgt et produkt");
        }
        if (pris < 0) {
            throw new IllegalArgumentException("Prisen skal være positiv");
        }
        Pris p;
        if (produkt.getProduktType().getNavn().equals("Normal")) {
            p = new Pris.PrisBuilder(produkt).pris(pris).build();
        }

        else if (produkt.getProduktType().getNavn().equals("Klippekort")) {
            p = new Pris.PrisBuilder(produkt).pris(klip).build();
        }

        else if (produkt.getProduktType().getNavn().equals("Pant")) {
            p = new Pris.PrisBuilder(produkt).pris(pant).build();

        } else {
            return null;
        }
        priser.add(p);
        this.priser.sort((Pris p1, Pris p2) -> p1.getProdukt().getProduktgruppe().getNavn().compareTo(p2.getProdukt().getProduktgruppe().getNavn()));
        return p;
    }

    public ArrayList<Pris> getPriser() {
        return  new ArrayList<>(this.priser);
    }


    @Override
    public String toString() {
        return navn;
    }
}

