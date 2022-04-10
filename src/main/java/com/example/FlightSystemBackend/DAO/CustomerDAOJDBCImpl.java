package com.example.FlightSystemBackend.DAO;

import com.example.FlightSystemBackend.PersistantDomainObjects.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOJDBCImpl extends DAOJDBCImpl<Customer> implements CustomerDAO {


    public CustomerDAOJDBCImpl() {
        super((new Customer()).getClass());
    }
    void assertNotNullValues(Customer t) throws InvalidNullValuesException {} ;

    // null when query has no result or involve SQLExceptions
    public Customer get(long id) {


        try (ResultSet rs = getSet(id)) {
            if (rs.next()) {
                return new Customer(rs.getLong(1), rs.getString(2),
                        rs.getString(3), rs.getString(4),rs.getString(5),
                        rs.getString(6), rs.getLong(7)) ;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null ;
    }
    public List<Customer> getAll() {
        List<Customer> list = new ArrayList<Customer>() ;
        try (ResultSet rs = getSet(-1)) {
            while (rs.next()) {
                list.add(
                        new Customer(rs.getLong(1), rs.getString(2),
                                rs.getString(3), rs.getString(4),rs.getString(5),
                                rs.getString(6), rs.getLong(7))) ;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return list ;
    }
    public Customer getCustomerByUsername(String username) {
        try (ResultSet rs = getSet("select * from get_customer_by_username('%s') ;".
                formatted(username))){
            if (rs.next()) {
                return new Customer(rs.getLong(1), rs.getString(2),
                        rs.getString(3), rs.getString(4),rs.getString(5),
                        rs.getString(6), rs.getLong(7)) ;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null ;
    }



}
