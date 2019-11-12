package com.countries.Helpers;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import com.countries.countriesAPI.Country;
import com.countries.countriesAPI.Language;
import com.google.gson.Gson;

public class HttpClientHandler {

    public static void main(String[] args) {
        
        Client client = ClientBuilder.newClient();
        Response res = client.target("https://restcountries.eu/rest/v2/all").request("application/json").get();
        
        String s = res.readEntity(String.class);

        Gson gson  = new Gson();
        Country[] countriesArray = gson.fromJson(s, Country[].class);
        List<Country> countries = new ArrayList<Country>(Arrays.asList(countriesArray));
       
    }
   
}