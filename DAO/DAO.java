package DAO;

import PersistantDomainObjects.Customer;
import PersistantDomainObjects.POCO;

import java.io.Closeable;
import java.util.List;

public interface DAO <T extends POCO> extends Closeable {

    public T get(long id);
    public List<T> getAll();
    public boolean add(T t) throws DAO.DAOJDBCImpl.InvalidNullValuesException;
    public boolean remove(long id) ;
    public boolean update(T t) throws DAO.DAOJDBCImpl.InvalidNullValuesException;
}
