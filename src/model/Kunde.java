package model;

import java.util.ArrayList;

public class Kunde implements Comparable<Kunde> {
    private String navn;
    private String mobil;
    private boolean isFirma;
    private RabatStrategy rabatStrategy; // nullable rabat (0..1)

    public Kunde(String navn, String mobil) {
        this.navn = navn;
        this.mobil = mobil;
    }

    // Double Ass -- Kunde(0..*)
    private final ArrayList<Order> orders = new ArrayList<>();

    public String getMobil() {
        return mobil;
    }

    public void setMobil(String mobil) {
        this.mobil = mobil;
    }

    public RabatStrategy getRabatStrategy() {
        return rabatStrategy;
    }

    public void setRabatStrategy(RabatStrategy rabatStrategy) {
        if (this.rabatStrategy != rabatStrategy)
            this.rabatStrategy = rabatStrategy;
    }

    public double totalPris() {
        double sum = 0;
        for (Order order : orders) {
            sum += order.orderPris();
        }
        return sum;
    }

    public double prisWithDC(){
        double total = this.totalPris();
        if(this.rabatStrategy != null) {
            total -= this.rabatStrategy.getRabat(total);
        }
        return total;
      }

    public ArrayList<Order> getOrders() {
            return new ArrayList<>(orders);
        }

        public void addOrder(Order order) {
            if (!orders.contains(order)) {
                orders.add(order);
                order.setKunde(this); // doublerettet
            }
        }

        public void removeOrder(Order order) {
            if (orders.contains(order)) {
                orders.remove(order);
                order.removeKunde(); // doublerettet
            }
        }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public void setFirma(boolean firma) {
        isFirma = firma;
    }

        @Override
        public int compareTo(Kunde o) {
            return this.getNavn().compareTo(o.getNavn());
        }

        @Override
        public String toString() {
            return String.format("%s, %s", this.navn, this.mobil);
        }
}
