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

        //Rabatter-regler som Aarhus Bryghus benytter sig:
        super.setRabatStrategy(new ProcentRabat(6));
        super.setRabatStrategy(new AmountRabat(500, 7000 ));
        }

    public LocalDate getForventetReturDato() {
        return forventetReturDato;
    }

    public void setForventetReturDato(LocalDate forventetReturDato) {
        this.forventetReturDato = forventetReturDato;
    }

    //Kan bruges hvis man ønsker at implementere Udlejningsordre som pending, når udlejningen ikke er afleveret
    //@Override
//   public double orderPris() {
//
//        double orderPris = 0.0;
//
//        for (int i = 0; i < getOrderLines().size(); i++) {
//            OrderLine orderLine = getOrderLines().get(i);
//            ReturnStatus returStatus = this.returStatusList.get(i);
//
//            if (orderStatus.equals(OrderStatus.OPRETTET)) {
//                orderPris += orderLine.getPris().getPris() + orderLine.getPris().getPantPris(); // pay everything
//
//            } else if (orderStatus.equals(OrderStatus.PENDING)) {
//               if (returStatus.equals("FULL")) {
//                    orderPris -= orderLine.getPris().getPris() + orderLine.getPris().getPantPris(); // the customer gets all money back 전부를 고객에게 돌려줘야함
//                } else if (returStatus.equals("TOM")) {
//                    orderPris -= orderLine.getPris().getPantPris(); // the customer get only pant price back
//                }
//            }
//        }
//        return orderPris;
//    }


    @Override
    public String toString() {
        return "UdlejningsOrder{" +
                "forventetReturDato=" + forventetReturDato +
                '}';
    }
}
