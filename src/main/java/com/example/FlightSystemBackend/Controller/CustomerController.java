package com.example.FlightSystemBackend.Controller;


import com.example.FlightSystemBackend.BussinessServices.AnonymousFacade;
import com.example.FlightSystemBackend.BussinessServices.CustomerFacade;
import com.example.FlightSystemBackend.BussinessServices.LoginToken;
import com.example.FlightSystemBackend.PersistantDomainObjects.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController extends AnonymousController {


    public static final LoginToken TEMP_TOKEN =
            new LoginToken(111, "assafi1", LoginToken.Role.CUSTOMER);

    private CustomerFacade facade =  new CustomerFacade(TEMP_TOKEN);

    CustomerFacade generateFacade(LoginToken loginToken) {
        return new CustomerFacade(loginToken) ;
    }

    @GetMapping("/flights/")
    public List<Flight> getFlights() {
        return facade.getAllFlights();
    }

    @GetMapping("/airlines/")
    public List<Airline_Company> getAirlines() {
        return facade.getAllAirlines();
    }

    @GetMapping("/countries/")
    public List<Country> getCountries() {
        return facade.getAllCountries();
    }

    @GetMapping("/flights/{id}")
    public Flight getFlight(@PathVariable("id") int id) {
        Flight flight = facade.getFlightById(id);
        if (flight != null) return flight;
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/airlines/{id}")
    public Airline_Company getAirline(@PathVariable("id") int id) {
        Airline_Company airline = facade.getAirlineById(id);
        if (airline != null) return airline;
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/countries/{id}")
    public Country getCountry(@PathVariable("id") int id) {
        Country country = facade.getCountryById(id);
        if (country != null) return country;
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/flights/{originCountry}/{destinationCountry}")
    public List<Flight> getFlights(@PathVariable("originCountry") int oc,
                                   @PathVariable("destinationCountry") int dc,
                                   @RequestParam("date") Date date) {
        return facade.getFlightsByParameters(oc, dc, date);
    }

    // TODO get airlines by varying params


    @PostMapping("/login/")
    public LoginToken login(@RequestBody String username, @RequestBody String password) {
        System.out.println(username + " --- " + password );

        System.out.println(username + "    " + password);
        if ((username == null) || (password == null))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return facade.login(username, password).getLoginToken();
    }


    @PostMapping("/signin/")
    public boolean addCustomer(@RequestBody Customer customer) {
        if (customer == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        // TODO create new user method within the FacadeBase class and to use it here
        //  with a user object from a requestbody arg
        return facade.addCustomer(customer);
    }


    // CUSTOMER CONTROLLER FUNCTIONALITIES


    @PutMapping("/")
    public boolean updateCustomer(Authentication authToken, @RequestBody Customer customer) {
        if ((customer == null) || (authToken == null))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        LoginToken loginToken = generateToken(authToken) ;
        facade = generateFacade(loginToken) ;
        return facade.updateCustomer(loginToken, customer);
    }

    @PostMapping("/tickets/")
    public boolean addTicket(Authentication authToken, @RequestBody Ticket ticket) {

        if ((ticket == null) || (authToken == null))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        LoginToken loginToken = generateToken(authToken) ;
        facade = generateFacade(loginToken) ;
        return facade.addTicket(loginToken, ticket);
    }

    @DeleteMapping("/tickets/")
    public boolean removeTicket(Authentication authToken, @RequestBody Ticket ticket) {

        if ((ticket == null) || (authToken == null))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        LoginToken loginToken = generateToken(authToken) ;
        facade = generateFacade(loginToken) ;
        return facade.removeTicket(loginToken, ticket);
    }
/*
    @PostMapping("/tickets/my/")
    public List<Ticket> getMyTickets(@RequestBody LoginToken loginToken) {
        if (loginToken == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return facade.getMyTickets(loginToken);

    }
*/
    @GetMapping("/tickets/my/")
    public List<Ticket> getMyTickets(Authentication authToken) {
        if (authToken == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        LoginToken loginToken = generateToken(authToken) ;
        facade = generateFacade(loginToken) ;
        return facade.getMyTickets(loginToken);

    }
}