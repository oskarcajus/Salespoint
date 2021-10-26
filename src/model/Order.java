package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Order {
    private int orderNr;
    private LocalDate oprettelsesDato;
    private LocalDate betalingsDato;
    private OrderStatus orderStatus; // oprettet, pending, afsluttet(betalt)
    private BetalingsType betalingsType; // Dankort, klippeKort....

    // nullable doubleRettet kunde(0..1)
    private Kunde kunde;

    // komposition : orderline(0..*)
    private ArrayList<OrderLine> orderLines = new ArrayList<>();

    public Order(int orderNr, LocalDate oprettelsesDato){
        this.orderNr = orderNr;
        this.oprettelsesDato = oprettelsesDato;
        this.betalingsDato = null;
        this.orderStatus = OrderStatus.OPRETTET;
    }

    // opret orderLine
    public OrderLine createOrderLine(int antalProdukt, Pris pris){
    OrderLine orderLine = new OrderLine(antalProdukt, pris);
    orderLines.add(orderLine);
    return orderLine;
    }

    // remove orderLine
    public void removeOrderLine(OrderLine orderLine){
        if(this.orderLines.contains(orderLine))
            orderLines.remove(orderLine);
    }

    public double orderPris() {
        double orderPris = 0.0;
        for (OrderLine orderLine : orderLines) {
            orderPris += orderLine.getOrderLineBeløb();
        }
        return orderPris;
    }

    // get orderLine arrayList
    public ArrayList<OrderLine> getOrderLines() {
        return new ArrayList<>(orderLines);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setBetalingsType(BetalingsType betalingsType) {
        this.betalingsType = betalingsType;
    }

    public int getOrderNr() {
        return orderNr;
    }

    public void setOrderNr(int orderNr) {
        this.orderNr = orderNr;
    }

    public LocalDate getOprettelsesDato() {
        return oprettelsesDato;
    }

    public void setOprettelsesDato(LocalDate oprettelsesDato) {
        this.oprettelsesDato = oprettelsesDato;
    }

    public BetalingsType getBetalingsType() {
        return betalingsType;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDate getBetalingsDato() {
        return betalingsDato;
    }

    public void setBetalingsDato(LocalDate betalingsDato) {
        this.betalingsDato = betalingsDato;
    }

    // nullable kunde(0..1)
    public Kunde getKunde() {
        return kunde;
    }

    public void setKunde(Kunde targetKunde){
    if(this.kunde != targetKunde){
        Kunde oldKunde = this.kunde;
        if(oldKunde != null){
            oldKunde.removeOrder(this);
        }
        this.kunde = targetKunde;
        if(kunde != null){
            kunde.addOrder(this);
        }
        }
    }
    public void removeKunde(){
        if(this.kunde != null){
            Kunde removeKunde = this.kunde;
            this.kunde = null;
            removeKunde.removeOrder(this);
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNr=" + orderNr +
                ", oprettelsesDato=" + oprettelsesDato +
                ", betalingsDato=" + betalingsDato +
                ", orderStatus=" + orderStatus +
                ", betalingsType=" + betalingsType +
                ", orderLines=" + orderLines +
                '}';
    }
}
