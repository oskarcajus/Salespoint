package storage;

import model.Order;
import model.ProduktType;
import model.Produktgruppe;
import model.SalgsSituation;

import java.util.ArrayList;

public class Storage {

    private static Storage storage;

    //Lav singleton
    public static Storage getStorage() {
        if (storage == null) {
            storage = new Storage();
        }
        return storage;
    }

    //Produktgruppe
    private final ArrayList<Produktgruppe> produktgrupper = new ArrayList<>();

    public void addProduktgruppe(Produktgruppe pg) {
        produktgrupper.add(pg);
    }
    public void removeProduktgruppe(Produktgruppe pg) {
        produktgrupper.remove(pg);
    }
    public ArrayList<Produktgruppe> getProduktgrupper() {
        return new ArrayList<>(this.produktgrupper);
    }

    //ProduktType
    private final ArrayList<ProduktType> produktTyper = new ArrayList<>();

    public void addProduktType(ProduktType pt) {
        produktTyper.add(pt);
    }
    public void removeProduktType(ProduktType pt) {
        produktTyper.remove(pt);
    }
    public ArrayList<ProduktType> getProduktTyper() {
        return new ArrayList<>(this.produktTyper);
    }

    //SalgsSituation
    private final ArrayList<SalgsSituation> salgsSituationer = new ArrayList<>();

    public void addSalgsSituation(SalgsSituation ss) {
        salgsSituationer.add(ss);
    }
    public void removeSalgsSituation(SalgsSituation ss) {
        salgsSituationer.remove(ss);
    }
    public ArrayList<SalgsSituation> getSalgsSituationer() {
        return new ArrayList<>(this.salgsSituationer);
    }

    //Order
    private final ArrayList<Order> orders = new ArrayList<>();
    public void addOrder(Order order) {
        orders.add(order);
    }
    public void removeOrder(Order order) {
        orders.remove(order);
    }
    public ArrayList<Order> getOrders() {
        return new ArrayList<>(this.orders);
    }


}
