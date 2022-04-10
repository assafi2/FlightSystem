package com.example.FlightSystemBackend.DAO;

import com.example.FlightSystemBackend.PersistantDomainObjects.Ticket;

import java.util.List;

public interface TicketDAO extends DAO<Ticket> {

    public List<Ticket> getTicketsByCustomer(long customer_id) ;
}
