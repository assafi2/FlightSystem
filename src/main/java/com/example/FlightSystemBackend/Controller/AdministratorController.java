package com.example.FlightSystemBackend.Controller;

import com.example.FlightSystemBackend.BussinessServices.AdministratorFacade;
import com.example.FlightSystemBackend.BussinessServices.CustomerFacade;
import com.example.FlightSystemBackend.BussinessServices.LoginToken;
import com.example.FlightSystemBackend.PersistantDomainObjects.Administrator;
import com.example.FlightSystemBackend.PersistantDomainObjects.Airline_Company;
import com.example.FlightSystemBackend.PersistantDomainObjects.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")

public class AdministratorController extends AnonymousController {

        public static final LoginToken TEMP_TOKEN =
                new LoginToken(111, "yossi1", LoginToken.Role.ADMIN);

    private AdministratorFacade facade = new AdministratorFacade(TEMP_TOKEN);

    // Administrator controller functionality

    @PostMapping("/customers/all")
    public List<Customer> getCustomers(@RequestBody LoginToken loginToken) {
        return facade.getAllCustomers(loginToken) ;
    }

    @PostMapping("/customers/")
    public boolean getCustomers(@RequestBody LoginToken loginToken,@RequestBody Customer customer) {
        return facade.addCustomer(loginToken,customer)  ;
    }
    @DeleteMapping("/customers/")
    public boolean removeCustomer(@RequestBody LoginToken loginToken, @RequestBody Customer customer) {
        return facade.removeCustomer(loginToken,customer) ;
    }

    @PostMapping("/airlines/")
    public boolean addAirline(@RequestBody LoginToken loginToken, @RequestBody Airline_Company airline) {
        return facade.addAirline(loginToken,airline) ;
    }

    @DeleteMapping("/airlines/")
    public boolean removeAirline(@RequestBody LoginToken loginToken, @RequestBody Airline_Company airline) {
        return facade.removeAirline(loginToken,airline) ;
    }

    // admin's related ops

    @PostMapping("/")
    public boolean delete(@RequestBody LoginToken loginToken, @RequestBody Administrator admin) {
        return facade.addAdministrator(loginToken,admin) ;
    }

    @DeleteMapping("/")
    public boolean removeAirline(@RequestBody LoginToken loginToken, @RequestBody Administrator admin) {

        System.out.println("in remove admin");
        return facade.removeAdministrator(loginToken,admin) ;

    }








}
