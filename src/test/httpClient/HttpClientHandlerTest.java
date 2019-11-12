package test.httpClient;


import javax.validation.constraints.AssertTrue;

public class HttpClientHandlerTest {
    @Before 
    public void setup() throws Exception{
        String url = "https://restcountries.eu/rest/v2/all";
        HttpClientHandler hch = new HttpClientHandler();
    }

    @Test    
    public void canAccessURL(){
        String jsonData = hch.getAPIData(url);
        AssertTrue(jsonData.length()>0);
    }

}