package com.example.FlightSystemBackend.Controller;

import com.example.FlightSystemBackend.BussinessServices.AnonymousFacade;
import com.example.FlightSystemBackend.BussinessServices.LoginToken;
import com.example.FlightSystemBackend.PersistantDomainObjects.Airline_Company;
import com.example.FlightSystemBackend.PersistantDomainObjects.Country;
import com.example.FlightSystemBackend.PersistantDomainObjects.Customer;
import com.example.FlightSystemBackend.PersistantDomainObjects.Flight;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Random;


@RestController
@RequestMapping("/anonymous")
public class AnonymousController {

    Random rnd = new Random() ;

    private AnonymousFacade facade = new AnonymousFacade() ;


    protected LoginToken generateToken(Authentication authen) {

        String role = authen.getAuthorities()
                .iterator().next()
                .getAuthority().replace("ROLE_","") ;
        return new LoginToken(rnd.nextInt(),authen.getName(),LoginToken.Role.valueOf(role.toUpperCase())) ;

    }

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
        System.out.println("hi");
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




}
