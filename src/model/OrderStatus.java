package model;

public enum OrderStatus {

    OPRETTET, PENDING, AFSLUTTET

    // Oprettet = en UdlejningsOrder oprettet (Default) --> betalt
    // Pending = Udlejet order is waiting for final calculation based on return situation
    // Afsluttet = the order is complete and payed.
}
