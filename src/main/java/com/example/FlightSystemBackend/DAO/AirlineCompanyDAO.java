package com.example.FlightSystemBackend.DAO;

import com.example.FlightSystemBackend.PersistantDomainObjects.Airline_Company;
import com.example.FlightSystemBackend.PersistantDomainObjects.User;

import java.util.List;

public interface AirlineCompanyDAO extends DAO<Airline_Company> {

    // get airlines by different parameters

    public List<Airline_Company> getAirlinesByCountry(int country_id) ;
   // public List<Airline_Company> getAirlineByParameters(int country_id,String name, String user_id) ;
    public List<Airline_Company> getAirlinesByName(String name) ;
    public List<Airline_Company> getAirlinesByUser(User user) ;
}
