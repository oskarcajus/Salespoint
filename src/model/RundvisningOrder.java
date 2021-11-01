package model;

import java.time.LocalDate;

public class RundvisningOrder extends Order{

    private LocalDate expectingBetalingsDato;

    public RundvisningOrder(int orderNr, LocalDate oprettelsesDato, LocalDate expectingBetalingsDato) {
        super(orderNr, oprettelsesDato);
        this.expectingBetalingsDato = expectingBetalingsDato;
    }

    //@Override
    public double orderPris() {
        double orderPris = 0.0;
        for (OrderLine orderLine : getOrderLines()) {
            orderPris += orderLine.getOrderLinePrisBeløb();
        }
        return orderPris;
    }

    public void setExpectingBetalingsDato(LocalDate expectingBetalingsDato) {
        this.expectingBetalingsDato = expectingBetalingsDato;
    }

    public LocalDate getExpectingBetalingsDato() {
        return expectingBetalingsDato;
    }

    @Override
    public String toString() {
        return "RundvisningOrder{" +
                "rundvisningsDato=" + expectingBetalingsDato +
                '}';
    }
}
