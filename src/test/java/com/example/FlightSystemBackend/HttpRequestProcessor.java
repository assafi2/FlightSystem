package com.example.FlightSystemBackend;

import com.example.FlightSystemBackend.Controller.CustomerController;
import com.example.FlightSystemBackend.DAO.*;
import com.example.FlightSystemBackend.PersistantDomainObjects.Administrator;
import com.example.FlightSystemBackend.PersistantDomainObjects.Country;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HttpRequestProcessor {

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest.Builder requestBuilder = HttpRequest.newBuilder() ;
    GsonBuilder gsonBuilder = new GsonBuilder();


    public HttpRequestProcessor(){

        gsonBuilder.setPrettyPrinting();

    }


    public <T> T processGETRequest(String uri, Class<T> clazz) {


        HttpRequest request = requestBuilder.uri(URI.create(uri)) 
                .build();

        HttpResponse<String> response = null;

        try {
            response =client.send(request,HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Gson gson = gsonBuilder.create();

        try {
            return gson.fromJson(response.body(), clazz);
        }
        catch (NullPointerException e) {
            throw e ;
        }
    }

    // curret implementation set to work with only 1 request body parameter,
    // should be modified to work with multiple request body parameters
    public <T> T processPOSTRequest(String uri, Class<T> clazz, List<Object> bodyParameters) {

        Gson gson = gsonBuilder.create() ;


        HttpRequest request = requestBuilder.uri(URI.create(uri))
                .headers("Content-Type", "application/json")
                // should be modified to use multiple body params
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(bodyParameters.get(0))))
                .build();

        HttpResponse<String> response = null;

        try {
            response =client.send(request,HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return gson.fromJson(response.body(),clazz);

    }

/*
    public static void main(String[] args) {
        var h = new HttpRequestProcessor() ;
        Gson gson = h.gsonBuilder.create() ;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDate localDate = LocalDateTime.parse("2022-03-26 09:30",formatter).toLocalDate() ;
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()) ;
        System.out.println(date);
        System.out.println(gson.toJson(date)) ;
        System.out.println(gson.toJson(new Country(1,"Thailand"))) ;
        System.out.println(gson.toJson(CustomerController.TEMP_TOKEN));
        System.out.println(gson.toJson("assafi1")) ;
        System.out.println(gson.toJson("assafi111")) ;
        CustomerDAO cdao = new CustomerDAOJDBCImpl() ;
        FlightDAO fdao = new FlightDAOJDBCImpl() ;
        System.out.println(gson.toJson(cdao.get(1))) ;
        System.out.println(fdao.get(2)) ;
        DAO<Administrator> addao = new AdministratorDAOJDBCImpl() ;
        System.out.println(gson.toJson(addao.get(3))) ;
        List<Object> list =  new ArrayList<Object>() ;
        list.add(CustomerController.TEMP_TOKEN) ;

        String jSonString = gson.toJson(list) ;
        System.out.println(jSonString);
    }
*/
}

