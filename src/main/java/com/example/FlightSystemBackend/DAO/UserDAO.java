package com.example.FlightSystemBackend.DAO;

import com.example.FlightSystemBackend.PersistantDomainObjects.User;

public interface UserDAO extends DAO<User> {

    public String retRoleName(int role_id) ;
    public User getUserByUsername(String userName) ;
}
