package DAO;

import PersistantDomainObjects.Airline_Company;
import PersistantDomainObjects.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AirlineCompanyDAOJDBCImpl extends DAOJDBCImpl<Airline_Company> implements AirlineCompanyDAO
{

    public AirlineCompanyDAOJDBCImpl()
    {
        super((new Airline_Company()).getClass());
    }

    void assertNotNullValues(Airline_Company t) throws InvalidNullValuesException {} ;


    // null when query has no result or involve SQLExceptions
    public Airline_Company get(long id) {


        try (ResultSet rs = getSet(id)) {
            if (rs.next()) {
                return new Airline_Company(rs.getLong(1), rs.getString(2),
                        rs.getInt(3), rs.getLong(4)) ;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null ;
    }
    public List<Airline_Company> getAll() {
        List<Airline_Company> companies = new ArrayList<Airline_Company>() ;
        try (ResultSet rs = getSet(-1)) {
            while (rs.next()) {
                companies.add(
                        new Airline_Company(rs.getLong(1), rs.getString(2),
                                rs.getInt(3), rs.getLong(4))) ;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return companies ;
    }


    // accept a query on the "airline_company" table to return a list of resulting Airline_Company
    // objects . In a JOIN query the "airline_company" must be the leftmost table in the JOIN
    protected List<Airline_Company> getAirlinesBy(String airlinesQuery) {
        List<Airline_Company> companies = new ArrayList<Airline_Company>() ;
    //    String queryString = "select * from Airline_Company where country_id = %d ;".formatted(country_id) ;

        try (Statement stmnt = con.createStatement()) {
            ResultSet rs = stmnt.executeQuery(airlinesQuery) ;
            while (rs.next()) {
                companies.add(
                        new Airline_Company(rs.getLong(1), rs.getString(2),
                                rs.getInt(3), rs.getLong(4))) ;
            }
        }
        catch (SQLException e) {
            System.out.println("could not execute query against db");
            e.printStackTrace();
        }
        return companies ;
    }

    public List<Airline_Company> getAirlinesByCountry(int country_id){

        return getAirlinesBy(getByPart + "where country_id = %d ;".formatted(country_id)) ;
        /*
        List<Airline_Company> companies = new ArrayList<Airline_Company>() ;
        String queryString = "select * from Airline_Company where country_id = %d ;".formatted(country_id) ;
        try (Statement stmnt = con.createStatement()) {
            ResultSet rs = stmnt.executeQuery(queryString) ;
            while (rs.next()) {
                companies.add(
                        new Airline_Company(rs.getLong(1), rs.getString(2),
                                rs.getInt(3), rs.getLong(4))) ;
            }
        }
        catch (SQLException e) {
            System.out.println("could not execute query against db");
            e.printStackTrace();
        }
        return companies ;


         */
    }

    public List<Airline_Company> getAirlinesByName(String name) {
        return getAirlinesBy(getByPart + "where name = '%s' ;".formatted(name)) ;

    }
    public List<Airline_Company> getAirlinesByUser(User user) {

        return getAirlinesBy("select * from get_airline_by_username('%s') ;".
                formatted(user.username)) ;
    }


}
