package com.example.FlightSystemBackend.BussinessServices;

import com.example.FlightSystemBackend.DAO.AirlineCompanyDAO;
import com.example.FlightSystemBackend.DAO.* ;
import com.example.FlightSystemBackend.DAO.DAO;
import com.example.FlightSystemBackend.DAO.FlightDAO;
import com.example.FlightSystemBackend.PersistantDomainObjects.*;
import com.example.FlightSystemBackend.DAO.FlightDAOJDBCImpl ;
import com.example.FlightSystemBackend.DAO.AirlineCompanyDAOJDBCImpl ;
import com.example.FlightSystemBackend.DAO.CountryDAOJDBCImpl ;
import com.example.FlightSystemBackend.DAO.UserDAOJDBCImpl ;
import com.example.FlightSystemBackend.DAO.UserDAO ;

import java.io.Closeable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

// we used proxy pattern (constructor doesn't have arguments)
// to prevent multiple openings of db connections unnecessarily

public abstract class FacadeBase  {

    FlightDAO fdao ;
    AirlineCompanyDAO aidao;
    DAO<Country> codao;
    UserDAO udao ;
    List<Closeable> daos = new LinkedList<Closeable> () ; // DAOs to close list when closing facade
    LoginToken loginToken ;

    public LoginToken getLoginToken() {
        return loginToken;
    }


    void instantiateFlightDAO() {
         fdao = new FlightDAOJDBCImpl() ;
         daos.add(fdao) ;

    }
    void instantiateAirlineCompanyDAO() {
        aidao = new AirlineCompanyDAOJDBCImpl() ;
        daos.add(aidao) ;
    }
    void instantiateCountryDAO() {
        codao = new CountryDAOJDBCImpl() ;
        daos.add(codao) ;
    }

    void instantiateUserDAO() {
        udao = new UserDAOJDBCImpl() ;
        daos.add(udao) ;
    }

    protected void displayNullError() {
        System.err.println("invalid null values update error");
    }

    public List<Flight> getAllFlights() {
        if (fdao == null) instantiateFlightDAO();
        return fdao.getAll() ;
    }

    public Flight getFlightById(long id) {
        if (fdao == null) instantiateFlightDAO();
        return fdao.get(id) ;
    }
    public List<Flight> getFlightsByParameters(int origin_country_id, int destination_country_id, Date date) {
        if (fdao == null) instantiateFlightDAO();
        return fdao.getFlightsByParameters(origin_country_id, destination_country_id, date) ;
    }
    public List<Airline_Company> getAllAirlines() {
        if (aidao == null) instantiateFlightDAO();
        return aidao.getAll() ;
    }
    public Airline_Company getAirlineById(long id) {
        if (aidao == null) instantiateAirlineCompanyDAO();
        return aidao.get(id) ;
    }
//  public getAirlineByParameters( ... )

    public List<Country> getAllCountries(){
        if (codao == null) instantiateCountryDAO();
        return codao.getAll() ;
    }
    public Country getCountryById(int id){
        if (codao == null) instantiateCountryDAO();
        return codao.get(id) ;
    }

    boolean createNewUser (User user) {
        if (udao == null) instantiateUserDAO();
        // role check
        if (udao.retRoleName(user.user_role) != "customer")
            return false ;
        // TODO fields values checkings
        try {
            return udao.add(user) ;
        }
        catch (DAOJDBCImpl.InvalidNullValuesException inve) {
            System.out.println("pojo contains invalid null values within the fields," +
                    " cannot do update op");
        }
        return false ;
    }

    // testing

    public void close() {
        for(Closeable c : daos) {
            try {
                c.close();
            }
            catch (Exception e )
            {
             e.printStackTrace();
            }
        }
    }

    public class InvalidTokenException extends  RuntimeException{
    }

    // TODO getAirlinesBy name , country etc ...


// tests

    public static void main(String[] args) {
        AnonymousFacade af = new AnonymousFacade();
        Customer c1 = new Customer() ;
    //   System.out.println(c1.toStringArray()[2]) ;
//        FacadeBase.LoginToken lt =  fb.new LoginToken() ;
//        lt.role =  LoginToken.Role.ADMIN ;

        System.out.println("at get all flights :" + af.getAllFlights()) ;
        System.out.println(af.getFlightById(2)) ;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy") ;
        try {
            System.out.println(af.getFlightsByParameters(1, 1, df.parse("28/04/2022")));
        } catch (Exception e) {e.printStackTrace();}
        System.out.println(af.getAirlineById(1)) ;
        System.out.println(af.getAllAirlines()) ;
        System.out.println(af.getAllCountries()) ;
        System.out.println(af.getCountryById(1));


    }
}
