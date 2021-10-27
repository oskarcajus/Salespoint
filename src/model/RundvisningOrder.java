package model;

import java.time.LocalDate;

public class RundvisningOrder extends Order{

    private LocalDate rundvisningsDato;

    public RundvisningOrder(int orderNr, LocalDate oprettelsesDato, LocalDate rundvisningsDato) {
        super(orderNr, oprettelsesDato);
        this.rundvisningsDato = rundvisningsDato;
    }

    public void setRundvisningsDato(LocalDate rundvisningsDato) {
        this.rundvisningsDato = rundvisningsDato;
    }

    public LocalDate getRundvisningsDato() {
        return rundvisningsDato;
    }

    //@Override
    public double orderPris() {
        double orderPris = 0.0;
        for (OrderLine orderLine : getOrderLines()) {
            orderPris += orderLine.getOrderLineBeløb();
        }
        return orderPris;
    }

    @Override
    public String toString() {
        return "RundvisningOrder{" +
                "rundvisningsDato=" + rundvisningsDato +
                '}';
    }
}
