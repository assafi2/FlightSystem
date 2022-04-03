package BussinessServices;

import DAO.DAO;
import PersistantDomainObjects.Administrator;
import PersistantDomainObjects.Airline_Company;
import PersistantDomainObjects.Customer;
import DAO.AdministratorDAOJDBCImpl ;

import java.util.List;

public class AdministratorFacade extends AnonymousFacade {

    DAO<Administrator> addao = new AdministratorDAOJDBCImpl() ;


  public AdministratorFacade() {
      if (aidao == null) instantiateAirlineCompanyDAO();
      if (udao == null) instantiateUserDAO();
      if (cudao == null) instantiateCustomerDAO();
  }

  public List<Customer> getAllCustomers(LoginToken loginToken) {
      if (!this.loginToken.equals(loginToken)) throw new InvalidTokenException() ;
      return cudao.getAll() ;

  }
  public boolean addAirline (LoginToken loginToken, Airline_Company airline){
      if (!this.loginToken.equals(loginToken)) throw new InvalidTokenException() ;
      try {
          return aidao.add(airline) ;
      }
      catch (DAO.DAOJDBCImpl.InvalidNullValuesException e) {
          displayNullError();
      }
      return false ;
  }
  public boolean addCustomer (LoginToken loginToken, Customer customer) {
      if (!this.loginToken.equals(loginToken)) throw new InvalidTokenException() ;
      try {
          return cudao.add(customer) ;
      }
      catch (DAO.DAOJDBCImpl.InvalidNullValuesException e) {
          displayNullError();
      }
      return false ;
  }
  public boolean addAdministrator (LoginToken loginToken, Administrator admin) {
      if (!this.loginToken.equals(loginToken)) throw new InvalidTokenException() ;
      try {
          return addao.add(admin) ;
      }
      catch (DAO.DAOJDBCImpl.InvalidNullValuesException e) {
          displayNullError();
      }
      return false ;
  }
  public boolean removeAirline (LoginToken loginToken, Airline_Company airline) {
      if (!this.loginToken.equals(loginToken)) throw new InvalidTokenException() ;
      return aidao.remove(airline.id) ;
  }
  public boolean removeCustomer (LoginToken loginToken, Customer customer) {
      if (!this.loginToken.equals(loginToken)) throw new InvalidTokenException() ;
      return cudao.remove(customer.id) ;
  }
  // we allow for an admin to remove another admin
  public boolean removeAdministrator (LoginToken loginToken, Administrator administrator) {
      if (!this.loginToken.equals(loginToken)) throw new InvalidTokenException() ;
      return addao.remove(administrator.id) ;  
  }
}
