package BussinessServices;

import DAO.DAO;
import DAO.DAOJDBCImpl;
import DAO.TicketDAOJDBCImpl;
import PersistantDomainObjects.Customer;
import PersistantDomainObjects.Ticket;
import DAO.TicketDAO ;

import java.util.List;

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
            cudao.add(customer) ;
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
   public boolean removeTicket(Ticket ticket) {
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
}
