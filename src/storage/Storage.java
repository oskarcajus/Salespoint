package storage;

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
    private static final ArrayList<Produktgruppe> produktgrupper = new ArrayList<>();

    public static void addProduktgruppe(Produktgruppe pg) {
        produktgrupper.add(pg);
    }
    public static void removeProduktgruppe(Produktgruppe pg) {
        produktgrupper.remove(pg);
    }
    public static ArrayList<Produktgruppe> getProduktgrupper() {
        return new ArrayList<Produktgruppe>(Storage.produktgrupper);
    }

    //ProduktType
    private static final ArrayList<ProduktType> produktTyper = new ArrayList<>();

    public static void addProduktType(ProduktType pt) {
        produktTyper.add(pt);
    }
    public static void removeProduktType(ProduktType pt) {
        produktTyper.remove(pt);
    }
    public static ArrayList<ProduktType> getProduktTyper() {
        return new ArrayList<ProduktType>(Storage.produktTyper);
    }

    //SalgsSituation
    private static final ArrayList<SalgsSituation> salgsSituationer = new ArrayList<>();

    public static void addSalgsSituation(SalgsSituation ss) {
        salgsSituationer.add(ss);
    }
    public static void removeSalgsSituation(SalgsSituation ss) {
        salgsSituationer.remove(ss);
    }
    public static ArrayList<SalgsSituation> getSalgsSituationer() {
        return new ArrayList<SalgsSituation>(Storage.salgsSituationer);
    }
}
