package com.example.FlightSystemBackend.Controller;


import com.example.FlightSystemBackend.BussinessServices.AirlineFacade;
import com.example.FlightSystemBackend.BussinessServices.AnonymousFacade;
import com.example.FlightSystemBackend.BussinessServices.LoginToken;
import com.example.FlightSystemBackend.PersistantDomainObjects.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/airline")
public class AirlineCompanyController {


    public static final LoginToken TEMP_TOKEN =
            new LoginToken(111, "elal", LoginToken.Role.CUSTOMER);

    private AirlineFacade facade = new AirlineFacade(TEMP_TOKEN) ;

    @GetMapping("/flights/")
    public List<Flight> getFlights() {
        return facade.getAllFlights() ;
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
        Flight flight = facade.getFlightById(id) ;
        if (flight!=null) return flight ;
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND) ;
    }

    @GetMapping("/airlines/{id}")
    public Airline_Company getAirline(@PathVariable("id") int id) {
        Airline_Company airline = facade.getAirlineById(id) ;
        if (airline!=null) return airline ;
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND) ;
    }
    @GetMapping("/countries/{id}")
    public Country getCountry(@PathVariable("id") int id) {
        Country country = facade.getCountryById(id) ;
        if (country!=null) return country ;
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND) ;
    }


    @GetMapping("/flights/{originCountry}/{destinationCountry}")
    public List<Flight> getFlights(@PathVariable("originCountry") int oc,
                                   @PathVariable("destinationCountry") int dc,
                                   @RequestParam("date") Date date) {
        return facade.getFlightsByParameters(oc,dc,date) ;
    }

    // TODO get airlines by varying params


    @PostMapping("/login/")
    public LoginToken login(@RequestBody String username, @RequestBody String password) {
        if ((username == null) || (password == null))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return facade.login(username, password).getLoginToken();
    }


    @PostMapping("/signin/")
    public boolean addCustomer(@RequestBody Customer customer) {
        if (customer==null)
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
        // TODO create new user method within the FacadeBase class and to use it here
        //  with a user object from a requestbody arg
        return facade.addCustomer(customer) ;
    }

    // Airline controller functionality



    @PostMapping("/flights/my/")
    public List<Flight> getMyTickets(@RequestBody LoginToken loginToken) {
        if (loginToken == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return facade.getMyFlights(loginToken);
    }

    @PutMapping("/")
    public boolean updateAirline(LoginToken loginToken, Airline_Company airline) {
        if ((airline == null) || (loginToken == null))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return facade.updateAirline(loginToken, airline);
    }

    @PostMapping("/flights/")
    public boolean addFlight(@RequestBody LoginToken loginToken, @RequestBody Flight flight) {
        if (flight == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return facade.addFlight(loginToken,flight);
    }

    @PutMapping("/flights/")
    public boolean updateFlight(@RequestBody LoginToken loginToken, @RequestBody Flight flight) {
        if (flight == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return facade.updateFlight(loginToken,flight);
    }



    @DeleteMapping("/flights/")
    public boolean removeFlight(@RequestBody LoginToken loginToken, Flight flight) {
        if (flight == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
           return facade.removeFlight(loginToken,flight);
    }

}
