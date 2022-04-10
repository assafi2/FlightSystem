package com.example.FlightSystemBackend.PersistantDomainObjects;

public class Ticket implements POCO {

    public long id ;
    public long flight_id;
    public long customer_id;

    public Ticket() {
    }

    public Ticket(long id, long flightId, long customerId) {
        this.id = id;
        this.flight_id = flightId;
        this.customer_id = customerId;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", flightId=" + flight_id +
                ", customerId=" + customer_id +
                '}';

    }
    public String toRecordString() {
        return "%s,%s".formatted(flight_id, customer_id) ;
    }
    public String[] toStringArray() {
        String[] arr =  {"%s".formatted(flight_id),"%s".formatted(customer_id),"%s".formatted(id)} ;
        return arr ;
    }
}
