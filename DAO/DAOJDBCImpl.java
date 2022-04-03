package DAO;

import PersistantDomainObjects.*;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public abstract class DAOJDBCImpl<T extends POCO> {

    // mutual queries templates, further settings of them in the constructor

    String getAllTemp = "select * from %s ;" ;
    String getByIdTemp = "select * from %s where id = %s ;" ;
    String deleteTemp = "delete from %s where id = %s " ;
    String addTemp ;
    String updateTemp;
    // partial query ! base part of query returning * from corresponding table
    String getByPart = "" ;


    Class<? extends T> classObj ;
    Connection con ;
    String tableName ;

    public DAOJDBCImpl(Class<? extends T> classObj) {

        this.classObj = classObj ;
        // extract table name from class object
        String className =  classObj.getName() ;
        tableName = className.substring(className.lastIndexOf(".")+1,className.length()) ;
        System.out.println(tableName);
        fixTableName();
        Field[] fields = classObj.getDeclaredFields() ;
        String attributesRec = "" ;
        for(int i = 1 ; i < fields.length ; i++) {
            attributesRec = attributesRec + "%s,".formatted(fields[i].getName()) ;
        }
        System.out.println(attributesRec);
        attributesRec = attributesRec.substring(0,attributesRec.length()-1) ;
        System.out.println(attributesRec);
        // prepare queries strings

        getAllTemp = getAllTemp.formatted(tableName) ;
        getByIdTemp = String.format(getByIdTemp,tableName,"%s") ;
        addTemp = "INSERT INTO %s(%s)\n".formatted(tableName,attributesRec) +
                "VALUES (%s);\t\n " ;
        deleteTemp = deleteTemp.formatted(tableName,"%s") ;

        updateTemp = "UPDATE %s SET %s ".formatted(tableName,"%s") +
                "WHERE id = %s ;" ;

        getByPart = getAllTemp.replace(";","") ;


        // manipulate SET query template according to table (class) attributes (fields)

        String fieldsString = "" ;
        for(int i=1  ; i < fields.length ; i++){
            fieldsString = fieldsString + fields[i].getName() + " = %s," ;
        }

        fieldsString = fieldsString.substring(0,fieldsString.length()-1) ;

        updateTemp = updateTemp.formatted(fieldsString,"%s");

        System.out.println(updateTemp);

        // creating DB connection

        try {
            //         Class.forName("org.postgresql.jdbc");
            String url =  "jdbc:postgresql://localhost/FlightsSystem";
            con = DriverManager.getConnection(url,"postgres","gHZhCR45") ;
            //      } catch (ClassNotFoundException e) {
            //          e.printStackTrace();
            //          System.out.println("can not open connection with DB");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    // callback in case table name field need a fix before formatting templates
    // to be overridden in this fashion
    void fixTableName(){}

    abstract void assertNotNullValues(T t) throws InvalidNullValuesException ;

    public abstract T get(long id) ;


    public abstract List<T> getAll() ;

    // return result set for a get(by id) query in id > 0 otherwise return result set for getAll query
    // might need to properly close statement object created each call

    protected ResultSet getSet(long id) {

        try {
            Statement stmnt = con.createStatement() ;
 //           System.out.println((id>0) ? getByIdTemp.formatted(id) : getAllTemp);
  //          System.out.println("in getSet : " + getAllTemp);
 //           ResultSet rs = stmnt.executeQuery(getAllTemp) ;

            System.out.println("in getSet " + getAllTemp);

            return stmnt.executeQuery((id>0) ? getByIdTemp.formatted(id) : getAllTemp);
 //       return rs ;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.printf("could not execute query on table %s", tableName);
        }
        return null ;
    }
    protected ResultSet getSet(String query) {
        try{
            Statement stmnt = con.createStatement() ;
            return stmnt.executeQuery(query) ;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null ;
    }
    public boolean add(T t) throws InvalidNullValuesException {
        assertNotNullValues (t);
        // generate insert query for the corresponding new values
        String insertQuery = addTemp.formatted(t.toRecordString());
        try (Statement stmnt = con.createStatement()) {
            if (stmnt.executeUpdate(insertQuery) > 0) return true ;
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.printf("could not add specified record to table %s ", tableName);
        }
        return false ;
    }

        public boolean remove(long id) {
    // generate update query for the corresponding new value
           String deleteQuery = deleteTemp.formatted(id);
           System.out.println(deleteQuery);
           try (Statement stmnt = con.createStatement()) {
               if (stmnt.executeUpdate(deleteQuery) > 0) return true ;
           }
           catch (SQLException e) {
             e.printStackTrace();
             System.out.printf("could not delete from table %s specified record", tableName);
           }
           return false ;
        }

    public boolean update(T t) throws  InvalidNullValuesException {

        assertNotNullValues(t);
        // generate update query for the corresponding new values
        String updateQuery = updateTemp.formatted(t.toStringArray());
        try (Statement stmnt = con.createStatement()) {
//          String setString = "";
            if (0 < stmnt.executeUpdate(updateQuery)) return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.printf("could not insert into table %s the new record\n", tableName);
        }
        return false ;
    }

    // close DB resources

    public void close() {
        try {
            con.close();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    // exception class that represent an exception that occurs from invalid 'null' values of a POCO object
    public static class InvalidNullValuesException extends Exception  { }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {

        AirlineCompanyDAO adao = new AirlineCompanyDAOJDBCImpl();
        FlightDAOJDBCImpl fdao = new FlightDAOJDBCImpl();
        DAO<User> udao = new UserDAOJDBCImpl() ;
        DAO<Customer> cdao = new CustomerDAOJDBCImpl();

        Customer c1 = new Customer(4, "moshe", "elias", "Haefrati 3 street Tel Aviv",
                "0528306444", "4580160166656789", 3);
        //    cdao.update(c1);
        //        System.out.println(c1.toRecordString());

        //      cdao.remove(3) ;
        //     cdao.add(c1) ;
        Customer c2 = new Customer() ;
   //     cdao.add(c2);
        System.out.println(adao.get(1));
        System.out.println();
        System.out.println(adao.getAll());
        System.out.println();
        System.out.println(adao.getAirlinesByCountry(1));
        System.out.println();
        System.out.println("airlines by name : \n" + adao.getAirlinesByName("el-al"));
        System.out.println();
        System.out.println("user by id : \n" + udao.get(100));
//        System.out.println("airlines by User : \n" + adao.getAirlinesByUser(udao.get(1)));
        System.out.println();
        System.out.println("----- FlightDAO test -----");
        System.out.println(fdao.get(2));
        System.out.println();
//        System.out.println(fdao.getAll());
//        System.out.println(fdao.getFlightsBy("where origin_country_id = 1 ; "));
        // getFlightsByOriginCountryId(int countryId) ;
        //       public List<Flight> getFlightsByDestinationCountryId(int countryId) ;
        System.out.println();
        System.out.println(fdao.getFlightsByCustomer(cdao.get(1)));
        System.out.println();
        System.out.println(fdao.getFlightsByDestinationCountryId(1));
        System.out.println();
        System.out.println(fdao.getFlightsByOriginCountryId(3));
        System.out.println();
        //   System.out.println(fdao.getFlightsByDepartureDate
        //           (Date.from(fdao.getFlightsByDestinationCountryId(1).get(0).departure_time.toInstant(ZoneOffset()))));

        LocalDateTime ldt1 = fdao.getFlightsByDestinationCountryId(1).get(0).departure_time;
        System.out.println(fdao.getFlightsByDepartureDate(fdao.localDateTimeToDate(ldt1)));
        LocalDateTime ldt2 = fdao.getFlightsByDestinationCountryId(1).get(0).landing_time;
        System.out.println("---");
        System.out.println(fdao.getFlightsByLandingDate(fdao.localDateTimeToDate(ldt2)));
        System.out.println(fdao.getFlightsByParameters(1,1,FlightDAOJDBCImpl.localDateTimeToDate(ldt1)));
    }
}
