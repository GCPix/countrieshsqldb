package test.java;

import static org.junit.Assert.assertTrue;

import com.countries.Helpers.HCHandler;

import org.junit.Before;
import org.junit.Test;

public class HCHandlerTests {
    @Before 
    public void setup() throws Exception{
   
    }

    @Test    
    public void CanAccessURLTest(){
        String url = "https://restcountries.eu/rest/v2/all";
        HCHandler hch = new HCHandler();
        String jsonData = hch.getAPIData(url);
        assertTrue(jsonData.length()>0);
    }
}