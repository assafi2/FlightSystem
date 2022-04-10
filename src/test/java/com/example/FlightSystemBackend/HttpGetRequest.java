package com.example.FlightSystemBackend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpGetRequest {

    HttpClient client = HttpClient.newHttpClient();
    GsonBuilder builder = new GsonBuilder();


    public HttpGetRequest(){

        builder.setPrettyPrinting();

    }

    public <T> T getJsonFromUri(String uri, Class<T> clazz) {


        HttpRequest request = HttpRequest
                .newBuilder(URI.create(uri))
                .build();

        HttpResponse<String> response = null;

        try {
            response =client.send(request,HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Gson gson = builder.create();

        return gson.fromJson(response.body(),clazz);

    }
}
