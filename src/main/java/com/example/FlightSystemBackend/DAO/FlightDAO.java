package com.example.FlightSystemBackend.DAO;

import com.example.FlightSystemBackend.PersistantDomainObjects.Customer ;
import com.example.FlightSystemBackend.PersistantDomainObjects.Flight;

import java.util.Date;
import java.util.List;

public interface FlightDAO extends DAO<Flight> {

    public List<Flight> getFlightsByOriginCountryId(int countryId) ;
    public List<Flight> getFlightsByDestinationCountryId(int countryId) ;
    public List<Flight> getFlightsByDepartureDate(Date date) ;
    public List<Flight> getFlightsByLandingDate(Date date) ;
    public List<Flight> getFlightsByCustomer(Customer customer) ;
    public List<Flight> getFlightsByParameters(int origin_country_id, int destination_country_id, Date date) ;
    public List<Flight> getFlightsByAirlineCompanyId(long airline_company_id) ;

}
