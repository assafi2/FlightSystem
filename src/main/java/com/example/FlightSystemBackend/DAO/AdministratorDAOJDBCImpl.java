package com.example.FlightSystemBackend.DAO;

import com.example.FlightSystemBackend.PersistantDomainObjects.Administrator;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdministratorDAOJDBCImpl extends DAOJDBCImpl<Administrator> implements DAO<Administrator>{

    public AdministratorDAOJDBCImpl() {
        super((new Administrator()).getClass());
    }

    void assertNotNullValues(Administrator t) throws InvalidNullValuesException {} ;

    // null when query has no result or involve SQLExceptions
    public Administrator get(long id) {


        try (ResultSet rs = getSet(id)) {
            if (rs.next()) {
                return new Administrator(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getLong(4)) ;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null ;
    }
    public List<Administrator> getAll() {
        List<Administrator> list = new ArrayList<Administrator>() ;
        try (ResultSet rs = getSet(-1)) {
            while (rs.next()) {
                list.add(
                        new Administrator(rs.getInt(1), rs.getString(2),
                                rs.getString(3), rs.getLong(4))) ;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return list ;
    }

}
