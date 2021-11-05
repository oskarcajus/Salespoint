package model;

public class Pris {
    private double pris;
    private double pantPris;
    private int klipPris;
    private Produkt produkt;

    public Pris(Produkt produkt, double pris, int klipPris, double pantPris) {
        this.produkt = produkt;
        this.pris = pris;
        this.pantPris = pantPris;
        this.klipPris = klipPris;
    }

    public double getPris() {
        return pris;
    }

    public double getPantPris() {
        return pantPris;
    }

    public void setPris(double pris) {
        this.pris = pris;
    }

    public void setPantPris(double pantPris) {
        this.pantPris = pantPris;
    }

    public int getKlipPris() {
        return klipPris;
    }

    public void setKlipPris(int klipPris) {
        this.klipPris = klipPris;
    }

    public Produkt getProdukt() {
        return produkt;
    }

    @Override
    public String toString() {
        if (getPantPris() <0 && getKlipPris() <0){
            return getProdukt().toString() + ": "+ "\t" + getPris() + " kr.";
        } else if (getPantPris() >0) {
            return getProdukt().toString() + ": "+ "\t" + getPris() + "kr/ " + getPantPris() + "kr.";
        } else if (getKlipPris() >0) {
            return getProdukt().toString() + ": "+ "\t" + getPris() + "kr/ " + getKlipPris() + " klip";
        } else{
            return getProdukt().toString() + ": "+ "\t" + getPris() + " kr.";
        }
    }
}