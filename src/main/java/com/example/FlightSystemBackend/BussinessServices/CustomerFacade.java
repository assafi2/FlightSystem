package com.example.FlightSystemBackend.BussinessServices;

import com.example.FlightSystemBackend.DAO.*;
import com.example.FlightSystemBackend.PersistantDomainObjects.Customer;
import com.example.FlightSystemBackend.PersistantDomainObjects.Ticket;

import java.util.List;
import java.util.Random;

public class CustomerFacade extends AnonymousFacade{

    TicketDAO tdao  ;


    public void instantiateTicketDAO() {

       tdao = new TicketDAOJDBCImpl() ;
       daos.add(tdao) ;
    }


    CustomerFacade(LoginToken loginToken) {
        this.loginToken = loginToken ;
        if (cudao == null) instantiateCustomerDAO();
    }
    public boolean updateCustomer(LoginToken loginToken, Customer customer) throws InvalidTokenException {
        if (this.loginToken.equals(loginToken) == false) throw new InvalidTokenException() ;
        try {
            cudao.update(customer) ;
        }
        catch (DAOJDBCImpl.InvalidNullValuesException e){
            }
        return false ;
   }
   public boolean addTicket(LoginToken loginToken, Ticket ticket) {
       if (this.loginToken.equals(loginToken) == false) throw new InvalidTokenException() ;
       if (tdao == null) instantiateTicketDAO();
       try {
           return tdao.add(ticket) ;
       }
       catch (DAOJDBCImpl.InvalidNullValuesException e){
       }
       return false ;
   }
   public boolean removeTicket(LoginToken loginToken, Ticket ticket) {
       if (this.loginToken.equals(loginToken) == false) throw new InvalidTokenException() ;
       if (tdao == null) instantiateTicketDAO();
       return tdao.remove(ticket.id) ;
    }

    // return null with an invalid token username
   public List<Ticket> getMyTickets (LoginToken loginToken) {
       if (this.loginToken.equals(loginToken) == false) throw new InvalidTokenException() ;
       if (tdao == null) instantiateTicketDAO();
       Customer cus = cudao.getCustomerByUsername(loginToken.name) ;
       if (cus != null)
           return tdao.getTicketsByCustomer(cus.id) ;
       return null ;
   }

    public static void main(String[] args) {

        Random rnd = new Random() ;
        LoginToken lt = new LoginToken(rnd.nextLong(),"assafi1", LoginToken.Role.CUSTOMER) ;
        CustomerFacade cf = new CustomerFacade(lt) ;
        System.out.println(cf.getMyTickets(lt));
        Customer c1 = (new CustomerDAOJDBCImpl()).get(1) ;
        c1.last_name = "cohen petel" ;
        cf.updateCustomer(lt,c1) ;
        Ticket t1 = new Ticket(100,3,4) ;
   //     cf.addTicket(lt,t1) ;
        cf.removeTicket(lt,new TicketDAOJDBCImpl().get(2)) ;
    }
}
