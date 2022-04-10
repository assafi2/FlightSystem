package com.example.FlightSystemBackend.BussinessServices;

import com.example.FlightSystemBackend.DAO.* ;
import com.example.FlightSystemBackend.DAO.DAO;
import com.example.FlightSystemBackend.PersistantDomainObjects.Customer;
import com.example.FlightSystemBackend.DAO.CustomerDAOJDBCImpl ;
import com.example.FlightSystemBackend.PersistantDomainObjects.User;
import com.example.FlightSystemBackend.DAO.CustomerDAO ;

import java.util.Random;

public class AnonymousFacade extends FacadeBase {

    CustomerDAO cudao ;

    Random rnd = new Random() ; // generating token IDs


    void instantiateCustomerDAO() {
        cudao = new CustomerDAOJDBCImpl();
        daos.add(cudao);
    }

    public boolean addCustomer(Customer customer) {
        if (cudao == null) instantiateCustomerDAO();
        try {
            return cudao.add(customer) ;
        }
        catch(DAOJDBCImpl.InvalidNullValuesException e){
            System.out.println("invalid null value within POJO can not make the update op");
        }
        return false ;
    }
    public FacadeBase login(String username,String password) {
        if (udao == null) instantiateUserDAO();
        User user =  udao.getUserByUsername(username) ;
        if (user == null) {
            return new AnonymousFacade();      // bad username
        }
        switch(udao.retRoleName(user.user_role)){
            case "customer" :
                return new CustomerFacade(new LoginToken(rnd.nextLong(), user.username, LoginToken.Role.CUSTOMER )) ;

            case "airline_company" :
                return new AirlineFacade(new LoginToken(rnd.nextLong(), user.username, LoginToken.Role.AIRLINE )) ;

            case "administrator" :
                return new AdministratorFacade(new LoginToken(rnd.nextLong(), user.username, LoginToken.Role.ADMIN )) ;

            default :
                return new AnonymousFacade(); // invalid role therefore continue as anonymous
        }
    }

    public static void main(String[] args) {

        AnonymousFacade af = new AnonymousFacade() ;
        FacadeBase ret = af.login("assafi1","assafi111") ;
        System.out.println(ret.getLoginToken() + " ---- " + ret.getClass().getName()) ;
    }

}
