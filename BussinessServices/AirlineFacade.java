package BussinessServices;

import DAO.DAOJDBCImpl;
import PersistantDomainObjects.Airline_Company;
import PersistantDomainObjects.Flight;

import java.util.List;

public class AirlineFacade extends AnonymousFacade{

    public AirlineFacade(){
        if (aidao == null) instantiateAirlineCompanyDAO();
        if (udao == null) instantiateUserDAO();
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
        if (udao.getUserByUsername(loginToken.name) != udao.get(airline.user_id))   // different airlines
            return false;
        try {
            return aidao.add(airline);
        } catch (DAOJDBCImpl.InvalidNullValuesException e) {
            displayNullError();
        }
        return false;
    }

    public boolean addFlight(LoginToken loginToken, Flight flight){
        if (!this.loginToken.equals(loginToken)) throw new InvalidTokenException() ;
        if (udao.getUserByUsername(loginToken.name) !=
                (udao.get(aidao.get(flight.airline_company_id).user_id)))   // different airlines
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
        if (udao.getUserByUsername(loginToken.name) !=
                (udao.get(aidao.get(flight.airline_company_id).user_id)))   // different airlines
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
        if (udao.getUserByUsername(loginToken.name) !=
                (udao.get(aidao.get(flight.airline_company_id).user_id)))   // different airlines
            return false;
        return fdao.remove(flight.id);

    }

}
