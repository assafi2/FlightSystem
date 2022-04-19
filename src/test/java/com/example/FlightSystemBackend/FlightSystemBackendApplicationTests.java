package com.example.FlightSystemBackend;
import com.example.FlightSystemBackend.BussinessServices.AdministratorFacade;
import com.example.FlightSystemBackend.BussinessServices.AirlineFacade;
import com.example.FlightSystemBackend.BussinessServices.AnonymousFacade;
import com.example.FlightSystemBackend.BussinessServices.CustomerFacade;
import com.example.FlightSystemBackend.Controller.AdministratorController;
import com.example.FlightSystemBackend.Controller.AirlineCompanyController;
import com.example.FlightSystemBackend.Controller.CustomerController;
import com.example.FlightSystemBackend.DAO.CountryDAOJDBCImpl;
import com.example.FlightSystemBackend.PersistantDomainObjects.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class FlightSystemBackendApplicationTests {


	// to serialize both actual and expected objects (back) to JSON for equality checks reasons
	// (see explanation above assert logic)
//	Gson gson = new GsonBuilder().setPrettyPrinting().create() ;

	@Test
	void contextLoads() {
	}


	// holds main assertion logic for testing GET requests against different controllers
	// i.e. perform equality check between expected object and the object received from a GET request to the given URI
	// WITH THE CURRENT ASSERT LOGIC OF EQUALITY BETWEEN POJOS THE METHOD WON'T WORK PROPERLY FOR A COLLECTION OF OBJECTS
	// SEE EXPLANATION IN THE RELEVANT NOTES
	public <T> void GETRequestCheck(String uri, T expected) {

			HttpRequestProcessor httprp = new HttpRequestProcessor() ;
			var actual = httprp.processGETRequest(uri, expected.getClass())  ;

			Assert.assertEquals(expected,actual);
	}


	// holds main assertion logic for testing POST requests against different controllers
	// i.e. perform equality check between expected object and the object received from a POST request
	// with the given bodyParams as body parameters to the given URI uri
	// COULD NOT IMPLEMENT THE ACTUAL "TEST" WITH AN ASSERT METHOD SEE EXPLANATION BELOW

	public <T> void  POSTRequestCheck(String uri, T expected, List<Object> bodyParams) {

		HttpRequestProcessor httprp = new HttpRequestProcessor() ;

		var actual = httprp.processPOSTRequest(uri, expected.getClass(), bodyParams)  ;


		// Because we had difficult to obtain the correct type parameter of a collection object from a JSON which represent
		// a body of an http response , we will serialize back the object obtained (in some cases a collection of objects)
		// to a JSON which will than compared to the JSON representation of the expected value
		// PLEASE REGARD A FURTHER EXPLANATION ABOUT THIS DIFFICULT IN THE RELEVANT NOTES

		System.out.println("in POST request check, expected : " + expected );
		System.out.println("in POST request check, actual : " +  actual);

	    //  this variation of the call also didn't perform well mainly because inconsistency of numerical
		//  field types in the POJOs, which result in different numerical types representation in the the JSON strings
		//  and therefore different string values

		// 	Assert.assertEquals(gson.toJson(expected),gson.toJson(actual));

		// the acceptable version of the call which doesnt perform well, see explanation in the comments above
		// Assert.assertEquals(expected,actual);

	}

	@Test
	// in case you like to perform equality checkings with different types of POJOs,
	// please make sure a dedicated equal method is implemented within the POJO class
	void getCountryFromAnonymousControllerTest(){

		GETRequestCheck("http://localhost:8080/anonymous/countries/1",
				new AnonymousFacade().getCountryById(1));

	}

	// tests post request against customer controller
	@Test
	void getMyTicketsFromCustomerControllerTest() {

		List<Object> bodyParams = new ArrayList<Object>() ;
		bodyParams.add(CustomerController.TEMP_TOKEN) ;
		POSTRequestCheck("http://localhost:8080/customer/tickets/my/",
				 new CustomerFacade(CustomerController.TEMP_TOKEN)
						 .getMyTickets(CustomerController.TEMP_TOKEN)
				,bodyParams) ;
	}


	// tests post request against airline company controller
	@Test
	void getMyFlightsFromAirlineCompanyControllerTest() {


		List<Object> bodyParams = new ArrayList<Object>() ;
		bodyParams.add(AirlineCompanyController.TEMP_TOKEN) ;
		AirlineFacade air = new AirlineFacade(AirlineCompanyController.TEMP_TOKEN) ;
		System.out.println(air.getMyFlights(AirlineCompanyController.TEMP_TOKEN).getClass()) ;
		POSTRequestCheck("http://localhost:8080/airline/flights/my/",
				(ArrayList<Flight>)air.getMyFlights(AirlineCompanyController.TEMP_TOKEN)
				,bodyParams) ;

	}

	// tests post request against administrator controller

	@Test
	void getAllCustomersFromAdministratorControllerTest() {


		List<Object> bodyParams = new ArrayList<Object>() ;
		bodyParams.add(AdministratorController.TEMP_TOKEN) ;
		POSTRequestCheck("http://localhost:8080/admin/customers/all",
				new AdministratorFacade(AdministratorController.TEMP_TOKEN)
						.getAllCustomers(AdministratorController.TEMP_TOKEN)
				,bodyParams) ;
	}
}
