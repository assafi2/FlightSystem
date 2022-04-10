package com.example.FlightSystemBackend.DAO;

import com.example.FlightSystemBackend.PersistantDomainObjects.Customer;
import com.example.FlightSystemBackend.PersistantDomainObjects.POCO;
import com.example.FlightSystemBackend.DAO.DAOJDBCImpl ;

import java.io.Closeable;
import java.util.List;

public interface DAO <T extends POCO> extends Closeable {

    public T get(long id);
    public List<T> getAll();
    public boolean add(T t) throws DAOJDBCImpl.InvalidNullValuesException;
    public boolean remove(long id) ;
    public boolean update(T t) throws DAOJDBCImpl.InvalidNullValuesException;
}
