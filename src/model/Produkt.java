package model;

public class Produkt {
    private String name;
    private ProduktType produktType;
    private Produktgruppe produktgruppe;

    public Produkt(String name, ProduktType produkType, Produktgruppe produktgruppe) {
        this.name = name;
        this.produktType = produkType;
        setProduktgruppe(produktgruppe);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProduktType getProdukType() {
        return produktType;
    }

    public void setProdukType(ProduktType produktType) {
        this.produktType = produktType;
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
}
