package model;

public class OrderLine {

    private int antalProdukt;
    private Pris pris;
    private double orderLinePrisBeløb;
    private double orderLinePantBeløb;
    private int getOrderLineKlipBeløb;

    public OrderLine(int antalProdukt, Pris pris){
        this.antalProdukt = antalProdukt;
        this.pris = pris;
        this.orderLinePrisBeløb = pris.getPris()  * antalProdukt;
        this.orderLinePantBeløb = pris.getPantPris() * antalProdukt;
        this.getOrderLineKlipBeløb = pris.getKlipPris() * antalProdukt;
    }

    public double getOrderLinePrisBeløb() {
        return orderLinePrisBeløb;
    }

    public void setOrderLinePrisBeløb(double orderLinePrisBeløb) {
        this.orderLinePrisBeløb = orderLinePrisBeløb;
    }

    public void getOrderLinePrisBeløb(double pris) {
        this.orderLinePrisBeløb = pris;
    }

    public double getOrderLinePantBeløb() {
        return orderLinePantBeløb;
    }

    public void setOrderLinePantBeløb(double orderLinePantBeløb) {
        this.orderLinePantBeløb = orderLinePantBeløb;
    }

    public int getOrderLineKlipBeløb() {
        return getOrderLineKlipBeløb;
    }

    public void setOrderLineKlipBeløb(int getOrderLineKlipBeløb) {
        this.getOrderLineKlipBeløb = getOrderLineKlipBeløb;
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
        return getPris() + " : " + "Antal: " + getAntalProdukt();
    }
}
