package DAO;

import PersistantDomainObjects.Ticket;

import java.util.List;

public interface TicketDAO extends DAO<Ticket> {

    public List<Ticket> getTicketsByCustomer(long customer_id) ;
}
