package model;

public class Pris {
    // Builder pattern - to make pris contructor flexible
//    public static class PrisBuilder {
//
//        private Produkt produkt;
//
//        // 3 different pris type
//        private int normalPris;
//        private int pantPris;
//        private int klipPris;

//        // necessary parameter - (1) produkt
//        public PrisBuilder(Produkt produkt) {
//            this.produkt = produkt;
//        }
//
//        public PrisBuilder pris(int pris) {
//            this.normalPris = pris;
//            return this;
//        }
//
//        public PrisBuilder pantPris(int pant) {
//            this.pantPris = pant;
//            return this;
//        }
//
//        public PrisBuilder klipPris(int klip) {
//            this.klipPris = klip;
//            return this;
//        }
//
//        public Pris build() {
//            Pris price = new Pris(produkt);
//            price.setPris(this.normalPris);
//            price.setPantPris(this.pantPris);
//            price.setKlipPris(this.klipPris);
//
//            return price;
//        }
//    }

    // BuilderPattern slut
//-------------------------------------------------------------------------------------------
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

    public double getPantPris() {
        return pantPris;
    }

    public double getPris() {
        return pris;
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
}
