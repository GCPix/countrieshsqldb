package com.countries.Helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import com.countries.countriesAPI.Country;
import com.google.gson.Gson;

public class HCHandler {
    public String getAPIData(String  url){
        String jsonString = null;
         Client client = ClientBuilder.newClient();
         Response res = client.target(url).request("application/json").get();
         if (res.getStatus() == 200 && res.hasEntity()){
            jsonString = res.readEntity(String.class);
        }
         return jsonString;
    }
    // Think this should be in a class of it's own and would call the HCHandler but leaving here for now.
    public List<Country> jsonToCountry(String jsonString){
        Gson gson  = new Gson();
        Country[] countriesArray = gson.fromJson(jsonString, Country[].class);
        List<Country> countries = new ArrayList<Country>(Arrays.asList(countriesArray));
        return countries;
    }    
    

    public static void main(String[] args) {
        HCHandler hpc = new HCHandler();
        hpc.jsonToCountry(hpc.getAPIData("https://restcountries.eu/rest/v2/all"));
       
    }
   
}