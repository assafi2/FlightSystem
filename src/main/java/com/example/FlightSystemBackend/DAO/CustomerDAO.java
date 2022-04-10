package com.example.FlightSystemBackend.DAO;

import com.example.FlightSystemBackend.PersistantDomainObjects.Customer;

public interface CustomerDAO extends DAO<Customer> {

    public Customer getCustomerByUsername(String username) ;



}
