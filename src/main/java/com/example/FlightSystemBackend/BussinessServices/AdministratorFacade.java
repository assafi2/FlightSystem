package com.example.FlightSystemBackend.BussinessServices;

import com.example.FlightSystemBackend.DAO.* ;
import com.example.FlightSystemBackend.DAO.DAO;
import com.example.FlightSystemBackend.PersistantDomainObjects.Administrator;
import com.example.FlightSystemBackend.PersistantDomainObjects.Airline_Company;
import com.example.FlightSystemBackend.PersistantDomainObjects.Customer;
import com.example.FlightSystemBackend.DAO.AdministratorDAOJDBCImpl ;

import java.util.List;

public class AdministratorFacade extends AnonymousFacade {

    DAO<Administrator> addao = new AdministratorDAOJDBCImpl() ;


  public AdministratorFacade(LoginToken loginToken) {
      this.loginToken = loginToken ;
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
      catch (DAOJDBCImpl.InvalidNullValuesException e) {
          displayNullError();
      }
      return false ;
  }
  public boolean addCustomer (LoginToken loginToken, Customer customer) {
      if (!this.loginToken.equals(loginToken)) throw new InvalidTokenException() ;
      try {
          return cudao.add(customer) ;
      }
      catch (DAOJDBCImpl.InvalidNullValuesException e) {
          displayNullError();
      }
      return false ;
  }
  public boolean addAdministrator (LoginToken loginToken, Administrator admin) {
      if (!this.loginToken.equals(loginToken)) throw new InvalidTokenException() ;
      try {
          return addao.add(admin) ;
      }
      catch (DAOJDBCImpl.InvalidNullValuesException e) {
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
