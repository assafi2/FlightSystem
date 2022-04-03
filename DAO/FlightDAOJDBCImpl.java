package DAO;

import PersistantDomainObjects.Administrator;
import PersistantDomainObjects.Customer;
import PersistantDomainObjects.Flight;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FlightDAOJDBCImpl extends DAOJDBCImpl<Flight> implements FlightDAO{


    // help method to convert Timestamp to Date
    public static Date localDateTimeToDate(LocalDateTime ldt) {

        return Date.from(ldt.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant()) ;
    }
    void assertNotNullValues(Flight t) throws InvalidNullValuesException {} ;


    public FlightDAOJDBCImpl() {
        super((new Flight()).getClass());
    }


    // null when query has no result or involve SQLExceptions
    public Flight get(long id) {

        try (ResultSet rs = getSet(id)) {
            if (rs.next()) {
                return new Flight(rs.getLong(1), rs.getLong(2),
                        rs.getInt(3), rs.getInt(4),
                        rs.getTimestamp(5).toLocalDateTime(),
                        rs.getTimestamp(6).toLocalDateTime(), rs.getInt(7)) ;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null ;
    }
    public List<Flight> getAll() {
        List<Flight> list = new ArrayList<Flight>();
        try (ResultSet rs = getSet(-1)) {
            while (rs.next()) {
                list.add(
                        new Flight(rs.getLong(1), rs.getLong(2),
                                rs.getInt(3), rs.getInt(4),
                                rs.getTimestamp(5).toLocalDateTime(),
                                rs.getTimestamp(6).toLocalDateTime(), rs.getInt(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // accept a query on the "flight" table to return a list of resulting Flight objects
    // in a JOIN query the flight must be the leftmost table in the JOIN
    protected List<Flight> getFlightsBy(String query) {
        List<Flight> list = new ArrayList<Flight>() ;
        // could be generated as below as well
//      String query = "select flight.id, airline_company_id, origin_country_id, destination_country_id," +
//              " departure_time, landing_time, remaining_tickets from flight " + whereClause ;
//        String query = getAllTemp.replace(";",whereClause) ;
//        System.out.println(query);
        try(Statement stmnt = con.createStatement()){
            ResultSet rs = stmnt.executeQuery(query) ;
            while (rs.next()) {
                list.add(
                        new Flight(rs.getLong(1), rs.getLong(2),
                                rs.getInt(3), rs.getInt(4),
                                rs.getTimestamp(5).toLocalDateTime(),
                                rs.getTimestamp(6).toLocalDateTime(), rs.getInt(7)));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println("Can not query the DB for Flights");
        }
        return list ;
    }


    public List<Flight> getFlightsByOriginCountryId(int countryId) {
        return getFlightsBy(getByPart + "where origin_country_id = %s ;".formatted(countryId)) ;
    }
    public List<Flight> getFlightsByDestinationCountryId(int countryId) {
        return getFlightsBy(getByPart + "where destination_country_id = %s ;".formatted(countryId)) ;
    }
    public List<Flight> getFlightsByDepartureDate(Date date) {
//        System.out.println("where DATE(departure_time) = DATE('%s') ;".formatted(date));
        return getFlightsBy(getByPart + "where DATE(departure_time) = Date('%s') ;".formatted(date)) ;
  //      return  null ;
    }
    public List<Flight> getFlightsByLandingDate(Date date) {
        return getFlightsBy(getByPart + "where DATE(landing_time) = DATE('%s') ;".formatted(date)) ;
    }
    public List<Flight> getFlightsByCustomer(Customer customer) {
        return getFlightsBy(getByPart + ", get_tickets_by_customer(%s) as T".formatted(customer.id) +
                " where flight.id = T.flight_id ;") ;
    }
    public List<Flight> getFlightsByParameters(int origin_country_id, int destination_country_id, Date date){
        return getFlightsBy("select * from get_flights_by_parameters(%s, %s,'%s') ;".
                formatted(origin_country_id, destination_country_id, date)) ;
    }

    public List<Flight> getFlightsByAirlineCompanyId(long airline_company_id){
        return getFlightsBy("select * from flights where airline_company_id = %d ;".
                formatted(airline_company_id)) ;
    }


}
