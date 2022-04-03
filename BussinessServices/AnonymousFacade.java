package BussinessServices;

import DAO.DAO;
import PersistantDomainObjects.Customer;
import DAO.CustomerDAOJDBCImpl ;
import PersistantDomainObjects.User;
import DAO.CustomerDAO ;

public class AnonymousFacade extends FacadeBase {

    CustomerDAO cudao ;

    void instantiateCustomerDAO() {
        cudao = new CustomerDAOJDBCImpl();
        daos.add(cudao);
    }

    public boolean addCustomer(Customer customer) {
        if (cudao == null) instantiateCustomerDAO();
        try {
            return cudao.add(customer) ;
        }
        catch(DAO.DAOJDBCImpl.InvalidNullValuesException e){
            System.out.println("invalid null value within POJO can not make the update op");
        }
        return false ;
    }
    public FacadeBase login(String username,String password) {
        User user =  udao.getUserByUsername(username) ;
        if (user == null) return null ;      // bad username
        switch(udao.retRoleName(user.user_role)){
            case "customer" :
                return null ;

            case "airline_company" :
                return null ;

            case "administrator" :
                return null ;
            default :
                return null ;
        }
    }

}
