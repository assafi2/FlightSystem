package com.example.FlightSystemBackend;

import com.example.FlightSystemBackend.PersistantDomainObjects.Country;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FlightSystemBackendApplicationTests {

	@Test
	void contextLoads() {
	}



	@Test
	void getCountryFromAnonymousControllerTest(){


		HttpGetRequest httpGetRequest = new HttpGetRequest() ;
		var current = httpGetRequest.getJsonFromUri("http://localhost:8080/anonymous/countries/1", Country.class)  ;

		var expected = new Country(1,"israel");

		System.out.println(current);
	}

}
