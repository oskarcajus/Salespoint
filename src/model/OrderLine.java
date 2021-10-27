package model;

public class OrderLine {

    private int antalProdukt;
    private Pris pris;
    private double orderLineBeløb;

    public OrderLine(int antalProdukt, Pris pris){
        this.antalProdukt = antalProdukt;
        this.pris = pris;
        this.orderLineBeløb = pris.getPris() * antalProdukt;

    }

    // beregn pris for orderLine
    // getPris() method 추가해야함
    public double getOrderLineBeløb() {
        return orderLineBeløb;
    }

    public void setOrderLineBeløb(double orderLineBeløb) {
        this.orderLineBeløb = orderLineBeløb;
    }

    // Skal måske bruges til opdatering i GUI
//    public double calcOrderLineBeløb() {
//       return this.pris.getPris() * this.antalProdukt;
//    }

    public Pris getPris() {
        return pris;
    }

    public void setPris(Pris pris) {this.pris = pris;}

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
