import static org.junit.Assert.assertTrue;

import com.countries.countriesAPI.models.Country;
import com.countries.countriesAPI.models.Language;

import org.junit.Before;
import org.junit.Test;

public class CountryTests {
    private Country country;
    @Before
    public void setup(){
        country = new Country();
        country.setName("Paraland");
        country.setCapital("ParaCapital");;
        country.setPopulation(123456789);
        country.setRegion("Europe");

    }
    

    @Test
    public void canAddLanguagesTest(){
        Language l = new Language();
        l.setIso639_1("iso639_1");
        l.setIso639_2("iso639_2");
        l.setName("Parapara");
        l.setNativeName("nativeName");

        country.addLanguage(l);
        assertTrue("check first language can be added", country.getLanguages().size()==1);

        country.addLanguage(l);
        assertTrue("check second language can be added", country.getLanguages().size()==2);

    }
}