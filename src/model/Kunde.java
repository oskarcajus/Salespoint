package model;

import java.util.ArrayList;

public class Kunde implements Comparable<Kunde> {
    private String navn;
    private String address;
    private String mobil;
    private boolean isFirma;

    public Kunde(String navn, String address, String mobil){
        this.navn = navn;
        this.address = address;
        this.mobil = mobil;
        this.isFirma = false;
    }
        // Double Ass -- Kunde(0..*)
        private final ArrayList<Order> orders = new ArrayList<>();

          public String getMobil() {
            return mobil;
        }

        public void setMobil(String mobil) {
            this.mobil = mobil;
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

        @Override
        public int compareTo(Kunde o) {
            return this.getNavn().compareTo(o.getNavn());
        }

        @Override
        public String toString() {
            return String.format("%s %s", this.navn, this.mobil);
        }


    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
