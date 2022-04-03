package DAO;

import PersistantDomainObjects.Customer;

public interface CustomerDAO extends DAO<Customer> {

    public Customer getCustomerByUsername(String username) ;



}
