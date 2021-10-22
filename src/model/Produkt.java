package model;

public class Produkt implements Comparable<Produkt> {
    private String name;
    private ProduktType produktType;
    private Produktgruppe produktgruppe;

    public Produkt(String name, ProduktType produktType, Produktgruppe produktgruppe) {
        this.name = name;
        setProduktType(produktType);
        setProduktgruppe(produktgruppe);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProduktType getProduktType() {
        return produktType;
    }

    public void setProduktType(ProduktType produktType) {
        if (this.produktType != produktType) {
            this.produktType = produktType;
            if (produktType != null) {
                produktType.addProdukt(this);
            }
        }
    }

    public Produktgruppe getProduktgruppe() {
        return produktgruppe;
    }

    public void setProduktgruppe(Produktgruppe produktgruppe) {
        if (this.produktgruppe != produktgruppe) { // same Group - check
            if (this.produktgruppe != null) { // Gruppe - null check
                Produktgruppe oldProduktgruppe = this.produktgruppe;
                oldProduktgruppe.removeProdukt(this); // you have to remove old group first
            }

            this.produktgruppe = produktgruppe;
            if (this.produktgruppe != null) { // Gruppe null check
                this.produktgruppe.addProdukt(this); // Gruppe add this produkt
            }
        }
    }

    @Override
    public String toString() {
        return this.name + "PG: " + this.produktgruppe + " PT: " + this.produktType;
    }


    @Override
    public int compareTo(Produkt o) {
        return this.getName().compareTo(o.getName());
    }
}
