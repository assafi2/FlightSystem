package com.example.FlightSystemBackend.BussinessServices;

import com.example.FlightSystemBackend.DAO.AirlineCompanyDAOJDBCImpl;
import com.example.FlightSystemBackend.DAO.DAOJDBCImpl;
import com.example.FlightSystemBackend.DAO.FlightDAOJDBCImpl;
import com.example.FlightSystemBackend.DAO.UserDAOJDBCImpl;
import com.example.FlightSystemBackend.PersistantDomainObjects.Airline_Company;
import com.example.FlightSystemBackend.PersistantDomainObjects.Flight;
import com.example.FlightSystemBackend.PersistantDomainObjects.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class AirlineFacade extends AnonymousFacade{


    public AirlineFacade(LoginToken loginToken){
        this.loginToken = loginToken ;
        if (aidao == null) instantiateAirlineCompanyDAO();
        if (udao == null) instantiateUserDAO();
        if (fdao == null) instantiateFlightDAO();

    }
    // return null for a not valid airline company corresponding to current user
    public List<Flight> getMyFlights (LoginToken loginToken) {
        if (!this.loginToken.equals(loginToken)) throw new InvalidTokenException() ;
        // only one airline can reference a user
        List<Airline_Company> airline = aidao.getAirlinesByUser(udao.getUserByUsername(loginToken.name)) ;
        if (!airline.isEmpty())
            return fdao.getFlightsByAirlineCompanyId(airline.get(0).id) ;
        return null ;

    }
    public boolean updateAirline(LoginToken loginToken, Airline_Company airline) {
        if (!this.loginToken.equals(loginToken)) throw new InvalidTokenException();
        if (!(udao.getUserByUsername(loginToken.name).equals( udao.get(airline.user_id))))   // different airlines
            return false;
        try {
            return aidao.update(airline);
        } catch (DAOJDBCImpl.InvalidNullValuesException e) {
            displayNullError();
        }
        return false;
    }

    public boolean addFlight(LoginToken loginToken, Flight flight){
        if (!this.loginToken.equals(loginToken)) throw new InvalidTokenException() ;
        if (!udao.getUserByUsername(loginToken.name)
                .equals((udao.get(aidao.get(flight.airline_company_id).user_id))))   // different airlines
            return false;
        try {
            return fdao.add(flight);
        } catch (DAOJDBCImpl.InvalidNullValuesException e) {
            displayNullError();
        }
        return false;
    }

    public boolean updateFlight (LoginToken loginToken, Flight flight) {
        if (!this.loginToken.equals(loginToken)) throw new InvalidTokenException() ;
        if (!udao.getUserByUsername(loginToken.name).
                equals((udao.get(aidao.get(flight.airline_company_id).user_id))))   // different airlines
            return false;
        try {
            return fdao.update(flight);
        } catch (DAOJDBCImpl.InvalidNullValuesException e) {
            displayNullError();
        }
        return false;
    }
    public boolean removeFlight (LoginToken loginToken, Flight flight) {
        if (!this.loginToken.equals(loginToken)) throw new InvalidTokenException() ;
        if (!udao.getUserByUsername(loginToken.name).equals(udao.get(aidao.get(flight.airline_company_id).user_id)))   // different airlines
            return false;
        return fdao.remove(flight.id);

    }

    public static void main(String[] args) {
        LoginToken lt = new LoginToken(new Random().nextLong(), "elal", LoginToken.Role.AIRLINE) ;
        AirlineFacade af = new AirlineFacade(lt) ;
        System.out.println(af.getMyFlights(lt));
 /*       UserDAOJDBCImpl udao = new UserDAOJDBCImpl() ;
        try {
            udao.add(new User(8, "arkia", "arkia111", "arkia1@gmail.com", 1));
        }
        catch (Exception e) {
            e.printStackTrace() ;
        }
*/
        Airline_Company a1 = new Airline_Company(1, "el-al", 1,1) ;
/*        try {
            new AirlineCompanyDAOJDBCImpl().add(a1) ;
        }
        catch (Exception e){
            e.printStackTrace();
        }
*/
 //       System.out.println(af.updateAirline(lt,a1)) ;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Flight f1 = new Flight(8,1,1, 1,
                LocalDateTime.parse("2016-05-04 09:30",formatter),
                LocalDateTime.parse("2022-05-04 11:30",formatter),150) ;
        System.out.println(af.updateFlight(lt,f1)) ;
    }

}
