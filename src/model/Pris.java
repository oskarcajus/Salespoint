package model;

public class Pris {
    // Builder pattern - to make pris contructor flexible
    public static class PrisBuilder {

        private Produkt produkt;

        // 3 different pris type
        private int normalPris;
        private int pantPris;
        private int klipPris;

        // necessary parameter - (1) produkt
        public PrisBuilder(Produkt produkt) {
            this.produkt = produkt;
        }

        public PrisBuilder pris(int pris) {
            this.normalPris = pris;
            return this;
        }

        public PrisBuilder pantPris(int pant) {
            this.pantPris = pant;
            return this;
        }

        public PrisBuilder klipPris(int klip) {
            this.klipPris = klip;
            return this;
        }

        public Pris build() {
            Pris price = new Pris(produkt);
            price.setPris(this.normalPris);
            price.setPantPris(this.pantPris);
            price.setKlipPris(this.klipPris);

            return price;
        }
    }

    // BuilderPattern slut
//-------------------------------------------------------------------------------------------
    private int pris;
    private int pantPris;
    private int klipPris;

    private Produkt produkt;

    // Builder pattern - private constructor
    private Pris(Produkt produkt) {
        this.produkt = produkt;
    }

    // default pris
    public void setPris(int pris) {
        if (this.pris == pris) {
            this.pris = pris;
        } else
            this.pris = pris;
    }

    public void setPantPris(int pantPris) {
        if (!this.produkt.getProduktType().getNavn().equals("Pant")) {
            throw new RuntimeException(produkt.getProduktgruppe() + " cannot have pantPris");
        } else {
            this.pantPris = pantPris;
        }

    }

    public void setKlipPris(int klipPris) {
        if (!produkt.getProduktType().getNavn().equals("Klippe  kort")) {
            throw new RuntimeException(produkt.getProduktgruppe() + " cannot have klipPris");
        } else {
            this.klipPris = klipPris;

        }
    }

    public Produkt getProdukt() {
        return produkt;
    }
}
