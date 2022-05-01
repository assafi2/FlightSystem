package com.example.FlightSystemBackend.Controller;

import com.example.FlightSystemBackend.BussinessServices.AdministratorFacade;
import com.example.FlightSystemBackend.BussinessServices.AirlineFacade;
import com.example.FlightSystemBackend.BussinessServices.LoginToken;
import com.example.FlightSystemBackend.PersistantDomainObjects.Administrator;
import com.example.FlightSystemBackend.PersistantDomainObjects.Airline_Company;
import com.example.FlightSystemBackend.PersistantDomainObjects.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/admin")

public class AdministratorController extends AnonymousController {

//        public static final LoginToken TEMP_TOKEN =
//                new LoginToken(111, "yossi1", LoginToken.Role.ADMINISTRATOR);

    private AdministratorFacade facade = null ; /// new AdministratorFacade(TEMP_TOKEN);

    // could br exclude  ...

    AdministratorFacade generateFacade(LoginToken loginToken) {
        return new AdministratorFacade(loginToken) ;
    }


    // Administrator controller functionality

    @PostMapping("/customers/all")
    public List<Customer> getCustomers(Authentication authToken) {
        if (authToken == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        LoginToken loginToken = generateToken(authToken) ;
        facade = generateFacade(loginToken) ;
        return facade.getAllCustomers(loginToken) ;
    }

    @PostMapping("/customers/")
    public boolean addCustomers(Authentication authToken,@RequestBody Customer customer) {
        if (authToken == null || customer == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        LoginToken loginToken = generateToken(authToken) ;
        facade = generateFacade(loginToken) ;
        return facade.addCustomer(loginToken,customer)  ;
    }
    @DeleteMapping("/customers/")
    public boolean removeCustomer(Authentication authToken, @RequestBody Customer customer) {
        if (authToken == null || customer == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        LoginToken loginToken = generateToken(authToken) ;
        facade = generateFacade(loginToken) ;
        return facade.removeCustomer(loginToken,customer) ;
    }

    @PostMapping("/airlines/")
    public boolean addAirline(Authentication authToken, @RequestBody Airline_Company airline) {
        if (authToken == null || airline == null )
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        LoginToken loginToken = generateToken(authToken) ;
        facade = generateFacade(loginToken) ;
        return facade.addAirline(loginToken,airline) ;
    }

    @DeleteMapping("/airlines/")
    public boolean removeAirline(Authentication authToken, @RequestBody Airline_Company airline) {
        if (authToken == null || airline == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        LoginToken loginToken = generateToken(authToken) ;
        facade = generateFacade(loginToken) ;
        return facade.removeAirline(loginToken,airline) ;
    }

    // admin's related ops

    @PostMapping("/")
    public boolean delete(Authentication authToken, @RequestBody Administrator admin) {
        if (authToken == null || admin == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        LoginToken loginToken = generateToken(authToken) ;
        facade = generateFacade(loginToken) ;
        return facade.addAdministrator(loginToken,admin) ;
    }

    @DeleteMapping("/")
    public boolean removeAdmin(Authentication authToken, @RequestBody Administrator admin) {
        if (authToken == null || admin == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        LoginToken loginToken = generateToken(authToken) ;
        facade = generateFacade(loginToken) ;
        return facade.removeAdministrator(loginToken,admin) ;
    }








}
