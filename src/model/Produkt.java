package model;

public class Produkt {
    private String name;
    private ProduktType produktType;
    private Produktgruppe produktgruppe;

    public Produkt(String name, ProduktType produktType, Produktgruppe produktgruppe) {
        this.name = name;
        setProduktType(produktType);
        setProduktgruppe(produktgruppe);
    }

    public void setProduktgruppe(Produktgruppe produktgruppe) {
        if (this.produktgruppe != produktgruppe) {
            if (this.produktgruppe != null) {
                Produktgruppe oldProduktgruppe = this.produktgruppe;
                oldProduktgruppe.removeProdukt(this); // doublerettet
            }

            this.produktgruppe = produktgruppe;
            if (this.produktgruppe != null) {
                this.produktgruppe.addProdukt(this);  // doublerettet
            }
        }
    }

    public void setProduktType(ProduktType produktType) {
        if (this.produktType != produktType) {
            this.produktType = produktType;
            if (produktType != null) {
                produktType.addProdukt(this);  // doublerettet
            }
        }
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

    public Produktgruppe getProduktgruppe() {
        return produktgruppe;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
