package DAO;

import PersistantDomainObjects.Airline_Company;
import PersistantDomainObjects.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class UserDAOJDBCImpl extends DAOJDBCImpl<User> implements UserDAO{

    Map<Integer,String> roles = new TreeMap<Integer,String>() ;

    public UserDAOJDBCImpl()
    {
        super((new User()).getClass());
        // fetch roles from db
        try (Statement stmnt = con.createStatement()){
            ResultSet rs = stmnt.executeQuery("select * from user_role ;") ;
            while (rs.next()) {
                roles.put(rs.getInt(1),rs.getString(2)) ;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void assertNotNullValues(User t) throws InvalidNullValuesException {} ;


    void fixTableName(){
        this.tableName += 's' ;
    }

    public String retRoleName(int role_id) {
        return roles.get(new Integer(role_id)) ;
    }

    // null when query has no result or involve SQLExceptions
    public User get(long id) {

        try (ResultSet rs = getSet(id)) {
            if (rs.next()) {
                return new User(rs.getLong(1), rs.getString(2),
                        rs.getString(3),rs.getString(4),
                        rs.getInt(5)) ;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null ;
    }
    public List<User> getAll() {
        List<User> list = new ArrayList<User>() ;
        try (ResultSet rs = getSet(-1)) {
            while (rs.next()) {
                list.add(
                        new User(rs.getLong(1), rs.getString(2),
                        rs.getString(3),rs.getString(4),
                        rs.getInt(5))) ;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return list ;
    }

    @Override
    public User getUserByUsername(String userName) {
        try(ResultSet rs = getSet("select * from get_user_by_username('%s') ; ".formatted(userName))){
            if (rs == null) return null ;
            if (rs.next()) {
                return new User(rs.getLong(1),rs.getString(2),
                        rs.getString(3),rs.getString(4),rs.getInt(5)) ;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null ;
    }
}
