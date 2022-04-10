package com.example.FlightSystemBackend.DAO;

import com.example.FlightSystemBackend.PersistantDomainObjects.Country;
import com.example.FlightSystemBackend.PersistantDomainObjects.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryDAOJDBCImpl extends DAOJDBCImpl<Country> implements DAO<Country>{


    public CountryDAOJDBCImpl() {
        super((new Country()).getClass());
    }

    void assertNotNullValues(Country t) throws InvalidNullValuesException {} ;


    // null when query has no result or involve SQLExceptions
    public Country get(long id) {


        try (ResultSet rs = getSet(id)) {
            if (rs.next()) {
                return new Country(rs.getInt(1), rs.getString(2)) ;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null ;
    }
    public List<Country> getAll() {
        List<Country> list = new ArrayList<Country>() ;
        try (ResultSet rs = getSet(-1)) {
            while (rs.next()) {
                list.add(
                        new Country(rs.getInt(1), rs.getString(2))) ;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return list ;
    }



}
