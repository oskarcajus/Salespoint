package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UdlejningsOrder extends Order {

    private LocalDate forventetReturDato;


    public UdlejningsOrder(int orderNr, LocalDate oprettelsesDato,
                            LocalDate forventetReturDato) {

        super(orderNr, oprettelsesDato);
        this.forventetReturDato = forventetReturDato;
    }

    public LocalDate getForventetReturDato() {
        return forventetReturDato;
    }

    public void setForventetReturDato(LocalDate forventetReturDato) {
        this.forventetReturDato = forventetReturDato;
    }
    //@Override
   /* public double orderPris() {
        double orderPris = 0.0;
        for (int i = 0; i < getOrderLines().size(); i++) {
            OrderLine orderLine = getOrderLines().get(i);
            ReturnStatus returStatus = this.returStatusList.get(i);

            if (orderStatus.equals(OrderStatus.OPRETTET)) {
                orderPris += orderLine.getPris().getPris() + orderLine.getPris().getPantPris();

            } else if (orderStatus.equals(OrderStatus.PENDING)) {
                if (returStatus.equals("NORETURN")) {
                    // adding 0 to something is the same as not adding anything
                } else if (returStatus.equals("FULL")) {
                    orderPris -= orderLine.getPris().getPris() + orderLine.getPris().getPantPris();
                } else if (returStatus.equals("TOM")) {
                    orderPris -= orderLine.getPris().getPantPris();
                }
            }

        }
        return orderPris;
    }
    */

    @Override
    public String toString() {
        return "UdlejningsOrder{" +
                "forventetReturDato=" + forventetReturDato +
                '}';
    }
}
