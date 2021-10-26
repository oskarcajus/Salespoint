package model;

import java.time.LocalDate;

public class RundvisningOrder extends Order{

    private LocalDate rundvisningsDato;

    public RundvisningOrder(int orderNr, LocalDate oprettelsesDato, LocalDate expectingBetalingsDato) {
        super(orderNr, oprettelsesDato);
        this.rundvisningsDato = expectingBetalingsDato;
    }

    public void setExpectingBetalingsDato(LocalDate expectingBetalingsDato) {
        this.rundvisningsDato = expectingBetalingsDato;
    }

    public LocalDate getExpectingBetalingsDato() {
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
