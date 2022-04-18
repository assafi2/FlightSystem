package com.example.FlightSystemBackend;

import com.example.FlightSystemBackend.Controller.AdministratorController;
import com.example.FlightSystemBackend.Controller.AirlineCompanyController;
import com.example.FlightSystemBackend.Controller.CustomerController;
import com.example.FlightSystemBackend.PersistantDomainObjects.Country;
import com.example.FlightSystemBackend.PersistantDomainObjects.Customer;
import com.example.FlightSystemBackend.PersistantDomainObjects.Flight;
import com.example.FlightSystemBackend.PersistantDomainObjects.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class FlightSystemBackendApplicationTests {


	@Test
	void contextLoads() {
	}


	// holds main assertion logic for testing POST requests against different controllers
	// i.e. perform equality check between expected object and the object received from a GET request to the given URI

	public <T> void GETRequestCheck(String uri, T expected) {

			HttpRequestProcessor httpGetRequest = new HttpRequestProcessor() ;
			var current = httpGetRequest.processGETRequest(uri, expected.getClass())  ;

			// TODO assert equal between current and expected

			System.out.println("current : " + current);
		}


	// holds main assertion logic for testing POST requests against different controllers
	// i.e. perform equality check between expected object and the object received from a POST request
	// with the given bodyParams as body parameters to the given URI uri

	public <T> void  POSTRequestCheck(String uri, T expected, List<Object> bodyParams) {

		HttpRequestProcessor httpGetRequest = new HttpRequestProcessor() ;
		var current = httpGetRequest.processPOSTRequest(uri, expected.getClass(), bodyParams)  ;

		// TODO assert equal between current and expected

		System.out.println("current : " + current);
	}

	@Test
	void getCountryFromAnonymousControllerTest(){

		GETRequestCheck("http://localhost:8080/anonymous/countries/1",new Country());

	}

	// tests post request against customer controller
	@Test
	void getMyTicketsFromCustomerControllerTest() {

		List<Object> bodyParams = new ArrayList<Object>() ;
		bodyParams.add(CustomerController.TEMP_TOKEN) ;
		POSTRequestCheck("http://localhost:8080/customer/tickets/my/)", new ArrayList<Ticket>(),bodyParams) ;
	}


	// tests post request against airline company controller
	@Test
	void getMyFlightsFromAirlineCompanyControllerTest() {


		List<Object> bodyParams = new ArrayList<Object>() ;
		bodyParams.add(AirlineCompanyController.TEMP_TOKEN) ;
		POSTRequestCheck("http://localhost:8080/airline/flights/my/)", new ArrayList<Flight>(),bodyParams) ;

	}

	// tests post request against administrator controller

	@Test
	void getAllCustomersFromAdministratorControllerTest() {


		List<Object> bodyParams = new ArrayList<Object>() ;
		bodyParams.add(AdministratorController.TEMP_TOKEN) ;
		POSTRequestCheck("http://localhost:8080/admin//customers/all)", new ArrayList<Customer>(),bodyParams) ;

	}

}
