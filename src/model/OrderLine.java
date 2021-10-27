package model;

public class OrderLine {

    private int antalProdukt;
    private Pris pris;

    public OrderLine(int antalProdukt, Pris pris){
        this.antalProdukt = antalProdukt;
        this.pris = pris;
    }

     public double getOrderLineBeløb() {
        double price = pris.getPris() * antalProdukt;
            return price;
        }

    public Pris getPris() {
        return pris;
    }

    public int getAntalProdukt() {
        return antalProdukt;
    }

    public void setAntalProdukt(int antalProdukt) {
        this.antalProdukt = antalProdukt;
    }

    @Override
    public String toString() {
        return "OrderLine{" +
                "antalProdukt=" + antalProdukt +
                '}';
    }
}
