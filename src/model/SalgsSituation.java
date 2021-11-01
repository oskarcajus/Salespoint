package model;

import java.util.ArrayList;

public class SalgsSituation {
    private String navn;
    private ArrayList<Pris> priser = new ArrayList<Pris>();

    public SalgsSituation(String navn) {
        this.navn = navn;
    }

    public Pris createPris(Produkt produkt, double pris, int klipPris, double pantPris) {
        Pris newPris = new Pris(produkt, pris, klipPris, pantPris);
        this.priser.add(newPris);
        return newPris;
    }

    public void removePris(Pris pris) {
        if(priser.contains(pris))
            priser.remove(pris);
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public ArrayList<Pris> getPriser() {
        return  new ArrayList<>(this.priser);
    }

    @Override
    public String toString() {
        return navn;
    }
}

