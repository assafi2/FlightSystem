package com.example.FlightSystemBackend.PersistantDomainObjects;

import java.time.LocalDateTime;

public class Flight implements POCO {

    public long id ;
    public long airline_company_id; // FK
    public int origin_country_id; // FK
    public int destination_country_id; // FK
    public LocalDateTime departure_time;
    public LocalDateTime landing_time;
    public int remaining_tickets;

    public Flight() {
    }

    public Flight(long id, long airlineCompanyId, int originCountryId, int destinationCountryId,
                  LocalDateTime departureTime, LocalDateTime landingTime, int remainingTickets) {
        this.id = id;
        this.airline_company_id = airlineCompanyId;
        this.origin_country_id = originCountryId;
        this.destination_country_id = destinationCountryId;
        this.departure_time = departureTime;
        this.landing_time = landingTime;
        this.remaining_tickets = remainingTickets;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", airlineCompanyId=" + airline_company_id +
                ", originCountryId=" + origin_country_id +
                ", destinationCountryId=" + destination_country_id +
                ", departureTime=" + departure_time +
                ", landingTime=" + landing_time +
                ", remainingTickets=" + remaining_tickets +
                '}';
    }
    public String toRecordString() {
        return "%s,%s,%s,'%s','%s',%s".formatted(airline_company_id, origin_country_id,
                destination_country_id, departure_time, landing_time, remaining_tickets) ;
    }
    public String[] toStringArray() {
        String[] arr =  {"%s".formatted(airline_company_id),"%s".formatted(origin_country_id),
                "%s".formatted(destination_country_id),"'%s'".formatted(departure_time),
                "'%s'".formatted(landing_time),"%s".formatted(remaining_tickets),"%s".formatted(id)} ;
        return arr ;
    }

}
