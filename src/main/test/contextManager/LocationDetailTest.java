package unit.support;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import support.LocationDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class LocationDetailTest {

    private final List<String> input;
    private final String name;
    private final String location;
    private final String info;
    private final List<String> services;
    private final String message;

    public LocationDetailTest(List<String> input, String name, String location, String info, List<String> services, String message){
        this.input = input;
        this.name = name;
        this.location = location;
        this.info = info;
        this.services = services;
        this.message = message;
    }

    static List<String> corrStr = Arrays.asList("name: Vivo City Shopping Centre",
            "location: A",
            "information: Vivo City Shopping Centre is a major regional shopping centre in the southern suburb of Ho" +
                    " Chi Minh City, Vietnam. It is the second largest shopping centre in the southern suburbs of Ho" +
                    " Chi Minh City, by gross area, and contains the only H&M store in that region.",
            "services: cinema, restaurants, pool, shops, bowling");

    static List<String> noNameStr = Arrays.asList("location: A",
            "information: Vivo City Shopping Centre is a major regional shopping centre in the southern suburb of Ho" +
                    " Chi Minh City, Vietnam. It is the second largest shopping centre in the southern suburbs of Ho" +
                    " Chi Minh City, by gross area, and contains the only H&M store in that region.",
            "services: cinema, restaurants, pool, shops, bowling");

    static List<String> noLocationStr = Arrays.asList("name: Vivo City Shopping Centre",
            "information: Vivo City Shopping Centre is a major regional shopping centre in the southern suburb of Ho" +
                    " Chi Minh City, Vietnam. It is the second largest shopping centre in the southern suburbs of Ho" +
                    " Chi Minh City, by gross area, and contains the only H&M store in that region.",
            "services: cinema, restaurants, pool, shops, bowling");

    static List<String> noInfoStr = Arrays.asList("name: Vivo City Shopping Centre",
            "location: A",
            "services: cinema, restaurants, pool, shops, bowling");

    static List<String> noServicesStr = Arrays.asList("name: Vivo City Shopping Centre",
            "location: A",
            "information: Vivo City Shopping Centre is a major regional shopping centre in the southern suburb of Ho" +
                    " Chi Minh City, Vietnam. It is the second largest shopping centre in the southern suburbs of Ho" +
                    " Chi Minh City, by gross area, and contains the only H&M store in that region.");
    static String corrName = "Vivo City Shopping Centre";
    static String corrLocation = "A";
    static String corrInfo = "Vivo City Shopping Centre is a major regional shopping centre in the southern suburb of Ho" +
            " Chi Minh City, Vietnam. It is the second largest shopping centre in the southern suburbs of Ho" +
            " Chi Minh City, by gross area, and contains the only H&M store in that region.";
    static List<String> corrServices = Arrays.asList("cinema", "restaurants", "pool", "shops", "bowling");

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] {
            {corrStr, corrName, corrLocation, corrInfo, corrServices,"test Location details with correct input format"},
            {noNameStr, null, null, null, null,"test Location details with lack of name, object must not be created"},
            {noLocationStr, null, null, null, null, "test Location details with lack of location, object must not be created"},
            {noInfoStr, corrName,corrLocation, null, corrServices, "test Location details with lack of location"},
            {noServicesStr, corrName,corrLocation, corrInfo, null, "test Location details with lack of services"}
        };

        return Arrays.asList(data);
    }

    @Test
    public void testLocationDetails(){
        System.out.println(message);
        LocationDetails testLocationDetails = new LocationDetails(input);
        assertEquals(message, name, testLocationDetails.getName());
        assertEquals(message, location, testLocationDetails.getLocation());
        assertEquals(message, info, testLocationDetails.getInfo());
        assertEquals(message, services, testLocationDetails.getServices());
    }
}