package com.example.FlightSystemBackend.DAO;

import com.example.FlightSystemBackend.PersistantDomainObjects.Ticket;
import com.example.FlightSystemBackend.PersistantDomainObjects.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketDAOJDBCImpl extends DAOJDBCImpl<Ticket> implements TicketDAO{


    public TicketDAOJDBCImpl() {
        super((new Ticket()).getClass());
    }

    void assertNotNullValues(Ticket t) throws InvalidNullValuesException {} ;

    // null when query has no result or involve SQLExceptions
    public Ticket get(long id) {

        try (ResultSet rs = getSet(id)) {
            if (rs.next()) {
                return new Ticket(rs.getLong(1), rs.getLong(2),
                        rs.getLong(3)) ;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null ;
    }
    public List<Ticket> getAll() {
        List<Ticket> list = new ArrayList<Ticket>() ;
        try (ResultSet rs = getSet(-1)) {
            while (rs.next()) {
                list.add(
                        new Ticket(rs.getLong(1), rs.getLong(2),
                                rs.getLong(3))) ;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return list ;
    }

    public List<Ticket> getTicketsByCustomer(long customer_id) {
        List<Ticket> list = new ArrayList<Ticket>() ;
        try (ResultSet rs = getSet("select * from get_tickets_by_customer(%d) ; ".
                formatted(customer_id))) {
            while (rs.next()) {
                list.add(
                        new Ticket(rs.getLong(1), rs.getLong(2),
                                rs.getLong(3)));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list ;
    }
}
