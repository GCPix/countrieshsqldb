import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.countries.Helpers.HCHandler;

public class HCHandlerTests {
    @Before
    public void setup() throws Exception{
        // String url = "https://restcountries.eu/rest/v2/all";
        // HCHandler hch = new HCHandler();
    }

    @Test    
    public void CanAccessURLTest(){
        String url = "https://restcountries.eu/rest/v2/all";
        HCHandler hch = new HCHandler();
        String jsonData = hch.getAPIData(url);
        assertTrue(jsonData.length()>0);
    }


}